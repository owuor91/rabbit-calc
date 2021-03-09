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
@RabbitListener(queues = { Constants.SUBTRACT_QUEUE })
public class SubtractConsumer {
  private static Logger logger = LoggerFactory.getLogger(SubtractConsumer.class);

  @RabbitHandler
  public BigDecimal receiveMessage(Operands operands) {
    logger.info("received message " + operands);
    BigDecimal result = operands.getA().subtract(operands.getB());
    logger.info("Subtraction result: " + result);
    return result;
  }
}
