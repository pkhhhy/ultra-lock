package com.ultralock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Data
@ConfigurationProperties(prefix = "lock.config")
public class LockConfigProperties {
    /**
     * 看门狗时间，单位毫秒
     */
    private Duration lockWatchdogTimeout = Duration.ofMillis(30 * 1000);

}
