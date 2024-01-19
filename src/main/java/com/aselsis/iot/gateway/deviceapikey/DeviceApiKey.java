package com.aselsis.iot.gateway.deviceapikey;

import com.aselsis.iot.gateway.deviceapikey.enums.ProtocolType;
import com.aselsis.iot.gateway.deviceapikey.request.PostDeviceApiKeyRequest;
import com.aselsis.iot.gateway.deviceapikey.request.PutDeviceApiKeyRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import static com.aselsis.iot.gateway.utils.apigenerator.ApiGenerator.generateApiKey;

@Entity
@Getter@Setter
public class DeviceApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String token;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUntil;
    @Enumerated(EnumType.STRING)
    private ProtocolType protocol;
    private String room;


    public static DeviceApiKey create(PostDeviceApiKeyRequest request){
        DeviceApiKey response = new DeviceApiKey();
        response.token = generateApiKey(32);
        response.validUntil = request.getValidUntil();
        response.protocol = request.getProtocol();
        response.room = request.getRoom();
        return response;
    }

    public static DeviceApiKey update(DeviceApiKey dak, PutDeviceApiKeyRequest request){
        dak.setValidUntil(request.getValidUntil());
        dak.setRoom(request.getRoom());
        return dak;
    }

}