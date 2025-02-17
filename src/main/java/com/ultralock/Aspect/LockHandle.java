package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
public interface LockHandle {

    void lock(String[] paramNames, Object[] paramValues, Lock lock);

    void unLock();


}
