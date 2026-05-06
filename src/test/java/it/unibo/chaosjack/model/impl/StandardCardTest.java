package it.unibo.chaosjack.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import it.unibo.chaosjack.model.api.Card;

class StandardCardTest {

    private static final int LOW_VAL = 5;
    private static final int FACE_VAL = 10;
    private static final int ACE_VAL = 11;

    @Test
    void testCardValues() {
        final Card fiveOfHearts = new StandardCard(Rank.FIVE, Suit.HEARTS);
        assertEquals(LOW_VAL, fiveOfHearts.getValue());

        final Card jackOfSpades = new StandardCard(Rank.JACK, Suit.SPADES);
        assertEquals(FACE_VAL, jackOfSpades.getValue());

        final Card kingOfDiamonds = new StandardCard(Rank.KING, Suit.DIAMONDS);
        assertEquals(FACE_VAL, kingOfDiamonds.getValue());

        final Card aceOfClubs = new StandardCard(Rank.ACE, Suit.CLUBS);
        assertEquals(ACE_VAL, aceOfClubs.getValue());
    }

    @Test
    void testCardNames() {
        final Card card = new StandardCard(Rank.QUEEN, Suit.SPADES);

        assertEquals("QUEEN of SPADES", card.getName());

        assertEquals("QUEEN of SPADES", card.toString());
    }
}
