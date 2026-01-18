package pack.algo;

import java.util.Iterator;

public class myArrayList<T> implements Iterable<T> {
    private Object[] list;
    private int capacity;
    private int index;

    public myArrayList(int capacity) {
        if (capacity < 1) capacity = 1;
        list = new Object[capacity];
        this.capacity = capacity;
        index = 0;
    }

    public myArrayList() {
        this.capacity = 5;
        list = new Object[capacity];
        index = 0;
    }

    // Resizing Array When it Becomes Full
    private void resize() {
        if (index == capacity) {
            if (capacity < 1) capacity = 1;
            else capacity *= 2;

            Object[] newList = new Object[capacity];
            for (int i = 0; i < index; i++) {
                newList[i] = list[i];
            }
            list = newList;
        }
    }

    //Adds to The List
    public void add(T t) {
        resize();
        list[index++] = t;
    }

    // Helper Method To Remove an Element
    public void remove(T obj) {
        int index = search(obj);
        if (index != -1) removeByIndex(index);
    }

    // Removes an Element from The List
    private void removeByIndex(int indexToRemove) {
        if (indexToRemove < 0 || indexToRemove >= index) return;
        for (int i = indexToRemove; i < index - 1; i++) list[i] = list[i + 1];
        list[--index] = null;
    }

    // Searches For a Certain Element
    public int search(T obj) {
        for (int i = 0; i < index; i++) {
            Object v = list[i];
            if (v == null) {
                if (obj == null) return i;
            } else {
                if (v.equals(obj)) return i;
            }
        }
        return -1;
    }

    // Get an Element By Index
    public T get(int index) {
        if (index < 0 || index >= this.index)
            throw new IndexOutOfBoundsException();
        return (T) list[index];
    }

    public void set(int idx, T value) {
        if (idx < 0 || idx >= index)
            throw new IndexOutOfBoundsException();
        list[idx] = value;
    }

    // In Case The User Wanted to Forcibly Change The List Length
    public void updateCapacity(int newCap) {
        if (newCap <= list.length) return;

        Object[] temp = new Object[newCap];
        for (int i = 0; i < size(); i++)
            temp[i] = list[i];
        list = temp;
        capacity = newCap;
    }

    public void clear() {
        for (int i = 0; i < index; i++) list[i] = null;
        index = 0;
    }

    // Return if The List Has The Element
    public boolean contains(T obj) {
        return search(obj) != -1;
    }

    // Return if The List is Empty
    public boolean isEmpty() {
        return index == 0;
    }

    // Return The Size of The List
    public int size() {
        return index;
    }

    // To Be Able To Iterate Throughout The List via foreach
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int current = 0;

            @Override
            public boolean hasNext() {
                return current < index;
            }

            @Override
            public T next() {
                return (T) list[current++];
            }
        };
    }
}
