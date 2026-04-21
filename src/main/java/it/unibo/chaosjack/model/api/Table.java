package it.unibo.chaosjack.model.api;

public interface Table {

    /**
     * Rapresents the game phases managed by the table
     */
    enum State{
        FIRST_BET,
        PLAYING,
        FINAL_BET,
        DEALER_TURN,
        RESULTS
    }

    /**
     * @return current status of the table
     */
    State getCurrentState();

    /**
     * 
     */
    void placeBet(int amount);

    /**
     * @return number of fishes on the table
     */
    int getPot();

    /**
     * Reset the table
     */
    void reset();
    
    /**
     * @return winner of the round
     */
    RoundResult getWinner();

}