package com.UltraLock;

import com.ultralock.TestService;
import com.ultralock.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class UltraLockApplicationTests {

    @Resource
    private TestService testService;

    @Test
    void lock() {
        new Thread(() -> {
            User user = new User();
            user.setName("pkh");
            user.setAge(26);
            testService.lock(user, "001");
        }).start();
        new Thread(() -> {
            User user = new User();
            user.setName("pkh");
            user.setAge(26);
            testService.lock(user, "001");
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            User user = new User();
            user.setName("pkh");
            user.setAge(26);
            testService.lock_01(user, "001");
        }).start();


        try {
            TimeUnit.SECONDS.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void multiLock() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.multiLock(user, "002");
    }

    @Test
    void redLock() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.redLock(user, "003");
    }

    @Test
    void rwLock_write() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.rwLock_write(user, "004");
    }


    @Test
    void rwLock_read() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.rwLock_read(user, "004");
    }

    @Test
    void ultraLock_lock() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.ultraLock_lock(user, "004");
    }

    @Test
    void ultraLock_multi() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.ultraLock_multi(user, "004");
    }

    @Test
    void ultraLock_red() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.ultraLock_red(user, "004");
    }

    @Test
    void ultraLock_read() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.ultraLock_read(user, "004");
    }

    @Test
    void ultraLock_write() {
        User user = new User();
        user.setName("pkh");
        user.setAge(26);
        testService.ultraLock_write(user, "004");
    }


}
