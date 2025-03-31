package com.enigma.enigma;

public class PannelloScambiatore {
    private char[] mappatura;

    public PannelloScambiatore() {
        // Inizialmente nessuna coppia è scambiata
        mappatura = new char[26];
        for (int i = 0; i < 26; i++) {
            mappatura[i] = (char)('A' + i);
        }
    }

    public void aggiungiScambio(char a, char b) {
        mappatura[a - 'A'] = b;
        mappatura[b - 'A'] = a;
    }

    public void rimuoviScambio(char a, char b) {
        mappatura[a - 'A'] = a;
        mappatura[b - 'A'] = b;
    }

    public char codifica(char carattere) {
        return mappatura[carattere - 'A'];
    }

    public boolean haScambio(char lettera) {
        return mappatura[lettera - 'A'] != lettera;
    }

    public char getScambio(char lettera) {
        return mappatura[lettera - 'A'];
    }
}
