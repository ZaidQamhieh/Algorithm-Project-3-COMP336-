package pack.algo;

public class Graph {

    // Stores All Unique Vertex Names After Graph Is Built
    private myArrayList<String> names = new myArrayList<>();
    /* Adjacency List Where Each Index Represents a Vertex
     Each Inner List Contains Outgoing Edges from That Vertex*/
    private myArrayList<myArrayList<Edge>> adj = new myArrayList<>();
    /* Temporarily Stores Edges Read from Input before Graph Construction
    Used to Collect All Vertices and Build Adjacency Lists Later*/
    private final myArrayList<Edge> pending = new myArrayList<>();
    // Indicates Whether the Graph Structure Has Been Built
    private boolean built = true;

    /*Adds a Directed Edge Definition to the Pending Edge List
    Actual Graph Structure Is Built Lazily When Needed*/
    public void addDirectedEdge(String from, String to, double dist, double time) {
        if (from == null || to == null) return;
        pending.add(new Edge(from, to, dist, time));
        built = false;
    }

    /* Returns Total Number of Vertices in the Graph
     Triggers Graph Construction if Not Yet Built*/
    public int size() {
        ensureBuilt();
        return adj.size();
    }

    /* Returns the Index of a Vertex Name in the Internal List
     Triggers Graph Construction if Not Yet Built*/
    public int indexOf(String name) {
        ensureBuilt();
        return indexOfBuilt(name);
    }

    /* Returns the Vertex Name Corresponding to a Given Index
     Returns Null if Index Is Out of Bounds*/
    public String nameOf(int idx) {
        ensureBuilt();
        if (idx < 0 || idx >= names.size()) return null;
        return names.get(idx);
    }

    /* Returns All Outgoing Edges from a Given Vertex Index
    Used by Graph Traversal and Shortest Path Algorithms*/
    myArrayList<Edge> edgesFrom(int idx) {
        ensureBuilt();
        return adj.get(idx);
    }

    /* Builds the Graph Structure from Pending Edges if Not Already Built
     This Includes Extracting Vertices, Sorting, Deduplication, and Adjacency Setup*/
    private void ensureBuilt() {
        if (built) return;
        // Collects All Source and Destination Vertex Names from Pending Edges
        myArrayList<String> all = new myArrayList<>(pending.size() * 2);
        for (int i = 0; i < pending.size(); i++) {
            Edge e = pending.get(i);
            all.add(e.srcName);
            all.add(e.destName);
        }
        // Sorts All Vertex Names to Enable Binary Search
        heapSortStrings(all);
        // Removes Duplicate Vertex Names While Preserving Sorted Order
        names = new myArrayList<>(all.size());
        for (int i = 0; i < all.size(); i++) {
            String cur = all.get(i);
            if (names.isEmpty() || !names.get(names.size() - 1).equals(cur))
                names.add(cur);
        }
        // Initializes an Empty Adjacency List for Each Vertex
        adj = new myArrayList<>(names.size());
        for (int i = 0; i < names.size(); i++)
            adj.add(new myArrayList<>(5));
        // Converts Pending Edge Definitions into Indexed Adjacency Entries
        for (int i = 0; i < pending.size(); i++) {
            Edge pe = pending.get(i);
            int f = indexOfBuilt(pe.srcName);
            int t = indexOfBuilt(pe.destName);
            if (f >= 0 && t >= 0)
                adj.get(f).add(new Edge(t, pe.dist, pe.time));
        }
        // Clears Temporary Edge Storage and Marks Graph as Fully Built
        pending.clear();
        built = true;
    }

    /* Performs Binary Search on the Sorted Vertex Name List
    Used Internally After Graph Construction*/
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

    /* Sorts a List of Strings Using a Min-Heap
     Used to Order Vertex Names before Deduplication*/
    private void heapSortStrings(myArrayList<String> a) {
        if (a.size() <= 1) return;

        myHeap<String> heap = new myHeap<>();
        for (String s : a) heap.add(s);

        for (int i = 0; i < a.size(); i++)
            a.set(i, heap.pop());
    }
}
