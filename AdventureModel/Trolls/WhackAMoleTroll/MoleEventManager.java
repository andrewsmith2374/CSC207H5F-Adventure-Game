package AdventureModel.Trolls.WhackAMoleTroll;

import java.util.ArrayList;
import java.util.List;

public class MoleEventManager {
    private List<MoleEventListener> listeners = new ArrayList<>();

    public void subscribe(MoleEventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(MoleEventListener listener) {
        listeners.remove(listener);
    }

    public void notifyMoleClicked() {
        for (MoleEventListener listener : listeners) {
            listener.moleClicked();
        }
    }

}
