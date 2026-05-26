package it.unibo.chaosjack.controller.impl;



import java.util.Random;

import it.unibo.chaosjack.controller.api.ActionController;
import it.unibo.chaosjack.controller.api.GameFlowController;
import it.unibo.chaosjack.model.api.Dealer;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.api.NPC;
import it.unibo.chaosjack.model.api.Player;
import it.unibo.chaosjack.model.api.SpecialRound;
import it.unibo.chaosjack.model.api.Table;
import it.unibo.chaosjack.model.impl.DoubleHeartsRule;
import it.unibo.chaosjack.model.impl.RoyalFreezeTurn;
import it.unibo.chaosjack.model.impl.YingYung;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameFlowControllerImpl implements GameFlowController {

    private GameEngine gameEngine;
    private ActionController actionController; 
    private Table table;

    public GameFlowControllerImpl(final GameEngine gameEngine, final ActionController actionController) {
        this.gameEngine = gameEngine;
        this.actionController = actionController;
    }

    public void newGame() {
        gameEngine.resetGame();
        gameEngine.initialCards();

        Random random = new Random();
        if (random.nextInt(100) < 20) {
            gameEngine.setSpecialRound(this.chooseSpecialRound());
        } else {
            gameEngine.setSpecialRound(null);
        }

    }

    @Override
    public void phaseOfGame() {
        if ( gameEngine.isGameOver()) {
            table.getWinner();
            table.geStatistics();
        }

        Table.State state = table.getCurrentState();

        switch(state) {
            case FIRST_BET:
            case FINAL_BET:
                if (gameEngine.getCurrentPlayer() instanceof Player) {
                    // devo abilitare i bottoni
                } else {
                    
                    this.automaticBet(); // se è il turno di un npc faccio fare la puntata automatica
                }
            case PLAYING:
                if (gameEngine.getCurrentPlayer() instanceof Player) {
                    // devo abilitare i bottoni
                } else {
                    this.automaticShift(); // se è il turno di un npc faccio fare la mossa automatica
                }
            case DEALER_TURN:
                if (gameEngine.getCurrentPlayer() instanceof Dealer) {
                    this.automaticShift(); // faccio giocare il dealer
                }
        }

    }

    @Override
    public void automaticBet() {
        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(event -> {
            if (gameEngine.getCurrentPlayer() instanceof NPC) {
                // chiamo il metodo di giulia
            }
            this.phaseOfGame();
        });
        pausa.play();
    }

    @Override // gestisco il timer per far pescare le carte 
    public void automaticShift() {

        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished ( event -> {
            if (gameEngine.getCurrentPlayer() instanceof NPC) {
                 // chiamo il metodo di giulia
            } else if (gameEngine.getCurrentPlayer() instanceof Dealer) {
                // chiamo il metodo di giulia
            }

            this.automaticShift();// richiamo questo metodo per far avanzare il turno
            // alla giuli devo dire di cambiare il while con un semplice if 
        } );
        pausa.play();
    }

    private SpecialRound chooseSpecialRound() {
        int choise = new Random().nextInt(3);
        SpecialRound specialRound = null;
        switch (choise) {
            case 0:
                specialRound = new YingYung();
                break;
            case 1: 
                specialRound = new DoubleHeartsRule();
                break;
            case 2:
                specialRound = new RoyalFreezeTurn();
                break;
        }
        return specialRound;
    }

}
