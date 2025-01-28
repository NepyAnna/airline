package com.sheoanna.airline.encryptions;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptSystem implements IEncoder {

    PasswordEncoder encoder;

    public BcryptSystem(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String data) {
        String dataEncoded = encoder.encode(data);
        return dataEncoded;
    }

}