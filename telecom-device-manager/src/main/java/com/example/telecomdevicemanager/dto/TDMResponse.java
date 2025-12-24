package com.example.telecomdevicemanager.dto;

import com.example.telecomdevicemanager.entity.DeviceState;

import java.time.Instant;

public record TDMResponse(Long id, String name, String brand, DeviceState state, Instant creationTime) {
}
