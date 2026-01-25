package com.example.telecomdevicemanager.dto;

import com.example.telecomdevicemanager.entity.DeviceState;

import java.time.Instant;

public record TDMResponse(java.util.UUID id, String name, String brand, DeviceState state, Instant creationTime) {
}
