# LyMbl_AliyunDDNS_Java

aliyun 服务器的 DDNS Java 软件，可以实现自动获取 ipv4 和 ipv6 公网 然后在阿里云进行 ddns 操作！

## 启动方法

> 您需要下载和安装 aliyun 的客户端软件哦！

```shell
java -jar ./LyMbl_AliyunDDNS_Java.jar [日志目录] [动态更新DNS间隔(ms)] [需要被动态更新的子域名(如 www)] [需要被动态更新的顶级域名(如 lingyuzhao.top)]
```

## 启动示例

在这里我们展示了部分日志，且对其中的数据进行了脱敏，您启动之后看到类型下面的日志，则代表成功运行了！

```
# 这代表 以当前的 logs 目录为日志文件存储目录，并且每 3600000 ms 更新一次，子域名为 ***，顶级域名为 **********
root@gust-desktop:/opt/app/LyMbl_DDNS_Java# java -jar ./LyMbl_AliyunDDNS_Java.jar ./logs 3600000 *** **********

十一月 12, 2024 10:53:55 下午 top.lingyuzhao.lyMbl.ddns.aliyun.LyMbl_DDNS_Java main
信息: 创建日志目录成功！
十一月 12, 2024 10:53:56 下午 top.lingyuzhao.lyMbl.ddns.aliyun.DDNSManager lambda$initIp$0
信息: RR: lsb value: ***********  
十一月 12, 2024 10:53:56 下午 top.lingyuzhao.lyMbl.ddns.aliyun.DDNSManager lambda$initIp$0
信息: 初始化 ipv6: ************
十一月 12, 2024 10:53:56 下午 top.lingyuzhao.lyMbl.ddns.aliyun.DDNSManager lambda$initIp$0
信息: RR: *** value: ************
十一月 12, 2024 10:53:56 下午 top.lingyuzhao.lyMbl.ddns.aliyun.DDNSManager lambda$initIp$0
信息: 初始化 ipv4: ************
十一月 12, 2024 10:53:56 下午 top.lingyuzhao.lyMbl.ddns.aliyun.DDNSManager start
信息: DDNS running updateTimeMS=3600000
十一月 12, 2024 10:53:57 下午 top.lingyuzhao.lyMbl.ddns.aliyun.DDNSManager updateIp
信息: DDNS update type: A ip: ************ same with last
......
```