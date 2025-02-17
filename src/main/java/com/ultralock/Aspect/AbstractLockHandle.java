package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;
import com.ultralock.enums.LockTypeEnum;
import com.ultralock.help.LockHandleHelp;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Component
public abstract class AbstractLockHandle  implements LockHandle {

    @Resource
    private LockHandleHelp lockHandleHelp;

    protected RLock getLockByLockType(String lockName, LockTypeEnum lockType) {
        return lockHandleHelp.getLockByLockType(lockName, lockType);
    }

    protected boolean paramValid(Object[] args) {
        return Objects.nonNull(args) && args.length > 0;
    }


    protected String getKeyByLock(String[] paramNames, Object[] paramValues, Lock lock) {
        return lockHandleHelp.getKeyByLock(paramNames, paramValues, lock);
    }

}