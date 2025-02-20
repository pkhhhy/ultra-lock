package com.ultralock.annotation;

import com.ultralock.enums.LockHandleTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UltraLock {
    LockHandleTypeEnum useLock();

    Lock lock();
}
