package br.com.projeto;

import br.com.projeto.model.Tabuleiro;
import br.com.projeto.visual.TabuleiroConsole;

public class Main {
    public static void main(String[] args) {
        Tabuleiro tab = new Tabuleiro(6, 6, 6);

        new TabuleiroConsole(tab);

    }
}
