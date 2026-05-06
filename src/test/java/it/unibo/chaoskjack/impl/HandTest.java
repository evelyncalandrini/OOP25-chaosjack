package it.unibo.chaoskjack.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.unibo.chaosjack.model.impl.Hand;
import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.impl.StandardCard;
import org.junit.jupiter.api.Test;
import it.unibo.chaosjack.model.impl.Rank;
import it.unibo.chaosjack.model.impl.Suit;


public class HandTest {
    @Test

    public void addCard(){
        final Hand myHand = new Hand();
        final Card card1 = new StandardCard(Rank.TWO, Suit.HEARTS);

        myHand.addCard(card1);
        assertEquals(1, myHand.getCards().size());

        assertEquals(2, myHand.getCards().get(0).getValue());

        assertEquals("TWO of HEARTS", myHand.getCards().get(0).getName());

    }

    @Test
    public void getScore() {
        final Hand myHand = new Hand();
        final Card card1 = new StandardCard(Rank.TWO, Suit.HEARTS);
        final Card card2 = new StandardCard(Rank.JACK, Suit.SPADES);
        final Card card3 = new StandardCard(Rank.ACE, Suit.CLUBS);

        myHand.addCard(card1);
        myHand.addCard(card2);
        myHand.addCard(card3);

        assertEquals(13, myHand.getScore());
    }

    @Test
    public void sameColor() {
        final Hand myHand = new Hand();
        final Card card1 = new StandardCard(Rank.TWO, Suit.HEARTS);
        final Card card2 = new StandardCard(Rank.THREE, Suit.HEARTS);
        final Card card3 = new StandardCard(Rank.FOUR, Suit.CLUBS);

        myHand.addCard(card1);
        myHand.addCard(card2);
        myHand.addCard(card3);

        assertEquals(false, myHand.sameColor(myHand.getCards()));
    }
   
}
