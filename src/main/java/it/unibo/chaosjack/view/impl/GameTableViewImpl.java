package it.unibo.chaosjack.view.impl;

import it.unibo.chaosjack.model.api.Table;
import it.unibo.chaosjack.view.api.GameTableView;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implementation of playing table.
 */
public class GameTableViewImpl implements GameTableView {
    private final BorderPane root;
    private final Label statusLabel = new Label("Fase: FIRST BET");
    private final Label potLabel = new Label("Pot: 0 fishes");

    private final Button hitButton = new Button("Hit");
    private final Button standButton = new Button("Stand");
    private final Button betButton = new Button("Bet");

    public GameTableViewImpl() {
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #2E8B57;");
        this.initLayout();
    }

    private void initLayout() {
        final VBox centerArea = new VBox(20, statusLabel, potLabel);
        centerArea.setAlignment(Pos.CENTER);
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        potLabel.setStyle("-fx-text-fill: yellow; -fx-font-size: 18px;");
        this.root.setCenter(centerArea);     

        final HBox actionButtons = new HBox(15, betButton, hitButton, standButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setStyle("-fx-padding: 20;");
        this.root.setBottom(actionButtons);
    }
    
    @Override
    public Parent getRootNode() {
        return this.root;
    }

    @Override
    public void updatePot(int amount) {
       this.potLabel.setText("Pot : " + amount + "fishs");
    }

    @Override
    public void setGameState(Table.State state) {
        this.statusLabel.setText("Accutal phase: " + state.name());
    }

    @Override
    public void setHitHandler(Runnable handler) {
        this.hitButton.setOnAction(e -> handler.run());
    }

    @Override
    public void setStandHandler(Runnable handler) {
        this.standButton.setOnAction(e -> handler.run());
    }

    @Override
    public void setBetHandler(Runnable handler) {
        this.betButton.setOnAction(e -> handler.run());
    }
    
}
