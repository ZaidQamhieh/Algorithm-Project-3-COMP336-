package pack.algo;

public class Vertex implements Comparable<Vertex> {
    // Index of the Vertex in the Graph
    public int idx;
    /* Key Value Used for Comparison in Priority Queue
    Represents Current Distance or Cost*/
    public double key;

    public Vertex(int idx, double key) {
        this.idx = idx;
        this.key = key;
    }

    // Compares Vertices Based on Key Value
    // Used to Maintain Ordering in Min-Heap
    @Override
    public int compareTo(Vertex o) {
        if (this.key < o.key) return -1;
        if (this.key > o.key) return 1;
        return 0;
    }
}
