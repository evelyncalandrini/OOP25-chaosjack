package it.unibo.chaoskjack.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.chaosjack.model.api.Table;
import it.unibo.chaosjack.model.api.Table.State;
import it.unibo.chaosjack.model.api.Wallet;
import it.unibo.chaosjack.model.impl.TableImpl;

public class TableTest {
    private Table table;
    private Wallet wallet;
    private final List<String> players = List.of("Marameo", "Bob");
    
    @BeforeEach
    void setUp(){
        wallet = new Wallet() {
            private int balance = 2000;
            @Override
            public int getBalance() {return balance;}
            @Override
            public void addFunds(int amount){balance += amount;}
            @Override
            public boolean removeFunds(int amount){
                if (balance >= amount) {balance -= amount; return true;}
                return false;
            }
        };
        table = new TableImpl(wallet, players);
    }

    @Test
    void testInitialState(){
        assertEquals(State.FIRST_BET, table.getCurrentState());
        assertEquals(0, table.getPot(), "the pot must be 0");
        assertEquals(1,table.getRoundCount());
    }


}
