package it.unibo.chaosjack.controller.impl;



import it.unibo.chaosjack.controller.api.ActionController;
import it.unibo.chaosjack.controller.api.GameFlowController;
import it.unibo.chaosjack.model.api.Dealer;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.api.NPC;
import it.unibo.chaosjack.model.api.Player;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameFlowControllerImpl implements GameFlowController {

    private GameEngine gameEngine;
    private ActionController actionController; // si sistema quando implemento la calsse di giulia 

    public GameFlowControllerImpl(final GameEngine gameEngine, final ActionController actionController) {
        this.gameEngine = gameEngine;
        this.actionController = actionController;
    }


    @Override
    public void phaseOfGame() {
        if ( gameEngine.isGameOver()) {
            // devo mostrare i risultati ci penserò a come devo fare
        }

        var cuuretnPlayer = gameEngine.getCurrentPlayer();
        if (cuuretnPlayer instanceof Player) {
            // devo abilitare i bottoni
        } else {
            // disabilito i bottoni
            this.automaticShift();
        }
    }


    @Override // gestisco il timer per far pescare le carte 
    public void automaticShift() {

        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished ( event -> {
            if (gameEngine.getCurrentPlayer() instanceof NPC) {
                 ActionController. // lo andrò a sostituire con i metodi giusti
            } else if (gameEngine.getCurrentPlayer() instanceof Dealer) {
                ActionController.playDealer(); // lo andrò a sostituire con i metodi giusti
            }

            this.automaticShift();// richiamo questo metodo per far avanzare il turno
            // alla giuli devo dire di cambiare il while con un semplice if 
        } );
        pausa.play();
    }






    
}
