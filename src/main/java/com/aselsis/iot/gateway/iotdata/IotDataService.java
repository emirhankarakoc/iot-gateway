package com.aselsis.iot.gateway.iotdata;

import com.aselsis.iot.gateway.soketconfig.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IotDataService {
    public void postData(Message message, String token);

    Page<IotData> getAll(Pageable pageable);

    List<IotData> getByRoom(String room);

    Optional<IotData> getByRoomAndSerial(String room, String serial);

    void postStringData(String message, String token);
}
