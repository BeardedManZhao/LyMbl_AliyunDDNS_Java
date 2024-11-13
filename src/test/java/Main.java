import top.lingyuzhao.lyMbl.ddns.aliyun.AliYunDDnsManager;

import java.util.logging.Logger;

/**
 * @author zhao
 */
public class Main {
    public static void main(String[] args) {
        final Logger test = Logger.getLogger("test");
        try (
                // 实例化 DDNS 管理器
                final AliYunDDnsManager aliYunDDnsManager = new AliYunDDnsManager(
                        // 传递日志对象 以及域名信息
                        test, "baidu.com", "www"
                )
        ) {
            // 启动 每3600s 检查一次 DNS 信息
            aliYunDDnsManager.start(3600000);
        }
    }
}
