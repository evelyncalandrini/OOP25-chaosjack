package it.unibo.chaosjack.model.impl;

import it.unibo.chaosjack.model.api.Card;
import java.util.List;
import java.util.ArrayList;

public class Hand {
    private final List<Card> cards = new ArrayList<>(); // è l'insieme di carte che ho in mano 

    

    public void addCard( Card card) { // metodo per aggiungere una carta nella mia mano 
        cards.add(card);
    }

    public int getScore(){ // restituisce lo score della mano 
        int score = 0;
        int assesCount = 0;

        for (int i = 0; i < cards.size(); i++) {
             score += cards.get(i).getValue();
              if ( cards.get(i).getValue() == 11) {
                assesCount++;
              }
        }

        while ( score > 21 && assesCount > 0) { /*se ho deli assi in mano e il punteggio totale è maggiore di 21 toglo 10 dal punteggio 
            è come se contassi alcuni assi con vlaore 1*/
            score -=10;
            assesCount--;
        }

        return score;
    }


    public boolean isRed(List<Card> cards){
        int redCount = 0;
        for (Card c : cards) {
            if (c.getName().equals("HEARTS") || c.getName().equals("DIAMONDS")){
                redCount++;
            }
        }

        if ( redCount == cards.size()) {
            return true;
        } else {
            return false;
        }
    }

    

    
}
