package com.ultralock.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.ultralock.enums.LockServerTypeEnum;
import com.ultralock.lockFactory.RedissonClientFactory;
import com.ultralock.util.CollectionsUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description:reddison 配置类
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Configuration
@ComponentScan(basePackages = {"com.ultralock"})
@EnableConfigurationProperties(value = {
        LockServerProperties.class,
        LockMoreServerProperties.class,
        LockConfigProperties.class
})
public class RedissonConfiguration {

    @Autowired
    private LockServerProperties lockServerProperties;

    @Autowired
    private LockMoreServerProperties lockMoreServerProperties;

    @Autowired
    private LockConfigProperties lockConfigProperties;

    private RedissonClient initReddisionClient() {
        Config config = initConfig();
        boolean initServer = initServer(config, lockServerProperties);
        if (initServer) {
            return Redisson.create(config);
        }
        return null;
    }

    private Config initConfig() {
        Config config = new Config();
        Duration lockWatchdogTimeout = lockConfigProperties.getLockWatchdogTimeout();
        config.setLockWatchdogTimeout(lockWatchdogTimeout.toMillis());
        return config;
    }

    private List<RedissonClient> initMoreReddisonClient() {
        if (CollectionsUtil.isNotEmpty(lockMoreServerProperties.getServers())) {
            List<RedissonClient> list = new ArrayList<>(lockMoreServerProperties.getServers().size());
            for (LockServerProperties server : lockMoreServerProperties.getServers()) {
                Config config = initConfig();
                boolean initServer = initServer(config, server);
                if (initServer) {
                    list.add(Redisson.create(config));
                }
            }
            if (CollectionsUtil.isNotEmpty(list)) {
                return list;
            }
        }
        return null;
    }

    private boolean initServer(Config config, LockServerProperties lockServerProperties) {
        String serverType = lockServerProperties.getServerType();
        LockServerTypeEnum enumByType = LockServerTypeEnum.getEnumByType(serverType);
        switch (enumByType) {
            case SINGLE:
                initSingleServer(config, lockServerProperties);
                break;
            case CLUSTER:
                initClusterServer(config, lockServerProperties);
                break;
            case MASTER_SALVE:
                initMasterSlaveServer(config, lockServerProperties);
                break;
            default:
                return false;
        }
        return true;
    }

    private void initSingleServer(Config config, LockServerProperties lockServerProperties) {
        SingleServerConfig single = lockServerProperties.getSingle();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        BeanUtil.copyProperties(single, singleServerConfig, CopyOptions.create().ignoreNullValue());
    }

    private void initClusterServer(Config config, LockServerProperties lockServerProperties) {
        ClusterServersConfig cluster = lockServerProperties.getCluster();
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        BeanUtil.copyProperties(cluster, clusterServersConfig, CopyOptions.create().ignoreNullValue());
    }

    private void initMasterSlaveServer(Config config, LockServerProperties lockServerProperties) {
        MasterSlaveServersConfig masterSlave = lockServerProperties.getMasterSlave();
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
        BeanUtil.copyProperties(masterSlave, masterSlaveServersConfig, CopyOptions.create().ignoreNullValue());
    }

    @Bean(value = "reddisonClientFactory", destroyMethod = "shutdown")
    public RedissonClientFactory reddisonClientFactory() {
        RedissonClientFactory redissonClientFactory = new RedissonClientFactory();
        RedissonClient redissonClient = initReddisionClient();
        if (Objects.nonNull(redissonClient)) {
            redissonClientFactory.setRedissonClient(redissonClient);
        }
        List<RedissonClient> redissonClients = initMoreReddisonClient();
        if (CollectionsUtil.isNotEmpty(redissonClients)) {
            redissonClientFactory.setRedissonClients(redissonClients);
        }

        return redissonClientFactory;
    }
}
