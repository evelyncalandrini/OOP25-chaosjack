package it.unibo.chaosjack.model.impl;
import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.api.gameEngine;
import it.unibo.chaosjack.model.api.turnState;
import java.util.Optional;

public class playerTurn  implements turnState{
    /* gestisco il turno del giocatore */
   
   private final gameEngine game;

   public playerTurn(gameEngine game){
    this.game = game;
   }

   @Override
    public void hit(){
        Optional<Card> controlloCarta = game.getDeck().draw(); //questo metodo andrà bene quando elena farà il push
        
        if (controlloCarta.isPresent()){ // contorllo che il valore della carta non sia nullo (che il mazzo non sia vuoto)
            Card cartaPescata = controlloCarta.get(); // se la carta è presnete allora la assegno a una carta vera e propria e la aggiungo alla mano
            game.getPlayerHand().addCard(cartaPescata);
        }
    }
       

    @Override
    public void stand(){ // cambio  il turno ( passo al banco )
        game.changeState(new dealerTurn(game));// passo il turno al giocatore successivo o al banco 

    }

    @Override
    public void doubleDown(){
    }

    @Override
    public String getStateName(){
        return "playerTurn";
    }

}
