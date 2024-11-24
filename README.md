![图片1](https://github.com/user-attachments/assets/a72c4694-9725-42ed-bd44-abcd09939d64)

# LyMbl_AliyunDDNS_Java

aliyun DNS 解析的 DDNS Java 软件，可以实现自动获取 ipv4 和 ipv6 公网 然后在阿里云进行 ddns 操作！

## 操作之前的准备工作

### 安装 aliyun 客户端

> 您需要下载和安装 aliyun 的客户端软件哦！

安装好 aliyun 客户端之后，您可以使用 `aliyun` 命令，然后会像下面一样打印出很多的日志，这个时候，前期准备任务就算是成功了！

```
阿里云CLI命令行工具 3.0.231

Usage:
  aliyun <product> <operation> [--parameter1 value1 --parameter2 value2 ...]

Commands:
  configure       配置身份认证和其他信息
  oss             阿里云OSS对象存储
  auto-completion 启用自动完成

Flags:
  --mode               使用 `--mode {AK|StsToken|RamRoleArn|EcsRamRole|RsaKeyPair|RamRoleArnWithRoleName}` 指定认证方式
  --profile,-p         使用 `--profile <profileName>` 指定操作的配置集
  --language           使用 `--language [en|zh]` 来指定语言
  --region             使用 `--region <regionId>` 来指定访问大区
  --config-path        使用 `--config-path` 指定配置文件路径
  --access-key-id      使用 `--access-key-id <AccessKeyId>` 指定AccessKeyId
  --access-key-secret  使用 `--access-key-secret <AccessKeySecret>` 指定AccessKeySecret
  --sts-token          使用 `--sts-token <StsToken>` 指定StsToken
  --sts-region         使用 `--sts-region <StsRegion>` 指定StsRegion
  --ram-role-name      使用 `--ram-role-name <RamRoleName>` 指定RamRoleName
  --ram-role-arn       使用 `--ram-role-arn <RamRoleArn>` 指定RamRoleArn
  --role-session-name  使用 `--role-session-name <RoleSessionName>` 指定RoleSessionName
  --private-key        使用 `--private-key <PrivateKey>` 指定RSA私钥
  --key-pair-name      使用 `--key-pair-name <KeyPairName>` 指定KeyPairName
  --read-timeout       使用 `--read-timeout <seconds>` 指定I/O超时时间(秒)
  --connect-timeout    使用 `--connect-timeout <seconds>` 指定请求连接超时时间(秒)
  --retry-count        使用 `--retry-count <count>` 指定重试次数
  --skip-secure-verify 使用 `--skip-secure-verify` 跳过https的证书校验 [不推荐使用]
  --expired-seconds    使用 `--expired-seconds <seconds>` 指定凭证过期时间
  --process-command    使用 `--process-command <ProcessCommand>` 指定外部程序运行命令
  --oidc-provider-arn  使用 `--oidc-provider-arn <OIDCProviderARN>` 来指定 OIDC 提供者 ARN
  --oidc-token-file    使用 `--oidc-token-file <OIDCTokenFile>` 来指定 OIDC Token 文件路径
  --secure             使用 `--secure` 开关强制使用https方式调用
  --force              添加 `--force` 开关可跳过API与参数的合法性检查
  --endpoint           使用 `--endpoint <endpoint>` 来指定接入点地址
  --version            使用 `--version <YYYY-MM-DD>` 来指定访问的API版本
  --header             使用 `--header X-foo=bar` 来添加特定的HTTP头, 可多次添加
  --body               使用 `--body $(cat foo.json)` 来指定在RESTful调用中的HTTP包体
  --pager              使用 `--pager` 在访问分页的API时合并结果分页
  --output,-o          使用 `--output cols=Field1,Field1 [rows=jmesPath]` 使用表格方式打印输出
  --waiter             使用 `--waiter expr=<jmesPath> to=<value>` 来轮询调用OpenAPI，直到返回期望的值
  --dryrun             使用 `--dryrun` 在执行校验后打印请求包体，跳过实际运行
  --quiet,-q           使用 `--quiet` 关闭正常输出
  --method             使用 `--method {GET|POST}` 来指定 RPC 请求的 Method
  --help               打印帮助信息

Sample:
  aliyun ecs DescribeRegions

Use `aliyun --help` for more information.

```

### 在阿里云的DNS解析创建您要解析的子域 DNS

这里我们可以直接前往 [阿里云 DNS 解析](https://wanwang.aliyun.com/domain/dns) 网页中进行 DNS 解析操作，在其中创建出 `A`
或者 `AAAA` 记录，并指向您的服务器 IP 地址（若您没有 ipv6 可以不指定 AAAA 解析类型）

IP地址的位置可以写服务器当前的 IP 地址，但是也可以写错误的，DDNS 程序会自动的将您的 IP 地址进行更改！只要新增了 DNS
解析记录就可以了

## 做为软件使用的 启动方法

### 启动 jar 程序

从仓库中下载好您需要的 jar 包之后，使用下面的命令启动即可！

```shell
java -jar ./LyMbl_AliyunDDNS_Java.jar [日志目录] [动态更新DNS间隔(ms) 不要太短,会导致性能浪费] [需要被动态更新的子域名(如 www)] [需要被动态更新的顶级域名(如 baidu.com)]
```

#### 启动示例

在这里我们展示了部分日志，且对其中的数据进行了脱敏，您启动之后看到类型下面的日志，则代表成功运行了！

```
# 这代表 以当前的 logs 目录为日志文件存储目录，并且每 3600000 ms 更新一次，子域名为 ***，顶级域名为 **********
root@gust-desktop:/opt/app/LyMbl_DDNS_Java# java -jar ./LyMbl_AliyunDDNS_Java.jar ./logs 3600000 *** **********

[INFO] [1731472913323] RR: lsb value: ****
[INFO] [1731472913325] 初始化 ipv6: ****
[INFO] [1731472913327] RR: lsb value: ****
[INFO] [1731472913329] 初始化 ipv4: ****
[INFO] [1731472913331] DDNS running updateTimeMS=3600000
[INFO] [1731472913333] AliYunDDnsManager
                                     ..,,,'';::::::;'',,,..
                              ..,';:::::;;::::::::::::;;:::::;',..
                          .,;:::;;::::::;;;::::::::::;;;::::::;;:::;,.
                       ,;::;:::::;::::;;',,,,......,,,,';;::::;:::::;::;,
                    ,;::;::::;::;',.                        .,';::;::::;::;,
                 .;::::::;::;,.                                  .,;::;::::::;.
               ,;:;::::::'.                    .                     .'::::::;:;,
             ,::::::;:;,           ..,,,,,,,,,,,,,.,,,,,,,..            ,;:;::::::,
           ,::::::::,.         .,,,,,,',,.     :,.    ,,,',.,,,..         .,::::::::,
          ;:::::::,         .,,,,.  .3,,.      :,.     ,','.  .,,,,,.        ,:::::::;
        ,:::::::'        .,,,,.    ,:,,        7,.      .:,,,    .,,,,.        ':::::::,
       ':::::::.       .,,,.      ,:,,         7,.        7,,,      .,,,.       .:::::::'
      ;::::::'       .,,,.       ,7,,          7,.         7,,,        ,,,.       '::::::;
     ;;:::::,       ,,,.        .l,,           :,.          :,,.        .',,       ,:::::;;
    ;;:::::,       ',,,.........o,,............:,,..........;','.,;;;;;;''b,,.      ,:::::;;
   '::::::,      .',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,      ,::::::'
                                                                                               
                     ░▒▒▒▒▒▒▒▒░     ▒▒▒▒▒▒▒▒▒░    ░▒▒▒░    ░▒▒░    ░▒▓▓▓▒▒                     
                     ░▓▓▓░░░▓▓▓▓▒   ▒▓▓▒░░▒▓▓▓▓   ░▓▓▓▓▒   ▒▓▓░  ░▓▓▓░  ░░      ░              
                     ░▓▓▓    ▒▓▓▓   ▒▓▓░   ░▓▓▓▒  ░▓▓▓▓▓▓░ ▒▓▓░  ░▓▓▓▓▒░                       
                     ░▓▓▓    ░▓▓▓   ▒▓▓░    ▒▓▓▒  ░▓▓▒ ▒▓▓▒▒▓▓░   ░▒▒▓▓▓▓▒                     
                     ░▓▓▓   ░▒▓▓▒   ▒▓▓▒   ▒▓▓▓   ░▓▓▒  ░▓▓▓▓▓░       ▒▓▓▓░     ░              
                     ░▓▓▓▓▓▓▓▓▒░    ▒▓▓▓▓▓▓▓▓▒    ░▓▓▒    ▒▓▓▓░  ░▓▓▒▒▓▓▓▒                     
  ,::::::'       ,,',,,,,,,,,,,3,,,,,,,,,,,,,,':,,,,,,,,,,,,,,7,,,,,,,,,,,,,:7,.      '::::::,
   '::::::,       ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.      ,::::::'
    ;;:::::,       .,,'.        ;,,.           :,.          ;,,,          ;,,       ,:::::;;
     ;;:::::,        ,,'.        7,,,          :,.         ,:,,         ;:,.       ,:::::;;
      ;::::::'        .,,,.       :,,.         7,.        ,3,,        ,:,,        '::::::;
       ':::::::.        .,,',      ;,,.        7,.       ,3,,      .::,..       .:::::::'
        ,:::::::'          .,,',.   :,,,       7,.      ':,,    .;7',.         ':::::::,
          ;:::::::,          .,,';;,.7,,'      7,.     3',. .'7:',.          ,:::::::;
           ,::::::::,.           ..,';',,;,....7,. ..,3,,':;,,..          .,::::::::,
             ,::::::;:;,              ..,,,,,'',,,'',,,,..              ,;:;::::::,
               ,;:;::::::'.                                          .'::::::;:;,
                 .;::::::;::;,.                                  .,;::;::::::;.
                    ,;::;::::;::;',.                        .,';::;::::;::;,
                       ,;::;:::::;::::;;',,,,......,,,,';;::::;:::::;::;,
                          .,;:::;;::::::;;;::::::::::;;;::::::;;:::;,.
                              ..,';:::::;;::::::::::::;;:::::;',..
[INFO] [1731472913915] DDNS update type: A ip: **** same with last
[INFO] [1731472914293] DDNS update type: AAAA ip: **** same with last

......
```

## 做为依赖包组件的使用方法

### 添加依赖

```xml

<dependency>
    <groupId>io.github.BeardedManZhao</groupId>
    <artifactId>LyMbl_AliyunDDNS_Java</artifactId>
    <version>2024.11.24</version>
</dependency>
```

### maven 组件包中的 main 函数调用

这样的方式中，您可以在您自己的main函数中调用 LyMbl_AliyunDDNS_Java 中的 main 函数，以启动服务。

```java
import top.lingyuzhao.lyMbl.ddns.aliyun.LyMbl_DDNS_Java;

/**
 * @author zhao
 */
public class Main {
    public static void main(String[] args) {
        // "请输入参数：【logDir】【更新时间间隔（MS）】【RR参数：代表子域名字，如 www】【顶级域名：如 baidu.com】"
        LyMbl_DDNS_Java.main("./logs", "3600000", "www", "baidu.com");
    }
}

```

### maven 组件包中的组件开发调用

这样的方式中，您可以在您自己的项目代码中装载我们的 D DNS 管理器，灵活性最高，下面是一个示例

```java
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
```

# 更新日志

## 2024-11-24

- 优化了域名解析操作中，日志的打印逻辑！！
