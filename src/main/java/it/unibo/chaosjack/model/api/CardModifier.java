package it.unibo.chaosjack.model.api;

/**
 * Enumeration of the possible special modifiers for a card.
 */
public enum CardModifier {
    /** No special effect. */
    NONE,
    /** Forces the card value to 12. */
    BUST_MAGNET,
    /** Steals a percentage of the opponent's wallet. */
    TAX,
    /** Potential jackpot on win. */
    LUCKY
}
