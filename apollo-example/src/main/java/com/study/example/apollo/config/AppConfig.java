package com.study.example.apollo.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author didi
 */
@Configuration
@EnableApolloConfig
public class AppConfig {
    @Bean
    public SampleRedisConfig javaConfigBean() {
        return new SampleRedisConfig();
    }

    @Bean
    public TestJavaConfigBean testJavaConfigBean() {
        return new TestJavaConfigBean();
    }
}
