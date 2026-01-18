package pack.algo;

import java.util.ArrayList;

public class Graph {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<ArrayList<Edge>> adj = new ArrayList<>();

    private final ArrayList<Edge> pending = new ArrayList<>();
    private boolean built = true;

    public void addDirectedEdge(String from, String to, double dist, double time) {
        if (from == null || to == null) return;
        pending.add(new Edge(from, to, dist, time));
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

    ArrayList<Edge> edgesFrom(int idx) {
        ensureBuilt();
        return adj.get(idx);
    }

    private void ensureBuilt() {
        if (built) return;

        ArrayList<String> all = new ArrayList<>(pending.size() * 2);
        for (int i = 0; i < pending.size(); i++) {
            Edge e = pending.get(i);
            all.add(e.fromName);
            all.add(e.toName);
        }

        heapSortStrings(all);

        names = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            String cur = all.get(i);
            if (names.isEmpty() || !names.get(names.size() - 1).equals(cur))
                names.add(cur);
        }

        adj = new ArrayList<>(names.size());
        for (int i = 0; i < names.size(); i++)
            adj.add(new ArrayList<>(5));

        for (int i = 0; i < pending.size(); i++) {
            Edge pe = pending.get(i);
            int f = indexOfBuilt(pe.fromName);
            int t = indexOfBuilt(pe.toName);
            if (f >= 0 && t >= 0)
                adj.get(f).add(new Edge(t, pe.dist, pe.time));
        }

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

    private static void heapSortStrings(ArrayList<String> a) {
        if (a.size() <= 1) return;

        myHeap<String> heap = new myHeap<>();
        for (String s : a) heap.add(s);

        for (int i = 0; i < a.size(); i++)
            a.set(i, heap.pop());
    }
}
