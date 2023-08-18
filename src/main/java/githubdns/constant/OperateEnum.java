package githubdns.constant;

import java.util.Objects;

/**
 * @author gaozijie
 * @date 2023-08-17
 */
public enum OperateEnum {
    /**
     * 重写host
     */
    REWRITE(1),
    /**
     * 清除host
     */
    CLEAN(2),
    /**
     * 退出
     */
    EXIT(3);

    private final Integer num;

    OperateEnum(Integer num) {
        this.num = num;
    }

    public static OperateEnum getInstance(Integer num) {
        for (OperateEnum operateEnum : OperateEnum.values()) {
            if (Objects.equals(operateEnum.num, num)) {
                return operateEnum;
            }
        }
        return null;
    }
}
