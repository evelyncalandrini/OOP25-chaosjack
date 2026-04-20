package it.unibo.chaosjack;

import it.unibo.chaosjack.view.api.MainMenuView;
import it.unibo.chaosjack.view.impl.MainMenuViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage finestraPrincipale) {
        // 1. Creiamo l'oggetto del tuo menu
        MainMenuView menu = new MainMenuViewImpl();
        
        // 2. Chiamiamo il tuo metodo per far apparire la grafica sulla finestra!
        menu.display(finestraPrincipale); 
    }
}
