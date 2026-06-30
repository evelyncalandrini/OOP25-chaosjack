package it.unibo.chaosjack.controller.api;

/**
 * This interface defines the actions that can be triggered from the UI buttons.
 */
public interface ActionController {

    /**
     * Requests a new card for the current player.
     */
    void hit();

    /**
     * Passes the turn of the current player.
     */
    void stand();

    /**
     * Places a bet for the current player.
     *
     * @param amount the amount to bet
     */
    void bet(int amount);

    /**
     * Doubles the current bet and requests one last card.
     */
    void doubleDown();

    /**
     * Plays the automated bet for NPCs.
     */
    void playAutomatedBet();

    /**
     * Plays the automated turns for NPCs.
     */
    void playAutomatedTurns();

    /**
     * Plays the automated turns for the dealer.
     */
    void playDealerTurns();
}
