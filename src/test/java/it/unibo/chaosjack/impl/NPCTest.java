package it.unibo.chaosjack.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import it.unibo.chaosjack.model.api.NPC;
import it.unibo.chaosjack.model.impl.NPCimpl;
import it.unibo.chaosjack.model.impl.Rank;
import it.unibo.chaosjack.model.impl.StandardCard;
import it.unibo.chaosjack.model.impl.Suit;

 /**
  * Tests for the NPCImpl class.
  */
 class NPCTest {
    private static final int INITIAL_FUNDS = 100;
    private static final int INITIAL_FUNDS_LOW = 5;
    private static final int BET_NORMAL = 10;
    private static final int BET_ALL_IN = 5;
    private static final int SCORE_HIT_TRUE = 10;
    private static final int SCORE_HIT_FALSE = 19;
    private static final int SCORE_DOUBLE_TRUE = 11;
    private static final int SCORE_DOUBLE_FALSE = 14;

    @Test
    void testMakeBet() {
        final NPC npc = new NPCimpl("bot-1", INITIAL_FUNDS);
        npc.makeBet();
        assertEquals(BET_NORMAL, npc.getCurrentBet(), "La scommessa dovrebbe essere 10");
    }

    @Test
    void testMakeBetWithLowFunds() {
        final NPC npc = new NPCimpl("bot-2", INITIAL_FUNDS_LOW);
        npc.makeBet();
        assertEquals(BET_ALL_IN, npc.getCurrentBet(), "La scommessa dobrebbe essere 5 perchè fa all-in");
    }

    @Test
    void testHitStrategy() {
        final NPC npc = new NPCimpl("bot-3", INITIAL_FUNDS);
        npc.addCard(new StandardCard(Rank.SIX, Suit.CLUBS));
        npc.addCard(new StandardCard(Rank.FOUR, Suit.HEARTS));
        assertTrue(npc.wantsToHit(SCORE_HIT_TRUE), "L'NPC dovrebbe chiedere carta con un punteggio di 10");
        npc.resetHand();
        npc.addCard(new StandardCard(Rank.KING, Suit.SPADES));
        npc.addCard(new StandardCard(Rank.NINE, Suit.DIAMONDS));
        assertFalse(npc.wantsToHit(SCORE_HIT_FALSE), "L'NPC non dovrebbe chiedere carta con 19");
    }

    @Test
    void testShouldDouble() {
        final NPC npc = new NPCimpl("bot-4", INITIAL_FUNDS);
        npc.addCard(new StandardCard(Rank.FIVE, Suit.SPADES));
        npc.addCard(new StandardCard(Rank.SIX, Suit.CLUBS));
        assertTrue(npc.wantsToDouble(SCORE_DOUBLE_TRUE), "L'NPC dovrebbe raddoppiare con 11");

        npc.resetHand();
        npc.addCard(new StandardCard(Rank.JACK, Suit.HEARTS));
        npc.addCard(new StandardCard(Rank.FOUR, Suit.SPADES));
        assertFalse(npc.wantsToDouble(SCORE_DOUBLE_FALSE), "L'NPC non dovrebbe raddoppaire con 14");
    }
}
