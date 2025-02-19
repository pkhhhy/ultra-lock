package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;
import com.ultralock.annotation.MultiLock;
import com.ultralock.annotation.ReadWriteLock;
import com.ultralock.annotation.RedLock;
import com.ultralock.enums.LockHandleTypeEnum;
import com.ultralock.enums.ReadWriteLockTypeEnum;
import com.ultralock.help.LockHandleHelp;
import com.ultralock.help.LockThreadLocalHelp;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
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
 * @Date: 02-19
 */
@Slf4j
@Component
public class ReadWriteLockHandle extends AbstractLockHandle {

    @Autowired
    private LockThreadLocalHelp lockThreadLocalHelp;
    @Autowired
    private LockHandleHelp lockHandleHelp;

    @Override
    public List<Integer> lockHandleType() {
        return Arrays.asList(LockHandleTypeEnum.READWRITE_LOCK.getType(), LockHandleTypeEnum.READ_LOCK.getType(), LockHandleTypeEnum.WRITE_LOCK.getType());
    }

    @Override
    public void lock(String[] paramNames, Object[] paramValues, Lock lock) {

    }


    @Override
    public void readWriteLock(String[] paramNames, Object[] paramValues, ReadWriteLock readWriteLock) {
        if (!paramValid(paramValues)) {
            return;
        }

        ReadWriteLockTypeEnum readWriteLockTypeEnum = readWriteLock.lockType();
        Lock lock = readWriteLock.lock();
        readWriteLock(paramNames, paramValues, lock, readWriteLockTypeEnum);
    }

    private void readWriteLock(String[] paramNames, Object[] paramValues, Lock lock, ReadWriteLockTypeEnum readWriteLockTypeEnum) {
        String lockName = getKeyByLock(paramNames, paramValues, lock);
        RReadWriteLock rwLock = lockHandleHelp.getRWLock(lockName);
        boolean write = Objects.equals(readWriteLockTypeEnum, ReadWriteLockTypeEnum.WRITE);
        boolean read = Objects.equals(readWriteLockTypeEnum, ReadWriteLockTypeEnum.READ);
        RLock rLock = null;
        if (write) {
            rLock = rwLock.writeLock();
        } else if (read) {
            rLock = rwLock.readLock();
        } else {
            throw new RuntimeException("需要选择读锁还是写锁");
        }

        long waitTime = lock.waitTime();
        long leaseTime = lock.leaseTime();
        TimeUnit timeUnit = lock.timeUnit();

        boolean getLock = false;
        try {
            getLock = rLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            if (read) {
                log.error("获取读锁失败", e);
            } else {
                log.error("获取写锁失败", e);
            }
        }

        if(getLock) {
            lockThreadLocalHelp.setLock(rLock, LockHandleTypeEnum.READWRITE_LOCK);
        }
    }

    @Override
    public void unLock() {
        lockThreadLocalHelp.removeLock(LockHandleTypeEnum.READWRITE_LOCK);
    }

    @Override
    public void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock) {

    }

    @Override
    public void redLock(String[] paramNames, Object[] paramValues, RedLock redLock) {

    }
}
