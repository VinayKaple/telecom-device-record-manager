package com.example.telecomdevicemanager.dto;

import com.example.telecomdevicemanager.entity.DeviceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRequest(@NotBlank String name,
                            @NotBlank String brand,
                            @NotNull DeviceState state) {
}
