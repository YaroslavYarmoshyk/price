package com.prices.service;

public interface EncryptingService {

    String encryptJwt(String jwt);

    String decryptJwt(String jwt);

}
