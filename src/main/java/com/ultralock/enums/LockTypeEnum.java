package com.ultralock.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Getter
public enum LockTypeEnum {
    NO_FAIR(1, "不公平锁"),
    FAIR(2, "公平锁"),
    UNKNOWN(404, "未知");
    /**
     * 锁类型
     */
    private int type;

    /**
     * 描述
     */
    private String desc;

    LockTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static LockTypeEnum getEnumByType(Integer type) {
        for (LockTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
