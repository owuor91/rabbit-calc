package consumers;


import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { Constants.SUM_QUEUE })
public class SumConsumer{
  @RabbitHandler
  public BigDecimal receiveMessage(Operands operan){
    return operan.getA().add(operan.getB());
  }
}
