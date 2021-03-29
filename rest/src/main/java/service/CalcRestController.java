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
import service.errors.DivisionByZeroException;

@RestController
public class CalcRestController {
  @Autowired private RabbitTemplate rabbitTemplate;
  private static final String CALC_DIRECT_EXCHANGE = Constants.CALC_DIRECT_EXCHANGE;
  private static Logger logger = LoggerFactory.getLogger(CalcRestController.class);

  @GetMapping(value = "/sum")
  public ResponseEntity<CalcResult> sum(@RequestParam("a") BigDecimal a,
      @RequestParam("b") BigDecimal b)
      throws NumberFormatException {
    BigDecimal result = sanitizeAndSendMessage(a, b, Constants.SUM);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/subtract")
  public ResponseEntity<CalcResult> subtract(@RequestParam("a") BigDecimal a,
      @RequestParam("b") BigDecimal b) throws NumberFormatException {
    BigDecimal result = sanitizeAndSendMessage(a, b, Constants.SUBTRACT);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/divide")
  public ResponseEntity<CalcResult> divide(@RequestParam("a") BigDecimal a,
      @RequestParam("b") BigDecimal b) throws DivisionByZeroException, NumberFormatException {
    if (b.equals(0.0)) {
      throw new DivisionByZeroException("Division by zero forbidden");
    }
    BigDecimal result = sanitizeAndSendMessage(a, b, Constants.DIVIDE);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/multiply")
  public ResponseEntity<CalcResult> multiply(@RequestParam("a") BigDecimal a,
      @RequestParam("b") BigDecimal b) throws NumberFormatException {
    BigDecimal result = sanitizeAndSendMessage(a, b, Constants.MULTIPLY);
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

  private BigDecimal sanitizeAndSendMessage(BigDecimal a, BigDecimal b, String operation)
      throws NumberFormatException {
    logger.info("received payload /" + operation + "?a=" + a + "&b=" + b);
    try {
      Operands operands = new Operands(a, b);
      setRequestId();
      BigDecimal result = (BigDecimal) rabbitTemplate.convertSendAndReceive(CALC_DIRECT_EXCHANGE,
          operation, operands);
      logger.info("Received result " + result);
      return result;
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid operand passed");
    }
  }
}
