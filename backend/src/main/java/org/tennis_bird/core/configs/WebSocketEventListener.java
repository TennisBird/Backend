//package org.tennis_bird.core.configs;
//
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandler;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.tennis_bird.core.services.chat.ChatService;
//
//@Component
//public class WebSocketEventListener implements StompSessionHandler {
//
//    @Autowired
//    private ChatService chatService;
//
//    @Override
//    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username;
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            username = authentication.getName();
//            chatService.addUser(username);
//        }
//    }
//
//    public void handleSessionDisconnect(StompSession session) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username;
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            username = authentication.getName();
//            chatService.removeUser(username);
//        }
//    }
//
//    @Override
//    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
//        //TODO
//    }
//
//    @Override
//    public void handleTransportError(StompSession session, Throwable exception) {
//        //TODO
//    }
//
//    @Override
//    public void handleFrame(StompHeaders headers, Object payload) {
//        //TODO
//    }
//
//    @NotNull
//    @Override
//    public Class<?> getPayloadType(StompHeaders headers) {
//        return String.class;
//    }
//}