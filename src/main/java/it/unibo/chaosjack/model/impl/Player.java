package it.unibo.chaosjack.model.impl;
import it.unibo.chaosjack.model.api.Wallet;

public class Player {
    private String name;
    private Hand playerHand;
    private boolean isBot;
    private Wallet wallet;
    private int bet;

    public Player ( String name,  boolean isBot, Wallet wallet, int bet) {
        this.name = name;
        this.playerHand = new Hand();
        this.isBot = isBot;
        this.wallet = wallet;
        this.bet = bet;

    }

    public boolean isBot() {
       return this.isBot;
    }

    public String getName(){
        return this.name;
    }

    public Hand getHand(){
        return this.playerHand;
    }

    public int getBet() {
        return this.bet;
    }

    public Wallet getWallet() {
        return this.wallet;
    }
}
