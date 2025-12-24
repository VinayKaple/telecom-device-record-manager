package com.example.telecomdevicemanager.service;

import com.example.telecomdevicemanager.dto.CreateRequest;
import com.example.telecomdevicemanager.dto.TDMResponse;
import com.example.telecomdevicemanager.dto.UpdateRequest;
import com.example.telecomdevicemanager.entity.Device;
import com.example.telecomdevicemanager.entity.DeviceState;
import com.example.telecomdevicemanager.repository.DeviceRepository;
import com.example.telecomdevicemanager.utility.TDMUtility;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        LOGGER.info("Record created for a new device with id: "+ tdmResponse.id());
        return tdmResponse;
    }

    public TDMResponse get(Long id) {
        return TDMUtility.responseMapper(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with id: "+id)));
    }

    public TDMResponse update(Long id, UpdateRequest request) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with id: "+id));

        if (device.getState() == DeviceState.IN_USE) {
            if (!Objects.equals(request.name(), device.getName()) || !Objects.equals(request.brand(), device.getBrand())) {
                throw new IllegalStateException("Cannot update name or brand when device is in use.");
            }
        }

        if (request.name() != null) device.setName(request.name());
        if (request.brand() != null) device.setBrand(request.brand());
        if (request.state() != null) device.setState(request.state());

        TDMResponse tdmResponse = TDMUtility.responseMapper(device);
        LOGGER.info("Record updated for a device with id: "+ tdmResponse.id());
        return tdmResponse;
    }

    public List<TDMResponse> getAll() {
        return repository.findAll().stream().map(TDMUtility::responseMapper).toList();
    }

    public List<TDMResponse> getByBrand(String brand) {

        return repository.findByBrandIgnoreCase(brand).stream().map(TDMUtility::responseMapper).toList();
    }

    public List<TDMResponse> getByState(DeviceState state) {

        return repository.findByState(state).stream().map(TDMUtility::responseMapper).toList();
    }

    public void delete(Long id) {
        Device device = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found with id: "+id));
        if (device.getState() == DeviceState.IN_USE) {
            throw new IllegalStateException("In-use devices cannot be deleted");
        }
        repository.delete(device);
    }
}
