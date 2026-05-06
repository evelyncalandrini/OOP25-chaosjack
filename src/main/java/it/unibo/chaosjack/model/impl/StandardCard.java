package it.unibo.chaosjack.model.impl;

import it.unibo.chaosjack.model.api.Card;

/**
 * Implementation of a standard playing card.
 */
public final class StandardCard implements Card {

    private final Rank rank;
    private final Suit suit;

    /**
     * Constructs a new StandardCard.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public StandardCard(final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public int getValue() {
        return this.rank.getValue();
    }

    @Override
    public String getName() {
        return this.rank + " of " + this.suit;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
