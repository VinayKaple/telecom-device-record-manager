package com.example.telecomdevicemanager.service;

import com.example.telecomdevicemanager.dto.CreateRequest;
import com.example.telecomdevicemanager.dto.TDMResponse;
import com.example.telecomdevicemanager.dto.UpdateRequest;
import com.example.telecomdevicemanager.entity.Device;
import com.example.telecomdevicemanager.entity.DeviceState;
import com.example.telecomdevicemanager.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository repository;

    @InjectMocks
    private DeviceService service;

    private Device device;

    @BeforeEach
    void setUp() {
        device = new Device();
        device.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        device.setBrand("Apple");
        device.setState(DeviceState.valueOf("AVAILABLE"));
    }

    @Test
    void shouldReturnAllDevices() {
        when(repository.findAll()).thenReturn(List.of(device));

        List<TDMResponse> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }
    @Test
    void shouldReturnDeviceById() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));

        TDMResponse result = service.get(1L);

        assertEquals("Apple", result.brand());
    }

    @Test
    void shouldThrowExceptionWhenDeviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.get(1L));
    }

    @Test
    void shouldCreateDevice() {
        // GIVEN
        CreateRequest request = new CreateRequest(
                "iPhone 15",
                "Apple",
                DeviceState.AVAILABLE
        );

        when(repository.save(any(Device.class)))
                .thenAnswer(invocation -> {
                    Device saved = invocation.getArgument(0);
                    saved.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")); // simulate DB-generated ID
                    return saved;
                });

        // WHEN
        TDMResponse response = service.create(request);

        // THEN
        assertNotNull(response);
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), response.id());
        assertEquals("iPhone 15", response.name());
        assertEquals("Apple", response.brand());
        assertEquals(DeviceState.AVAILABLE, response.state());

        verify(repository).save(any(Device.class));
    }

    @Test
    void shouldUpdateOnlyProvidedFields() {
        UpdateRequest request = new UpdateRequest("Samsung", null, null);
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        when(repository.save(any(Device.class))).thenAnswer(i -> i.getArgument(0));

        TDMResponse result = service.update(1L, request);

        assertEquals("Apple", result.brand());
        assertEquals(DeviceState.AVAILABLE, result.state()); // unchanged
    }

    @Test
    void shouldDeleteDevice() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));

        service.delete(1L);

        verify(repository).delete(device);
    }

    @Test
    void shouldReturnDevicesByState() {
        when(repository.findByState(DeviceState.AVAILABLE)).thenReturn(List.of(device));

        List<TDMResponse> result = service.getByState(DeviceState.AVAILABLE);

        assertEquals(1, result.size());
        verify(repository).findByState(DeviceState.AVAILABLE);
    }

    @Test
    void shouldReturnDevicesByBrand() {
        when(repository.findByBrandIgnoreCase("Apple")).thenReturn(List.of(device));

        List<TDMResponse> result = service.getByBrand("Apple");

        assertEquals(1, result.size());
        verify(repository).findByBrandIgnoreCase("Apple");
    }
}
