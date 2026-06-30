package it.unibo.chaosjack.model.api;

import java.util.List;

/**
 * Rappresents the final outcome of a game round.
 * 
 * @param result of the round (outcome, scores, payouts).
 * @param winners the list of players who won or tied.
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = "EI_EXPOSE_REP",
    justification = "We trust the caller and need direct access to the list."
)
public record RoundEvaluation(RoundResult result, List<String> winners) { }
