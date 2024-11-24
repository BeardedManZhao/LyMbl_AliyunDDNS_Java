package top.lingyuzhao.lyMbl.ddns.aliyun;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LyMbl_DDNS_Java {

    private static final Logger logger = Logger.getLogger("LyMbl_DDNS_Java");

    static {
        logger.setLevel(Level.INFO);
    }

    public static void main(String... args) {
        if (args.length < 4) {
            logger.warning("请输入参数：【logDir】【更新时间间隔（MS）】【RR参数：代表子域名字，如 www】【顶级域名：如 lingyuzhao.top】");
            return;
        }

        final String logDir = args[0];
        final long updateTimeMS = Long.parseLong(args[1]);
        final String RR = args[2];
        final String domain = args[3];

        // 创建 FileHandler 实例，指定日志文件路径和是否追加写入
        FileHandler fileHandler;
        try {
            final File file = new File(logDir);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    logger.info("创建日志目录成功！");
                }
            }
            fileHandler = new FileHandler(logDir + "/LyMbl_DDNS_Java.log", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new LoggerFormatter());
        logger.addHandler(fileHandler);

        try (AliYunDDnsManager dDnsManager = new AliYunDDnsManager(logger, domain, RR)) {
            dDnsManager.start(updateTimeMS);
        }
    }
}