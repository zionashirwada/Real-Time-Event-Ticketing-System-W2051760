package lk.W2051760.ticketing_system_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define the WebSocket endpoint that clients will use to connect
        registry.addEndpoint("/ws-ticketing")
                .setAllowedOriginPatterns("*") // Adjust allowed origins as needed
                .withSockJS(); // Fallback options for browsers that don’t support WebSocket
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefix for messages bound for methods annotated with @MessageMapping
        config.setApplicationDestinationPrefixes("/app");

        // Enable a simple in-memory broker with destinations prefixed with /topic
        config.enableSimpleBroker("/topic");
    }
}
