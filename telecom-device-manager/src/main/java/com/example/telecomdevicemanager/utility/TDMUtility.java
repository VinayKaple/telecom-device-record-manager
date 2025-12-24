package com.example.telecomdevicemanager.utility;

import com.example.telecomdevicemanager.dto.TDMResponse;
import com.example.telecomdevicemanager.entity.Device;

public class TDMUtility {

    public static TDMResponse responseMapper(Device d) {
        return new TDMResponse(
                d.getId(),
                d.getName(),
                d.getBrand(),
                d.getState(),
                d.getCreationTime()
        );
    }
}
