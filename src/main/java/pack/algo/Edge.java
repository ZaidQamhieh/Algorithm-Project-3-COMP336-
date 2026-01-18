package pack.algo;

public class Edge {
    public int to;
    public double dist;
    public double time;

    public String fromName;
    public String toName;

    public Edge(int to, double dist, double time) {
        this.to = to;
        this.dist = dist;
        this.time = time;
    }

    public Edge(String fromName, String toName, double dist, double time) {
        this.fromName = fromName;
        this.toName = toName;
        this.dist = dist;
        this.time = time;
    }
}
