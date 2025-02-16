package com.ultralock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Data
@ConfigurationProperties(prefix = "lock.more")
public class LockMoreServerProperties {
    List<LockServerProperties> servers;
}
