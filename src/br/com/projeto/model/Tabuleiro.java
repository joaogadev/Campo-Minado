package br.com.projeto.model;

import br.com.projeto.except.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private int minas;

    private List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(int linhas, int coluna) {
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linhas && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrir());
        } catch (Explosion e) {
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void marcar(int linhas, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linhas && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        while (minasArmadas < minas) {
            int aleatorio = (int)(Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        }
    }

    private void associarVizinhos() {
        for (Campo c1: campos) {
            for (Campo c2: campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void gerarCampos() {
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                campos.add(new Campo(l, c));
            }
        }
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean isValido(int li, int co) {
        return li >= 0 && li < linhas && co >= 0 && co < colunas;
    }
}
