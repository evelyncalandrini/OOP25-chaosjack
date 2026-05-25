package it.unibo.chaosjack.controller.api;

public interface GameFlowController {
    /**
     * faccio giocare i giocatori non umani 
     */
    void automaticShift();

    /**
     * gestisce le fasi del gioco, ad esempio se è il turno del dealer o dei npc, se è il momento di mostrare i risultati eccetera
     */
    void phaseOfGame();
}
