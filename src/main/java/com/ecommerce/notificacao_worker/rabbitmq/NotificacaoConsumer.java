package com.ecommerce.notificacao_worker.rabbitmq;

import com.ecommerce.notificacao_worker.model.NotificacaoPedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificacaoConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = {"notificacao-queue"})
    public void receive(@Payload String message) {
        log.info("Mensagem recebida: {}", message);
        try {
            objectMapper.readValue(message, NotificacaoPedido.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            log.error("Erro ao desserializar mensagem", e);
        }
    }
}
