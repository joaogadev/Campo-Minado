package br.com.projeto.except;

public class SairException extends RuntimeException {

    public String getMessage() {
        return "Usuário saiu do jogo";
    }

}
