package pack.algo;

public class Calculations {

    private final double infinity = Double.POSITIVE_INFINITY;
    private double[] dist;
    private int[] parent;

    public void run(Graph g, int s, int mode) {
        int n = g.size();
        dist = new double[n];
        parent = new int[n];
        boolean[] known = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = infinity;
            parent[i] = -1;
            known[i] = false;
        }
        if (s < 0 || s >= n) return;

        myHeap<Vertex> heap = new myHeap<>();
        dist[s] = 0.0;
        heap.add(new Vertex(s, 0.0));

        while (!heap.isEmpty()) {
            Vertex cur = heap.pop();
            if (cur == null)
                break;
            int v = cur.id;
            if (v < 0 || v >= n)
                continue;
            if (known[v])
                continue;
            if (cur.key > dist[v])
                continue;
            known[v] = true;

            for (Edge e : g.edgesFrom(v)) {
                int w = e.destIndex;
                if (w < 0 || w >= n)
                    continue;
                if (known[w])
                    continue;
                double cost;
                if (mode == 2)
                    cost = e.time;
                else
                    cost = e.dist;
                double nd = dist[v] + cost;
                if (nd < dist[w]) {
                    dist[w] = nd;
                    parent[w] = v;
                    heap.add(new Vertex(w, nd));
                }
            }
        }
    }

    public myArrayList<Integer> buildPath(int s, int t) {
        myArrayList<Integer> path = new myArrayList<>();
        if (dist == null || parent == null) return path;
        if (s < 0 || t < 0 || s >= dist.length || t >= dist.length) return path;
        if (dist[t] == infinity) return path;

        int cur = t;
        while (cur != -1) {
            path.add(cur);
            if (cur == s)
                break;
            cur = parent[cur];
        }

        if (path.isEmpty() || path.get(path.size() - 1) != s)
            return new myArrayList<>();

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

    public double getInfinity() {
        return infinity;
    }

    public double[] getDist() {
        return dist;
    }

    public int[] getParent() {
        return parent;
    }
}
