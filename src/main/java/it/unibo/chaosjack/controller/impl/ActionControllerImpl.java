package it.unibo.chaosjack.controller.impl;

import it.unibo.chaosjack.controller.api.ActionController;
import it.unibo.chaosjack.model.api.Dealer;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.api.NPC;
import it.unibo.chaosjack.model.api.Player;
import it.unibo.chaosjack.model.api.Table;
import it.unibo.chaosjack.model.api.Partecipant;

/**
 * Implementation of the ActionController interface.
 */
public final class ActionControllerImpl implements ActionController {

    private static final int SPECIAL_ROUND_MAX_CARDS = 5;

    private final Table table;
    private final GameEngine engine;

    /**
     * Constructs a new ActionControllerImpl.
     *
     * @param table the game table
     * @param engine the game engine
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The controller needs to hold references to the shared table and engine."
    )
    public ActionControllerImpl(final Table table, final GameEngine engine) {
        this.table = table;
        this.engine = engine;
    }

    @Override
    public void hit() {
        if (table.getCurrentState() != Table.State.PLAYING) {
            return;
        }
        final Player human = getCurrentHumanPlayer();
        if (human == null) {
            return;
        }
        //aggiungo il controllo per le carte massime nei turni speciali
        if (engine.getSpecialRound() != null && human.getHand().getCards().size() >= SPECIAL_ROUND_MAX_CARDS) {
            this.stand();
            return;
        }
        final int score = engine.currentScore(human.getHand());
        if (human.isBusted(score) || engine.currentScore(human.getHand()) >= Partecipant.MAX_SCORE) {
            return;
        }
        engine.hit();

        if (human.isBusted(score) || engine.currentScore(human.getHand()) >= Partecipant.MAX_SCORE) {
            this.stand();
        }
    }

    @Override
    public void stand() {
        if (table.getCurrentState() != Table.State.PLAYING) {
            return;
        }

        final Player human = getCurrentHumanPlayer();
        if (human == null) {
            return;
        }

        engine.stand();
    }

    @Override
    public void bet(final int amount) {
        final Player human = getCurrentHumanPlayer();
        if (human == null) {
            return;
        }
        try {
           table.placeBet(human.getName(), amount);
           human.setBet(amount);
           engine.stand();
        } catch (IllegalStateException | IllegalArgumentException e) {
            java.util.logging.Logger.getLogger(this.getClass().getName()).warning(e.getMessage());
        }
    }

    @Override
     public void doubleDown() {
        if (table.getCurrentState() != Table.State.PLAYING) {
           return;
        }
        final Player human = getCurrentHumanPlayer();
        if (human == null) {
            return;
        }
        if (human.getHand().getCards().size() != 2) {
           return;
        }
        final int currentBet = human.getCurrentBet();
        if (human.getWallet() < currentBet) {
          return;
        }
        table.placeBet(human.getName(), currentBet);
        human.doubleDown();
        try {
            table.placeBet(human.getName(), currentBet);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return;
        }
        engine.hit();
        this.stand();
    }

    private boolean isHumanPlayer(final Partecipant p) { //mi serve nei vari metodi per dire sepuò usare i bottoni
        return p instanceof Player && !(p instanceof NPC);
    }

    //metodi per NPC e Dealer

    @Override
    public void playAutomatedBet() {
        final Partecipant p = engine.getCurrentPlayer();
        if (p instanceof NPC) {
            final NPC bot = (NPC) p;
            bot.makeBet();

            table.placeBet(bot.getName(), bot.getCurrentBet());
            engine.stand();
        }
    }

    @Override
    public void playAutomatedTurns() {
        if (engine.getCurrentPlayer() instanceof NPC) {
            final NPC bot = (NPC) engine.getCurrentPlayer();

            final int botscore = engine.currentScore(bot.getHand());
            final int cardsInHand = bot.getHand().getCards().size();

            if (engine.getSpecialRound() != null && cardsInHand >= SPECIAL_ROUND_MAX_CARDS) {
               engine.stand();
               return;
            }

            if (bot.wantsToDouble(botscore) && cardsInHand == 2) {
                bot.doubleDown();
                engine.hit();
                engine.stand();
            } else if (bot.wantsToHit(botscore)) {
                engine.hit();
            } else {
                engine.stand();
            }
        }
    }

    @Override
    public void playDealerTurns() {
       final Dealer dealer = (Dealer) engine.getCurrentPlayer();
       final int dealerScore = engine.currentScore(dealer.getHand());
       final int cardsInHand = dealer.getHand().getCards().size();

       if (engine.getSpecialRound() != null && cardsInHand >= SPECIAL_ROUND_MAX_CARDS) {
           engine.stand();
           return;
       }
       if (dealer.shouldHit(dealerScore)) {
           engine.hit();
       } else {
           engine.stand();
       }
    }

    //metodo per il player umano per non fare troppe ripetizioni
    private Player getCurrentHumanPlayer() {
        final Partecipant current = engine.getCurrentPlayer();
        if (isHumanPlayer(current)) {
            return (Player) current;
        }
        return null;
    }
}
