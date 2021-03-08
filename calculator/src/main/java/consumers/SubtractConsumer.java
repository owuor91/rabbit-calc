package consumers;

import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { Constants.SUBTRACT_QUEUE })
public class SubtractConsumer {
  @RabbitHandler
  public BigDecimal receiveMessage(Operands operan){
    return operan.getA().subtract(operan.getB());
  }
}
