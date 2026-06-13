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
import it.unibo.chaosjack.view.api.PauseMenuView;
import it.unibo.chaosjack.view.api.GameScoreDisplay;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


public class GameFlowControllerImpl implements GameFlowController {

    private GameEngine gameEngine;
    private ActionController actionController; 
    private Table table;
    private GameTableView tableView;
    private MainMenuView mainMenuView;
    private ViewManager viewManager;
    private PauseMenuView pauseMenu;
    

    public GameFlowControllerImpl(final GameEngine gameEngine, final ActionController actionController, final GameTableView tableView,
        final MainMenuView mainMenuView, final ViewManager viewManager, final Table table, final PauseMenuView pause) {
        this.gameEngine = gameEngine;
        this.actionController = actionController;
        this.tableView = tableView;
        this.mainMenuView = mainMenuView;
        this.viewManager = viewManager;
        this.table = table;
        this.pauseMenu = pause;
       

        this.connectButtons();
        
    }

    private void connectButtons() {

        mainMenuView.setPlayHandler(() -> {
            this.viewManager.showGameTable();
            this.newGame();
        });

        tableView.setPauseHandler(() -> {
            tableView.getPauseMenu().setVisible(true);
        });

        tableView.getPauseMenu().setResumeHandler(() -> {
            tableView.getPauseMenu().setVisible(false);
        });

        tableView.getPauseMenu().setRestartHanlder(() -> {
            tableView.getPauseMenu().setVisible(false);
            this.newGame();
        });

        tableView.getPauseMenu().setExitHandler(() -> {
            tableView.getPauseMenu().setVisible(false);
            this.viewManager.showMainMenu();
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
        this.reset_score();
        tableView.setDealerScore(0);
        this.tableView.updatePot(0);
        gameEngine.nextTurn(); 
        tableView.setGameState(Table.State.FIRST_BET);
        this.tableView.setBetButton(false);
        this.tableView.setPlayerButtons(true);

        Random random = new Random();
        if (random.nextInt(100) < 20) {
            gameEngine.setSpecialRound(this.chooseSpecialRound()); //this.chooseSpecialRound()
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

        for (int i = 0; i < gameEngine.getPlayers().size(); i++) {
            if (i==0) {
                tableView.setPlayer1Score(gameEngine.currentScore(gameEngine.getPlayers().get(i).getHand()));
            } else if (i==1) {
                tableView.setPlayer2Score(gameEngine.currentScore(gameEngine.getPlayers().get(1).getHand()));
            }
        }

        tableView.setDealerScore(gameEngine.currentScore(gameEngine.getDealerHand()));


    }

    private void reset_score() {
        if (gameEngine.getPlayers().size() >= 2) {
            tableView.setPlayer1Score(0);
            tableView.setPlayer2Score(0);
        } else {
            tableView.setPlayer1Score(0);
        }
    }

}
