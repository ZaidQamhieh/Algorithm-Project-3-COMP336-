package pack.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Graph {
    private static final class PendingEdge {
        String from;
        String to;
        double dist;
        double time;

        PendingEdge(String from, String to, double dist, double time) {
            this.from = from;
            this.to = to;
            this.dist = dist;
            this.time = time;
        }
    }

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<LinkedList<Edge>> adj = new ArrayList<>();

    private final ArrayList<String> namePool = new ArrayList<>();
    private final ArrayList<PendingEdge> pending = new ArrayList<>();
    private boolean built = true;

    public void addDirectedEdge(String from, String to, double dist, double time) {
        if (from == null || to == null) return;
        namePool.add(from);
        namePool.add(to);
        pending.add(new PendingEdge(from, to, dist, time));
        built = false;
    }

    public int size() {
        ensureBuilt();
        return adj.size();
    }

    public int indexOf(String name) {
        ensureBuilt();
        return indexOfBuilt(name);
    }

    public String nameOf(int idx) {
        ensureBuilt();
        if (idx < 0 || idx >= names.size()) return null;
        return names.get(idx);
    }

    LinkedList<Edge> edgesFrom(int idx) {
        ensureBuilt();
        return adj.get(idx);
    }

    private void ensureBuilt() {
        if (built) return;

        ArrayList<String> all = new ArrayList<>(namePool);
        Collections.sort(all);

        names = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            String cur = all.get(i);
            if (names.isEmpty() || !names.get(names.size() - 1).equals(cur)) names.add(cur);
        }

        adj = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) adj.add(new LinkedList<>());

        for (int i = 0; i < pending.size(); i++) {
            PendingEdge pe = pending.get(i);
            int f = indexOfBuilt(pe.from);
            int t = indexOfBuilt(pe.to);
            if (f >= 0 && t >= 0) adj.get(f).add(new Edge(t, pe.dist, pe.time));
        }

        namePool.clear();
        pending.clear();
        built = true;
    }

    private int indexOfBuilt(String name) {
        int lo = 0;
        int hi = names.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int c = names.get(mid).compareTo(name);
            if (c == 0) return mid;
            if (c < 0) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }
}
