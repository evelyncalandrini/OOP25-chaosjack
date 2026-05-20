package it.unibo.chaosjack;

import it.unibo.chaosjack.view.api.ViewManager;
import it.unibo.chaosjack.view.impl.ViewManagerImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final ViewManager viewManager = new ViewManagerImpl(primaryStage);
        viewManager.showGameTable();
    }

    
}
