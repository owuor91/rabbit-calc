package consumers;

import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { Constants.DIVIDE_QUEUE })
public class DivideConsumer {
  private static Logger logger = LoggerFactory.getLogger(DivideConsumer.class);

  @RabbitHandler
  public BigDecimal receiveMessage(Operands operands) {
    logger.info("received message " + operands);
    BigDecimal result = operands.getA().divide(operands.getB(), 10, RoundingMode.FLOOR);
    logger.info("Division result: " + result);
    return result;
  }
}
