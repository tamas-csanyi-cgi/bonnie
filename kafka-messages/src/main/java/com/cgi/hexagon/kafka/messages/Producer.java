package com.cgi.hexagon.kafka.messages;

import com.cgi.hexagon.communicationplugin.MessageService;
import com.cgi.hexagon.communicationplugin.SendRequest;

public class Producer implements MessageService {

    public Producer() {
        messagingService = new MessagingService();
    }
    MessagingService messagingService;
    @Override
    public void send(SendRequest request) {
        messagingService.sendMessage("test-topic", request);
        //messagingService.readMessages();
    }
}
