package com.ultralock.help;

import com.ultralock.annotation.Lock;
import com.ultralock.enums.LockTypeEnum;
import com.ultralock.lockFactory.RedissonClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Slf4j
@Component
public class LockHandleHelp {

    @Autowired
    private RedissonClientFactory redissonClientFactory;


    public RLock getLockByLockType(String lockName, LockTypeEnum lockType) {
        switch (lockType) {
            case NO_FAIR:
                return redissonClientFactory.getRedissonClient().getLock(lockName);
            case FAIR:
                return redissonClientFactory.getRedissonClient().getFairLock(lockName);
            default:
                break;
        }
        return null;
    }

    public String getKeyByLock(String[] paramNames, Object[] paramValues, Lock lock) {
        // spel 设置参数
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], paramValues[i]);
        }

        String prefix = lock.prefix();
        String singleKey = lock.singleKey();
        String[] combineKey = lock.combineKey();
        String constantKey = lock.constantKey();

        // singleKey > combineKey > constantKey
        StringJoiner keyJoiner = new StringJoiner(":");
        keyJoiner.add(prefix);
        if (!StringUtils.isEmpty(singleKey)) {
            keyJoiner.add(objToString(parser.parseExpression(singleKey).getValue(context)));
        } else if (combineKey.length > 0) {
            for (String ck : combineKey) {
                keyJoiner.add(objToString(parser.parseExpression(ck).getValue(context)));
            }
        } else if (!StringUtils.isEmpty(constantKey)) {
            keyJoiner.add(constantKey);
        } else {
            throw new RuntimeException("没有指定一下Lock中的key");
        }

        String key = keyJoiner.toString();

        return key;
    }

    private String objToString(Object obj) {
        if (Objects.isNull(obj)) {
            return "null";
        }
        return obj.toString();
    }

    public List<RLock> getLocksByLockType(String lockName, LockTypeEnum lockType) {
        List<RLock> rLocks = new ArrayList<>(redissonClientFactory.getRedissonClients().size());
        switch (lockType) {
            case NO_FAIR:
                for (RedissonClient redissonClient : redissonClientFactory.getRedissonClients()) {
                    RLock lock = redissonClient.getLock(lockName);
                    rLocks.add(lock);
                }
                return rLocks;
            case FAIR:
                for (RedissonClient redissonClient : redissonClientFactory.getRedissonClients()) {
                    RLock lock = redissonClient.getFairLock(lockName);
                    rLocks.add(lock);
                }
            default:
                break;
        }
        return null;
    }
}
