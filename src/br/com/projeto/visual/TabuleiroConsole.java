package br.com.projeto.visual;

import br.com.projeto.except.ValorCorretoException;
import br.com.projeto.except.Explosion;
import br.com.projeto.except.SairException;
import br.com.projeto.model.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {
    private Tabuleiro tab;
    private Scanner sc = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tab) {
        this.tab = tab;

        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;

            while (continuar) {
                cicloDoJogo();

                System.out.println("Outra partida?: (S/n)");
                String resp = sc.nextLine();
                if ("n".equalsIgnoreCase(resp)) continuar = false;
                else {tab.reiniciar();}
            }
        } catch (SairException e) {
            System.out.println("Até Mais!!!");
            e.getMessage();
        } finally {
            sc.close(); //garante a finalização independente se cai na exceção
        }
    }

    private void cicloDoJogo() {
        try {
            while(!tab.objetivoAlcancado())/*caso abra um minado caíra na exceção*/ {
                System.out.println(tab);
                System.out.println("Para sair do jogo digite `sair`");
                try {
                    String digitado = pegarValorDigitado("Digite (x, y): ");
                    //verifica se o valor nn dá bo
                    TabuleiroConsole.validarDigito(digitado);

                    //Mapeamento que transforma tipos strings em um array para Integer
                    //Quebra os valores por virgula e pega eles
                    Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                            .map(e -> Integer.parseInt(e.trim())).iterator();

                    digitado = pegarValorDigitado("Digite (x, y): ");
                    //verifica se o valor nn dá bo
                    validarDigito(digitado);

                    if ("1".equals(digitado)) {
                        tab.abrir(xy.next(), xy.next());
                    } else if ("2".equals(digitado)) {
                        tab.abrir(xy.next(), xy.next());
                    }
                } catch (ValorCorretoException e) {
                    System.out.println("Entrada inválida, digite apenas números separados por vírgula.");
                }
            }

            System.out.println("Você ganhou!!!");
        } catch (Explosion e) {
            System.out.println("Você perdeu!!");
        }
    }

    private String pegarValorDigitado(String texto) {
        System.out.print(texto);
        String digitado = sc.nextLine();
        if ("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        } return digitado;
    }
    public static void validarDigito(String digitado) throws ValorCorretoException {
        if (!digitado.matches("\\d+,\\d+")) {
            throw new ValorCorretoException("Entrada inválida, digite apenas números separados por vírgula.");
        }
    }
}
