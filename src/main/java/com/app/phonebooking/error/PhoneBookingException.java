package com.app.phonebooking.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PhoneBookingException extends RuntimeException{


    public PhoneBookingException(String message) {
        super(message);
    }
}
