package pack.algo;

public class Vertex implements Comparable<Vertex> {
    public int idx;
    public double key;

    public Vertex(int idx, double key) {
        this.idx = idx;
        this.key = key;
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.key < o.key) return -1;
        if (this.key > o.key) return 1;
        return 0;
    }
}
