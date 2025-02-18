package com.ultralock.Aspect;

import com.ultralock.annotation.Lock;
import com.ultralock.annotation.MultiLock;
import com.ultralock.annotation.RedLock;
import com.ultralock.enums.LockHandleTypeEnum;
import com.ultralock.lockFactory.LockHandleFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-18
 */
@Aspect
@Order(-1)
@Component
public class LockAspect {

    @Resource
    private LockHandleFactory lockHandleFactory;

    @Before("@annotation(lock)")
    public void before(JoinPoint joinPoint, Lock lock) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.R_LOCK.getType());
        lockHandle.lock(parameterNames, args, lock);
    }

    @After("@annotation(lock)")
    public void after(JoinPoint joinPoint, Lock lock) {
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.R_LOCK.getType());
        lockHandle.unLock();
    }


    @Before("@annotation(multiLock)")
    public void beforeMulti(JoinPoint joinPoint, MultiLock multiLock) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.MULTI_LOCK.getType());
        lockHandle.multiLock(parameterNames, args, multiLock);
    }

    @After("@annotation(multiLock)")
    public void afterMulti(JoinPoint joinPoint, MultiLock multiLock) {
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.MULTI_LOCK.getType());
        lockHandle.unLock();
    }

    @Before("@annotation(redLock)")
    public void beforeRed(JoinPoint joinPoint, RedLock redLock) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        LockHandle lockHandle = lockHandleFactory.getLockHandle(LockHandleTypeEnum.RED_LOCK.getType());
        lockHandle.redLock(parameterNames, args, redLock);
    }
}
