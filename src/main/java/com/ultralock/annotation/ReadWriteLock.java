package com.ultralock.annotation;

import com.ultralock.enums.ReadWriteLockTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:读写锁
 *
 * @Author: pkh
 * @Date: 02-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadWriteLock{

    ReadWriteLockTypeEnum lockType();

    Lock lock();

}