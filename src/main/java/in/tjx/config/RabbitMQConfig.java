package in.tjx.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //    @Value("${rabbitmq.queue.simple_msg_queue_name}")
    public static final String SIMPLE_MSG_QUEUE_NAME = "rabbit_mq_simple_msg_queue";
    public static final String ROUTING_KEY_NAME = "rabbit_mq_simple_msg_routing_key";
    public static final String EXCHANGE_NAME = "rabbit_mq_exchange";

    @Bean
    public Queue simpleMsgQueue() {
        return new Queue(SIMPLE_MSG_QUEUE_NAME);
    }

//    @Bean
//    public Exchange simpleMsgExchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }

    @Bean
    public DirectExchange simpleMsgExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding simpleMsgBinding(Queue simpleMsgQueue, DirectExchange simpleMsgExchange) {
        return BindingBuilder.bind(simpleMsgQueue).to(simpleMsgExchange).with(ROUTING_KEY_NAME);
    }

    // java.lang.IllegalArgumentException: SimpleMessageConverter only supports String, byte[] and Serializable payloads, received: in.tjx.entity.OrderDTO
    // If we want to send Objects or JSON messages, then add the following configurations

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    // Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
