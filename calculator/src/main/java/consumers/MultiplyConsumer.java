package consumers;


import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { Constants.MULTIPLY_QUEUE })
public class MultiplyConsumer {
  @RabbitHandler
  public BigDecimal receiveMessage(Operands operand){
    return operand.getA().multiply(operand.getB());
  }
}
