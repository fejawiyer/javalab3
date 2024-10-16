package org.example;

import lombok.Getter;

@Getter
public class SMS {
    private final String phoneNumber;
    private final String message;
    public SMS(String phone, String msg) {
        phoneNumber = phone;
        message = msg;
    }
}
