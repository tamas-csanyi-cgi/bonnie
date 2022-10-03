package com.cgi.hexagon.kafka.messages;

import com.cgi.hexagon.communicationplugin.MessageService;
import com.cgi.hexagon.communicationplugin.SendRequest;

public class Sender implements MessageService {
    public Sender() {
        messagingService = new MessagingService();
    }
    MessagingService messagingService;
    @Override
    public void send(SendRequest request) {
        messagingService.sendMessage("test-topic", request);
    }
}
