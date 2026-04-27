package it.unibo.chaosjack.model.api;

import java.util.List;

public interface Player {

     void addCard(Card card); 
     int getScore();
     boolean isBusted(); //mi dice se ha superato i 21
     List<Card> getHand(); //mi restituisce le carte che ho in mano
     void stand();//per dire che il giocatore ha finito di giocare

}
