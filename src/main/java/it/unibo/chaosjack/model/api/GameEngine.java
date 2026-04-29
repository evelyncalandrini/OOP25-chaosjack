package it.unibo.chaosjack.model.api;

public interface GameEngine {
    
    void startNewRound(); //starts a new game
    void hit(); //draws a card for the player
    void stand(); //ends the turn
    GameStatus getCurrentStatus(); //tells me at what point we are at with the game
    Player getPlayer(); //gives the players to show him on video
    Dealer getDealer(); //same with the dealer
}
