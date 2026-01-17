package pack.algo;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    public ArrayList<String> names = new ArrayList<>();
    public ArrayList<LinkedList<Edge>> adj = new ArrayList<>();

    public int indexOf(String name) {
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(name)) return i;
        }
        names.add(name);
        adj.add(new LinkedList<>());
        return names.size() - 1;
    }

    public int size() {
        return adj.size();
    }

    public void addDirectedEdge(String from, String to, double dist, double time) {
        int f = indexOf(from);
        int t = indexOf(to);
        adj.get(f).add(new Edge(t, dist, time));
    }
}