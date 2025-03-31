package com.enigma.enigma;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EnigmaController implements Initializable {

    // Componenti FXML
    @FXML
    private ComboBox<Integer> rotore1Box;
    @FXML
    private ComboBox<Integer> rotore2Box;
    @FXML
    private ComboBox<Integer> rotore3Box;
    @FXML
    private ComboBox<String> riflessoreBox;
    @FXML
    private ComboBox<Character> pos1Box;
    @FXML
    private ComboBox<Character> pos2Box;
    @FXML
    private ComboBox<Character> pos3Box;
    @FXML
    private Button resetBtn;
    @FXML
    private TextArea inputText;
    @FXML
    private TextArea outputText;

    // Lampade
    @FXML
    private Circle lampA, lampB, lampC, lampD, lampE, lampF, lampG, lampH, lampI, lampJ;
    @FXML
    private Circle lampK, lampL, lampM, lampN, lampO, lampP, lampQ, lampR, lampS, lampT;
    @FXML
    private Circle lampU, lampV, lampW, lampX, lampY, lampZ;

    // Campi del pannello scambiatore
    @FXML
    private TextField plug1a, plug1b, plug2a, plug2b, plug3a, plug3b, plug4a, plug4b, plug5a, plug5b;
    @FXML
    private TextField plug6a, plug6b, plug7a, plug7b, plug8a, plug8b, plug9a, plug9b, plug10a, plug10b;

    // Modello Enigma
    private Enigma enigma;
    private Map<Character, Circle> lampMap = new HashMap<>();
    private TextField[][] plugFields;

    // Rotori e riflessori
    private static final String[] ROTORI_MAPPATURE = {
            "EKMFLGDQVZNTOWYHXUSPAIBRCJ", // I
            "AJDKSIRUXBLHWTMCQGZNPYFVOE", // II
            "BDFHJLCPRTXVZNYEIWGAKMUSQO", // III
            "ESOVPZJAYQUIRHXLNFTGKDCMWB", // IV
            "VZBRGITYUPSDNHLXAWMJQOFECK"  // V
    };

    private static final char[] ROTORI_NOTCH = {
            'Q', // I
            'E', // II
            'V', // III
            'J', // IV
            'Z'  // V
    };

    private static final String[] RIFLESSORI_MAPPATURE = {
            "YRUHQSLDPXNGOKMIEBFZCWVJAT", // B
            "FVPJIAOYEDRZXWGCTKUQSBNMHL"  // C
    };

    // Colori delle lampade
    private final Color LAMP_OFF_COLOR = Color.DARKGRAY;
    private final Color LAMP_ON_COLOR = Color.YELLOW;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inizializzazione delle ComboBox
        initializeComboBoxes();

        // Mappa delle lampade
        initializeLampMap();

        // Inizializzazione del pannello scambiatore
        initializePlugboard();

        // Inizializzazione della macchina Enigma
        createEnigmaMachine();

        // Listener per l'input text
        setupInputTextListener();
    }

    private void initializeComboBoxes() {
        // Popola le ComboBox dei rotori
        for (int i = 1; i <= 5; i++) {
            rotore1Box.getItems().add(i);
            rotore2Box.getItems().add(i);
            rotore3Box.getItems().add(i);
        }

        rotore1Box.setValue(1);
        rotore2Box.setValue(2);
        rotore3Box.setValue(3);

        // Listener per il cambio rotori
        rotore1Box.setOnAction(e -> createEnigmaMachine());
        rotore2Box.setOnAction(e -> createEnigmaMachine());
        rotore3Box.setOnAction(e -> createEnigmaMachine());

        // Popola la ComboBox del riflessore
        riflessoreBox.getItems().addAll("B", "C");
        riflessoreBox.setValue("B");
        riflessoreBox.setOnAction(e -> createEnigmaMachine());

        // Popola le ComboBox per le posizioni dei rotori
        for (char c = 'A'; c <= 'Z'; c++) {
            pos1Box.getItems().add(c);
            pos2Box.getItems().add(c);
            pos3Box.getItems().add(c);
        }

        pos1Box.setValue('A');
        pos2Box.setValue('A');
        pos3Box.setValue('A');

        pos1Box.setOnAction(e -> updateRotorPositions());
        pos2Box.setOnAction(e -> updateRotorPositions());
        pos3Box.setOnAction(e -> updateRotorPositions());

        // Configura il pulsante di reset
        resetBtn.setOnAction(e -> resetMachine());
    }

    private void initializeLampMap() {
        // Associa ogni lampada alla lettera corrispondente
        lampMap.put('A', lampA);
        lampMap.put('B', lampB);
        lampMap.put('C', lampC);
        lampMap.put('D', lampD);
        lampMap.put('E', lampE);
        lampMap.put('F', lampF);
        lampMap.put('G', lampG);
        lampMap.put('H', lampH);
        lampMap.put('I', lampI);
        lampMap.put('J', lampJ);
        lampMap.put('K', lampK);
        lampMap.put('L', lampL);
        lampMap.put('M', lampM);
        lampMap.put('N', lampN);
        lampMap.put('O', lampO);
        lampMap.put('P', lampP);
        lampMap.put('Q', lampQ);
        lampMap.put('R', lampR);
        lampMap.put('S', lampS);
        lampMap.put('T', lampT);
        lampMap.put('U', lampU);
        lampMap.put('V', lampV);
        lampMap.put('W', lampW);
        lampMap.put('X', lampX);
        lampMap.put('Y', lampY);
        lampMap.put('Z', lampZ);

        // Inizializza le lampade come spente
        turnOffAllLamps();
    }

    private void initializePlugboard() {
        // Organizza i campi del pannello scambiatore in una matrice
        plugFields = new TextField[][]{
                {plug1a, plug1b}, {plug2a, plug2b}, {plug3a, plug3b},
                {plug4a, plug4b}, {plug5a, plug5b}, {plug6a, plug6b},
                {plug7a, plug7b}, {plug8a, plug8b}, {plug9a, plug9b},
                {plug10a, plug10b}
        };

        // Aggiungi listener a tutti i campi
        for (TextField[] pair : plugFields) {
            configurePlugField(pair[0], pair[1]);
            configurePlugField(pair[1], pair[0]);
        }
    }

    private void configurePlugField(TextField source, TextField target) {
        source.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Se il campo è vuoto, rimuovi la connessione
                if (!oldValue.isEmpty() && !target.getText().isEmpty()) {
                    enigma.getPannelloScambiatore().rimuoviScambio(
                            oldValue.toUpperCase().charAt(0),
                            target.getText().toUpperCase().charAt(0)
                    );
                }
            } else {
                // Normalizza l'input: solo una lettera maiuscola
                String normalized = newValue.toUpperCase();
                if (normalized.length() > 1) {
                    normalized = normalized.substring(0, 1);
                }

                char letter = normalized.charAt(0);
                if (letter < 'A' || letter > 'Z') {
                    source.setText("");
                    return;
                }

                // Aggiorna il testo nel campo
                if (!normalized.equals(newValue)) {
                    source.setText(normalized);
                    return;
                }

                // Aggiorna la connessione nel pannello scambiatore
                if (!target.getText().isEmpty()) {
                    // Rimuovi la vecchia connessione se esisteva
                    if (!oldValue.isEmpty()) {
                        enigma.getPannelloScambiatore().rimuoviScambio(
                                oldValue.charAt(0),
                                target.getText().charAt(0)
                        );
                    }

                    // Aggiungi la nuova connessione
                    enigma.getPannelloScambiatore().aggiungiScambio(
                            letter,
                            target.getText().charAt(0)
                    );
                }
            }
        });
    }

    private void createEnigmaMachine() {
        // Ottieni gli indici selezionati (correggi per essere 0-based)
        int r1Index = rotore1Box.getValue() - 1;
        int r2Index = rotore2Box.getValue() - 1;
        int r3Index = rotore3Box.getValue() - 1;

        // Crea i rotori
        Rotore rotore1 = new Rotore(ROTORI_MAPPATURE[r1Index], ROTORI_NOTCH[r1Index]);
        Rotore rotore2 = new Rotore(ROTORI_MAPPATURE[r2Index], ROTORI_NOTCH[r2Index]);
        Rotore rotore3 = new Rotore(ROTORI_MAPPATURE[r3Index], ROTORI_NOTCH[r3Index]);

        // Crea il riflessore
        int rifIndex = riflessoreBox.getValue().equals("B") ? 0 : 1;
        Riflessore riflessore = new Riflessore(RIFLESSORI_MAPPATURE[rifIndex]);

        // Crea il pannello scambiatore
        PannelloScambiatore pannello = new PannelloScambiatore();

        // Aggiungi gli scambi esistenti
        for (TextField[] pair : plugFields) {
            if (!pair[0].getText().isEmpty() && !pair[1].getText().isEmpty()) {
                char a = pair[0].getText().charAt(0);
                char b = pair[1].getText().charAt(0);
                pannello.aggiungiScambio(a, b);
            }
        }

        // Crea la macchina Enigma
        enigma = new Enigma(rotore1, rotore2, rotore3, riflessore, pannello);

        // Imposta le posizioni iniziali dei rotori
        updateRotorPositions();

        // Reset del testo
        resetText();
    }

    private void updateRotorPositions() {
        if (enigma != null) {
            enigma.impostaPosizioni(
                    pos1Box.getValue(),
                    pos2Box.getValue(),
                    pos3Box.getValue()
            );
        }
    }

    private void setupInputTextListener() {
        inputText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (enigma == null) return;

                // Resetta le lampade
                turnOffAllLamps();

                if (oldValue.length() < newValue.length()) {
                    // È stato aggiunto un carattere
                    char lastChar = newValue.charAt(newValue.length() - 1);

                    // Se è una lettera, codificala
                    if (Character.isLetter(lastChar)) {
                        lastChar = Character.toUpperCase(lastChar);

                        // Codifica il carattere
                        char encoded = enigma.codifica(lastChar);

                        // Aggiorna l'output
                        outputText.setText(outputText.getText() + encoded);

                        // Accendi la lampada corrispondente
                        Circle lamp = lampMap.get(encoded);
                        if (lamp != null) {
                            lamp.setFill(LAMP_ON_COLOR);
                        }
                    } else {
                        // Se non è una lettera, copiala semplicemente
                        outputText.setText(outputText.getText() + lastChar);
                    }
                } else if (oldValue.length() > newValue.length()) {
                    // È stato rimosso un carattere o più caratteri
                    outputText.setText(encodeFullText(newValue));
                }
            }
        });
    }

    private String encodeFullText(String text) {
        // Ricrea la macchina Enigma per resettare lo stato
        createEnigmaMachine();

        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                encoded.append(enigma.codifica(Character.toUpperCase(c)));
            } else {
                encoded.append(c);
            }
        }

        // Aggiorna la lampada per l'ultimo carattere
        if (!text.isEmpty() && Character.isLetter(text.charAt(text.length() - 1))) {
            char lastEncoded = encoded.charAt(encoded.length() - 1);
            Circle lamp = lampMap.get(lastEncoded);
            if (lamp != null) {
                lamp.setFill(LAMP_ON_COLOR);
            }
        }

        return encoded.toString();
    }

    private void resetMachine() {
        // Reimposta le posizioni dei rotori
        pos1Box.setValue('A');
        pos2Box.setValue('A');
        pos3Box.setValue('A');

        // Ricrea la macchina
        createEnigmaMachine();

        // Reset del testo
        resetText();

        // Spegni tutte le lampade
        turnOffAllLamps();
    }

    private void resetText() {
        inputText.clear();
        outputText.clear();
    }

    private void turnOffAllLamps() {
        for (Circle lamp : lampMap.values()) {
            lamp.setFill(LAMP_OFF_COLOR);
        }
    }
}