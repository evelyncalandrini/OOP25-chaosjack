package it.unibo.chaosjack.controller.impl;

import java.util.Locale;
import java.util.Random;
import it.unibo.chaosjack.controller.api.ActionController;
import it.unibo.chaosjack.controller.api.GameFlowController;
import it.unibo.chaosjack.model.api.Dealer;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.api.NPC;
import it.unibo.chaosjack.model.api.Partecipant;
import it.unibo.chaosjack.model.api.Player;
import it.unibo.chaosjack.model.api.RoundEvaluation;
import it.unibo.chaosjack.model.api.RoundResult;
import it.unibo.chaosjack.model.api.SpecialRound;
import it.unibo.chaosjack.model.api.Table;
import it.unibo.chaosjack.model.api.Table.State;
import it.unibo.chaosjack.model.impl.DoubleHeartsRule;
import it.unibo.chaosjack.model.impl.RoyalFreezeTurn;
import it.unibo.chaosjack.model.impl.YingYung;
import it.unibo.chaosjack.view.api.GameTableView;
import it.unibo.chaosjack.view.api.MainMenuView;
import it.unibo.chaosjack.view.api.ViewManager;
import it.unibo.chaosjack.view.api.PauseMenuView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Implementation of GameFlowController.
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = {"EI_EXPOSE_REP", "DM_EXIT"},
    justification = "The controller requires access to models/views, uses random for rounds, and terminates on exit."
)
public final class GameFlowControllerImpl implements GameFlowController {

    private static final int CHANCE_BOUND = 100;
    private static final int SPECIAL_ROUND_THRESHOLD = 20;
    private static final int NUM_SPECIAL_ROUNDS = 3;
    private static final int PAUSE_DURATION_SECONDS = 1;

    private final GameEngine gameEngine;
    private final ActionController actionController;
    private final Table table;
    private final GameTableView tableView;
    private final MainMenuView mainMenuView;
    private final ViewManager viewManager;
    private PauseTransition currentPause;
    private boolean isPaused;
    private final Random random = new Random();

    /**
     * Constructor for GameFlowControllerImpl.
     *
     * @param gameEngine the game engine
     * @param actionController the action controller
     * @param tableView the game table view
     * @param mainMenuView the main menu view
     * @param viewManager the view manager
     * @param table the game table
     * @param pause the pause menu view
     */
    public GameFlowControllerImpl(final GameEngine gameEngine, final ActionController actionController,
        final GameTableView tableView, final MainMenuView mainMenuView, final ViewManager viewManager,
        final Table table, final PauseMenuView pause) {
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

        mainMenuView.setStatsHandler(() -> {
            this.viewManager.showStatistics(this.table.getStatistics());
        });

         mainMenuView.setExitHandler(() -> {
            System.exit(0);
        });

        tableView.setPauseHandler(() -> {
            this.isPaused = true;
            tableView.getPauseMenu().setVisible(true);
            if (this.currentPause != null) {
                this.currentPause.pause();
            }
        });

        tableView.getPauseMenu().setResumeHandler(() -> {
            this.isPaused = false;
            tableView.getPauseMenu().setVisible(false);
            if (this.currentPause != null) {
                this.currentPause.play();
            }
        });

        tableView.getPauseMenu().setRestartHanlder(() -> {
            this.isPaused = false;
            tableView.getPauseMenu().setVisible(false);
            if (this.currentPause != null) {
                this.currentPause.stop();
            }
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
            this.actionController.bet(amount);
            this.phaseOfGame();
        });

        tableView.setDoubleDownHandler(() -> {
            this.actionController.doubleDown();
            this.phaseOfGame();
        });
    }

    @Override
    public void newGame() {
        this.isPaused = false;
        gameEngine.resetGame();
        this.upDateView();
        this.resetScore();
        tableView.setDealerScore(0);
        this.tableView.updatePot(0);

        tableView.setGameState(Table.State.FIRST_BET);

        this.tableView.setBetButton(false);
        this.tableView.setPlayerButtons(true);
        gameEngine.nextTurn();

        this.setRound();
    }

