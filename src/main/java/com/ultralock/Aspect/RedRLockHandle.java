package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;
import com.ultralock.annotation.MultiLock;
import com.ultralock.annotation.RedLock;
import com.ultralock.enums.LockHandleTypeEnum;
import com.ultralock.help.LockHandleHelp;
import com.ultralock.help.LockThreadLocalHelp;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-18
 */
@Slf4j
@Component
public class RedRLockHandle extends AbstractLockHandle {

    @Resource
    private LockThreadLocalHelp lockThreadLocalHelp;
    @Resource
    private LockHandleHelp lockHandleHelp;

    @Override
    public List<Integer> lockHandleType() {
        return Arrays.asList(LockHandleTypeEnum.RED_LOCK.getType());
    }

    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {

    }


    @Override
    public void redLock(String[] paramNames, Object[] paramValues, RedLock redLock) {
        if (!paramValid(paramValues)) {
            return;
        }
        Lock lock = redLock.lock();
        redLock(paramNames, paramValues, lock);
    }

    private void redLock(String[] paramNames, Object[] paramValues, Lock lock) {
        String lockName = getKeyByLock(paramNames, paramValues, lock);
        List<RLock> locks = lockHandleHelp.getLocksByLockType(lockName, lock.lockType());
        RedissonRedLock redissonRedLock = new RedissonRedLock(locks.toArray(new RLock[0]));

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        boolean getLock = false;
        try {
            getLock = redissonRedLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取红锁失败", e);
        }

        if (getLock) {
            lockThreadLocalHelp.setLock(redissonRedLock, LockHandleTypeEnum.RED_LOCK);
        }
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.RED_LOCK);
    }

    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {

    }
}
