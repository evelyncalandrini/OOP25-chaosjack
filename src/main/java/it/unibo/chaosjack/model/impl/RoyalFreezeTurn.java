package it.unibo.chaosjack.model.impl;
import it.unibo.chaosjack.model.api.SpecialRound;
import java.util.List;
import it.unibo.chaosjack.model.api.Card;
public class RoyalFreezeTurn implements SpecialRound{

    /**
     * this class represent a special round where the royals cards (king,queen,jack) are worth 0 points
     */
    @Override
    public int specialScore(List<Card> playersCards){
        int score =0;
        for ( Card c :playersCards){
            if (c.getName().equals("KING") || c.getName().equals("QUEEN") || c.getName().equals("JACK")){
                score += 0;
            } 
            else {
                score += c.getValue();
            }
         }
         return score;
        } 


        
    }
    

