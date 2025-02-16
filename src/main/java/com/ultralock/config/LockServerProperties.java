package com.ultralock.config;

import lombok.Getter;
import lombok.Setter;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "lock.server")
public class LockServerProperties {
    /**
     * 服务模式：
     * single
     * cluster
     * masterSlave
     * @see com.ultralock.enums.LockServerTypeEnum
     */
    private String serverType;

    /**
     * 单节点
     */
    private SingleServerConfig single;

    /**
     * 集群
     */
    private ClusterServersConfig cluster;

    /**
     * 主从
     */
    private MasterSlaveServersConfig masterSlave;
}
