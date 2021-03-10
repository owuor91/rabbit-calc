package service.errors;

public class InvalidOperandException extends Exception {
  public InvalidOperandException(String message) {
    super(message);
  }
}
