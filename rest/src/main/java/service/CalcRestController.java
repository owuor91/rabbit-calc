package service;

import core.Constants;
import core.Operands;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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
  public ResponseEntity<CalcResult> sum(@RequestParam("a") String a, @RequestParam("b") String b)
      throws NumberFormatException {
    BigDecimal result = sanitizeAndSendMessage(a, b, Constants.SUM);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/subtract")
  public ResponseEntity<CalcResult> subtract(@RequestParam("a") String a,
      @RequestParam("b") String b) throws NumberFormatException {
    BigDecimal result = sanitizeAndSendMessage(a, b, Constants.SUBTRACT);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/divide")
  public ResponseEntity<CalcResult> divide(@RequestParam("a") String a,
      @RequestParam("b") String b) throws DivisionByZeroException, NumberFormatException {
    List<String> forbidden = Arrays.asList("0", "0.0");
    if (forbidden.contains(a) || forbidden.contains(b)) {
      throw new DivisionByZeroException("Division by zero forbidden");
    }
    BigDecimal result = sanitizeAndSendMessage(a, b, Constants.DIVIDE);
    return new ResponseEntity<CalcResult>(new CalcResult(result), HttpStatus.OK);
  }

  @GetMapping(value = "/multiply")
  public ResponseEntity<CalcResult> multiply(@RequestParam("a") String a,
      @RequestParam("b") String b) throws NumberFormatException {
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

  private BigDecimal sanitizeAndSendMessage(String a, String b, String operation)
      throws NumberFormatException {
    logger.info("received payload /" + operation + "?a=" + a + "&b=" + b);
    try {
      BigDecimal operand1 = new BigDecimal(a);
      BigDecimal operand2 = new BigDecimal(b);
      Operands operands = new Operands(operand1, operand2);
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
