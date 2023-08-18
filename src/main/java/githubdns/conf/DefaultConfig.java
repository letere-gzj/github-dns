package githubdns.conf;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaozijie
 * @date 2023-08-18
 */
public class DefaultConfig {

    public static List<String> domains;
    public static String hostsPath;

    private final static String CONF_DOMAINS = "domains";
    private final static String CONF_HOSTS_PATH = "hosts_path";
    private final static String CONFIG_PATH = "./conf/default.conf";

    static {
        Map<String, String> config = loadConfig();
        domains = Arrays.asList(config.get(CONF_DOMAINS).split(", "));
        hostsPath = config.get(CONF_HOSTS_PATH);
    }

    /**
     * 加载配置
     * @return 配置文件映射
     */
    private static Map<String, String> loadConfig() {
        String confText = FileUtil.readUtf8String(new File(CONFIG_PATH));
        String[] confParams = confText.split("\r\n");
        Map<String, String> config = new HashMap<>(16);
        for (String confParam : confParams) {
            // 跳过注释
            if (confParam.startsWith("#")) {
                continue;
            }
            String[] params = confParam.split(": ");
            if (params.length != 2) {
                continue;
            }
            config.put(params[0], params[1]);
        }
        return config;
    }
}
