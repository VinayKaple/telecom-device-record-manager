package com.example.telecomdevicemanager.repository;

import java.util.List;
import com.example.telecomdevicemanager.entity.Device;
import com.example.telecomdevicemanager.entity.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrandIgnoreCase(String brand);

    List<Device> findByState(DeviceState state);
}
