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
@RabbitListener(queues = { Constants.SUM_QUEUE })
public class SumConsumer{
  private static Logger logger = LoggerFactory.getLogger(SumConsumer.class);

  @RabbitHandler
  public BigDecimal receiveMessage(Operands operands) {
    logger.info("received message " + operands);
    BigDecimal result = operands.getA().add(operands.getB());
    logger.info("Result from sum: " + result);
    return result;
  }
}
