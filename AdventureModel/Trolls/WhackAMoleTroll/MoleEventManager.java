package AdventureModel.Trolls.WhackAMoleTroll;

import java.util.ArrayList;
import java.util.List;

/**
 * MoleEventManager Class
 */
public class MoleEventManager {
    private List<MoleEventListener> listeners = new ArrayList<>();

    /**
     * Add a listener to MoleEventManager
     * @param listener
     */
    public void subscribe(MoleEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener from MoleEventManager
     * @param listener
     */

    public void unsubscribe(MoleEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify listeners when a mole is clicked.
     */

    public void notifyMoleClicked() {
        for (MoleEventListener listener : listeners) {
            listener.moleClicked();
        }
    }

}
