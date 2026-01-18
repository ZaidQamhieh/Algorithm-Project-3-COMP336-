package pack.algo;

import java.util.ArrayList;

public class Dijkstra {
    public static final double MAX = Double.POSITIVE_INFINITY;

    public double[] dist;
    public int[] parent;

    public void run(Graph g, String sName, int mode) {
        int s = g.indexOf(sName);
        int n = g.size();

        dist = new double[n];
        parent = new int[n];
        boolean[] known = new boolean[n];

        for (int i = 0; i < n; i++) {
            dist[i] = MAX;
            parent[i] = -1;
            known[i] = false;
        }

        if (s < 0 || s >= n) return;

        MinHeap<Vertex> pq = new MinHeap<>();
        dist[s] = 0.0;
        pq.add(new Vertex(s, 0.0));

        while (!pq.isEmpty()) {
            Vertex cur = pq.pop();
            if (cur == null) break;

            int v = cur.idx;
            if (v < 0 || v >= n) continue;
            if (known[v]) continue;
            if (cur.key != dist[v]) continue;

            known[v] = true;

            for (Edge e : g.edgesFrom(v)) {
                int w = e.to;
                if (w < 0 || w >= n) continue;
                if (known[w]) continue;

                double cost = (mode == 2) ? e.time : e.dist;
                double nd = dist[v] + cost;

                if (nd < dist[w]) {
                    dist[w] = nd;
                    parent[w] = v;
                    pq.add(new Vertex(w, nd));
                }
            }
        }
    }

    public ArrayList<String> buildNamePath(Graph g, String sName, String tName) {
        int s = g.indexOf(sName);
        int t = g.indexOf(tName);

        ArrayList<String> path = new ArrayList<>();
        if (dist == null || parent == null) return path;
        if (s < 0 || t < 0 || s >= dist.length || t >= dist.length) return path;
        if (dist[t] == MAX) return path;

        int cur = t;
        while (cur != -1) {
            path.add(g.nameOf(cur));
            if (cur == s) break;
            cur = parent[cur];
        }

        if (path.isEmpty() || !path.get(path.size() - 1).equals(sName)) return new ArrayList<>();

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
