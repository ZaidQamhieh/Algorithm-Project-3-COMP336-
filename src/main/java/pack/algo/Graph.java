package pack.algo;

public class Graph {
    private myArrayList<String> names = new myArrayList<>();
    private myArrayList<myArrayList<Edge>> adj = new myArrayList<>();

    private final myArrayList<Edge> pending = new myArrayList<>();
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

    myArrayList<Edge> edgesFrom(int idx) {
        ensureBuilt();
        return adj.get(idx);
    }

    private void ensureBuilt() {
        if (built) return;

        myArrayList<String> all = new myArrayList<>(pending.size() * 2);
        for (int i = 0; i < pending.size(); i++) {
            Edge e = pending.get(i);
            all.add(e.srcName);
            all.add(e.destName);
        }

        heapSortStrings(all);

        names = new myArrayList<>(all.size());
        for (int i = 0; i < all.size(); i++) {
            String cur = all.get(i);
            if (names.isEmpty() || !names.get(names.size() - 1).equals(cur))
                names.add(cur);
        }

        adj = new myArrayList<>(names.size());
        for (int i = 0; i < names.size(); i++)
            adj.add(new myArrayList<>(5));

        for (int i = 0; i < pending.size(); i++) {
            Edge pe = pending.get(i);
            int f = indexOfBuilt(pe.srcName);
            int t = indexOfBuilt(pe.destName);
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

    private void heapSortStrings(myArrayList<String> a) {
        if (a.size() <= 1) return;

        myHeap<String> heap = new myHeap<>();
        for (String s : a) heap.add(s);

        for (int i = 0; i < a.size(); i++)
            a.set(i, heap.pop());
    }
}
