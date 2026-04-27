package it.unibo.chaosjack.model.api;

public interface GameEngine {
    
    void StartNewRound(); //incomincia una nuova partita,resetta le mani e mischia il mazzo
    void hit(); //pesca una carta dal mazzo per il giocatore
    void stand(); //termina il turno
    GameStatus getCurrentStatus(); //usa gamestatus per dirmi a che punto sono con il gioco
    Player getPlayer(); //restituisce il giocatore e il banco per farli vedere a video
    Dealer getDealer();
}
