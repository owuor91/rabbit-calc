package service.errors;

public class DivisionByZeroException extends Exception {
  public DivisionByZeroException(String message) {
    super(message);
  }
}
