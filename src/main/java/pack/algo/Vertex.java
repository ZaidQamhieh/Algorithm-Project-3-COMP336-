package pack.algo;

public class Vertex implements Comparable<Vertex> {
    // Index of the Vertex in the Graph
    private int index;
    /* Key Value Used for Comparison in Heap
    Represents Current Distance or Cost*/
    private double key;

    public Vertex(int index, double key) {
        this.index = index;
        this.key = key;
    }

    public int getIndex() {
        return index;
    }

    public double getKey() {
        return key;
    }

    /* Compares Vertices Based on Key Value
    Used to Maintain Ordering in the Heap*/
    @Override
    public int compareTo(Vertex o) {
        if (this.key < o.key) return -1;
        if (this.key > o.key) return 1;
        return 0;
    }
}
