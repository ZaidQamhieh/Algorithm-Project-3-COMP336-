package pack.algo;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    public ArrayList<LinkedList<Edge>> adj = new ArrayList<>();

    public void ensureVertex(int id) {
        while (adj.size() <= id) adj.add(new LinkedList<>());
    }

    public int size() {
        return adj.size();
    }

    public void addDirectedEdge(int from, int to, double dist, double time) {
        ensureVertex(from);
        ensureVertex(to);
        adj.get(from).add(new Edge(to, dist, time));
    }
}
