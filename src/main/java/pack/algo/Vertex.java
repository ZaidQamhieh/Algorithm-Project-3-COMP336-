package pack.algo;

public class Vertex implements Comparable<Vertex> {
    // Index of the Vertex in the Graph
    private int index;
    /* Key Value Used for Comparison in Heap
    Represents Current Distance or Cost*/
    private double cost;

    public Vertex(int index, double cost) {
        this.index = index;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public double getCost() {
        return cost;
    }

    /* Compares Vertices Based on Key Value
    Used to Maintain Ordering in the Heap*/
    @Override
    public int compareTo(Vertex o) {
        if (this.cost < o.cost) return -1;
        if (this.cost > o.cost) return 1;
        return 0;
    }
}
