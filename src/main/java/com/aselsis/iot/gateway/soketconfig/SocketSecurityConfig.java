package com.aselsis.iot.gateway.soketconfig;

import com.aselsis.iot.gateway.deviceapikey.DeviceApiKeyService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class SocketSecurityConfig {

    private final String API_TOKEN = "API_TOKEN";

    private final SocketIOServer server;

    private final DeviceApiKeyService deviceApiKeyService;

    @PostConstruct
    public void init(){
        server.addConnectListener(onConnected());
    }

    private ConnectListener onConnected() {
        return (client) -> {
            String token = client.getHandshakeData().getHttpHeaders().get(API_TOKEN);
            String room = client.getHandshakeData().getSingleUrlParam("room");

            if(isAuthorized(token,room)){
                client.joinRoom(room);
                log.info("Connected : " + client.getSessionId());
            }else {
                log.warn("Socket ID[{}] Unauthorized", client.getSessionId().toString());
                client.disconnect();
                log.info("Disconnected : " + client.getSessionId());
            }
        };
    }
    private boolean isAuthorized(String token, String room){
        if(deviceApiKeyService.findByTokenAndRoomId(token, room).isPresent())
            return true;
        else return false;
    }
}