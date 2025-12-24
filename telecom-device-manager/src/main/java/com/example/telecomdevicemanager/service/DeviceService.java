package com.example.telecomdevicemanager.service;

import com.example.telecomdevicemanager.dto.CreateRequest;
import com.example.telecomdevicemanager.dto.TDMResponse;
import com.example.telecomdevicemanager.entity.Device;
import com.example.telecomdevicemanager.repository.DeviceRepository;
import com.example.telecomdevicemanager.utility.TDMUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository repository;


    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public TDMResponse create(CreateRequest request) {
        Device device = new Device();
        device.setName(request.name());
        device.setBrand(request.brand());
        device.setState(request.state());
        TDMResponse tdmResponse = TDMUtility.responseMapper(repository.save(device));
        LOGGER.info("Record created for device with id: "+ tdmResponse.id());
        return tdmResponse;
    }
}
