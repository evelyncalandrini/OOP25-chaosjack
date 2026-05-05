package it.unibo.chaosjack.model.api;

import java.util.List;

   /**
   * This interface represents a generic player in Blackjack.
   */

public interface Partecipant {

    public final static int MAX_SCORE = 21;

    /**
     * returns the name of the partecipant.
     * 
     * @return the partecipant's name
     */
    String getName();

    /**
     * Clears the hand of the partecipants to start a new round.
     */
    void resetHand();

    /**
     * Adds a card to the player's current hand.
     * 
     * @param card
     */
    void addCard(Card card);

    /**
     * @return  the total score of the cards currently in the player's hand.
     */
    int getScore();

    /**
     * Checks if the player's score exceeds the maximum limit of 21.
     * 
     * @return true if the partecipant is busted
     */
    default boolean isBusted() {
        return getScore() > MAX_SCORE;
    }

    /**
     * Provides a list of all cards currently held by the player.
     * 
     * @return the list of the cards in the partecipant's hands
     */
    List<Card> getHand();

}
