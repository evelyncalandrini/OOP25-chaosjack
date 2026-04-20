package it.unibo.chaosjack.model.impl;
import it.unibo.chaosjack.model.api.gameEngine;
import it.unibo.chaosjack.model.api.deck;
import it.unibo.chaosjack.model.api.turnState;
import it.unibo.chaosjack.model.impl.hand;

public class gameEngineImpl implements gameEngine {

    private deck deck;
    private hand playerHand;
    private hand dealerHand;
    //private Wallet walletPlayer;
    private turnState currentState;

    public gameEngineImpl( deck deck, hand PlayerHand, hand DealerHand) { // ricorda di aggiuungere il wallet
        this.deck = deck;
        this.playerHand = new hand();
        this.dealerHand = new hand();

        this.currentState = new playerTurn(this); // il gioco inizia con il turno del giocatore.
    }

    @Override
    public void changeState(turnState newState){
        this.currentState = newState; // questo metodo mi permette di cambiare lo stato del gioco (passare dal turno del giocatore a quello del banco e viceversa)
    }

    @Override
    public deck getDeck() {
        return deck;
    }

    @Override
    public hand getPlayerHand() {
        return playerHand;
    }

    @Override
    public hand getDealerHand() {
        return dealerHand;
    }

}
