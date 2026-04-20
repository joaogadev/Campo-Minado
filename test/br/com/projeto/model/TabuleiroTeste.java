package br.com.projeto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TabuleiroTeste {
    Tabuleiro tab = new Tabuleiro(3, 3, 0);

    @Test
    void testarCriarTabuleiro() {
        assertNotNull(tab);
    }

    @Test
    void testarAbrir() {
        assertDoesNotThrow(() -> tab.abrir(0, 0));
    }

    /*@Test
    void testeMarcarCampo() {
        assertTrue(tab.isValido(4, 4));
    }*/

    @Test
    void testarObjetivoAlcancado() {
        Tabuleiro tab = new Tabuleiro(3, 3, 0);

        for (int l = 0; l < 3; l++) {
            for (int c = 0; c < 3; c++) {
                tab.abrir(l, c);
            }
        }

        assertTrue(tab.objetivoAlcancado());
    }
}
