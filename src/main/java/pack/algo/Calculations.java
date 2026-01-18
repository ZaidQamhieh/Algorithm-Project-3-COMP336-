package pack.algo;

public class Calculations {

    // Infinity value for unreachable vertices
    public static final double MAX = Double.POSITIVE_INFINITY;
    // Stores final distance from source
    public double[] dist;
    // Stores parent index for path reconstruction
    public int[] parent;

    // Runs Dijkstra algorithm based on selected mode
    public void run(Graph g, String sName, int mode) {
        int s = g.indexOf(sName);
        int n = g.size();
        // Initialize distance, parent, and visited arrays
        dist = new double[n];
        parent = new int[n];
        boolean[] known = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = MAX;
            parent[i] = -1;
            known[i] = false;
        }
        // Stop if source is invalid
        if (s < 0 || s >= n) return;
        // Priority queue ordered by current best cost
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
            // Skip outdated queue entries
            if (cur.key > dist[v]) continue;
            known[v] = true;
           // Relax outgoing edges
            for (Edge e : g.edgesFrom(v)) {
                int w = e.destIndex;
                // Skip invalid or finalized neighbors
                if (w < 0 || w >= n) continue;
                if (known[w]) continue;
                double cost = (mode == 2) ? e.time : e.dist;
                double nd = dist[v] + cost;
                // Update distance and parent if shorter path is found
                if (nd < dist[w]) {
                    dist[w] = nd;
                    parent[w] = v;
                    pq.add(new Vertex(w, nd));
                }
            }
        }
    }

    // Builds path from source to destination using parent array
    public myArrayList<String> buildNamePath(Graph g, String sName, String tName) {
        int s = g.indexOf(sName);
        int t = g.indexOf(tName);
        myArrayList<String> path = new myArrayList<>();
        // Stop if data is not ready or indexes are invalid
        if (dist == null || parent == null) return path;
        if (s < 0 || t < 0 || s >= dist.length || t >= dist.length) return path;
        if (dist[t] == MAX) return path;
        // Trace path backward from destination
        int cur = t;
        while (cur != -1) {
            path.add(g.nameOf(cur));
            if (cur == s) break;
            cur = parent[cur];
        }
        // Stop if source was not reached
        if (path.isEmpty() || !path.get(path.size() - 1).equals(sName))
            return new myArrayList<>();
        // Reverse path order
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
}
