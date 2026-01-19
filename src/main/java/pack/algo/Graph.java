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

    myArrayList<Edge> edgesFrom(int id) {
        if (id < 0 || id >= adj.size()) return new myArrayList<>();
        return adj.get(id);
    }

    private void ensureVertex(int id) {
        while (adj.size() <= id)
            adj.add(new myArrayList<>(5));
    }
}
