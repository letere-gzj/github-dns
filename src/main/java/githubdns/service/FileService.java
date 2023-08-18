package githubdns.service;

import cn.hutool.core.io.FileUtil;
import githubdns.conf.DefaultConfig;
import githubdns.constant.Constant;

import java.util.List;
import java.util.Map;

/**
 * @author gaozijie
 * @date 2023-08-15
 */
public class FileService {

    private final LogService LOG = new LogService();

    private final String PATTERN = "\n" + Constant.HOST_PREFIX + "\n" + "(.*\\s)*" + Constant.HOST_SUFFIX + "\n";

    /**
     * 重写host文件
     * @param domainIpMap 域名ip映射map
     * @throws Exception
     */
    public void rewriteHost(Map<String, List<String>> domainIpMap) throws Exception{
        LOG.info("#### 重写host文件... ####");
        String content = FileUtil.readUtf8String(DefaultConfig.hostsPath);
        String hostText = this.buildHostText(domainIpMap);
        content = this.buildHostContent(content, hostText);
        FileUtil.writeUtf8String(content, DefaultConfig.hostsPath);
    }

    /**
     * 清除写入host文件的内容
     */
    public void cleanHost() {
        LOG.info("#### 清除写入host文件内容... ####");
        String content = FileUtil.readUtf8String(DefaultConfig.hostsPath);
        content = content.replaceAll(PATTERN, "");
        FileUtil.writeUtf8String(content, DefaultConfig.hostsPath);
    }

    /**
     * 构建写入host文件的脚本
     * @param domainIpMap 域名->ip地址Map
     * @return host文件的脚本
     */
    private String buildHostText(Map<String, List<String>> domainIpMap) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append(Constant.HOST_PREFIX).append("\n");
        for (Map.Entry<String, List<String>> entry : domainIpMap.entrySet()) {
            for (String ip : entry.getValue()) {
                builder.append(ip).append(" ").append(entry.getKey()).append("\n");
            }
        }
        builder.append(Constant.HOST_SUFFIX).append("\n");
        return builder.toString();
    }

    /**
     * 构建host文件内容
     * @param content host原内容
     * @param hostText host新内容
     * @return host文件内容
     */
    private String buildHostContent(String content, String hostText) {
        boolean isReplace = content.contains(Constant.HOST_PREFIX)
                && content.contains(Constant.HOST_SUFFIX);
        String hostContent;
        if (isReplace) {
            // 正则匹配替换ip映射内容
            hostContent = content.replaceAll(PATTERN, hostText);
        } else {
            // 尾处添加ip映射内容
            hostContent = content + hostText;
        }
        return hostContent;
    }
}
