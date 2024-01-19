package com.aselsis.iot.gateway.iotdata;

import com.aselsis.iot.gateway.deviceapikey.DeviceApiKey;
import com.aselsis.iot.gateway.deviceapikey.DeviceApiKeyService;
import com.aselsis.iot.gateway.exceptions.NotFoundSerialException;
import com.aselsis.iot.gateway.soketconfig.Message;
import com.aselsis.iot.gateway.exceptions.EntityNotFoundException;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@AllArgsConstructor
public class IotDataManager implements IotDataService {
        private final SocketIOServer socketIOServer;
        private final DeviceApiKeyService deviceApiKeyService;
        private final IotDataRepository repository;

    public void postData( Message message, String token) {
        Optional<DeviceApiKey> deviceApiKey = deviceApiKeyService.findByToken(token);
        String room = deviceApiKey.get().getRoom();
        Optional<IotData> iotData = repository.findBySerial(message.getSerial());
        if (!iotData.isPresent()){

            if (deviceApiKey.isPresent()) {
                repository.save(IotData.create(message, room));
                socketIOServer.getRoomOperations(room)
                        .sendEvent("get_message", message);
            }
            else {
                throw new EntityNotFoundException("Device not found for token : " + token);
            }
        }
        else {
            IotData update = iotData.get();
            //update
            repository.save(IotData.update(update, message));
            socketIOServer.getRoomOperations(room)
                    .sendEvent("get_message", message);
        }
    }
    @Override
    public Page<IotData> getAll(Pageable pageable) {
        Pageable allPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort() == null ?
                        Sort.by("createdDateTime").descending() : pageable.getSort()
        );
        return repository.findAll(allPageable);
    }
    @Override
    public List<IotData> getByRoom(String room) {
       Optional<DeviceApiKey> deviceApiKey = deviceApiKeyService.findByRoom(room);
        if (deviceApiKey.isPresent()){
            return repository.findByRoom(room);
        } else {throw new NotFoundRoomException(room+" numaralı oda bulunamadı!");}
    }
    @Override
    public Optional<IotData> getByRoomAndSerial(String room, String serial) {
        Optional<IotData> iotData = repository.findBySerial(serial);
        Optional<DeviceApiKey> deviceApiKey = deviceApiKeyService.findByRoom(room);
        if (deviceApiKey.isPresent()){
        if (iotData.isPresent()){
                return repository.findByRoomAndSerial(room,serial);
        } else {throw new NotFoundSerialException(serial+" numaralı serial bulunamadı!");}
        }else {throw new NotFoundRoomException(room+" numaralı oda bulunamadı!");}
    }

    @Override
    public void postStringData(String message, String token) {
        DeviceApiKey deviceApiKey = deviceApiKeyService.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(token+" token not found!!"));
        List<String> keyValueStrings = new ArrayList<>();
        keyValueStrings.addAll(Arrays.stream(message.split(";")).toList());

        Map<String, String> attributes = new HashMap<>();
        keyValueStrings.forEach(keyValue -> {
           List<String> keyValues = Arrays.stream(keyValue.split(":")).toList();
            attributes.put(keyValues.get(0), keyValues.get(1));});

       String room = deviceApiKey.getRoom();
       String serial = attributes.get("serial");
       attributes.remove("serial", attributes.get("serial"));

        Message mes = new Message();
        mes.setAttributes(attributes);
        mes.setSerial(serial);

       Optional<IotData> iotDataOptional = repository.findBySerial(serial);
       if(iotDataOptional.isPresent()){                                 //update iodata
           IotData update = iotDataOptional.get();
           repository.save(IotData.update(update,mes));
           socketIOServer.getRoomOperations(room)
                   .sendEvent("get_message", mes);
       }else{                                                           //creat iodata
           repository.save(IotData.create(mes,room));
           socketIOServer.getRoomOperations(room)
                   .sendEvent("get_message", mes);
       }
    }
}
