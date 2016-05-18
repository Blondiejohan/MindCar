package mindcar.testing.objects;

import java.util.LinkedList;

/**
 * this class takes values from eeg and patterns them in a linked list
 * Created by sarahaldelame and Mattias Landkvist on 19/04/16.
 */

public class Pattern {
    // the list to contain the eeg values
    private LinkedList<Eeg>list ;
    private final int capacity;

    // constructor of the list and a capacity limit to the list
    public Pattern(){
        this.list = new LinkedList<>();
        this.capacity = 100;
    }

    public Pattern(Eeg eeg){
        this.list = new LinkedList<>();
        this.capacity = 100;
        add(eeg);
    }
    // another constructor with the capacity set to the limit
    public Pattern(int capacity){
        this.list = new LinkedList<>();
        this.capacity = capacity;
    }

    // values are added to the pattern list and are limited to the size of the list
    public void add(Eeg eeg) {
        if(isFull()) {
            list.removeFirst();
        }
        list.add(eeg);
    }

    /**
     * Check if the Eeg value isFull() before adding it to the Pattern. Populates the Eeg value with
     * a Eeg value from another pattern if isFull() is false.
     * @param eeg
     * @param pattern
     */
    public void add(Eeg eeg, Pattern pattern){
        if(eeg.isFull()){
            this.add(eeg);
        } else {
            eeg.populate(pattern.get(this.list.size()-1));
            this.add(eeg);
        }
    }


    // checking if the size of the list is full
    private boolean isFull() {
        if(list.size() < capacity){
            return false;
        }
        return true;
    }



    public Eeg get(int i) {
        return list.get(i);
    }

    public double[] toArray(){
        double[] doubles = new double[capacity * 8];
        int i = 0;
        for(Eeg eeg : list){
            double[] tmp = eeg.toDoubleArray();
            for(double d: tmp){
                doubles[i] = d;
                i++;
            };
        }
        return doubles;
    }
}
