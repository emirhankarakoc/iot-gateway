package com.aselsis.iot.gateway.soketconfig;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {

    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
       server.addEventListener("get_message", Message.class, onChatReceived());
       //usttekini kapatirsaniz socket-socket iletisimi kopuyor.

    }
    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            String room = senderClient.getHandshakeData().getSingleUrlParam("room");
            log.info(data.toString());
            socketService.sendMessage(room,"get_message", senderClient, data);
        };
    }
}