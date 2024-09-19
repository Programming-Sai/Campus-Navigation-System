package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class to parse CSV files and populate a Graph with nodes and edges.
 */
public class CSVParser {

    // Walking speed in meters per second
    public static final double WALKING_SPEED_MPS = 7.0;

    /**
     * Parses a CSV file and populates the provided graph with nodes and edges.
     *
     * @param filePath The path to the CSV file.
     * @param graph The graph to be populated.
     * @throws IOException If an error occurs while reading the file.
     */
    public static void parseCSV(String filePath, Graph graph) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = reader.readLine().split(",\\s*");
            Map<String, Node> nodeMap = new HashMap<>();

            // Create nodes based on headers
            for (String header : headers) {
                nodeMap.put(header, new Node(header));
            }

            // Process each line to create edges
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",\\s*"); // Split by comma and optional spaces
                String fromName = values[0];
                Node fromNode = nodeMap.get(fromName);

                for (int i = 1; i < values.length; i++) {
                    if (values[i].trim().isEmpty() || values[i].equals("0")) {
                        continue; // Skip empty or zero values
                    }

                    String toName = headers[i];
                    Node toNode = nodeMap.get(toName);
                    double distance = Double.parseDouble(values[i]);
                    double time = distance / WALKING_SPEED_MPS; // Calculate time in seconds

                    // Add edge to graph
                    graph.addEdge(new Edge(fromNode, toNode, distance, time));
                }
            }
        }
    }

    /**
     * Main method to test CSV parsing and graph population.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize the Graph
        Graph graph = new Graph();

        // Path to the CSV file
        String csvFilePath = "/Users/mac/Desktop/Actual-204-Semester-Project/Scrapper/dummy_data.csv"; // Update with actual file path

        try {
            // Parse the CSV file and populate the graph
            CSVParser.parseCSV(csvFilePath, graph);

            // Print all edges in the graph to verify the data
            System.out.println("Edges in the graph:");
            for (Edge edge : graph.getEdges()) {
                System.out.println(edge);
            }

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}
