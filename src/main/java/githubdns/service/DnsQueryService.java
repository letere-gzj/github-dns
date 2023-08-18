package githubdns.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import githubdns.conf.DefaultConfig;
import githubdns.constant.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gaozijie
 * @date 2023-08-15
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
            String htmlText = this.getHtmlText(domainName);
            List<String> ips = this.getIpByHtml(htmlText);
            LOG.info(String.format("%s -> %s", domainName, ips));
            domainIpMap.put(domainName, ips);
        }
        return domainIpMap;
    }

    /**
     * 获取查询域名ip地址页面文本
     * @param dnsDomainName 域名
     * @return html文本
     */
    private String getHtmlText(String dnsDomainName) {
        HttpRequest request = HttpUtil.createGet(String.format(Constant.QUERY_URL, dnsDomainName));
        HttpResponse response = request.execute();
        return response.body();
    }

    /**
     * 爬虫(jsoup)解析文本，获取ip地址
     * @param htmlText html文本
     * @return ip地址集合
     */
    private List<String> getIpByHtml(String htmlText) {
        Document doc = Jsoup.parse(htmlText);
        Elements elements = doc.getElementsByClass("WhwtdWrap bor-b1s col-gray03");
        if (CollUtil.isEmpty(elements)) {
            return new ArrayList<>();
        }
        return elements.stream().map(x -> x.child(1).text()).collect(Collectors.toList());
    }
}
