package it.unibo.chaosjack.model.impl;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.api.Deck;
import it.unibo.chaosjack.model.api.TurnState;
import java.util.List;

public class GameEngineImpl implements GameEngine {

    private Deck deck;
    private Hand dealerHand;
    private List<Player> players;
    //private Wallet walletPlayer;
    private TurnState currentState;
    private int currentPlayerIndex = -1;

    public GameEngineImpl( Deck deck, List<Player> players) { // ricorda di aggiuungere il wallet
        this.deck = deck;
        this.players = players;
        this.dealerHand = new Hand();

        this.nextTurn();
    }


    

    @Override
    public void changeState(TurnState newState){
        this.currentState = newState; // questo metodo mi permette di cambiare lo stato del gioco (passare dal turno del giocatore a quello del banco e viceversa)
    }


    @Override
    public Deck getDeck() {
        return deck;
    }

    
    @Override
    public Hand getDealerHand() {
        return dealerHand;
    }

    

    
    public int getPlayerScore(String name) { // metodo per ottenere lo score di un giocatore specifico
        for ( Player p : players) {
           if ( p.getName().equals(name) ) {
             return p.getHand().getScore();
           }
        }
        return 0; 
    }


    @Override
    public void nextTurn() {
       currentPlayerIndex++; //aumento l'inidice del giocatore corrente
         if (currentPlayerIndex < players.size()) { 

            Player nextPlayer = players.get(currentPlayerIndex);
            if (nextPlayer.isBot()) { // controllo che sia effettivamente presente il giocatore 
                this.changeState(new BotTurn(this, currentPlayerIndex));
            } else {
                this.changeState(new PlayerTurn(this, currentPlayerIndex)); // passo al turno del giocatore successivo
            } 
            
        } else {
            this.changeState(new DealerTurn(this)); // se non ci sono più giocatori passo al turno del banco
        }
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void hit(){
        this.currentState.hit();
    }

    @Override
    public void stand(){
        this.currentState.stand();
    }
   }


