package com.predoana.app.chat.controller;


import com.predoana.app.chat.model.ChatInMessage;
import com.predoana.app.chat.model.ChatOutMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/guestchat")
    @SendTo("/topic/guestchats")
    public ChatOutMessage handleMessaging(ChatInMessage message) throws Exception {
        Thread.sleep(1000); // simulate delay

        return new ChatOutMessage(message.getMessage());
    }

    @MessageMapping("/guestupdate")
    @SendTo("/topic/guestupdates")
    public ChatOutMessage handleUserIsTyping(ChatInMessage message) throws Exception {
        return new ChatOutMessage("Someone is typing...");
    }

    public ChatOutMessage handleException(Throwable exception){
        ChatOutMessage myError = new ChatOutMessage("An error happened...");
        return myError;
    }







}
