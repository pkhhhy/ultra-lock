package com.ultralock.lockFactory;

import com.ultralock.Aspect.LockHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:锁工厂
 *
 * @Author: pkh
 * @Date: 02-17
 */
@Component
public class LockHandleFactory {
    @Autowired
    private Map<String, LockHandle> lockHandleMap;

    private Map<Integer, LockHandle> typeToHandleMap;

    @PostConstruct
    public void init() {
        typeToHandleMap = new HashMap<>();
        lockHandleMap.forEach((k, v) -> {
            for (Integer type : v.lockHandleType()) {
                typeToHandleMap.put(type, v);
            }
        });
    }

    public LockHandle getLockHandle(Integer type) {
        return typeToHandleMap.get(type);
    }
}
