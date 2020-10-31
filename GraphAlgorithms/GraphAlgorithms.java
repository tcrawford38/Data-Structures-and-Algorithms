import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;


/**
 * Your implementation of various different graph algorithms.
 *
 * @author Thomas Crawford
 * @version 1.0
 *
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start vertex is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> list
                = graph.getAdjList();
        if (!list.containsKey(start)) {
            throw new IllegalArgumentException(
                    "start not in the list");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> q = new LinkedList<>();
        List<Vertex<T>> resList = new ArrayList<>();
        q.add(start);
        visitedSet.add(start);
        while (!q.isEmpty()) {
            Vertex<T> temp = q.remove();
            resList.add(temp);
            for (VertexDistance<T> vertex : list.get(temp)) {
                if (!visitedSet.contains(vertex.getVertex())) {
                    q.add(vertex.getVertex());
                    visitedSet.add(vertex.getVertex());
                }
            }
        }
        return resList;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start vertex is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> list
                = graph.getAdjList();
        if (!list.containsKey(start)) {
            throw new IllegalArgumentException(
                    "Start vertex doesn't exist in graph");
        }
        List<Vertex<T>> resMap = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        dfsHelper(resMap, visited, start, list);
        return resMap;
    }

    /**
     * Dfs Helper
     * @param resMap resMap list
     * @param visited visited set
     * @param start start vertex
     * @param vertexList vertex list
     * @param <T> Generic typing of data
     */

    private static <T> void dfsHelper(
            List<Vertex<T>> resMap,
            Set<Vertex<T>> visited,
            Vertex<T> start,
            Map<Vertex<T>, List<VertexDistance<T>>> vertexList) {
        resMap.add(start);
        visited.add(start);
        for (VertexDistance<T> vertex: vertexList.get(start)) {
            if (!visited.contains(vertex.getVertex())) {
                dfsHelper(resMap, visited,
                        vertex.getVertex(), vertexList);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start vertex is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> vers = graph.getAdjList();
        if (!vers.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex doesn't exist"
                    + " in graph");
        }
        Queue<VertexDistance<T>> q = new PriorityQueue<>();
        q.add(new VertexDistance<>(start, 0));
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, Integer> resMap = new HashMap<>();
        for (Vertex<T> vertex: vers.keySet()) {
            if (vertex.equals(start)) {
                resMap.put(vertex, 0);
            } else {
                resMap.put(vertex, Integer.MAX_VALUE);
            }
        }
        while (!q.isEmpty() && visitedSet.size() != vers.size()) {
            VertexDistance<T> temp = q.remove();
            for (VertexDistance<T> ver:vers.get(temp.getVertex())) {
                if (resMap.get(ver.getVertex()) > temp.getDistance()
                        + ver.getDistance()) {
                    int maxDistance = temp.getDistance() + ver.getDistance();
                    q.add(new VertexDistance<T>(ver.getVertex(), maxDistance));
                    resMap.put(ver.getVertex(), maxDistance);
                }
            }
        }
        return resMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        Set<Edge<T>> edges = graph.getEdges();
        PriorityQueue<Edge<T>> q = new PriorityQueue<>(edges);
        Set<Vertex<T>> vers = graph.getAdjList().keySet();
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>(vers);
        Set<Edge<T>> resultSet = new HashSet<>();
        while (!q.isEmpty()) {
            Edge<T>  temp = q.poll();
            Vertex u = temp.getU();
            Vertex v = temp.getV();
            int weight = temp.getWeight();
            if (!(disjointSet.find(u).equals(disjointSet.find(v)))) {
                resultSet.add(new Edge<T>(v,
                        u, weight));
                disjointSet.union(v, u);
                resultSet.add(temp);
            }
        }
        if (resultSet.size() != 2 * (vers.size() - 1)) {
            return null;
        }
        return resultSet;
    }
}
