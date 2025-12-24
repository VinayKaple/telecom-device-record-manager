package com.example.telecomdevicemanager.controller;

import com.example.telecomdevicemanager.dto.CreateRequest;
import com.example.telecomdevicemanager.dto.TDMResponse;
import com.example.telecomdevicemanager.dto.UpdateRequest;
import com.example.telecomdevicemanager.entity.DeviceState;
import com.example.telecomdevicemanager.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tdm/api/")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(value = "/v1/device")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new device record", description = "Adds a new device to the system")
    public TDMResponse create(@Valid @RequestBody CreateRequest request){
        return deviceService.create(request);
    }

    @GetMapping("/v1/device/{id}")
    @Operation(summary = "Get a device record by id", description = "Gives details of an existing device")
    public TDMResponse get(@PathVariable Long id) {
        return deviceService.get(id);
    }

    @PatchMapping("/v1/device/{id}")
    @Operation(summary = "Update a device record by id", description = "Updates details of an existing device")
    public TDMResponse update(@PathVariable Long id,@RequestBody UpdateRequest request) {
        return deviceService.update(id, request);
    }

    @GetMapping("/v1/device")
    @Operation(summary = "Get all devices record", description = "Gets details of all existing devices. Records can also be fetched by brand/state.")
    public List<TDMResponse> getAll(@RequestParam(required = false) String brand, @RequestParam(required = false) DeviceState state) {

        if (brand != null) return deviceService.getByBrand(brand);
        if (state != null) return deviceService.getByState(state);
        return deviceService.getAll();
    }

    @DeleteMapping("/v1/device/{id}")
    @Operation(summary = "Delete a device record", description = "Deletes a device record from system by id")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        deviceService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Device with id: " + id +" deleted successfully")
        );
    }

}
