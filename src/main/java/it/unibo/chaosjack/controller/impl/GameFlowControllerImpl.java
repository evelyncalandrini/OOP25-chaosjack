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
import it.unibo.chaosjack.view.api.GameTableView;
import it.unibo.chaosjack.view.api.MainMenuView;
import it.unibo.chaosjack.view.api.ViewManager;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameFlowControllerImpl implements GameFlowController {

    private GameEngine gameEngine;
    private ActionController actionController; 
    private Table table;
    private GameTableView tableView;
    private MainMenuView mainMenuView;
    private ViewManager viewManager;

    public GameFlowControllerImpl(final GameEngine gameEngine, final ActionController actionController, final GameTableView tableView,
        final MainMenuView mainMenuView, final ViewManager viewManager, final Table table) {
        this.gameEngine = gameEngine;
        this.actionController = actionController;
        this.tableView = tableView;
        this.mainMenuView = mainMenuView;
        this.viewManager = viewManager;
        this.table = table;

        this.connectButtons();
        
    }

    private void connectButtons() {

        mainMenuView.setPlayHandler(() -> {
            this.viewManager.showGameTable();
            this.newGame();
        });

        tableView.setHitHandler(() -> {
            this.actionController.hit();
            
            this.phaseOfGame();
        });

        tableView.setStandHandler(() -> {
            this.actionController.stand();
            
            this.phaseOfGame();
        });

        tableView.setBetHandler(amount -> {
             //tableView.setBetButton(false)
            this.actionController.bet(amount);
            
            this.phaseOfGame();
        });

    }

    public void newGame() {
        
        this.upDateView();
        gameEngine.resetGame();
        gameEngine.nextTurn(); 
        tableView.setGameState(Table.State.FIRST_BET);

        Random random = new Random();
        if (random.nextInt(100) < 20) {
            gameEngine.setSpecialRound(this.chooseSpecialRound());
        } else {
            gameEngine.setSpecialRound(null);
        }

        this.tableView.setSpecialRound(gameEngine.getSpecialRound().getDescription() != null ? gameEngine.getSpecialRound().getDescription() : null);// aggiorno la view in modo da mostrare il round speciale se è presente
        this.phaseOfGame();

    }

    @Override
    public void phaseOfGame() {
        this.upDateView();
        this.tableView.setActiveTurn(gameEngine.getCurrentPlayer().getName());
        this.tableView.updatePot(this.table.getPot());

        if ( gameEngine.isGameOver()) { // da rivedere
            this.table.getWinner();
            
            this.tableView.setGameState(Table.State.RESULTS);
        }

        Table.State state = this.table.getCurrentState();

        switch(state) {
            case FIRST_BET:
            case FINAL_BET:
                this.tableView.setGameState(state);

                if (gameEngine.getCurrentPlayer() instanceof Player && !(gameEngine.getCurrentPlayer() instanceof NPC) && !(gameEngine.getCurrentPlayer() instanceof Dealer)) {
                    this.tableView.setBetButton(false);
                    this.tableView.setPlayerButtons(true);
                    return;
                } else {
                    this.tableView.setBetButton(true);
                    this.tableView.setPlayerButtons(true);
                    this.automaticBet(); 
                }
                break;

            case PLAYING:
                tableView.setGameState(Table.State.PLAYING);
                
                if (gameEngine.getDealerHand().getCards().isEmpty()) {
                    gameEngine.initialCards();
                    this.upDateView();
                }
                this.automaticShift(); 
                break;

            case DEALER_TURN:
                gameEngine.dealerTurn();
                this.tableView.setActiveTurn("dealer");
                this.tableView.setGameState(state);

                this.automaticShift(); // faccio giocare il dealer
                break;
        }

    }

    @Override
    public void automaticBet() {

        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(event -> {
            if (gameEngine.getCurrentPlayer() instanceof NPC) {
                
                actionController.playAutomatedBet();
                this.phaseOfGame();
            }
            
        });
        pausa.play();
    }

    @Override // gestisco il timer per far pescare le carte 
    public void automaticShift() {

        if (gameEngine.getCurrentPlayer() instanceof Player && !(gameEngine.getCurrentPlayer() instanceof NPC) 
            && !(gameEngine.getCurrentPlayer() instanceof Dealer)) {

            tableView.setPlayerButtons(false);
            tableView.setBetButton(true);
            return;
        } 

         PauseTransition pausa = new PauseTransition(Duration.seconds(1));
         pausa.setOnFinished ( event -> {

            if (gameEngine.getCurrentPlayer() instanceof NPC) {

                tableView.setPlayerButtons(true);
                tableView.setBetButton(false);
                actionController.playAutomatedTurns();
                
            } else if (gameEngine.getCurrentPlayer() instanceof Dealer) {

                tableView.setPlayerButtons(true);
                tableView.setBetButton(false);
                actionController.playDealerTurns();
            }

            this.phaseOfGame();
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

    private void upDateView() {

        if (gameEngine.getPlayers().size() >= 2) {
            tableView.setPlayerNames(
                gameEngine.getPlayers().get(0).getName(),
                gameEngine.getPlayers().get(1).getName()
            );
            
        }

        tableView.updateDealerCard(gameEngine.getDealerHand().getCards());

        if ( gameEngine.getPlayers().size() >= 1) {
            tableView.updatePlayer1Cards(gameEngine.getPlayers().get(0).getHand().getCards());
        } 

        if (gameEngine.getPlayers().size() >= 2) {
            tableView.updatePlayer2Cards(gameEngine.getPlayers().get(1).getHand().getCards());
        }


    }

}
