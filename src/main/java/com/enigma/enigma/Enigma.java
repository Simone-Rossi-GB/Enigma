package com.enigma.enigma;

public class Enigma {
    private Rotore[] rotori;
    private Riflessore riflessore;
    private PannelloScambiatore pannello;

    public Enigma(Rotore rotore1, Rotore rotore2, Rotore rotore3, Riflessore riflessore, PannelloScambiatore pannello) {
        this.rotori = new Rotore[3];
        this.rotori[0] = rotore3; // rotore di destra (primo nella catena)
        this.rotori[1] = rotore2; // rotore centrale
        this.rotori[2] = rotore1; // rotore di sinistra (ultimo nella catena)
        this.riflessore = riflessore;
        this.pannello = pannello;
    }

    public char codifica(char carattere) {
        // Controlla se il carattere è una lettera
        if (carattere < 'A' || carattere > 'Z') {
            return carattere;
        }

        // Rotazione dei rotori
        ruotaRotori();

        // Passaggio attraverso il pannello scambiatore
        carattere = pannello.codifica(carattere);

        // Passaggio attraverso i rotori (andata)
        for (int i = 0; i < 3; i++) {
            carattere = rotori[i].codifica(carattere);
        }

        // Passaggio attraverso il riflettore
        carattere = riflessore.codifica(carattere);

        // Passaggio attraverso i rotori (ritorno)
        for (int i = 2; i >= 0; i--) {
            carattere = rotori[i].codificaInversa(carattere);
        }

        // Passaggio attraverso il pannello scambiatore
        carattere = pannello.codifica(carattere);

        return carattere;
    }

    private void ruotaRotori() {
        // Verifica se il rotore medio deve ruotare (double stepping)
        boolean rotoreMedioAlNotch = rotori[1].alNotch();

        // Verifica se il primo rotore è al notch
        boolean rotoreDxAlNotch = rotori[0].alNotch();

        // Il rotore di destra ruota sempre
        rotori[0].ruota();

        // Verifica se il rotore medio deve ruotare
        if (rotoreDxAlNotch || rotoreMedioAlNotch) {
            rotori[1].ruota();
        }

        // Verifica se il rotore sinistro deve ruotare
        if (rotoreMedioAlNotch) {
            rotori[2].ruota();
        }
    }

    public void impostaPosizioni(char pos1, char pos2, char pos3) {
        rotori[2].setPosizione(pos1); // rotore sinistro
        rotori[1].setPosizione(pos2); // rotore medio
        rotori[0].setPosizione(pos3); // rotore destro
    }

    public char[] getPosizioniRotori() {
        char[] posizioni = new char[3];
        posizioni[0] = rotori[2].getPosizioneLettera(); // rotore sinistro
        posizioni[1] = rotori[1].getPosizioneLettera(); // rotore medio
        posizioni[2] = rotori[0].getPosizioneLettera(); // rotore destro
        return posizioni;
    }

    public PannelloScambiatore getPannelloScambiatore() {
        return pannello;
    }
}
