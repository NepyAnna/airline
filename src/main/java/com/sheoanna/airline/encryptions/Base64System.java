package com.sheoanna.airline.encryptions;

import java.util.Base64;

public class Base64System implements IEncoder {

    @Override
    public String encode(String data) {
        String dataEncoded = Base64.getEncoder().encodeToString(data.getBytes());
        return dataEncoded;
    }

    public String decode(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        String dataDecoded = new String(decodedBytes);
        return dataDecoded;
    }

}
