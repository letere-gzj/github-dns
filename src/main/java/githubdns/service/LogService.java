package githubdns.service;

import githubdns.constant.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author gaozijie
 * @since 2023-08-16
 */
public class LogService {

    /**
     * 打印日志信息
     * @param info 信息
     */
    public void info(String info) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT_PATTEN)) + " --- " + info);
    }
}
