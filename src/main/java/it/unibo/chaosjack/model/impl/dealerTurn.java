package it.unibo.chaosjack.model.impl;

import it.unibo.chaosjack.model.api.gameEngine;
import it.unibo.chaosjack.model.api.turnState;

import java.util.Optional;

import it.unibo.chaosjack.model.api.Card;

public class dealerTurn implements turnState { 
  private final gameEngine game;
  public dealerTurn(gameEngine game){
    this.game = game;
  }

  
    
  @Override
  public void hit() {
    Optional<Card> controlloCarta = game.getDeck().draw(); //questo metodo andrà bene quando elena farà il push
        
        if (controlloCarta.isPresent()){ // contorllo che il valore della carta non sia nullo (che il mazzo non sia vuoto)
            Card cartaPescata = controlloCarta.get(); // se la carta è presnete allora la assegno a una carta vera e propria e la aggiungo alla mano
            game.getDealerHand().addCard(cartaPescata);
        }
  }

  @Override
  public void stand() {
    // il banco si ferma e si confronta il punteggio con quello del giocatore per decidere chi vince
  }

  @Override
    public void doubleDown() {
    }

  @Override
    public String getStateName() {
        return "dealerTurn";
    }

/* gestisco il turno del banco  */    
}
