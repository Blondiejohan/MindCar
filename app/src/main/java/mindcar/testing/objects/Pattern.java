package mindcar.testing.objects;

import java.util.ArrayList;
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

    public Double[] toDoubleArray(){
        ArrayList<Double[]> doubles = new ArrayList<>();
        for(Eeg eeg : list){
            doubles.add(eeg.toDoubleArray());
        }
        return (Double[]) doubles.toArray();
    }
}
