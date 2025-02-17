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
public enum LockHandleTypeEnum {
    R_LOCK(1, "可重入锁"),
    MULTI_LOCK(2,"联合锁"),
    UNKNOWN(404, "未知"),
    ;


    private int type;
    private String desc;

    LockHandleTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static LockHandleTypeEnum getEnumByType(Integer type) {
        for (LockHandleTypeEnum value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
