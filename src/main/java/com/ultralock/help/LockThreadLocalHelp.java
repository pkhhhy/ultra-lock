package com.ultralock.help;

import com.ultralock.enums.LockHandleTypeEnum;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Component
public class LockThreadLocalHelp {
    private final ThreadLocal<RLock> lockThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<RedissonMultiLock> multiLockThreadLocal = new ThreadLocal<>();


    public void setLock(Lock lock, LockHandleTypeEnum lockHandleTypeEnum) {
        switch (lockHandleTypeEnum) {
            case R_LOCK:
                lockThreadLocal.set((RLock) lock);
                break;
            default:
                break;
        }
    }

    public void removeLock(LockHandleTypeEnum lockHandleTypeEnum) {
        switch (lockHandleTypeEnum) {
            case R_LOCK:
                RLock rLock = lockThreadLocal.get();
                if (Objects.nonNull(rLock)) {
                    rLock.unlockAsync();
                    lockThreadLocal.remove();
                }
                break;
            default:
                break;
        }
    }
}
