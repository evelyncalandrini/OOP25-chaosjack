package it.unibo.chaosjack.test;

import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.impl.PlayerImpl;

public class TestPlayer {
    public static void main(String[] args) {
        // Creiamo il giocatore da testare
        PlayerImpl p = new PlayerImpl();

        // Creiamo delle classi "volanti" per le carte (Stub)
        // Se Card ha solo getValue(), questo è il modo più veloce
        Card asso = new Card() {
            @Override public int getValue() { return 11; }
        };
        Card dieci = new Card() {
            @Override public int getValue() { return 10; }
        };
        Card sette = new Card() {
            @Override public int getValue() { return 7; }
        };

        System.out.println("--- INIZIO TEST PLAYER ---");

        // TEST 1: Somma semplice
        p.addCard(dieci);
        p.addCard(sette);
        System.out.println("Caso 10 + 7 -> Risultato: " + p.getScore() + " (Atteso: 17)");

        // TEST 2: L'asso che deve diventare 1
        p.addCard(asso);
        System.out.println("Caso 17 + Asso -> Risultato: " + p.getScore() + " (Atteso: 18)");
        System.out.println("Sballato? " + p.isBusted() + " (Atteso: false)");

        // TEST 3: Sballo
        p.addCard(dieci);
        System.out.println("Caso 18 + 10 -> Risultato: " + p.getScore() + " (Atteso: 28)");
        System.out.println("Sballato? " + p.isBusted() + " (Atteso: true)");
    }
}
//da rifare con junit
