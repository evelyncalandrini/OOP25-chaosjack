package it.unibo.chaosjack.model.impl;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.api.TurnState;

public class BotTurn implements TurnState {

   /**
     * this interface represents the bot player's turn
     */
     private GameEngine game;
     private int myIndex;


     public BotTurn(GameEngine game, int myindex) {
        this.game = game;
        this.myIndex = myindex;
     }

    

     @Override
     public boolean stand() {
        game.nextTurn();
        return true;
     }


     /**
     * the bot continues to draw cards until it reaches a certain condition
     */
     @Override 
        public void hit() {
         Hand botHand = game.getPlayers().get(myIndex).getHand();
         while (botHand.getScore() < 21){
            game.getDeck().draw().ifPresent(botHand :: addCard);
         }

         stand();
        }

        
        @Override
        public boolean doubleDown() {
        if ( game.getPlayers().get(myIndex).getBet() *2 < game.getPlayers().get(myIndex).getWallet().getBalance() ) { // controllo che il giocatore abbia abbastanza soldi per raddoppiare
            this.hit(); // faccio pescare una carta al giocatore
            this.stand(); // passo al turno successivo
            return true;
        } else{
            // se il giocatore non ha abbastanza soldi per raddoppiare, non faccio nulla e restituisco la scommessa originale
            return false; // devo capire cosa far tornare nel caso in cui la doppia scommessa non sia possibile
        }
    }


     
        


    

    
    
}
