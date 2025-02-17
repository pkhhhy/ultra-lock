package com.ultralock.lockFactory;

import com.ultralock.util.CollectionsUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Slf4j
@Getter
@Setter
public class RedissonClientFactory {

    private RedissonClient redissonClient;

    private List<RedissonClient> redissonClients;


    private void shutdownRedissonClient(RedissonClient redissonClient) {
        try {
            redissonClient.shutdown();
        } catch (Exception e) {
            log.error("关闭RedissonClient出错", e);
        }
    }

    public void shutdown() {
        if (Objects.nonNull(redissonClient)) {
            shutdownRedissonClient(redissonClient);
        }

        if (CollectionsUtil.isNotEmpty(redissonClients)) {
            for (RedissonClient client : redissonClients) {
                shutdownRedissonClient(redissonClient);
            }
        }
    }

}
