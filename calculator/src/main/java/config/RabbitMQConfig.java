package config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  @Bean
  public ConnectionFactory connectionFactory() {
    return new CachingConnectionFactory("localhost");
  }

  @Bean
  public RabbitAdmin rabbitAdmin() {
    ConnectionFactory connectionFactory;
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
    rabbitAdmin.declareExchange(directExchange());
    rabbitAdmin.setIgnoreDeclarationExceptions(true);
    return rabbitAdmin;
  }

  @Bean Queue sumQueue() {
    return new Queue("sumQueue", false);
  }

  @Bean Queue subtractQueue() {
    return new Queue("subtractQueue", false);
  }

  @Bean Queue multiplyQueue() {
    return new Queue("multiplyQueue", false);
  }

  @Bean Queue divideQueue() {
    return new Queue("divideQueue", false);
  }

  @Bean DirectExchange directExchange() {
    return new DirectExchange("calc-direct-exchange");
  }

  @Bean Binding sumBinding(Queue sumQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(sumQueue).to(directExchange).with("sum");
  }

  @Bean Binding subtractBinding(Queue subtractQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(subtractQueue).to(directExchange).with("subtract");
  }

  @Bean Binding multiplyBinding(Queue multiplyQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(multiplyQueue).to(directExchange).with("multiply");
  }

  @Bean Binding divideBinding(Queue divideQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(divideQueue).to(directExchange).with("divide");
  }

  @Bean RabbitTemplate rabbitTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
    rabbitTemplate.setMessageConverter(new JacksonMessageConverter());
    return rabbitTemplate;
  }
}
