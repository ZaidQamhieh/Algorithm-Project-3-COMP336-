package pack.algo;

public class Calculations {

    // Infinity Value for Unreachable Vertices
    private double MAX = Double.POSITIVE_INFINITY;
    // Stores Final Distance from Source
    private double[] dist;
    // Stores Parent Index for Path Reconstruction
    private int[] parent;

    // Runs Dijkstra Algorithm Based on Selected Mode
    public void run(Graph g, String sName, int mode) {
        int s = g.indexOf(sName);
        int n = g.size();
        // Initialize Distance, Parent, and Visited Arrays
        dist = new double[n];
        parent = new int[n];
        boolean[] known = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = MAX;
            parent[i] = -1;
            known[i] = false;
        }
        // Stop if Source is Invalid
        if (s < 0 || s >= n) return;
        // Heap Ordered by Current Best Cost
        myHeap<Vertex> pq = new myHeap<>();
        dist[s] = 0.0;
        pq.add(new Vertex(s, 0.0));
        // Main Dijkstra loop
        while (!pq.isEmpty()) {
            Vertex cur = pq.pop();
            if (cur == null) break;
            int v = cur.idx;
            // Skip invalid or finalized vertices
            if (v < 0 || v >= n) continue;
            if (known[v]) continue;
            // Skip Outdated Queue Entries
            if (cur.key > dist[v]) continue;
            known[v] = true;
           // Relax Outgoing Edges
            for (Edge e : g.edgesFrom(v)) {
                int w = e.destIndex;
                // Skip Invalid or Finalized Neighbors
                if (w < 0 || w >= n) continue;
                if (known[w]) continue;
                double cost = (mode == 2) ? e.time : e.dist;
                double nd = dist[v] + cost;
                // Update Distance and Parent if Shorter Path is Found
                if (nd < dist[w]) {
                    dist[w] = nd;
                    parent[w] = v;
                    pq.add(new Vertex(w, nd));
                }
            }
        }
    }

    // Builds Path from Source to Destination Using Parent Array
    public myArrayList<String> buildNamePath(Graph g, String sName, String tName) {
        int s = g.indexOf(sName);
        int t = g.indexOf(tName);
        myArrayList<String> path = new myArrayList<>();
        // Stop if Data is not Ready or Indexes are Invalid
        if (dist == null || parent == null) return path;
        if (s < 0 || t < 0 || s >= dist.length || t >= dist.length) return path;
        if (dist[t] == MAX) return path;
        // Trace Path Backward From Destination
        int cur = t;
        while (cur != -1) {
            path.add(g.nameOf(cur));
            if (cur == s) break;
            cur = parent[cur];
        }
        // Stop if Source was not Reached
        if (path.isEmpty() || !path.get(path.size() - 1).equals(sName))
            return new myArrayList<>();
        // Reverse Path Order
        int i = 0, j = path.size() - 1;
        while (i < j) {
            String tmp = path.get(i);
            path.set(i, path.get(j));
            path.set(j, tmp);
            i++;
            j--;
        }
        return path;
    }

    public double getMAX() {
        return MAX;
    }

    public double[] getDist() {
        return dist;
    }

    public int[] getParent() {
        return parent;
    }
}
