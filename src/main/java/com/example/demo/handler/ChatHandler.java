package com.example.demo.handler;

import org.springframework.web.socket.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatHandler implements WebSocketHandler {

    private Map<Long, WebSocketSession> sessions = new HashMap<>();

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long chatroomId = getChatRoomIdFromSession(session);
        sessions.put(chatroomId,session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Long chatroomId = getChatRoomIdFromSession(session);
        WebSocketSession otherSession = sessions.get(chatroomId);
        if(otherSession != null){
            try {
                otherSession.sendMessage(new TextMessage("Полученное сообщение: " + message.getPayload()));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        Long chatroomId = getChatRoomIdFromSession(session);
        sessions.remove(chatroomId);
    }

    private Long getChatRoomIdFromSession(WebSocketSession session){
        String path = session.getUri().toString();
        String[] pathSegments = path.split("/");
        if (pathSegments.length >= 3){
            try {
                return Long.parseLong(pathSegments[2]);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }else {
            throw new EntityNotFoundException();
        }
        return null;
    }
}