    @Override
    public void phaseOfGame() {
        this.upDateView();

        this.tableView.setActiveTurn(gameEngine.getCurrentPlayer().getName());
        this.tableView.updatePot(this.table.getPot());

        if (gameEngine.isGameOver()) {
            final RoundEvaluation evaluation = this.table.getWinner();

            this.tableView.setGameState(Table.State.RESULTS);
            final String messageToShow;
            final RoundResult.Outcome outcome = evaluation.result().outcome();
            if (evaluation.winners().isEmpty() || outcome == RoundResult.Outcome.DEALER_WON) {
                messageToShow = outcome.getMessage();
            } else {
                final String winnersList = String.join("&", evaluation.winners());
                messageToShow = winnersList.toUpperCase(Locale.ROOT) + " " + outcome.getMessage();
            }

            this.tableView.showResult(messageToShow);
        }

        final Table.State state = this.table.getCurrentState();

        switch (state) {
            case FIRST_BET:
            case FINAL_BET:
             this.tableView.setGameState(state);

                if (this.humanPlayer(gameEngine.getCurrentPlayer()) && table.getCurrentState() == State.FINAL_BET) {
                    if (gameEngine.currentScore(gameEngine.getCurrentPlayer().getHand()) > Partecipant.MAX_SCORE) {
                        this.gameEngine.stand();
                        this.phaseOfGame();
                    } else {
                        this.tableView.setBetButton(false);
                        this.tableView.setPlayerButtons(true);
                        return;
                    }

                } else {

                    if (gameEngine.currentScore(gameEngine.getCurrentPlayer().getHand()) > Partecipant.MAX_SCORE) {
                        gameEngine.stand();
                        this.phaseOfGame();
                    } else {
                        this.tableView.setBetButton(false);
                        this.tableView.setPlayerButtons(true);
                        this.automaticBet();
                    }
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
                this.tableView.setBetButton(true);
                this.tableView.setPlayerButtons(true);

                gameEngine.dealerTurn();
                this.tableView.setActiveTurn("dealer");
                this.tableView.setGameState(state);

                this.automaticShift(); // faccio giocare il dealer
                break;

            case RESULTS:
                break;
        }
    }

    @Override
    public void automaticBet() {
        this.currentPause = new PauseTransition(Duration.seconds(PAUSE_DURATION_SECONDS));
        this.currentPause.setOnFinished(event -> {
            if (gameEngine.getCurrentPlayer() instanceof NPC) {
                actionController.playAutomatedBet();
                this.phaseOfGame();
            }
        });

        if (!this.isPaused) {
         this.currentPause.play();
        }
    }

    @Override
    public void automaticShift() {
        if (this.humanPlayer(this.gameEngine.getCurrentPlayer())) {
            if (gameEngine.currentScore(gameEngine.getCurrentPlayer().getHand()) <= Partecipant.MAX_SCORE) {
                tableView.setPlayerButtons(false);
                tableView.setBetButton(true);
                return;
            } else {
                gameEngine.stand();
                this.phaseOfGame();
            }
        }

         this.currentPause = new PauseTransition(Duration.seconds(PAUSE_DURATION_SECONDS));
         this.currentPause.setOnFinished(event -> {

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
         });

        if (!this.isPaused) {
         this.currentPause.play();
        }
    }

    private void setRound() {
        if (this.random.nextInt(CHANCE_BOUND) < SPECIAL_ROUND_THRESHOLD) {
            gameEngine.setSpecialRound(this.chooseSpecialRound());
        } else {
            gameEngine.setSpecialRound(null);
            this.tableView.setSpecialRound("");
        }

        String specialRoundDesc = null;
        if (gameEngine.getSpecialRound() != null) {
            specialRoundDesc = gameEngine.getSpecialRound().getDescription();
        }
        this.tableView.setSpecialRound(specialRoundDesc); // aggiorno la view in modo da mostrare il round speciale se è presente
        this.phaseOfGame();
    }

    private SpecialRound chooseSpecialRound() {
        final int choice = this.random.nextInt(NUM_SPECIAL_ROUNDS);
        SpecialRound specialRound = null;
        switch (choice) {
            case 0:
                specialRound = new YingYung();
                break;
            case 1:
                specialRound = new DoubleHeartsRule();
                break;
            case 2:
                specialRound = new RoyalFreezeTurn();
                break;
            default:
                break;
        }
        return specialRound;
    }

    private void upDateView() {
        tableView.updateDealerCard(gameEngine.getDealerHand().getCards());
        tableView.setDealerScore(gameEngine.currentScore(gameEngine.getDealerHand()));

        if (gameEngine.getPlayers().size() >= 2) {
            tableView.setPlayerNames(
                gameEngine.getPlayers().get(0).getName(),
                gameEngine.getPlayers().get(1).getName()
            );
        }

        for (int i = 0; i < gameEngine.getPlayers().size(); i++) {
            final var p = gameEngine.getPlayers().get(i);
            final int score = gameEngine.currentScore(p.getHand());
            if (i == 0) {
               tableView.updatePlayer1Cards(gameEngine.getPlayers().get(i).getHand().getCards());
               tableView.setPlayer1Score(score);
               if (p instanceof Player) {
                tableView.setPlayer1Wallet(((Player) p).getWallet());
               }
            } else if (i == 1) {
                tableView.updatePlayer2Cards(gameEngine.getPlayers().get(i).getHand().getCards());
                tableView.setPlayer2Score(score);
                if (p instanceof Player) {
                tableView.setPlayer2Wallet(((Player) p).getWallet());
               }
            }
        }
    }

    private void resetScore() {
        if (gameEngine.getPlayers().size() >= 2) {
            tableView.setPlayer1Score(0);
            tableView.setPlayer2Score(0);
        } else {
            tableView.setPlayer1Score(0);
        }
    }

    private boolean humanPlayer(final Partecipant p) {
        return p instanceof Player && !(p instanceof NPC);
    }
}
