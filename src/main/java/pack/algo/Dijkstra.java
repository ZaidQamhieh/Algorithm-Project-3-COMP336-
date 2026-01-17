package pack.algo;

import java.util.ArrayList;

public class Dijkstra {
    public static final double INF = Double.POSITIVE_INFINITY;

    public static Result run(Graph g, int s, int mode) {
        int n = g.size();
        double[] d = new double[n];
        int[] p = new int[n];
        boolean[] known = new boolean[n];

        for (int i = 0; i < n; i++) {
            d[i] = INF;
            p[i] = -1;
            known[i] = false;
        }

        MinHeap<Vertex> pq = new MinHeap<>();
        d[s] = 0.0;
        pq.add(new Vertex(s, 0.0));

        while (!pq.isEmpty()) {
            Vertex cur = pq.pop();
            int v = cur.v;

            if (known[v]) continue;
            known[v] = true;

            for (Edge e : g.adj.get(v)) {
                int w = e.to;
                if (known[w]) continue;

                double cost = (mode == 2) ? e.time : e.dist;
                double nd = d[v] + cost;

                if (nd < d[w]) {
                    d[w] = nd;
                    p[w] = v;
                    pq.add(new Vertex(w, nd));
                }
            }
        }

        return new Result(d, p);
    }

    public static ArrayList<Integer> buildPath(int s, int t, int[] parent) {
        ArrayList<Integer> path = new ArrayList<>();
        int cur = t;
        while (cur != -1) {
            path.add(cur);
            if (cur == s) break;
            cur = parent[cur];
        }
        if (path.isEmpty() || path.get(path.size() - 1) != s) return new ArrayList<>();
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
}
