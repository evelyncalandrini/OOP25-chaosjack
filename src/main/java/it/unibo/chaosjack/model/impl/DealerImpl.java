package it.unibo.chaosjack.model.impl;

import it.unibo.chaosjack.model.api.*;

public class DealerImpl  extends PlayerImpl implements Dealer{
//magari dovrò aggiungere un costruttore

    @Override
    public boolean shouldHit() {
         return this.getScore() < 17; //says if it is convinient to draw
    }
    @Override
    public void playTurn(Deck deck) {
       while ( this.shouldHit() ){
         Card Carddrawn = deck.drawcard(); //temporary method
        this.addCard(Carddrawn);
       }
       this.stand(); //when is done drawing it stands
    }
  
    
}
