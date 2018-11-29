package com.study.example.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.study.example.apollo.config.SampleRedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest {


    /**
     * This will get the new value
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        Config config = ConfigService.getAppConfig();
        config.addChangeListener(changeEvent -> {
            System.out.println("Changes for namespace " + changeEvent.getNamespace());
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
            }
        });
        System.out.println(config.getIntProperty("redis.cache.expireSeconds", 20));
        System.out.println(config.getIntProperty("redis.cache.commandTimeout", 30));
        TimeUnit.MINUTES.sleep(1);
        System.out.println(config.getIntProperty("redis.cache.expireSeconds", 10));
        System.out.println(config.getIntProperty("redis.cache.commandTimeout", 10));
    }


    @Test
    public void testNameSpace() throws InterruptedException {
        Config config = ConfigService.getConfig("TEST1.dbconfig");
        config.addChangeListener(changeEvent -> {
            System.out.println("Changes for namespace " + changeEvent.getNamespace());
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
            }
        });
        System.out.println(config.getIntProperty("redis.cache.expireSeconds", 20));
        System.out.println(config.getIntProperty("redis.cache.commandTimeout", 30));
        System.out.println(config.getIntProperty("readTimeout", 100));
        System.out.println(config.getIntProperty("connectTimeout", 300));
        TimeUnit.MINUTES.sleep(2);
    }



    @Autowired
    SampleRedisConfig sampleRedisConfig;

    /**
     * this will not get the new value
     * @throws InterruptedException
     */
    @Test
    public void testConfigurationButNoAutoRefresh() throws InterruptedException {
        Config config = ConfigService.getAppConfig();
        config.addChangeListener(changeEvent -> {
            System.out.println("Changes for namespace " + changeEvent.getNamespace());
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
            }
        });
        System.out.println(sampleRedisConfig.getCommandTimeout());
        System.out.println(sampleRedisConfig.getExpireSeconds());
        TimeUnit.MINUTES.sleep(2);
        // TODO: import,
        System.out.println(sampleRedisConfig.getCommandTimeout());
        System.out.println(sampleRedisConfig.getExpireSeconds());
    }

}