package it.unibo.chaosjack.controller.api;

/**
 * Interface for the GameFlowController.
 */
public interface GameFlowController {
    /**
     * Faccio giocare i giocatori non umani.
     */
    void automaticShift();

    /**
     * Gestisce le fasi del gioco, ad esempio se è il turno del dealer o dei npc, 
     * se è il momento di mostrare i risultati eccetera.
     */
    void phaseOfGame();

    /**
     * Inizializza una nuova partita.
     */
    void newGame();

    /**
     * Faccio scommettere gli npc in maniera utomatica.
     */
    void automaticBet();
}
