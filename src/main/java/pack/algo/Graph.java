package pack.algo;

public class Graph {

    private myArrayList<myArrayList<Edge>> adj = new myArrayList<>();

    public void addDirectedEdge(int src, int dest, double dist, double time) {
        if (src < 0 || dest < 0) return;
        int mx = Math.max(src, dest);
        ensureVertex(mx);
        adj.get(src).add(new Edge(dest, dist, time));
    }

    public int size() {
        return adj.size();
    }

    myArrayList<Edge> edgesFrom(int idx) {
        if (idx < 0 || idx >= adj.size()) return new myArrayList<>();
        return adj.get(idx);
    }

    private void ensureVertex(int idx) {
        while (adj.size() <= idx) adj.add(new myArrayList<>(5));
    }
}
