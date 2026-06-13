package it.unibo.chaosjack.view.api;

import it.unibo.chaosjack.model.api.Statistics;
import javafx.scene.Parent;

public interface StatisticsView {
    
    /**
     * Show the statistics window on screen.
     * @param stats the Statistics object from which to extract data.
     * @return the root node (Parent).
     */
    Parent createRoot(Statistics stats, Runnable onBack);
}
