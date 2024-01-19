package com.aselsis.iot.gateway.deviceapikey;

import com.aselsis.iot.gateway.deviceapikey.request.PostDeviceApiKeyRequest;
import com.aselsis.iot.gateway.deviceapikey.request.PutDeviceApiKeyRequest;
import com.aselsis.iot.gateway.exceptions.EntityNotFoundException;
import com.aselsis.iot.gateway.utils.mapper.ModelMapperManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class DeviceApiKeyManager implements DeviceApiKeyService{
    private final DeviceApiKeyRepository deviceApiKeyRepository;
    private final ModelMapperManager modelMapperManager;

    @Override
    public DeviceApiKeyDTO getDeviceApiKey(String id) {
        DeviceApiKey deviceApiKey = this.deviceApiKeyRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Device Api Key Not Found"));
        DeviceApiKeyDTO doneceknesne = this.modelMapperManager.forResponse().map(deviceApiKey, DeviceApiKeyDTO.class);
        return doneceknesne;
    }
    @Override
    public List<DeviceApiKeyDTO> getAllDeviceApiKeys() {
        List<DeviceApiKey> keys = this.deviceApiKeyRepository.findAll();

        List<DeviceApiKeyDTO> allKeysResponse = keys.stream()
                .map(key -> this.modelMapperManager.forResponse()
                        .map(key,DeviceApiKeyDTO.class)).collect(Collectors.toList());
        return allKeysResponse;
    }
    @Override
    public DeviceApiKeyDTO postDeviceApiKey(PostDeviceApiKeyRequest request) {
         DeviceApiKey key = this.deviceApiKeyRepository.save(DeviceApiKey.create(request));
            return this.modelMapperManager.forResponse().map(key, DeviceApiKeyDTO.class);
}
    @Override
    public DeviceApiKeyDTO putDeviceApiKey(PutDeviceApiKeyRequest putDeviceApiKeyRequest, String id) {
        Optional<DeviceApiKey> nesne = deviceApiKeyRepository.findById(id);
        if (nesne.isPresent()) {
            DeviceApiKey updatedNesne = nesne.get();

            deviceApiKeyRepository.save(DeviceApiKey.update(updatedNesne, putDeviceApiKeyRequest));
            return this.modelMapperManager.forResponse().map(updatedNesne, DeviceApiKeyDTO.class);

        } else {
            throw new CantDeleteUserException("An error occurs somewhere in the DeviceApiKey>Manager>PutRequest class. The id you entered probably does not correspond to anything in the program. You may have sent null data.");
                       }
   }
    @Override
    public void deleteDeviceApiKey(String id) {
        boolean requestedApiKeyIsExist = this.deviceApiKeyRepository.existsById(id);
        if (requestedApiKeyIsExist) {
            this.deviceApiKeyRepository.deleteById(id);
            System.out.println(id + "li deviceApikey silindi. ");
        } else {
            throw new CantDeleteUserException("Unable to delete deviceApiKey with id-->"+id+". It may have been deleted before.");
        }
    }



    @Override
    public Optional<DeviceApiKey> findByTokenAndRoomId(String token, String room) {
        return deviceApiKeyRepository.findByTokenAndRoom(token, room);
    }

    @Override
    public Optional<DeviceApiKey> findByToken(String token) {
        return deviceApiKeyRepository.findByToken(token);
    }

    @Override
    public Optional<DeviceApiKey> findByRoom(String room) {
        return deviceApiKeyRepository.findByRoom(room);
    }
}
