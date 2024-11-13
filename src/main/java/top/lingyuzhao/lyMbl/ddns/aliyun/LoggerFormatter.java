package top.lingyuzhao.lyMbl.ddns.aliyun;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 日志格式化
 *
 * @author zhao
 */
public class LoggerFormatter extends Formatter {

    private static final String LEVEL_PREFIX = "[";
    private static final String LEVEL_SUFFIX = "] ";
    private static final String TIME_PREFIX = "[";
    private static final String TIME_SUFFIX = "] ";
    private static final String MESSAGE_SUFFIX = "\n";

    @Override
    public String format(LogRecord record) {
        return LEVEL_PREFIX +
                record.getLevel() +
                LEVEL_SUFFIX +
                TIME_PREFIX +
                record.getMillis() +
                TIME_SUFFIX +
                record.getMessage() +
                MESSAGE_SUFFIX;
    }
}