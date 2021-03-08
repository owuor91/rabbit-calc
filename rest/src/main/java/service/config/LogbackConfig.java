package service.config;

import ch.qos.logback.access.servlet.TeeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackConfig {
  @Bean
  public FilterRegistrationBean loggingFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new TeeFilter());
    return filterRegistrationBean;
  }
}
