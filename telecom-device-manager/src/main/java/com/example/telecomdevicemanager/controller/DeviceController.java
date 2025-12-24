package com.example.telecomdevicemanager.controller;

import com.example.telecomdevicemanager.service.DeviceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    public DeviceController(DeviceService service) {
    }
}
