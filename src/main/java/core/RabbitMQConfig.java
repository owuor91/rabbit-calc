package core;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  @Bean Queue sumQueue() {
    return new Queue(Constants.SUM_QUEUE, false);
  }

  @Bean Queue subtractQueue() {
    return new Queue(Constants.SUBTRACT_QUEUE, false);
  }

  @Bean Queue multiplyQueue() {
    return new Queue(Constants.MULTIPLY_QUEUE, false);
  }

  @Bean Queue divideQueue() {
    return new Queue(Constants.DIVIDE_QUEUE, false);
  }

  @Bean DirectExchange directExchange() {
    return new DirectExchange(Constants.CALC_DIRECT_EXCHANGE);
  }

  @Bean Binding sumBinding(Queue sumQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(sumQueue).to(directExchange).with(Constants.SUM);
  }

  @Bean Binding subtractBinding(Queue subtractQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(subtractQueue).to(directExchange).with(Constants.SUBTRACT);
  }

  @Bean Binding multiplyBinding(Queue multiplyQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(multiplyQueue).to(directExchange).with(Constants.MULTIPLY);
  }

  @Bean Binding divideBinding(Queue divideQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(divideQueue).to(directExchange).with(Constants.DIVIDE);
  }
}
