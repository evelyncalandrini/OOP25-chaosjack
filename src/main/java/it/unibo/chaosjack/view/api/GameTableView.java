package it.unibo.chaosjack.view.api;

import javafx.scene.Parent;

/**
 * Represents the visual interface of the game table.
 */
public interface GameTableView {

    /**
     * @return the root node.
     */
    Parent getRootNode();

    /**
     * @param amount shows the amount of fish in the pot.
     */
    void updatePot(int amount);

    /**
     * The action of click the button "Hit".
     * @param handler the action.
     */
    void setHitHandlew(Runnable handler);

    /**
     * The action of click the button "Stand".
     * @param handler the action.
     */
    void setStandHandler(Runnable handler);

    /**
     * The action of click the button "Bet".
     * @param handler the action.
     */
    void setBetHandler(Runnable handler);
}
