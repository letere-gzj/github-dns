package githubdns.constant;

/**
 * @author gaozijie
 * @date 2023-08-15
 */
public interface Constant {
    /**
     * 查询域名ip地址
     */
    String QUERY_URL = "https://ip.tool.chinaz.com/%s";
    /**
     * 日期格式化样式
     */
    String DATE_FORMAT_PATTEN = "yyyy-MM-dd HH:mm:ss";
    /**
     * hosts文件内容前缀
     */
    String HOST_PREFIX = "# github speedup start";
    /**
     * hosts文件内容后缀
     */
    String HOST_SUFFIX = "# github speedup end";
    /**
     * 刷新windows DNS 缓存命令
     */
    String REFRESH_DNS_CMD = "ipconfig /flushdns";
}
