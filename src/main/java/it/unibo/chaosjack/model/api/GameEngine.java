package it.unibo.chaosjack.model.api;

import it.unibo.chaosjack.model.impl.Hand;
import java.util.List;

public interface GameEngine {

    /**
     * 
     * @return the current player of the game
     */
    Partecipant getCurrentPlayer();

    /**
     * this method is use to change the turn of the game
     */
    void nextTurn();

    /**
     * 
     * @return the Deck of the table
     */
    Deck getDeck(); 

    /**
     * 
     * @return the hand of the dealer
     */
    Hand getDealerHand(); 

    /**
     * 
     * @return the score of a player by their name
     */
    int getPlayerScore(String name); 

    /**
     * 
     * @return the list of players in the game
     */
    List<Partecipant> getPlayers();

    /**
     * allows to draw a card, useful for the controller
     * @param card is the drawn card
     */
    void hit(Card card); 

    /**
     * allows the player to pass the turn after
     */
    void stand(); 

    /** 
     * 
     * @param hand is the hand of the player o dealer
     * @return the score of the hand with the rules of the spcial round or the normal rules if there isn't a special round
     */    
     int currentScore(Hand hand); 
    
     /**
      * @param specialRound contains the special round you want to active
      * this method allows to set a special round or remove if the parameter is null
      */
     void setSpecialRound(SpecialRound specialRound); 

     /**
      * this method allows the dealer to play
     */
    void dealerTurn();

}
