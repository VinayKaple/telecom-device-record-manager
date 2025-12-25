package com.example.telecomdevicemanager.utility;

public class DeviceInUseException extends RuntimeException {
    public DeviceInUseException(String message) {
        super(message);
    }
}

