package it.unibo.chaosjack.model.api;

public interface GameStatus {
    
    boolean IsGameOver(); //mi dice se la partita è finita
    String GetResult(); //mi restituisce il risultato
    void NextStep(); //gestisce il passaggio di turno
    void getWinner(); //mi dà il vincitore
}
