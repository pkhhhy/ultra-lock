package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;
import com.ultralock.annotation.MultiLock;
import com.ultralock.annotation.RedLock;

import java.util.List;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
public interface LockHandle {

    List<Integer> lockHandleType();

    void lock(String[] paramNames, Object[] paramValues, Lock lock);

    void unLock();

    void multiLock(String[] paramNames, Object[] paramValues, MultiLock multiLock);

    void redLock(String[] paramNames, Object[] paramValues, RedLock redLock);

}
