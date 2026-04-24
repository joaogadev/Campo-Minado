package br.com.projeto.except;

public class ValorCorretoException extends RuntimeException {
  public ValorCorretoException(String message) {
    super(message);
  }
}
