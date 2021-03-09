package consumers;

import core.Operands;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestConsumers {
  private SumConsumer sumConsumer;
  private SubtractConsumer subtractConsumer;
  private MultiplyConsumer multiplyConsumer;
  private DivideConsumer divideConsumer;

  @Before
  public void setUp() {
    sumConsumer = new SumConsumer();
    subtractConsumer = new SubtractConsumer();
    multiplyConsumer = new MultiplyConsumer();
    divideConsumer = new DivideConsumer();
  }

  @Test
  public void testSum() {
    Operands operands = new Operands(new BigDecimal(21), new BigDecimal("34.5"));
    assertEquals(sumConsumer.receiveMessage(operands), new BigDecimal("55.5"));
  }

  @Test
  public void testSubtract() {
    Operands operands = new Operands(new BigDecimal(215), new BigDecimal(45));
    assertEquals(subtractConsumer.receiveMessage(operands), new BigDecimal(170));
  }

  @Test
  public void testMultiply() {
    Operands operands = new Operands(new BigDecimal(21), new BigDecimal(5));
    assertEquals(multiplyConsumer.receiveMessage(operands), new BigDecimal(105));
  }

  @Test
  public void testDivide() {
    Operands operands = new Operands(new BigDecimal(210), new BigDecimal(2));
    assertEquals(divideConsumer.receiveMessage(operands), new BigDecimal("105.0000000000"));
  }
}
