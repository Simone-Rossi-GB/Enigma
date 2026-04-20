# Enigma

A faithful software simulation of the **WWII Enigma cipher machine**, built with Java and JavaFX. The machine encrypts and decrypts messages using the same electro-mechanical substitution logic as the historical device.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)

---

## How the cipher works

Each keypress routes an electrical signal through the following components in sequence:

```
Plugboard → Rotor R → Rotor M → Rotor L → Reflector → Rotor L⁻¹ → Rotor M⁻¹ → Rotor R⁻¹ → Plugboard
```

This makes encryption and decryption identical operations: typing the ciphertext with the same settings recovers the original message.

**Key mechanical features implemented:**
- **3 rotors** with configurable starting positions
- **Double-stepping anomaly** — the middle rotor steps twice under certain conditions, matching the behavior of the real machine
- **Reflector** — ensures the signal path is symmetric (no letter maps to itself)
- **Plugboard (Steckerbrett)** — swaps pairs of letters before and after the rotor chain

---

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 17+ |
| UI | JavaFX (FXML layout) |
| Build | Maven (with `mvnw` wrapper) |

---

## Project structure

```
src/main/java/com/enigma/enigma/
├── Enigma.java              # Core cipher logic
├── EnigmaApplication.java   # JavaFX entry point
├── EnigmaController.java    # UI controller (FXML bindings)
├── Rotore.java              # Rotor with stepping and wiring
├── Riflessore.java          # Reflector
└── PannelloScambiatore.java # Plugboard
```

---

## Build & Run

Requirements: **Java 17+** and **Maven** (or use the included `mvnw` wrapper).

```bash
./mvnw javafx:run
```

On Windows:
```bash
mvnw.cmd javafx:run
```

---

## Project context

School project at IIS B. Castelli, Brescia (2024–2025).  
The assignment required implementing a historically accurate cipher machine from scratch, including the double-stepping rotor anomaly.

**Team:** 3 students
