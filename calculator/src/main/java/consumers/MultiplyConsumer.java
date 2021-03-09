package consumers;


import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { Constants.MULTIPLY_QUEUE })
public class MultiplyConsumer {
  private static Logger logger = LoggerFactory.getLogger(MultiplyConsumer.class);

  @RabbitHandler
  public BigDecimal receiveMessage(Operands operands) {
    logger.info("Received message " + operands);
    BigDecimal result = operands.getA().multiply(operands.getB());
    logger.info("Multiplication result: " + result);
    return result;
  }
}
