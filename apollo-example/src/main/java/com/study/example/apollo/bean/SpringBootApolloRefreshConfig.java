package com.study.example.apollo.bean;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.study.example.apollo.config.SampleRedisConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class SpringBootApolloRefreshConfig {

    @Autowired
    private SampleRedisConfig sampleRedisConfig;

    @Autowired
    private RefreshScope refreshScope;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean redisCacheKeysChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(changedKey);
            System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
            if (changedKey.startsWith("redis.cache")) {
                redisCacheKeysChanged = true;
                //break;
            }
        }
        if (!redisCacheKeysChanged) {
            return;
        }

        log.info("before refresh {}", sampleRedisConfig.toString());
        refreshScope.refresh("sampleRedisConfig");
        log.info("after refresh {}", sampleRedisConfig.toString());
    }
}
