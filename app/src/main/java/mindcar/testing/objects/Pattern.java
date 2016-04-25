package mindcar.testing.objects;

import java.util.LinkedList;

/**
 * this class takes values from eeg and patterns them in a linked list
 * Created by sarahaldelame and Mattias Landkvist on 19/04/16.
 */

public class Pattern <T> {
    // the list to contain the eeg values
    private LinkedList<T>list ;

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
    public void add(T t) {
        if(isFull()) {
            list.removeFirst();
        }
        list.add(t);
    }
    // checking if the size of the list is full
    private boolean isFull() {
        if(list.size() < capacity){
            return false;
        }
        return true;
    }

    /**
     * Complexity: O(N), the following method compares two patterns
     * @param p
     * @return
     */
    public boolean equals(Pattern<T> p){
        int i = 0;
        for(T t : list){
            if(!t.equals(p.get(i))){
                return false;
            }
            i++;
        }
        return true;

    }

    public T get(int i) {
        return list.get(i);
    }
}