package pack.algo;

public class Calculations {

    // Represents an Unreachable Distance Value
    private final double infinity = Double.POSITIVE_INFINITY;
    // Stores the Shortest Known Distance From the Source
    private double[] dist;
    // Stores the Previous Vertex in the Path
    private int[] previous;

    // Runs Dijkstra on the Given Graph
    public void run(Graph g, int s, int mode) {
        // Number of Vertices in the Graph
        int n = g.size();
        // Distance Array Initialization
        dist = new double[n];
        // Previous Vertex Array Initialization
        previous = new int[n];
        // Marks Visited Vertices
        boolean[] visited = new boolean[n];
        // Initialize Distance and Previous Arrays
        for (int i = 0; i < n; i++) {
            dist[i] = infinity;
            previous[i] = -1;
        }
        // Invalid Source Vertex Check
        if (s < 0 || s >= n) return;
        // Initializing Heap
        myHeap<Vertex> heap = new myHeap<>();
        // Source Distance Is Zero
        dist[s] = 0.0;
        // Insert Source Vertex Into the Heap
        heap.add(new Vertex(s, 0.0));

        while (!heap.isEmpty()) {
            // Extract Vertex With Minimum Distance
            Vertex cur = heap.pop();
            // Current Vertex Index
            int v = cur.getIndex();
            // Skip If Vertex Is Already Finalized
            if (visited[v])
                continue;
            // Skip Outdated Heap Entries
            if (cur.getKey() > dist[v])
                continue;
            // Mark Vertex as Finalized
            visited[v] = true;
            // Relax All Outgoing Edges
            for (Edge e : g.edgesFrom(v)) {
                // Destination Vertex Index
                int w = e.destIndex;
                // Skip If Destination Is Already Finalized
                if (visited[w])
                    continue;
                // Select Cost Based on Mode
                double cost;
                if (mode == 2)
                    cost = e.time;
                else
                    cost = e.dist;
                // New Distance Calculation
                double newDist = dist[v] + cost;
                // Relaxation Condition
                if (newDist < dist[w]) {
                    dist[w] = newDist;
                    previous[w] = v;
                    heap.add(new Vertex(w, newDist));
                }
            }
        }
    }

    // Builds the Path From Source to Destination
    public myArrayList<Integer> buildPath(int s, int t) {
        // List That Stores the Path
        myArrayList<Integer> path = new myArrayList<>();
        // Ensure Algorithm Has Been Run
        if (dist == null) return path;
        // Bounds Check for Vertices
        if (s < 0 || t < 0 || s >= dist.length || t >= dist.length)
            return path;
        // Destination Is Unreachable
        if (dist[t] == infinity)
            return path;
        // Follow Path from Destination to Source
        int cur = t;
        while (cur != -1) {
            path.add(cur);
            if (cur == s)
                break;
            cur = previous[cur];
        }
        // Ensure Valid Path Exists
        if (path.isEmpty() || path.get(path.size() - 1) != s)
            return path;
        // Reverse the Path to Correct Order
        int i = 0, j = path.size() - 1;
        while (i < j) {
            int tmp = path.get(i);
            path.set(i, path.get(j));
            path.set(j, tmp);
            i++;
            j--;
        }
        return path;
    }

    // Returns the Infinity Value Used in the Algorithm
    public double getInfinity() {
        return infinity;
    }

    // Returns the Distance Array
    public double[] getDist() {
        return dist;
    }

    // Returns the Parent Array
    public int[] getPrevious() {
        return previous;
    }
}
