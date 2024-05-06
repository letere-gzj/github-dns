package githubdns.service;

import cn.hutool.core.util.CharsetUtil;
import githubdns.constant.Constant;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author gaozijie
 * @since 2023-08-17
 */
public class CmdService {

    private final static LogService LOG = new LogService();

    /**
     * 刷新dns缓存
     */
    public void refreshDnsCache() throws Exception{
        LOG.info("#### 刷新DNS缓存... ####");
        this.executeCmd(Constant.REFRESH_DNS_CMD);
    }

    /**
     * 执行cmd命令
     * @param cmd cmd命令
     * @throws Exception
     */
    private void executeCmd(String cmd) throws Exception{
        // 执行命令，window系统需要额外配置
        Process process = Runtime.getRuntime().exec("cmd /c " + cmd);
        // 打印控制台信息
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), CharsetUtil.CHARSET_GBK));
        String line;
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }
}
