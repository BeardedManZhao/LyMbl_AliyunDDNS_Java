package top.lingyuzhao.lyMbl.ddns.aliyun;

import com.alibaba.fastjson2.JSONObject;
import top.lingyuzhao.utils.IOUtils;
import top.lingyuzhao.utils.PublicIpFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 阿里云的 DDNS 管理器 其可以实现域名解析，支持 AAAA 和 A 类型
 *
 * @author lingyuzhao
 */
public class AliYunDDnsManager implements DDnsManager {

    private final Logger logger;
    private final String domain;
    private final String RR;
    private final ExecutorService executorService;
    private String backIpv4, backIpv6, ipv4DnsId, ipv6DnsId;

    /**
     * 构造函数
     *
     * @param logger 日志对象
     * @param domain 域名
     * @param RR     被操作的子域名
     */
    public AliYunDDnsManager(Logger logger, String domain, String RR) {
        this.logger = logger;
        this.domain = domain;
        this.RR = RR;
        this.executorService = Executors.newFixedThreadPool(2);
        initIp();
    }

    @Override
    public String getManagerName() {
        return "AliYunDDnsManager";
    }

    @Override
    public void start(long updateTimeMS) {
        logger.info("DDNS running updateTimeMS=" + updateTimeMS);
        logger.info(this.getLogo());
        while (true) {
            try {
                updateIPs();
            } catch (Exception e) {
                logger.severe("更新 IP 时发生错误: " + e.getMessage());
            }
            try {
                Thread.sleep(updateTimeMS);
            } catch (InterruptedException e) {
                logger.warning("线程中断: " + e.getMessage());
            }
        }
    }

    public void initIp() {
        try {
            final Process exec = Runtime.getRuntime().exec("aliyun alidns DescribeDomainRecords --DomainName " + domain);
            try (final InputStream inputStream = exec.getInputStream()) {
                final JSONObject jsonObject = JSONObject.parseObject(IOUtils.getStringByStream(inputStream));
                jsonObject.getJSONObject("DomainRecords").getJSONArray("Record").forEach(o -> {
                    if (o instanceof JSONObject && RR.equals(((JSONObject) o).getString("RR"))) {
                        final JSONObject o1 = (JSONObject) o;
                        final String value = o1.getString("Value");
                        logger.info("RR: " + RR + " value: " + value);
                        switch (o1.getString("Type")) {
                            case "A":
                                backIpv4 = value;
                                ipv4DnsId = o1.getString("RecordId");
                                logger.info("初始化 ipv4: " + backIpv4);
                                break;
                            case "AAAA":
                                backIpv6 = value;
                                ipv6DnsId = o1.getString("RecordId");
                                logger.info("初始化 ipv6: " + backIpv6);
                                break;
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("初始化失败！", e);
        }
    }

    public void updateIPs() throws Exception {
        // 获取公网 IPV4
        final String publicIpv4Address = PublicIpFetcher.getPublicIpv4Address();
        updateIp(RR, "A", ipv4DnsId, publicIpv4Address);

        // 获取公网 IPV6
        try {
            final String publicIpv6Address = PublicIpFetcher.getPublicIpv6Address();
            updateIp(RR, "AAAA", ipv6DnsId, publicIpv6Address);
        } catch (IOException e) {
            logger.warning("获取 ipv6 错误: " + e);
        }
    }

    public void updateIp(String RR, String type, String recordId, String ip) throws Exception {
        switch (type) {
            case "A":
                if (ip.equals(backIpv4)) {
                    logger.info("DDNS update type: " + type + " ip: " + ip + " same with last");
                    return;
                }
                backIpv4 = ip;
                break;
            case "AAAA":
                if (ip.equals(backIpv6)) {
                    logger.info("DDNS update type: " + type + " ip: " + ip + " same with last");
                    return;
                }
                backIpv6 = ip;
                break;
        }

        final String format = String.format("aliyun alidns UpdateDomainRecord --region cn-hangzhou --Lang '' --RR %s --Type %s --RecordId %s --Value %s", RR, type, recordId, ip).trim();
        final Process exec = Runtime.getRuntime().exec(format);
        logger.info(format);

        // 读取标准输出
        executorService.submit(() -> {
            try (InputStream inputStream = exec.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                logger.info("DDNS exec result: " + output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 读取标准错误输出
        executorService.submit(() -> {
            try (InputStream errorStream = exec.getErrorStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
                String line;
                StringBuilder errorOutput = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    errorOutput.append(line).append("\n");
                }
                if (errorOutput.length() > 0) {
                    logger.warning("DDNS exec error: " + errorOutput);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 等待子进程终止
        int exitValue = exec.waitFor();
        logger.info("子进程退出值: " + exitValue);
    }

    @Override
    public void close() {
        // 关闭线程池
        executorService.shutdown();
    }
}
