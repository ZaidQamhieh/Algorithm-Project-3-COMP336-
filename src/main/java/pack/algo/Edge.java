package pack.algo;

public class Edge {
    // Destination index
    private int destIndex;
    // Distance between Edges
    private double dist;
    // Time between Edges
    private double time;
    // The Label of the Source and Destination
    private String srcName;
    private String destName;


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

    public int getDestIndex() {
        return destIndex;
    }

    public void setDestIndex(int destIndex) {
        this.destIndex = destIndex;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }
}
