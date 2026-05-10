package it.unibo.chaosjack.model.api;

import java.util.List;

/**
 * interface representing a special round rule game.
 */
@FunctionalInterface
public interface SpecialRound {

    /**
     * calculate the hand's score following the rules of the active special round.
     * 
     * @param playersCards cards in the player's hand.
     * @return the score of special round. 
     */
    int specialScore(List<Card> playersCards);

}
