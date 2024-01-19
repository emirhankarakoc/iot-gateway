package com.aselsis.iot.gateway.clientapikey;

import com.aselsis.iot.gateway.clientapikey.request.PostClientApiKeyRequest;
import com.aselsis.iot.gateway.clientapikey.request.PutClientApiKeyRequest;
import jakarta.persistence.*;
import lombok.*;

import static com.aselsis.iot.gateway.utils.apigenerator.ApiGenerator.generateApiKey;

@Entity
@Data
public class ClientApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "token")
    private String token;
    @Column(name = "appName")
    private String appName;
    @Column(name = "room")
    private String room;

    public static ClientApiKey create(PostClientApiKeyRequest request){
        ClientApiKey response = new ClientApiKey();
        response.room = request.getRoom();
        response.appName = request.getAppName();
        response.setToken(generateApiKey(32));
        return response;}
    public static ClientApiKey update(ClientApiKey clientApiKey, PutClientApiKeyRequest request){
        clientApiKey.setRoom(request.getRoom());
        clientApiKey.setAppName(request.getAppName());
        return clientApiKey;
    }





}

