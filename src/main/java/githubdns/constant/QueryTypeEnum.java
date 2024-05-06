package githubdns.constant;

import java.util.Objects;

/**
 * @author gaozijie
 * @since 2024-05-06
 */
public enum QueryTypeEnum {
    /**
     * ip工具
     */
    IP_TOOL("ipTool"),

    /**
     * ip地址
     */
    IP_ADDRESS("ipAddress");

    private final String value;

    QueryTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QueryTypeEnum init(String val) {
        for (QueryTypeEnum queryTypeEnum : QueryTypeEnum.values()) {
            if (Objects.equals(queryTypeEnum.value, val)) {
                return queryTypeEnum;
            }
        }
        return null;
    }
}
