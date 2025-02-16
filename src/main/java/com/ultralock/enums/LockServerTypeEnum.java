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
public enum LockServerTypeEnum {

    SINGLE("single", "单节点"),
    CLUSTER("cluster", "集群"),
    MASTER_SALVE("masterSlave", "主从"),

    UNKONWN("unkonwn", "未知"),
    ;

    /**
     * 服务类型
     */
    private String type;
    /**
     * 描述
     */
    private String desc;

    LockServerTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static LockServerTypeEnum getEnumByType(String type) {
        for (LockServerTypeEnum value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKONWN;
    }
}
