package pack.algo;

public class myHeap<T extends Comparable<T>> {
    private T[] list;
    private int size = 0;

    // Initialize Heap with Initial Capacity
    public myHeap() {
        list = (T[]) new Comparable[2];
    }

    // Double Array Capacity when Full
    private void grow() {
        T[] nl = (T[]) new Comparable[list.length * 2];
        for (int i = 1; i <= size; i++) nl[i] = list[i];
        list = nl;
    }

    // Add Element to Heap and Maintain Min-Heap Property
    public void add(T data) {
        if (data == null) return;
        if (size + 1 == list.length) grow();
        list[++size] = data;
        swim(size);
    }

    // Remove and Return Minimum Element
    public T pop() {
        if (size == 0) return null;
        T min = list[1];
        swap(1, size);
        list[size--] = null;
        sink(1);
        return min;
    }

    // Move Element Up to Restore Heap Property
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k, k / 2);
            k /= 2;
        }
    }

    // Move Element Down to Restore Heap Property
    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    // Compare Two Elements
    private boolean greater(int i, int j) {
        return list[i].compareTo(list[j]) > 0;
    }

    // Swap Two Elements in Array
    private void swap(int i, int j) {
        T t = list[i];
        list[i] = list[j];
        list[j] = t;
    }

    // Return Size of Heap
    public int size() {
        return size;
    }

    // Check if Heap is Empty
    public boolean isEmpty() {
        return size == 0;
    }
}