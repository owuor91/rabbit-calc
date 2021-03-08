package core;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Operands {
  private BigDecimal a;
  private BigDecimal b;
}
