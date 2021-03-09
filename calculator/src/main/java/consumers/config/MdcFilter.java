package consumers.config;

import core.Constants;
import java.util.UUID;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class MdcFilter implements MethodInterceptor {
  @Override public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    Object[] args = methodInvocation.getArguments();
    Message message = (Message) args[1];
    String requestId =
        (String) message.getMessageProperties().getHeaders().get(Constants.REQUEST_ID);
    if (requestId == null || requestId.isEmpty()) {
      requestId = UUID.randomUUID().toString();
      message.getMessageProperties().setHeader(Constants.REQUEST_ID, requestId);
    }

    MDC.put(Constants.REQUEST_ID, requestId);
    try {
      return methodInvocation.proceed();
    } finally {
      MDC.remove(Constants.REQUEST_ID);
    }
  }
}
