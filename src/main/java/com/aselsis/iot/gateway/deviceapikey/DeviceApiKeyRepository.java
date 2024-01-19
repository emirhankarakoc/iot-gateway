package com.aselsis.iot.gateway.deviceapikey;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;


public interface DeviceApiKeyRepository extends JpaRepository<DeviceApiKey,String> {
    Optional<DeviceApiKey> findByValidUntil(LocalDate validDate);

    @Override
    Optional<DeviceApiKey> findById(String id);

    Optional<DeviceApiKey> findByIdAndRoom(String token, String room);

    Optional<DeviceApiKey> findByTokenAndRoom(String token, String room);

    Optional<DeviceApiKey> findByToken(String token);

    Optional<DeviceApiKey> findByRoom(String room);
}
