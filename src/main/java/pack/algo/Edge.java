package pack.algo;

public class Edge {
    // Destination index
    public int destIndex;
    // Distance between Edges
    public double dist;
    // Time between Edges
    public double time;
    // The Label of the Source and Destination
    public String srcName;
    public String destName;


    public Edge(int destIndex, double dist, double time) {
        this.destIndex = destIndex;
        this.dist = dist;
        this.time = time;
    }

    public Edge(String srcName, String destName, double dist, double time) {
        this.srcName = srcName;
        this.destName = destName;
        this.dist = dist;
        this.time = time;
    }
}
