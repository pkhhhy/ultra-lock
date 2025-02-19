package com.ultralock.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-19
 */
@Getter
public enum ReadWriteLockTypeEnum {

    READ(1, "读锁"),
    WRITE(2, "写锁"),
    UNKNOWN(404, "未知"),
    ;


    private Integer type;

    private String desc;

    ReadWriteLockTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static ReadWriteLockTypeEnum getEnumByType(Integer type) {
        for (ReadWriteLockTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}