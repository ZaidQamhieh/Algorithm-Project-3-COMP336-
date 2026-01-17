package pack.algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    static final class Query {
        int s;
        int t;
        int choice;

        Query(int s, int t, int choice) {
            this.s = s;
            this.t = t;
            this.choice = choice;
        }
    }

    static Query readFile(String filePath, Graph g) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line = nextNonEmpty(br);
        if (line == null) {
            br.close();
            return null;
        }

        String[] first = splitWS(line);
        if (first.length < 3) {
            br.close();
            return null;
        }

        String s = first[0];
        String t = first[1];
        int choice = Integer.parseInt(first[2]);

        while ((line = nextNonEmpty(br)) != null) {
            String[] p = splitWS(line);
            if (p.length < 4) continue;
            String from = p[0];
            String to = p[1];
            double dist = Double.parseDouble(p[2]);
            double time = Double.parseDouble(p[3]);
            g.addDirectedEdge(from, to, dist, time);
        }

        br.close();
        int sI = g.indexOf(s);
        int tI = g.indexOf(t);
        return new Query(sI, tI, choice);
    }

    static String nextNonEmpty(BufferedReader br) throws Exception {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) return line;
        }
        return null;
    }

    static String[] splitWS(String s) {
        ArrayList<String> out = new ArrayList<>();
        int n = s.length();
        int i = 0;
        while (i < n) {
            while (i < n && Character.isWhitespace(s.charAt(i))) i++;
            if (i >= n) break;
            int j = i;
            while (j < n && !Character.isWhitespace(s.charAt(j))) j++;
            out.add(s.substring(i, j));
            i = j;
        }
        String[] a = new String[out.size()];
        for (int k = 0; k < out.size(); k++) a[k] = out.get(k);
        return a;
    }

    static String pathToString(ArrayList<Integer> path) {
        if (path.isEmpty()) return "NO PATH";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(path.get(i));
        }
        return sb.toString();
    }

    static void printOne(Graph g, String label, int s, int t, int mode) {
        Result r = Dijkstra.run(g, s, mode);
        if (t < 0 || t >= r.dist.length || r.dist[t] == Dijkstra.INF) {
            System.out.println(label + ": NO PATH");
            return;
        }
        ArrayList<Integer> path = Dijkstra.buildPath(s, t, r.parent);
        System.out.println(label + " Path: " + pathToString(path));
        System.out.println(label + " Total: " + r.dist[t]);
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\zqmhy\\Desktop\\graphNodes.txt";

        Graph g = new Graph();
        Query q = readFile(filePath, g);
        if (q == null) {
            System.out.println("INVALID FILE");
            return;
        }

        if (q.choice == 1) {
            printOne(g, "Distance", q.s, q.t, 1);
        } else if (q.choice == 2) {
            printOne(g, "Time", q.s, q.t, 2);
        } else if (q.choice == 3) {
            printOne(g, "Distance", q.s, q.t, 1);
            printOne(g, "Time", q.s, q.t, 2);
        } else {
            System.out.println("INVALID CHOICE");
        }
    }
}
