package githubdns.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import githubdns.conf.DefaultConfig;
import githubdns.constant.QueryTypeEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gaozijie
 * @since 2023-08-15
 */
public class DnsQueryService {

    private final static LogService LOG = new LogService();

    /**
     * 获取域名-ip地址对应的Map
     * @return 域名-ip地址对应的Map
     */
    public Map<String, List<String>> getDomainIpMap() {
        LOG.info("#### 查询域名ip地址... ####");
        List<String> domainNames = DefaultConfig.domains;
        Map<String, List<String>> domainIpMap = new HashMap<>(16);
        for (String domainName : domainNames) {
            List<String> ips = this.getIps(domainName);
            LOG.info(String.format("%s -> %s", domainName, ips));
            domainIpMap.put(domainName, ips);
        }
        return domainIpMap;
    }

    /**
     * 获取ip地址
     * @param domainName 域名
     * @return ip地址集合
     */
    private List<String> getIps(String domainName) {
        String htmlText = this.getHtmlText(domainName);
        if (Objects.equals(DefaultConfig.queryType, QueryTypeEnum.IP_TOOL)) {
            return this.getIpByIpTool(htmlText);
        } else if (Objects.equals(DefaultConfig.queryType, QueryTypeEnum.IP_ADDRESS)) {
            return this.getIpByIpAddress(htmlText);
        }
        return Collections.emptyList();
    }

    /**
     * 获取查询域名ip地址页面文本
     * @param dnsDomainName 域名
     * @return html文本
     */
    private String getHtmlText(String dnsDomainName) {
        HttpRequest request = HttpUtil.createGet(String.format(DefaultConfig.queryUrl, dnsDomainName));
        HttpResponse response = request.execute();
        return response.body();
    }

    /**
     * 通过ipTool获取ip地址
     * @param htmlText html文本
     * @return ip地址集合
     */
    @Deprecated
    private List<String> getIpByIpTool(String htmlText) {
        // 暂时失效
        Document doc = Jsoup.parse(htmlText);
        Elements elements = doc.getElementsByClass("WhwtdWrap bor-b1s col-gray03");
        if (CollUtil.isEmpty(elements)) {
            return new ArrayList<>();
        }
        return elements.stream().map(x -> x.child(1).text()).collect(Collectors.toList());
    }

    /**
     * 通过ipAddress获取ip地址
     * @param htmlText html文本
     * @return ip地址集合
     */
    private List<String> getIpByIpAddress(String htmlText) {
        Document doc = Jsoup.parse(htmlText);
        Elements elements = doc.selectXpath("//*[@id=\"tabpanel-dns-a\"]/pre/a");
        if (CollUtil.isEmpty(elements)) {
            return new ArrayList<>();
        }
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }
}
