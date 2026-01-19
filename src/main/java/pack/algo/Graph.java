package pack.algo;

public class Graph {

    // Adjacency List Where Each Index Represents a Vertex
    private final myArrayList<myArrayList<Edge>> adjList = new myArrayList<>();

    // Adds a Directed Edge From Source to Destination
    public void addDirectedEdge(int src, int dest, double dist, double time) {
        // Invalid Vertex Index Check
        if (src < 0 || dest < 0)
            return;
        // Determine the Highest Vertex Index
        int mx = Math.max(src, dest);
        // Ensure All Vertices up to the Highest Index Exist
        ensureVertex(mx);
        // Add the Edge to the Adjacency List
        adjList.get(src).add(new Edge(dest, dist, time));
    }

    // Returns the Number of Vertices in the Graph
    public int size() {
        return adjList.size();
    }

    // Returns All Outgoing Edges From a Given Vertex
    myArrayList<Edge> edgesFrom(int id) {
        // Invalid Vertex Index Check
        if (id < 0 || id >= adjList.size())
            return new myArrayList<>();
        return adjList.get(id);
    }

    // Ensures the Adjacency List Can Hold a Given Vertex Index
    private void ensureVertex(int id) {
        // Add Empty Lists Until the Required Index Exists
        while (adjList.size() <= id)
            adjList.add(new myArrayList<>(5));
    }
}
