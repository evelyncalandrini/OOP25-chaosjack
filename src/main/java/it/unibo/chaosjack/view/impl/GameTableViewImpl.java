package it.unibo.chaosjack.view.impl;

import it.unibo.chaosjack.view.api.GameTableView;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Implementation of playing table.
 */
public class GameTableViewImpl implements GameTableView {
    private final BorderPane root;
    private final Label statusLabel = new Label("Fase: FIRST BET");
    private final Label potLabel = new Label("Pot: 0 fishes");

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
    }
    
    @Override
    public Parent getRootNode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRootNode'");
    }

    @Override
    public void updatePot(int amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePot'");
    }

    @Override
    public void setHitHandlew(Runnable handler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHitHandlew'");
    }

    @Override
    public void setStandHandler(Runnable handler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStandHandler'");
    }

    @Override
    public void setBetHandler(Runnable handler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBetHandler'");
    }
    
}
