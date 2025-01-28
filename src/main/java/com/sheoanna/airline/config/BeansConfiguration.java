package com.sheoanna.airline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sheoanna.airline.encryptions.Base64System;
import com.sheoanna.airline.encryptions.BcryptSystem;
import com.sheoanna.airline.encryptions.EncryptionFacade;
import com.sheoanna.airline.encryptions.IEncryptFacade;

@Configuration
public class BeansConfiguration {

    @Bean
    public BcryptSystem bcryptSystem() {
        return new BcryptSystem(new BCryptPasswordEncoder());
    }

    @Bean
    public Base64System base64System() {
        return new Base64System();
    }

    public IEncryptFacade encryptFacade() {
        return new EncryptionFacade(new BCryptPasswordEncoder(), base64System());
    }

}