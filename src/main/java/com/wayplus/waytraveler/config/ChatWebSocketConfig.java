package com.wayplus.waytraveler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정
        config.enableSimpleBroker("/group","/private"); // "/topic"으로 시작하는 주제를 가진 클라이언트에게 메시지 전달
        config.setApplicationDestinationPrefixes("/app"); // "/app"으로 시작하는 메시지가 @MessageMapping 주석이 달린 메서드로 라우팅됨
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 엔드포인트 등록
        registry.addEndpoint("/chat").withSockJS(); // "/chat" 엔드포인트를 SockJS를 사용하여 등록
    }
}
