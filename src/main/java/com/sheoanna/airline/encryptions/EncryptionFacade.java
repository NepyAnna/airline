package com.sheoanna.airline.encryptions;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionFacade implements IEncryptFacade {

    private PasswordEncoder bcryptSystem;
    private Base64System base64System;

    public EncryptionFacade(PasswordEncoder bcryptSystem, Base64System base64System) {
        this.bcryptSystem = bcryptSystem;
        this.base64System = base64System;
    }

    @Override
    public String encode(String type, String data) {
        String dataEncrypted = "";

        if (type == "bcrypt")
            dataEncrypted = bcryptSystem.encode(data);
        if (type == "base64")
            dataEncrypted = base64System.encode(data);

        return dataEncrypted;
    }

    @Override
    public String decode(String type, String data) {
        String dataDecoded = "";

        if (type == "base64")
            dataDecoded = base64System.decode(data);

        return dataDecoded;
    }

}
