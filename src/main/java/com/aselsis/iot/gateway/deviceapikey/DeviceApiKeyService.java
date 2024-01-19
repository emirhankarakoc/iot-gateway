package com.aselsis.iot.gateway.deviceapikey;

import com.aselsis.iot.gateway.deviceapikey.request.PostDeviceApiKeyRequest;
import com.aselsis.iot.gateway.deviceapikey.request.PutDeviceApiKeyRequest;
import java.util.List;
import java.util.Optional;

public interface DeviceApiKeyService {

    DeviceApiKeyDTO getDeviceApiKey(String id);

    List<DeviceApiKeyDTO> getAllDeviceApiKeys();
    DeviceApiKeyDTO postDeviceApiKey(PostDeviceApiKeyRequest postDeviceApiKeyRequest);

    DeviceApiKeyDTO putDeviceApiKey(PutDeviceApiKeyRequest putDeviceApiKeyRequest, String id);

    void deleteDeviceApiKey(String id);



    Optional<DeviceApiKey> findByTokenAndRoomId(String token, String room);

    Optional<DeviceApiKey> findByToken(String token);

    Optional<DeviceApiKey> findByRoom(String room);
}
