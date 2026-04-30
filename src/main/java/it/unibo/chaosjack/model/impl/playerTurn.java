package it.unibo.chaosjack.model.impl;
import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.api.GameEngine;
import it.unibo.chaosjack.model.api.TurnState;
import java.util.Optional;

public class PlayerTurn  implements TurnState{
    /* gestisco il turno del giocatore */
   
   private final GameEngine game;
   private int myIndex;

   public PlayerTurn(GameEngine game, int index){
    this.game = game;
    this.myIndex = index;
   }

   @Override
    public void hit(){
        Optional<Card> controlloCarta = game.getDeck().draw(); 
        
        if (controlloCarta.isPresent()){ // controllo che il valore della carta non sia nullo (che il mazzo non sia vuoto)
            Card cartaPescata = controlloCarta.get(); // se la carta è presnete allora la assegno a una carta vera e propria e la aggiungo alla mano
            game.getPlayers().get(myIndex).getHand().addCard(cartaPescata);
        }

        if (game.getPlayers().get(myIndex).getHand().getScore() > 21) { // se il giocatore sballa passo al turno successivo
            stand();
        }
    }
       

    @Override
    public boolean stand(){ // cambio  il turno ( passo al giocatore successvo o al banco )
        game.nextTurn(); // passo il turno al giocatore successivo o al banco 
        return true;
    }

    @Override
    public boolean doubleDown() {
        if ( game.getPlayers().get(myIndex).getBet() *2 < game.getPlayers().get(myIndex).getWallet().getBalance() ) { // controllo che il giocatore abbia abbastanza soldi per raddoppiare
            this.hit(); // faccio pescare una carta al giocatore
            this.stand(); // passo al turno successivo
            return true;
        } else{
            // se il giocatore non ha abbastanza soldi per raddoppiare, non faccio nulla e restituisco la scommessa originale
            return false; 
        }
    }

    
}
