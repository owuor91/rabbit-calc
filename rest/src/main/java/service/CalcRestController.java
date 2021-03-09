package service;

import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
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
  private static Logger logger = LoggerFactory.getLogger(CalcRestController.class);

  @GetMapping(value = "/sum")
  public ResponseEntity<CalcResult> sum(@RequestParam("a") String a, @RequestParam("b") String b) {
    logger.info("received payload /sum?a=" + a + "&b=" + b);
    Operands operands = new Operands(new BigDecimal(a), new BigDecimal(b));
    setRequestId();
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.SUM, operands);
    logger.info("Received result " + result);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/subtract")
  public ResponseEntity<CalcResult> subtract(@RequestParam("a") String a,
      @RequestParam("b") String b) {
    logger.info("received payload /subtract?a=" + a + "&b=" + b);
    Operands operands = new Operands(new BigDecimal(a), new BigDecimal(b));
    setRequestId();
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.SUBTRACT, operands);
    logger.info("Received result " + result);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/divide")
  public ResponseEntity<CalcResult> divide(@RequestParam("a") String a,
      @RequestParam("b") String b) {
    logger.info("received payload /divide?a=" + a + "&b=" + b);
    Operands operands = new Operands(new BigDecimal(a), new BigDecimal(b));
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.DIVIDE, operands);
    logger.info("Received result " + result);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/multiply")
  public ResponseEntity<CalcResult> multiply(@RequestParam("a") String a,
      @RequestParam("b") String b) {
    logger.info("received payload /multiply?a=" + a + "&b=" + b);
    Operands operands = new Operands(new BigDecimal(a), new BigDecimal(b));
    BigDecimal result =
        (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
            Constants.MULTIPLY, operands);
    logger.info("Received result " + result);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  private void setRequestId() {
    rabbitTemplate.setBeforePublishPostProcessors(message -> {
      String requestId = MDC.get(Constants.REQUEST_ID);
      if (requestId != null && !requestId.isEmpty()) {
        message.getMessageProperties().setHeader(Constants.REQUEST_ID, requestId);
      }
      return message;
    });
  }
}
