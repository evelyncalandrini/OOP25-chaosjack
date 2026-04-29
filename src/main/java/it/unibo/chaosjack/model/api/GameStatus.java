package it.unibo.chaosjack.model.api;

public interface GameStatus {
    
    boolean isGameOver(); //tells me if the game is over
    String getResult(); //gives the result
    void nextStep(); //manages the turn change
    String getWinner(); //gives the winner
}
