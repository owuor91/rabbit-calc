package consumers;

import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { Constants.DIVIDE_QUEUE })
public class DivideConsumer {
  @RabbitHandler
  public BigDecimal receiveMessage(Operands operan){
    return operan.getA().divide(operan.getB(), 10, RoundingMode.FLOOR);
  }
}
