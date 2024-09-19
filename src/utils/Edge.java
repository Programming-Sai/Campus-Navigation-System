package utils;

/**
 * Represents an edge between two nodes in a graph with distance and time attributes.
 */
public class Edge implements Comparable<Edge>, Cloneable {
    private Node source;
    private Node destination;
    private double time;
    private double distance;
    @SuppressWarnings("unused")
    private String landMarks;

    /**
     * Constructs an edge with the specified source, destination, and distance.
     * Time is set to -1 by default.
     *
     * @param source The starting node.
     * @param destination The ending node.
     * @param distance The distance of the edge.
     */
    public Edge(Node source, Node destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.time = -1; // Default value for time
        this.landMarks = "";
    }

    /**
     * Constructs an edge with the specified source, destination, distance, and time.
     *
     * @param source The starting node.
     * @param destination The ending node.
     * @param distance The distance of the edge.
     * @param time The time to traverse the edge.
     */
    public Edge(Node source, Node destination, double distance, double time) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.time = time;
        this.landMarks = "";
    }

    /**
     * Constructs an edge with the specified source, destination, distance, time, and landmarks.
     *
     * @param source The starting node.
     * @param destination The ending node.
     * @param distance The distance of the edge.
     * @param time The time to traverse the edge.
     * @param landMarks Additional landmarks associated with the edge.
     */
    public Edge(Node source, Node destination, double distance, double time, String landMarks) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.time = time;
        this.landMarks = landMarks;
    }

    public Node getDestination() {
        return destination;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return source.getName() + " ➔ " + destination.getName() + " Distance: " + getDistance();
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.distance, other.distance);
    }

    @Override
    protected Edge clone() {
        try {
            return (Edge) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
