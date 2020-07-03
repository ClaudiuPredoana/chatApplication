package com.predoana.app.chat.controller;


import com.predoana.app.chat.model.ChatInMessage;
import com.predoana.app.chat.model.ChatOutMessage;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {

    @MessageMapping("/guestchat")
    @SendTo("/topic/guestchats")
    public ChatOutMessage handleMessaging(ChatInMessage message) throws Exception {
        Thread.sleep(1000); // simulate delay

        message=null;
        message.getMessage();


        return new ChatOutMessage(HtmlUtils.htmlEscape(message.getMessage()));
    }

    @MessageMapping("/guestupdate")
    @SendTo("/topic/guestupdates")
    public ChatOutMessage handleUserIsTyping(ChatInMessage message) throws Exception {
        return new ChatOutMessage("Someone is typing...");
    }

    @MessageMapping("/guestjoin")
    @SendTo("/topic/guestnames")
    public ChatOutMessage handleMemberJoins(ChatInMessage message) throws Exception {
        return new ChatOutMessage(message.getMessage());
    }

    @MessageExceptionHandler
    @SendTo("/topic/errors")
    public ChatOutMessage handleException(Throwable exception){

    ChatOutMessage myError = new ChatOutMessage("An error happened.");
		return myError;
    }
}
