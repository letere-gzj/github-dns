package githubdns.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author gaozijie
 * @date 2023-08-15
 */
public interface Constant {

    List<String> GITHUB_DOMAIN_NAMES = Arrays.asList(
            "github.com",
            "github.global.ssl.fastly.net",
            "assets-cdn.github.com",
            "github.githubassets.com"
    );

    String QUERY_URL = "https://ip.tool.chinaz.com/%s";

    String DATE_FORMAT_PATTEN = "yyyy-MM-dd HH:mm:ss";

    String HOST_PREFIX = "# github speedup start";

    String HOST_SUFFIX = "# github speedup end";

    String HOST_FILE_PATH = "C:\\Windows\\System32\\drivers\\etc\\hosts";

    String REFRESH_DNS_CMD = "ipconfig /flushdns";
}
