package it.unibo.chaosjack.model.api;

public interface Dealer extends Player {
    
    boolean shouldHit(); //this method tells me to keep hitting if the dealer didn't reach 17 yet
    void playTurn(Deck deck); //is the method that manages the turn
    
}
