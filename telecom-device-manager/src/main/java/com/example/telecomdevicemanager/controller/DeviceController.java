package com.example.telecomdevicemanager.controller;

import com.example.telecomdevicemanager.dto.CreateRequest;
import com.example.telecomdevicemanager.dto.TDMResponse;
import com.example.telecomdevicemanager.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tdm-api/")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(value = "/v1/device")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new device record.", description = "Add a new device to the system.")
    public TDMResponse create(@Valid @RequestBody CreateRequest request){
        return deviceService.create(request);
    }


}
