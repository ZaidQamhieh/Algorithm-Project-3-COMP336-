package pack.algo;

public class Vertex implements Comparable<Vertex> {
    public int v;
    public double key;

    public Vertex(int v, double key) {
        this.v = v;
        this.key = key;
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.key < o.key) return -1;
        if (this.key > o.key) return 1;
        return 0;
    }
}
