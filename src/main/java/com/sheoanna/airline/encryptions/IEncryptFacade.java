package com.sheoanna.airline.encryptions;

public interface IEncryptFacade {
    String encode(String type, String data);

    String decode(String type, String data);
}
