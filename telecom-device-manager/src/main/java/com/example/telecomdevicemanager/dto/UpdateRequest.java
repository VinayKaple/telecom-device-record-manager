package com.example.telecomdevicemanager.dto;

import com.example.telecomdevicemanager.entity.DeviceState;

public record UpdateRequest(String name, String brand, DeviceState state) {
}
