package pack.algo;

public class Vertex implements Comparable<Vertex> {
    // Index of the Vertex in the Graph
    public int id;
    /* Key Value Used for Comparison in Priority Queue
    Represents Current Distance or Cost*/
    public double key;

    public Vertex(int id, double key) {
        this.id = id;
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
