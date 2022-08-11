package model;

import java.util.ArrayList;
import java.util.List;

// Represents an observable in the Observer design pattern
public class Observable {
    protected List<Observer> observers;

    // EFFECTS: constructor, insantiates new empty list of observables
    public Observable() {
        this.observers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new observer to the list of observers
    public void addObserver(Observer o) {
        observers.add(o);
    }

    // EFFECTS: notifies observers when change occurs in this state of the observable
    public void notifyObservers(WifiPassword p) {
        for (Observer o: observers) {
            o.update(p);
        }
    }

}
