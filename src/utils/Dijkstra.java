package utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A utility class to implement Dijkstra's algorithm for finding the shortest path
 * in a weighted graph.
 */
public class Dijkstra {
    // Track all unvisited nodes in the graph
    private static ArrayList<Node> unvisited = new ArrayList<>();

    // Map to store the minimum distance from the source to each node
    private static HashMap<Node, Double> distanceMap = new HashMap<>();

    // Map to store the previous node in the shortest path for each node
    private static HashMap<Node, Node> previousNode = new HashMap<>();

    /**
     * Finds the shortest path between the source and destination nodes using Dijkstra's algorithm.
     *
     * @param graph The graph to search in.
     * @param source The starting node.
     * @param destination The target node.
     * @return An ArrayList of nodes representing the shortest path from source to destination.
     */
    public static ArrayList<Node> findShortestPath(Graph graph, Node source, Node destination) {
        if (source.equals(destination)) {
            ArrayList<Node> path = new ArrayList<>();
            path.add(source);
            return path;
        }

        // Initialize distances and previous nodes
        for (Node node : graph.getNodes()) {
            distanceMap.put(node, Double.MAX_VALUE);
            previousNode.put(node, null);
            unvisited.add(node);
        }

        // Set the source node distance to zero
        distanceMap.put(source, 0d);

        // Process nodes
        Node minNode;
        while ((minNode = findVertexWithMinDist()) != null) {
            unvisited.remove(minNode);

            // Update distances to neighboring nodes
            ArrayList<Edge> edges = graph.getDestinationEdges(minNode);
            for (Edge edge : edges) {
                if (unvisited.contains(edge.getDestination())) {
                    double alt = distanceMap.get(minNode) + edge.getDistance();
                    if (alt < distanceMap.get(edge.getDestination())) {
                        distanceMap.put(edge.getDestination(), alt);
                        previousNode.put(edge.getDestination(), minNode);
                    }
                }
            }
        }

        return getShortestPath(source, destination);
    }

    /**
     * Returns the distance from the source node to the given destination node.
     *
     * @param destination The target node.
     * @return The distance to the destination node.
     */
    public static double getDistance(Node destination) {
        return distanceMap.getOrDefault(destination, Double.MAX_VALUE) / 10f;
    }

    /**
     * Constructs the shortest path from source to destination.
     *
     * @param source The starting node.
     * @param destination The target node.
     * @return An ArrayList of nodes representing the shortest path.
     */
    private static ArrayList<Node> getShortestPath(Node source, Node destination) {
        ArrayList<Node> path = new ArrayList<>();
        while (previousNode.get(destination) != null) {
            path.add(0, destination);
            destination = previousNode.get(destination);
        }
        path.add(0, source);
        return path;
    }

    /**
     * Finds the unvisited node with the minimum distance.
     *
     * @return The node with the minimum distance.
     */
    private static Node findVertexWithMinDist() {
        Node minNode = null;
        double minDistance = Double.MAX_VALUE;
        for (HashMap.Entry<Node, Double> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            double distance = entry.getValue();
            if (unvisited.contains(node) && distance < minDistance) {
                minDistance = distance;
                minNode = node;
            }
        }
        return minNode;
    }
}
