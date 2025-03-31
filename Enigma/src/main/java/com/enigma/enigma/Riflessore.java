package com.enigma.enigma;

public class Riflessore {
    private char[] mappatura;

    public Riflessore(String mappatura) {
        this.mappatura = mappatura.toCharArray();
    }

    public char codifica(char carattere) {
        return mappatura[carattere - 'A'];
    }
}
