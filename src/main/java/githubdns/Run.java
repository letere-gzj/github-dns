package githubdns;

import githubdns.constant.OperateEnum;
import githubdns.service.CmdService;
import githubdns.service.DnsQueryService;
import githubdns.service.FileService;
import githubdns.service.LogService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author gaozijie
 * @date 2023-08-15
 */
public class Run {
    private final static DnsQueryService dnsQueryService = new DnsQueryService();
    private final static FileService fileService = new FileService();
    private final static CmdService cmdService = new CmdService();
    private final static LogService LOG = new LogService();

    public static void main(String[] args) {
        try {
            runProcess();
        } catch (Exception e) {
            System.out.println("程序出错！！！原因为:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runProcess() throws Exception{
        System.out.print("操作( 1:重写host, 2:清除host写入内容, 3:退出 )：");
        Scanner scanner = new Scanner(System.in);
        OperateEnum operateEnum;
        while (true) {
            operateEnum = OperateEnum.getInstance(Integer.parseInt(scanner.nextLine()));
            if (operateEnum != null) {
                break;
            } else {
                System.out.print("输入有误，请重新输入：");
            }
        }
        switch (operateEnum) {
            case REWRITE : {
                rewriteHost();
                break;
            }
            case CLEAN : {
                cleanHost();
                break;
            }
            case EXIT :
            default : {return;}
        }
        LOG.info("#### 执行完毕!!! ####");
    }

    /**
     * 重写host
     * @throws Exception
     */
    private static void rewriteHost() throws Exception{
        // 查询域名ip映射
        Map<String, List<String>> domainIpMap = dnsQueryService.getDomainIpMap();
        // 重写host文件
        fileService.rewriteHost(domainIpMap);
        // 刷新dns缓存
        cmdService.refreshDnsCache();
    }

    /**
     * 清除host内容
     * @throws Exception
     */
    private static void cleanHost() throws Exception {
        // 清除写入host内容
        fileService.cleanHost();
        // 刷新dns缓存
        cmdService.refreshDnsCache();
    }
}
