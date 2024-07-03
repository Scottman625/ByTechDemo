package com.bytech.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessageToUser(String userId, String message) {
        messagingTemplate.convertAndSend("/topic/assets_user_" + userId, message);
    }

    public void sendBroadcast(String message) {
        messagingTemplate.convertAndSend("/topic/broadcast", message);
    }
}
