package com.predoana.app.chat.controller;

import com.predoana.app.chat.model.ChatInMessage;
import com.predoana.app.chat.model.ChatOutMessage;

public class ChatController {

    public ChatOutMessage handleMessaging(ChatInMessage message) throws Exception {
        return new ChatOutMessage();
    }
}