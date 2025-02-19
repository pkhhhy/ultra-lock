package com.ultralock;

import com.ultralock.annotation.Lock;
import com.ultralock.annotation.MultiLock;
import com.ultralock.annotation.ReadWriteLock;
import com.ultralock.annotation.RedLock;
import com.ultralock.enums.ReadWriteLockTypeEnum;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-19
 */
@Component
public class TestService {


    @Lock(prefix = "lock", combineKey = {"#user.name", "#user.age", "#name"})
    public void lock(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Lock(prefix = "lock", combineKey = {"#user.name", "#user.age", "#name"}, waitTime = 25)
    public void lock_01(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @MultiLock(lock = @Lock(prefix = "multi", combineKey = {"#user.name", "#user.age", "#name"}))
    public void multiLock(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @RedLock(lock = @Lock(prefix = "red", combineKey = {"#user.name", "#user.age", "#name"}))
    public void redLock(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @ReadWriteLock(lockType = ReadWriteLockTypeEnum.WRITE,lock = @Lock(prefix = "rw", combineKey = {"#user.name", "#user.age", "#name"}))
    public void rwLock_write(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @ReadWriteLock(lockType = ReadWriteLockTypeEnum.READ,lock = @Lock(prefix = "rw", combineKey = {"#user.name", "#user.age", "#name"}))
    public void rwLock_read(User user, String name) {
        try {
            TimeUnit.SECONDS.sleep(50L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}

