package in.tjx.producer;

import in.tjx.config.RabbitMQConfig;
import in.tjx.entity.Order;
import in.tjx.entity.OrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/order")
    public OrderDTO placeOrder(@RequestBody Order order) {
        OrderDTO orderDTO = new OrderDTO(order, "Order Submitted", "Order Submitted Successfully.");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_NAME, orderDTO);
        return orderDTO;
    }
}