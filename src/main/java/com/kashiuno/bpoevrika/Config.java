package com.kashiuno.bpoevrika;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    Runtime runtime() {
        return Runtime.getRuntime();
    }
}
