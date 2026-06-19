package it.unibo.chaosjack.view.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.chaosjack.model.api.Statistics;
import it.unibo.chaosjack.view.api.StatisticsView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class StatisticsViewImpl implements StatisticsView {

    @Override
    public Parent createRoot(Statistics stats, Runnable onBack) {
       final VBox root = new VBox(25);
       root.setAlignment(Pos.CENTER);
       root.setPadding(new Insets(40));
       root.setStyle("-fx-background-color: #2b2b2b;");

       final Label titleLabel = new Label("STATISTCS");
       titleLabel.setStyle("-fx-text-fill: #FFD700; -x-font-size: 32px; -fx-font-weight: bold;");

       final Label roundsLabel = new Label("Total round: " + stats.getTotalRounds());
       roundsLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 20px");

       root.getChildren().addAll(titleLabel, roundsLabel);

       Set<String> players = new HashSet<>();
       players.addAll(stats.getNetProfit().keySet());
       players.addAll(stats.getLossHistory().keySet());

       final HBox playersContainer = new HBox(30);
       playersContainer.setAlignment(Pos.CENTER);

       for (String player : players) {
            VBox playerBox = createPlayerStatsBox(player, stats);
            playersContainer.getChildren().add(playerBox);
       }
       Button backButton = new Button("Menu");
       backButton.setStyle("-fx-background-color: #FF4444; -fx-text-fill: white; -fx-font-size: 18px;");
       backButton.setOnAction(e -> onBack.run());

       root.getChildren().add(playersContainer);
       root.getChildren().add(backButton);

       return root;
    }

    private VBox createPlayerStatsBox(String playerName, Statistics stats) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #3b3b3b; -fx-background-radius: 10;");

        Label nameLabel = new Label(playerName);
        nameLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 24px; -fx-font-weight: bold;");

        int wins = stats.getWinHistory().getOrDefault(playerName,0);
        int blackjacks = stats.getBlackHistory().getOrDefault(playerName, 0);
        int bonusWins = stats.getBonusHistory().getOrDefault(playerName,0);
        int blackBonus = stats.getBlackBonusHistory().getOrDefault(playerName,0);
        int losses = stats.getLossHistory().getOrDefault(playerName,0);
        int pushes = stats.getPushHistory().getOrDefault(playerName,0);
        int netProfit = stats.getNetProfit().getOrDefault(playerName, 0);

        Label winLabel = createStatLabel("Wins: " + wins);
        Label blackLabel = createStatLabel("Blackjacks: " + blackjacks);
        Label bonusWinLabel = createStatLabel("Bonus Wins: " + bonusWins);
        Label blackBonusLabel = createStatLabel("Blackjack Bonus: " + blackBonus);
        Label lossLabel = createStatLabel("Losses: " + losses);
        Label pushLabel = createStatLabel("Pushes: " + pushes);

        Label profitLabel = new Label("Net Profit: " + netProfit);
        if (netProfit > 0) {
            profitLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-size: 18px; -fx-font-weight: bold;");
        } else if (netProfit < 0) {
            profitLabel.setStyle("-fx-text-fill: #FF4444; -fx-font-size: 18px; -fx-font-weight: bold;");
        } else {
            profitLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18px; -fx-font-weight: bold;");
        }

        box.getChildren().addAll(
            nameLabel,
            winLabel,
            blackLabel,
            bonusWinLabel,
            blackBonusLabel,
            lossLabel,
            pushLabel,
            profitLabel
        );
        return box;
    }

    private Label createStatLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16px;");
        return label;
    }

    
}
