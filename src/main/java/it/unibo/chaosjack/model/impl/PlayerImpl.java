package it.unibo.chaosjack.model.impl;

import java.util.List;
import java.util.ArrayList;
import it.unibo.chaosjack.model.api.*; 

public class PlayerImpl implements Player {
    
    private final List<Card> hand; 
    private boolean isStanding; //i use it to see if the player is still playing


    public PlayerImpl(){ //this is the constructor
        this.hand = new ArrayList<>(); //create my hand
        this.isStanding = false; // the player is playing
    }

    @Override
    public void addCard(Card card) {
        if(!isStanding && getScore() <= 21){ //check the requirements
            this.hand.add(card);
        }
        else{
            System.out.println("The player is done playing or has busted");
        }
        
    }

    @Override
    public int getScore() {
        int total=0; //the total score of the player
        int aces=0; // counting how many aces i get

        for(Card card : this.hand){ //I get the value of each card
            int value = card.getValue();
            total += value;//I add every value to the total
            if(value == 11){ //I keep the count for the aces
                aces++;
            }
        }
        while (total > 21 && aces > 0){
            total -= 10; //I use the value 1 for the aces
            aces--;  
            }
            return total;
        }


    @Override
    public boolean isBusted() {
        return this.getScore() > 21;
    }

    @Override
    public List<Card> getHand() {
        return new ArrayList<>(this.hand); //i create a copy so I'm not changing the original
    }

    @Override
    public void stand() {
        this.isStanding = true; //the variabile is now true and I stop the game
    }
    
}
