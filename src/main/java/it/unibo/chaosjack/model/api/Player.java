package it.unibo.chaosjack.model.api;

import java.util.List;

public interface Player {

     void addCard(Card card); 
     int getScore();
     boolean isBusted(); //tells if I'm above 21
     List<Card> getHand(); //gives the card in  the hand of the player
     void stand();//when the player wants to end the game

}
