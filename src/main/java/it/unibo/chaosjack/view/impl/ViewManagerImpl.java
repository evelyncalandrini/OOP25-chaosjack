package it.unibo.chaosjack.view.impl;

import it.unibo.chaosjack.view.api.GameTableView;
import it.unibo.chaosjack.view.api.ViewManager;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Implementation of ViewManger's interface.
 */
public class ViewManagerImpl implements ViewManager{
    private final Stage stage;
    private final Scene scene;

    public ViewManagerImpl(final Stage stage) {
        this.stage = stage;
        this.scene = new Scene(new Pane(), 800, 600);
        this.stage.setScene(this.scene);
        this.stage.setTitle("ChaosJack");
    }

    @Override
    public void showMainMenu() {
        this.stage.show();
    }

    @Override
    public void showGameTable() {
        final GameTableView gameTable = new GameTableViewImpl();

        this.scene.setRoot(gameTable.getRootNode());
        this.stage.show();
    }

    @Override
    public void showStatistics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showStatistics'");
    }
    
}
