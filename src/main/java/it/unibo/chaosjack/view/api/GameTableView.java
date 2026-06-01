package it.unibo.chaosjack.view.api;

import java.util.List;
import java.util.function.Consumer;

import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.api.Table;
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
     * The current table state.
     * @param state .
     */
    void setGameState(Table.State state);

    /**
     * @param amount shows the amount of fish in the pot.
     */
    void updatePot(int amount);

    /**
     * The action of click the button "Hit".
     * @param handler the action.
     */
    void setHitHandler(Runnable handler);

    /**
     * The action of click the button "Stand".
     * @param handler the action.
     */
    void setStandHandler(Runnable handler);

    /**
     * The action of click the button "DoubleHandler".
     * @param handler the action.
     */
    void setDoubleDownHandler(Runnable handler);

    /**
     * The action of click the button "Bet".
     * @param handler the action.
     */
    void setBetHandler(Consumer<Integer> handler);

    /**
     * Navigaton in the menu.
     * @param handler .
     */
    void setMenuHandler(Runnable handler);

    /**
     * Update grafic of dealer's cards.
     * @param cards .
     */
    void updateDealerCard(final List<Card> cards);

    /**
     * Update grafic of first player's  cards.
     * @param cards .
     */
    void updatePlayer1Cards(final List<Card> cards);

    /**
     * Update grafic of second player's cards.
     * @param cards
     */
    void updatePlayer2Cards(final List<Card> cards);

    /**
     * Dynamically assigns names to players.
     * @param name1 first player.
     * @param name2 second player.
     */
    void setPlayerNames(final String name1, final String name2);
}
