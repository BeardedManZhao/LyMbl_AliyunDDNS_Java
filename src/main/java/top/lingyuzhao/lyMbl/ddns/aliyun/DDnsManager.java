package top.lingyuzhao.lyMbl.ddns.aliyun;

/**
 * DDNS 功能处理器
 *
 * @author zhao
 */
public interface DDnsManager extends AutoCloseable {

        /*

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


     */

    /**
     * @return 在启动成功之后，有些管理器会打印出 logo
     */
    default String getLogo() {
        return this.getManagerName() + '\n' +
                "                                     ..,,,'';::::::;'',,,..\n" +
                "                              ..,';:::::;;::::::::::::;;:::::;',..\n" +
                "                          .,;:::;;::::::;;;::::::::::;;;::::::;;:::;,.\n" +
                "                       ,;::;:::::;::::;;',,,,......,,,,';;::::;:::::;::;,\n" +
                "                    ,;::;::::;::;',.                        .,';::;::::;::;,\n" +
                "                 .;::::::;::;,.                                  .,;::;::::::;.\n" +
                "               ,;:;::::::'.                    .                     .'::::::;:;,\n" +
                "             ,::::::;:;,           ..,,,,,,,,,,,,,.,,,,,,,..            ,;:;::::::,\n" +
                "           ,::::::::,.         .,,,,,,',,.     :,.    ,,,',.,,,..         .,::::::::,\n" +
                "          ;:::::::,         .,,,,.  .3,,.      :,.     ,','.  .,,,,,.        ,:::::::;\n" +
                "        ,:::::::'        .,,,,.    ,:,,        7,.      .:,,,    .,,,,.        ':::::::,\n" +
                "       ':::::::.       .,,,.      ,:,,         7,.        7,,,      .,,,.       .:::::::'\n" +
                "      ;::::::'       .,,,.       ,7,,          7,.         7,,,        ,,,.       '::::::;\n" +
                "     ;;:::::,       ,,,.        .l,,           :,.          :,,.        .',,       ,:::::;;\n" +
                "    ;;:::::,       ',,,.........o,,............:,,..........;','.,;;;;;;''b,,.      ,:::::;;\n" +
                "   '::::::,      .',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,      ,::::::'\n" +
                "                                                                                               \n" +
                "                     ░▒▒▒▒▒▒▒▒░     ▒▒▒▒▒▒▒▒▒░    ░▒▒▒░    ░▒▒░    ░▒▓▓▓▒▒                     \n" +
                "                     ░▓▓▓░░░▓▓▓▓▒   ▒▓▓▒░░▒▓▓▓▓   ░▓▓▓▓▒   ▒▓▓░  ░▓▓▓░  ░░      ░              \n" +
                "                     ░▓▓▓    ▒▓▓▓   ▒▓▓░   ░▓▓▓▒  ░▓▓▓▓▓▓░ ▒▓▓░  ░▓▓▓▓▒░                       \n" +
                "                     ░▓▓▓    ░▓▓▓   ▒▓▓░    ▒▓▓▒  ░▓▓▒ ▒▓▓▒▒▓▓░   ░▒▒▓▓▓▓▒                     \n" +
                "                     ░▓▓▓   ░▒▓▓▒   ▒▓▓▒   ▒▓▓▓   ░▓▓▒  ░▓▓▓▓▓░       ▒▓▓▓░     ░              \n" +
                "                     ░▓▓▓▓▓▓▓▓▒░    ▒▓▓▓▓▓▓▓▓▒    ░▓▓▒    ▒▓▓▓░  ░▓▓▒▒▓▓▓▒                     \n" +
                "  ,::::::'       ,,',,,,,,,,,,,3,,,,,,,,,,,,,,':,,,,,,,,,,,,,,7,,,,,,,,,,,,,:7,.      '::::::,\n" +
                "   '::::::,       ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.      ,::::::'\n" +
                "    ;;:::::,       .,,'.        ;,,.           :,.          ;,,,          ;,,       ,:::::;;\n" +
                "     ;;:::::,        ,,'.        7,,,          :,.         ,:,,         ;:,.       ,:::::;;\n" +
                "      ;::::::'        .,,,.       :,,.         7,.        ,3,,        ,:,,        '::::::;\n" +
                "       ':::::::.        .,,',      ;,,.        7,.       ,3,,      .::,..       .:::::::'\n" +
                "        ,:::::::'          .,,',.   :,,,       7,.      ':,,    .;7',.         ':::::::,\n" +
                "          ;:::::::,          .,,';;,.7,,'      7,.     3',. .'7:',.          ,:::::::;\n" +
                "           ,::::::::,.           ..,';',,;,....7,. ..,3,,':;,,..          .,::::::::,\n" +
                "             ,::::::;:;,              ..,,,,,'',,,'',,,,..              ,;:;::::::,\n" +
                "               ,;:;::::::'.                                          .'::::::;:;,\n" +
                "                 .;::::::;::;,.                                  .,;::;::::::;.\n" +
                "                    ,;::;::::;::;',.                        .,';::;::::;::;,\n" +
                "                       ,;::;:::::;::::;;',,,,......,,,,';;::::;:::::;::;,\n" +
                "                          .,;:::;;::::::;;;::::::::::;;;::::::;;:::;,.\n" +
                "                              ..,';:::::;;::::::::::::;;:::::;',..";
    }

    /**
     * @return D DNS Manager 名称
     */
    String getManagerName();

    /**
     * D DNS 启动函数
     *
     * @param updateTimeMS 更新时间间隔（MS）
     */
    void start(long updateTimeMS);
}
