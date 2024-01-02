package com.example.paystacktransfer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${paystack.api.secret-key}")
    private String paystackSecretKey;


    @Bean
    public PaystackConfig paystackConfig(){
        return new PaystackConfig(paystackSecretKey);
    }

}
