package mindcar.testing.objects;

import java.util.LinkedList;

/**
 * this class takes values from eeg and patterns them in a linked list restricted by a capacity value
 * Created by sarahaldelame and Mattias Landkvist on 19/04/16.
 */

public class Pattern {
    // the list to contain the eeg values
    private LinkedList<Eeg> list;
    private final int capacity;

    /**
     *  constructor of the list and a capacity set to 100
     */
    public Pattern() {
        this.list = new LinkedList<>();
        this.capacity = 100;
    }

    /**
     * Contructs new pattern with an initial Eeg object and a custom capacity
     * @param eeg
     * @param capacity
     */
    public Pattern(Eeg eeg, int capacity) {
        this.list = new LinkedList<>();
        this.capacity = capacity;
        add(eeg);
    }

    // another constructor with the capacity set to the limit
    public Pattern(int capacity) {
        this.list = new LinkedList<>();
        this.capacity = capacity;
    }

    // values are added to the pattern list and are limited to the size of the list
    public void add(Eeg eeg) {
        if (isFull()) {
            list.removeFirst();
        }
        list.add(eeg);
    }

    /**
     * Check if the Eeg value isFull() before adding it to the Pattern. Populates the Eeg value with
     * a Eeg value from another pattern if isFull() is false.
     *
     * @param eeg
     * @param pattern
     */
    public void add(Eeg eeg, Pattern pattern) {
        if (eeg.isFull() || pattern == null) {
            this.add(eeg);
        } else {
            eeg.populate(pattern.get(this.list.size()));
            this.add(eeg);
        }
    }


    /**
     * Checking if the size of the list is full
     *
     * @return true if Pattern is full
     */
    public boolean isFull() {
        if (list.size() != capacity-1) {
            return false;
        }
        return true;
    }


    /**
     * @param i
     * @return Eeg at position i in this Pattern
     */
    public Eeg get(int i) {
        return list.get(i);
    }

    /**
     * Converts this Pattern to an array of doubles ordered by Eeg object
     *
     * @return double[]
     */
    public double[] toArray() {
        double[] doubles = new double[capacity * 8];
        int i = 0;
        for (Eeg eeg : list) {
            double[] tmp = eeg.toDoubleArray();
            for (double d : tmp) {
                doubles[i] = d;
                i++;
            }
            ;
        }
        return doubles;
    }

    public int length() {
        return list.size();
    }
}
