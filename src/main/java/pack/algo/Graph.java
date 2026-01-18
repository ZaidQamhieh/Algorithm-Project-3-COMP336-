package pack.algo;

public class Graph {

    // Stores Unique Vertex Names
    private myArrayList<String> names = new myArrayList<>();

    // Adjacency List For Each VertexStores Outgoing Edges 
    private myArrayList<myArrayList<Edge>> adj = new myArrayList<>();

    // Stores Edges Before Build 
    private final myArrayList<Edge> pending = new myArrayList<>();

    // Indicates Whether Graph Is Built
    private boolean built = true;

    // Adds Directed Edge Builds Later
    public void addDirectedEdge(String from, String to, double dist, double time) {
        if (from == null || to == null) return;
        pending.add(new Edge(from, to, dist, time));
        built = false;
    }

    // Returns Vertex Count Builds If Needed
    public int size() {
        ensureBuilt();
        return adj.size();
    }

    // Returns Vertex Index Builds If Needed
    public int indexOf(String name) {
        ensureBuilt();
        return indexOfBuilt(name);
    }

    // Returns Vertex Name Null If Invalid
    public String nameOf(int idx) {
        ensureBuilt();
        if (idx < 0 || idx >= names.size()) return null;
        return names.get(idx);
    }

    // Returns Outgoing Edges 
    myArrayList<Edge> edgesFrom(int idx) {
        ensureBuilt();
        return adj.get(idx);
    }

    // Builds Graph From Edges 
    private void ensureBuilt() {
        if (built) return;

        // Collects Vertex Names
        myArrayList<String> all = new myArrayList<>(pending.size() * 2);
        for (int i = 0; i < pending.size(); i++) {
            Edge e = pending.get(i);
            all.add(e.srcName);
            all.add(e.destName);
        }

        // Sorts Names
        sortStrings(all);

        // Removes Duplicates
        names = new myArrayList<>(all.size());
        for (int i = 0; i < all.size(); i++) {
            String cur = all.get(i);
            if (names.isEmpty() || !names.get(names.size() - 1).equals(cur))
                names.add(cur);
        }

        // Creates Adjacency Lists
        adj = new myArrayList<>(names.size());
        for (int i = 0; i < names.size(); i++)
            adj.add(new myArrayList<>(5));

        // Adds Indexed Edges
        for (int i = 0; i < pending.size(); i++) {
            Edge pe = pending.get(i);
            int f = indexOfBuilt(pe.srcName);
            int t = indexOfBuilt(pe.destName);
            if (f >= 0 && t >= 0)
                adj.get(f).add(new Edge(t, pe.dist, pe.time));
        }

        // Clears Temporary Data
        pending.clear();
        built = true;
    }

    // Binary Search On Names 
    private int indexOfBuilt(String name) {
        int lo = 0, hi = names.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int c = names.get(mid).compareTo(name);
            if (c == 0) return mid;
            if (c < 0) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    // Heap Sort For Strings 
    private void sortStrings(myArrayList<String> a) {
        int n = a.size();
        if (n <= 1) return;
        myHeap<String> h = new myHeap<>();
        for (int i = 0; i < n; i++) h.add(a.get(i));
        for (int i = 0; i < n; i++) a.set(i, h.pop());
    }
}
