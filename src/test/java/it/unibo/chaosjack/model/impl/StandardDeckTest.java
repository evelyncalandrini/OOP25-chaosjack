package it.unibo.chaosjack.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.api.Deck;

import java.util.Optional;

class StandardDeckTest {
    private static final int FULL_DECK = 52;
    private static final int DECK_MINUS_ONE = 51;

    @Test
    void testInitialDeckSize() {
        final Deck deck = new StandardDeck();
        assertEquals(FULL_DECK, deck.remainingCards());
    }

    @Test
    void testDrawCard() {
        final Deck deck = new StandardDeck();
        final Optional<Card> drawnCard = deck.draw();

        assertTrue(drawnCard.isPresent());
        assertEquals(DECK_MINUS_ONE, deck.remainingCards());
    }

    @Test
    void testEmptyDeck() {
        final Deck deck = new StandardDeck();

        for (int i = 0; i < FULL_DECK; i++) {
            assertTrue(deck.draw().isPresent());
        }

        assertEquals(0, deck.remainingCards());

        final Optional<Card> impossibleCard = deck.draw();
        assertFalse(impossibleCard.isPresent());
    }
}
