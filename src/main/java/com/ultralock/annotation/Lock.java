package com.ultralock.annotation;

import com.ultralock.enums.LockTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: pkh
 * @Date: 02-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    /**
     * 锁前缀
     * @return
     */
    String prefix();


    // ===========================
    // 下方三个key 优先级是 singleKey > combineKey > constantKey

    /**
     * 单一key; 使用spel,示例：
     * #user.name
     * @return
     */
    String singleKey() default "";

    /**
     * 组合key; 使用spel,示例：
     * {#user.name,#user.age}
     * @return
     */
    String[] combineKey() default {};

    /**
     * 常量key
     * @return
     */
    String constantKey() default "";

    // ==========================


    /**
     * 锁有效时长
     * -1 表示使用看门狗，自动续约锁时长；
     * @return
     */
    long leaseTime() default -1;

    /**
     * 默认等待时间
     * @return
     */
    long waitTime() default 3;

    /**
     * 时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 锁类型:
     * 读写锁指定改参数也没什么意义
     * @return
     */
    LockTypeEnum lockType() default LockTypeEnum.NO_FAIR;
}

