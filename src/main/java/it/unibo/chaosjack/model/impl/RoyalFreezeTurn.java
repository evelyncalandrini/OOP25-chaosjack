package it.unibo.chaosjack.model.impl;

import it.unibo.chaosjack.model.api.SpecialRound;
import java.util.List;
import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.api.CardModifier;

/**
 * This class implemets the interface SpecialRound.
 * Rapresent a special round where KIING, QUEEN and JACK,
 * have a value of 0
 */

public final class RoyalFreezeTurn implements SpecialRound {

    @Override
    public int specialScore(final List<Card> playersCards) {
        int score = 0;
        for (final Card c : playersCards) {
            int value = 0;
            if ((c.getModifier() == CardModifier.NONE) && (c.getName().contains("KING") || c.getName().contains("QUEEN") || c.getName().contains("JACK")
                )) {
                value += 0;
            } else {
                value += c.getValue();
            }

            if (score + value < 0){
                value = value / 2;
             }

             score += value;
         }
         
         return score;
        } 

    @Override
    public String getDescription() {
        return "Royal Freeze turn";
    }
}
