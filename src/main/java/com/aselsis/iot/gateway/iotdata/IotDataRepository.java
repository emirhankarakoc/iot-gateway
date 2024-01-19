package com.aselsis.iot.gateway.iotdata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IotDataRepository extends JpaRepository<IotData,String> {
    Optional<IotData> findBySerial(String serial);
    List<IotData> findByRoom(String room);
    Optional<IotData> findByRoomAndSerial(String room, String serial);
}
