package it.unibo.chaosjack.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.api.CardModifier;
import it.unibo.chaosjack.model.api.Deck;

import java.util.Optional;

/**
 * Test class for StandardDeck.
 */
class StandardDeckTest {
    private static final int FULL_DECK = 52;
    private static final int DECK_MINUS_ONE = 51;
    private static final int CARDS_TO_DRAW = 2;
    private static final int DECK_AFTER_DRAW = 50;
    private static final int EXPECTED_SPECIAL_CARDS = 12;

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
    void testResetDeck() {
        final Deck deck = new StandardDeck();
        // Utilizzo CARDS_TO_DRAW per evitare errori PMD
        for (int i = 0; i < CARDS_TO_DRAW; i++) {
            deck.draw();
        }
        assertEquals(DECK_AFTER_DRAW, deck.remainingCards());
        // Reset e verifica che sia tornato pieno
        deck.reset();
        assertEquals(FULL_DECK, deck.remainingCards());
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

    @Test
    void testSpecialCardsDistribution() {
        final Deck deck = new StandardDeck();
        int specialCount = 0;

        for (int i = 0; i < FULL_DECK; i++) {
            final Optional<Card> drawnCard = deck.draw();
            assertTrue(drawnCard.isPresent()); // Mettiamo in sicurezza l'Optional per il linter
            if (drawnCard.get().getModifier() != CardModifier.NONE) {
                specialCount++;
            }
        }

        assertEquals(EXPECTED_SPECIAL_CARDS, specialCount);
    }
}
