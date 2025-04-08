package com.enigma.enigma;

public class Rotore {
    private char[] mappatura;
    private char[] mappaturaInversa;
    private int posizione;
    private char anelloImpostazione;
    private char notch; // punto di scatto per la rotazione del rotore successivo

    public Rotore(String mappatura, char notch) {
        this.mappatura = mappatura.toCharArray();
        this.mappaturaInversa = new char[26];
        this.notch = notch;
        this.posizione = 0;
        this.anelloImpostazione = 'A';

        // Calcola la mappatura inversa
        for (int i = 0; i < 26; i++) {
            char carattereMappato = this.mappatura[i];
            this.mappaturaInversa[carattereMappato - 'A'] = (char)('A' + i);
        }
    }

    public char codifica(char carattere) {
        int offset = posizione - (anelloImpostazione - 'A');
        if (offset < 0) offset += 26;

        int pos = carattere - 'A';
        pos = (pos + offset) % 26;

        char mappato = mappatura[pos];

        return (char)(((mappato - 'A' - offset) % 26 + 26) % 26 + 'A');
    }

    public char codificaInversa(char carattere) {
        int offset = posizione - (anelloImpostazione - 'A');
        if (offset < 0) offset += 26;

        int pos = carattere - 'A';
        pos = (pos + offset) % 26;

        char mappato = mappaturaInversa[pos];

        return (char)(((mappato - 'A' - offset) % 26 + 26) % 26 + 'A');
    }

    public void ruota() {
        posizione = (posizione + 1) % 26;
    }

    public boolean alNotch() {
        return (posizione == (notch - 'A'));
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione % 26;
    }

    public void setPosizione(char posizione) {
        this.posizione = (posizione - 'A') % 26;
    }

    public int getPosizione() {
        return posizione;
    }

    public char getPosizioneLettera() {
        return (char)('A' + posizione);
    }
}
