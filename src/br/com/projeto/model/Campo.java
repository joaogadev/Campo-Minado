package br.com.projeto.model;

import br.com.projeto.except.Explosion;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;

    private boolean aberto;
    private boolean minado;
    private boolean marcado;

    private List<Campo> vizinhos = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
        }
    }
    boolean abrir() {
        if (!aberto && !marcado) {
            aberto = true;
            if (minado) {
                throw new Explosion();
            }
            if (vizinhancaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }
    boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar() {
        minado = true;
    }

    public boolean isMarcado() {
        return marcado;
    }
    public boolean isAberto() {
        return aberto;
    }
    public boolean isFechado() {
        return !aberto;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado() {
        boolean desnvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desnvendado || protegido;
    }
    long minasNaVizinhanca() {
        return vizinhos.stream()
                .filter(v -> v.minado)
                .count();
    }
    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
    }
    public String toString() {
        if (marcado) {
            return "x";
        } else if (aberto && minado) {
            return "\u001B[31m" + "*" + "\u001B[0m"; //vermelho
        } else if (aberto && minasNaVizinhanca() > 0) {
            if (minasNaVizinhanca() == 1) {
                return "\u001B[34m" + Long.toString(minasNaVizinhanca()) + "\u001B[0m"; //verde
            } else if (minasNaVizinhanca() == 2) {
                return "\u001B[32m" + Long.toString(minasNaVizinhanca()) + "\u001B[0m"; //azul
            } else if (minasNaVizinhanca() >= 3) {
                return "\u001B[35m" + Long.toString(minasNaVizinhanca()) + "\u001B[0m"; //roxo
            }
        } else if (aberto) {
            return " ";
        }
        return "?";
    }
}
