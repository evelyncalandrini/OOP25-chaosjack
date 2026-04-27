package it.unibo.chaosjack.model.api;

public interface Dealer extends Player {
    
    boolean ShouldHit(); //questo metodo mi dice di continuare a pescare se il banco non è arrivato a 17
    void PlayTurn(Deck deck); //è il metodo che gestisce l'effettivo turno,non c'è in player perchè è una scelta del giocatore
    
}
