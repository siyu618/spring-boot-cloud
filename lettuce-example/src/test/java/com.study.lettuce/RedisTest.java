package com.study.lettuce;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    int threadNum = 100;

    private void testSet(int i) {
        redisTemplate.opsForValue().set("test:set1", "testValue1");
        redisTemplate.opsForSet().add("test:set2", "asdf");
        redisTemplate.opsForHash().put("hash1", "name1", "lms1");
        redisTemplate.opsForHash().put("hash1", "name2", "lms2");
        redisTemplate.opsForHash().put("hash1", "name3", "lms3");
        System.out.println(i + ":" + redisTemplate.opsForValue().get("test:set"));
        System.out.println( i + ":" + redisTemplate.opsForHash().get("hash1", "name1"));
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void set() throws InterruptedException {
        ExecutorService e = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < 1; i ++) {
            final  int x = i;
            e.submit(new Runnable() {
                @Override
                public void run() {
                    testSet(x);
                }
            });
        }


        e.shutdown();
        while (!e.isTerminated() || !e.isShutdown()) {
            TimeUnit.SECONDS.sleep(1);
        }

    }
}