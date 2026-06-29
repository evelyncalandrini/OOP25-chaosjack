package it.unibo.chaosjack.controller.api;

public interface ActionController {
    /* */
    void hit();

    void stand();
    
    void bet(int amount);

    void doubleDown();

    void playAutomatedBet();

    void playAutomatedTurns();

    void playDealerTurns();
}
