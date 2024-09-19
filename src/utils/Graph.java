package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents a graph with nodes and edges.
 */
public class Graph {
    private final HashMap<Node, ArrayList<Node>> adjacencyList = new HashMap<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private int nodeSize = 0;

    /**
     * Adds a node to the graph if it doesn't already exist.
     *
     * @param node The node to be added.
     */
    public void addNode(Node node) {
        if (!adjacencyList.containsKey(node)) {
            adjacencyList.put(node, new ArrayList<>());
            nodeSize++;
        }
    }

    /**
     * Returns the adjacency list of the graph.
     *
     * @return The adjacency list.
     */
    public HashMap<Node, ArrayList<Node>> getGraphHashMap() {
        return this.adjacencyList;
    }

    /**
     * Adds an edge to the graph. Also adds the reverse edge to handle bidirectional graphs.
     *
     * @param edge The edge to be added.
     */
    public void addEdge(Edge edge) {
        addNode(edge.getSource());
        addNode(edge.getDestination());

        if (edges.contains(edge)) return;

        edges.add(edge);
        Edge reverseEdge = edge.clone();
        reverseEdge.setSource(edge.getDestination());
        reverseEdge.setDestination(edge.getSource());
        edges.add(reverseEdge);

        adjacencyList.get(edge.getSource()).add(edge.getDestination());
        adjacencyList.get(edge.getDestination()).add(edge.getSource());
    }

    /**
     * Calculates the total distance for a given path.
     *
     * @param nodes The path as a list of nodes.
     * @return The total distance of the path.
     */
    public double calculateDistance(ArrayList<Node> nodes) {
        double distance = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            Edge edge = getEdge(nodes.get(i), nodes.get(i + 1));
            if (edge != null) {
                distance += edge.getDistance();
            }
        }
        return distance;
    }

    /**
     * Gets the list of edges that are outgoing from a given node.
     *
     * @param source The source node.
     * @return The list of outgoing edges from the source node.
     */
    public ArrayList<Edge> getDestinationEdges(Node source) {
        ArrayList<Edge> destinationEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                destinationEdges.add(edge);
            }
        }
        return destinationEdges;
    }

    /**
     * Gets the neighboring nodes of a given node.
     *
     * @param source The source node.
     * @return The list of neighboring nodes.
     */
    public ArrayList<Node> getNeighbourNodes(Node source) {
        return adjacencyList.get(source);
    }

    /**
     * Gets the edge between two nodes.
     *
     * @param source The source node.
     * @param destination The destination node.
     * @return The edge between the two nodes, or null if no such edge exists.
     */
    public Edge getEdge(Node source, Node destination) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Finds a node by its name.
     *
     * @param name The name of the node.
     * @return The node with the specified name, or null if no such node exists.
     */
    public Node getNodeByName(String name) {
        for (Node node : adjacencyList.keySet()) {
            if (node.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns a set of all nodes in the graph.
     *
     * @return The set of nodes.
     */
    public Set<Node> getNodes() {
        return adjacencyList.keySet();
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return The number of nodes.
     */
    public int getNodeSize() {
        return nodeSize;
    }

    /**
     * Prints the graph's adjacency list.
     */
    public void printGraph() {
        System.out.println("\n          GRAPH: ADJACENCY LIST                ");
        System.out.println("              PLACES ON CAMPUS                 \n");
        for (HashMap.Entry<Node, ArrayList<Node>> entry : adjacencyList.entrySet()) {
            Node node = entry.getKey();
            ArrayList<Node> destinations = entry.getValue();
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            boolean emptyList = true;
            for (Node destination : destinations) {
                if (emptyList)
                    builder.append(destination.getName());
                else
                    builder.append(", ").append(destination.getName());
                emptyList = false;
            }
            builder.append("]");
            System.out.println(node.getName() + " ➔ " + builder.toString());
        }
    }

    /**
     * Lists all places except the specified one.
     *
     * @param except The node to be excluded from the list.
     */
    public void listPlaces(Node except) {
        int index = 1;
        for (Node node : adjacencyList.keySet()) {
            if (!node.equals(except)) {
                System.out.println(index + ". " + node.getName());
                index++;
            }
        }
    }

    /**
     * Prints a list of node names.
     *
     * @param names The list of node names.
     */
    public void printNodes(ArrayList<String> names) {
        for (int i = 0; i < names.size(); i++) {
            System.out.println("\t\t\t\t\t\t\t\t      " + AsciiColors.colorWrap(AsciiColors.CYAN, i + 1) + ". \t" + AsciiColors.colorWrap(AsciiColors.YELLOW, names.get(i)));
        }
    }

    /**
     * Converts a set of nodes to a list of their names.
     *
     * @param nodes The set of nodes.
     * @return The list of node names.
     */
    public ArrayList<String> nodeNamesArray(Set<Node> nodes) {
        ArrayList<String> nodeNames = new ArrayList<>();
        for (Node node : nodes) {
            nodeNames.add(node.getName());
        }
        return nodeNames;
    }

    /**
     * Selects a node by its index in the graph.
     *
     * @param i The index of the node.
     * @return The node at the specified index.
     */
    public Object selectNode(int i) {
        return adjacencyList.keySet().toArray()[i];
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return The number of nodes.
     */
    public int getSize() {
        return adjacencyList.size();
    }

    /**
     * Returns the list of all edges in the graph.
     *
     * @return The list of edges.
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Returns a list of node names in the graph.
     *
     * @return The list of node names.
     */
    public ArrayList<String> getNodeNames() {
        ArrayList<String> nodeNames = new ArrayList<>();
        for (Node node : adjacencyList.keySet()) {
            nodeNames.add(node.getName());
        }
        return nodeNames;
    }
}
