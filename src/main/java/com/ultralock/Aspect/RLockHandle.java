package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;
import com.ultralock.annotation.MultiLock;
import com.ultralock.annotation.ReadWriteLock;
import com.ultralock.annotation.RedLock;
import com.ultralock.enums.LockHandleTypeEnum;
import com.ultralock.help.LockThreadLocalHelp;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Slf4j
@Component
public class RLockHandle extends AbstractLockHandle {
    @Autowired
    private LockThreadLocalHelp lockThreadLocalHelp;


    @Override
    public List<Integer> lockHandleType() {
        return Arrays.asList(LockHandleTypeEnum.R_LOCK.getType());
    }


    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {
        if (!paramValid(paramValues)) {
            return;
        }

        String key = getKeyByLock(paramNames, paramValues, lock);
        RLock rLock = getLockByLockType(key, lock.lockType());
        if (Objects.isNull(rLock)) {
            return;
        }

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        boolean getLock = false;
        try {
            getLock = rLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取锁失败", e);
        }

        // 记得释放锁
        if (getLock) {
            lockThreadLocalHelp.setLock(rLock, LockHandleTypeEnum.R_LOCK);
        }
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.R_LOCK);
    }

    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {}

    @Override
    public void redLock(String[] paramNames, Object[] paramValues, RedLock redLock) {

    }

    @Override
    public void readWriteLock(String[] paramNames, Object[] paramValues, ReadWriteLock readWriteLock) {

    }
}
