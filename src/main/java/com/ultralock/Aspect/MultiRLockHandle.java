package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;
import com.ultralock.annotation.MultiLock;
import com.ultralock.enums.LockHandleTypeEnum;
import com.ultralock.help.LockHandleHelp;
import com.ultralock.help.LockThreadLocalHelp;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:联合锁
 *
 * @Author: pkh
 * @Date: 02-17
 */
@Slf4j
@Component
public class MultiRLockHandle extends AbstractLockHandle {

    @Resource
    private LockThreadLocalHelp lockThreadLocalHelp;
    @Resource
    private LockHandleHelp lockHandleHelp;

    @Override
    public List<Integer> lockHandleType() {
        return Arrays.asList(LockHandleTypeEnum.MULTI_LOCK.getType());
    }

    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {}


    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {
        if (!paramValid(paramValues)) {
            return;
        }

        Lock lock = multiLock.lock();
        multiLock(paramNames, paramValues, lock);
    }

    private void multiLock(String[] paramNames, Object[] paramValues, Lock lock) {
        String lockName = getKeyByLock(paramNames, paramValues, lock);
        List<RLock> locks = lockHandleHelp.getLocksByLockType(lockName, lock.lockType());
        RedissonMultiLock redissonMultiLock = new RedissonMultiLock(locks.toArray(new RLock[0]));

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        boolean getLock = false;
        try {
            getLock = redissonMultiLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取联合锁失败", e);
        }

        if(getLock) {
            lockThreadLocalHelp.setLock(redissonMultiLock, LockHandleTypeEnum.MULTI_LOCK);
        }
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.MULTI_LOCK);
    }
}
