package it.unibo.chaosjack.model.api;
import it.unibo.chaosjack.model.impl.Hand;
import java.util.List;
import it.unibo.chaosjack.model.impl.Player;

public interface GameEngine {
    

    void changeState(TurnState newState);
    void nextTurn();

    Deck getDeck(); // restituisce il mazzo di carte
    Hand getDealerHand(); // restituisce la mano del banco

    int getPlayerScore(String name); // restituisce lo score di un giocatore specifico
    List<Player> getPlayers();

    void hit(); // metodo per il controller
    void stand(); // metodo che richiama il controller
    
}
