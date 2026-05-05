package it.unibo.chaoskjack.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.chaosjack.model.api.Deck;
import it.unibo.chaosjack.model.impl.StandardDeck;
import it.unibo.chaosjack.model.impl.Player;
import it.unibo.chaosjack.model.impl.StandardWallet;
import java.util.List;
import java.util.ArrayList;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.impl.GameEngineImpl;
import it.unibo.chaosjack.model.api.TurnState;
import it.unibo.chaosjack.model.impl.PlayerTurn;
import it.unibo.chaosjack.model.impl.BotTurn;
import it.unibo.chaosjack.model.impl.DealerTurn;


public class GameEngineImplTest {
    
    private Deck deck;
    private Player human1;
    private Player bot1;
    private GameEngine engine;

    @BeforeEach
    public void setUp() {
       deck = new StandardDeck();
       human1 = new Player("topolino", false, new StandardWallet(1000), 100);
       bot1 = new Player("pippo", true, new StandardWallet(1000), 100);
        List<Player> players = new ArrayList<>();
        players.add(human1);
        players.add(bot1);

        engine = new GameEngineImpl(deck, players);
        engine.changeState(new PlayerTurn(engine, 0));
        
    }

    @Test
    public void nextTurnTest() {
        TurnState currentState = engine.getActualState();
        assertTrue(currentState instanceof PlayerTurn);
        engine.nextTurn();
        assertTrue(engine.getActualState() instanceof BotTurn);

        engine.nextTurn();
        assertTrue(engine.getActualState() instanceof DealerTurn);
    }
}
