package service;

import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcRestController {
  @Autowired private RabbitTemplate rabbitTemplate;
  private static final String CALC_DIRECT_EXCHANGE = Constants.CALC_DIRECT_EXCHANGE;

  @GetMapping(value = "/sum")
  public ResponseEntity<CalcResult> sum(@RequestParam("a") String a, @RequestParam("b") String b) {
    Operands operan = new Operands(new BigDecimal(a), new BigDecimal(b));
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.SUM, operan);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/subtract")
  public ResponseEntity<CalcResult> subtract(@RequestParam("a") String a,
      @RequestParam("b") String b) {
    Operands operan = new Operands(new BigDecimal(a), new BigDecimal(b));
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.SUBTRACT, operan);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/divide")
  public ResponseEntity<CalcResult> divide(@RequestParam("a") String a,
      @RequestParam("b") String b) {
    Operands operan = new Operands(new BigDecimal(a), new BigDecimal(b));
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.DIVIDE, operan);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/multiply")
  public ResponseEntity<CalcResult> multiply(@RequestParam("a") String a,
      @RequestParam("b") String b) {
    Operands operan = new Operands(new BigDecimal(a), new BigDecimal(b));
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.MULTIPLY, operan);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }
}
