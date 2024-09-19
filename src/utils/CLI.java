package utils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Command Line Interface (CLI) utility for interacting with a graph.
 * Provides options to select nodes, find paths, and display results.
 */
public class CLI {
    public static Graph graph;

    /**
     * Main method to run the CLI interface for graph operations.
     *
     * @param graph The graph to be used for operations.
     */
    public static void cli(Graph graph) {
        Scanner scanner = new Scanner(System.in);

        String header = "\t\t\t\t\t\t _____                                                               _____ \n" +
                        "\t\t\t\t\t\t( ___ )-------------------------------------------------------------( ___ )\n" +
                        "\t\t\t\t\t\t |   |                                                               |   | \n" +
                        "\t\t\t\t\t\t |   |  _   _  ____   _   _             _             _              |   | \n" +
                        "\t\t\t\t\t\t |   | | | | |/ ___| | \\ | | __ ___   _(_) __ _  __ _| |_ ___  _ __  |   | \n" +
                        "\t\t\t\t\t\t |   | | | | | |  _  |  \\| |/ _` \\ \\ / / |/ _` |/ _` | __/ _ \\| '__| |   | \n" +
                        "\t\t\t\t\t\t |   | | |_| | |_| | | |\\  | (_| |\\ V /| | (_| | (_| | || (_) | |    |   | \n" +
                        "\t\t\t\t\t\t |   |  \\___/ \\____| |_| \\_|\\__,_| \\_/ |_|\\__, |\\__,_|\\__\\___/|_|    |   | \n" +
                        "\t\t\t\t\t\t |   |                                    |___/                      |   | \n" +
                        "\t\t\t\t\t\t |___|                                                               |___| \n" +
                        "\t\t\t\t\t\t(_____)-------------------------------------------------------------(_____)";

        System.out.println(AsciiColors.getRandomColor() + header + AsciiColors.RESET + "\n\n\n");

        System.out.println("\t\t\t\t\t\t\t\t\t      " + AsciiColors.UNDERLINE + AsciiColors.BOLD + AsciiColors.getRandomColor() + " ALL LANDMARKS " + AsciiColors.RESET + "\n");

        Object[] graphNodes = performNodeSelection(scanner, graph);

        Node sourceNode = graph.getNodeByName((String) graphNodes[0]);
        Node destNode = graph.getNodeByName((String) graphNodes[1]);

        ArrayList<Node> shortestPath = Dijkstra.findShortestPath(graph, sourceNode, destNode);

        System.out.println(AsciiColors.RESET + "\tOPTIMAL ROUTE\n\nShortest Path: " +
                AsciiColors.colorWrap(AsciiColors.CYAN, printPath(shortestPath)) + "\nDistance: " +
                AsciiColors.colorWrap(AsciiColors.CYAN, String.format("%.2f", (Dijkstra.getDistance(destNode)) * 1000) + "m") + "\nTime: " +
                AsciiColors.colorWrap(AsciiColors.CYAN, String.format("%.2f", Dijkstra.getDistance(destNode)* 1000/ 10f / CSVParser.WALKING_SPEED_MPS) + " min(s)\n"));
            
        ArrayList<ArrayList<Node>> allPaths;
        StringBuilder builder = new StringBuilder();
        System.out.println("\n\n\tVIEW FIRST 10 ROUTES\n");
        allPaths = BFS.findAllPaths(graph, sourceNode, destNode);
        for (ArrayList<Node> nodes : allPaths.subList(allPaths.size() - 11, allPaths.size() - 1)) {
            // Calculate distance in meters and time in seconds
            double distanceInMeters = (graph.calculateDistance(nodes)) * 1000;
            double timeInSeconds = distanceInMeters / 10f / CSVParser.WALKING_SPEED_MPS;

            // Format distance and time as strings
            String distance = String.format("%.2f", distanceInMeters) + "m";
            String time = String.format("%.2f", timeInSeconds) + "min(s)";

            // Append formatted strings to the builder
            builder.append(AsciiColors.colorWrap(AsciiColors.CYAN, printPath(nodes)) + ",\t" + AsciiColors.colorWrap(AsciiColors.BRIGHT_GREEN, distance) + " \t" + AsciiColors.colorWrap(AsciiColors.BRIGHT_YELLOW, time) + " \n");
        }

        System.out.println(builder.toString());
        scanner.close();
    }

    /**
     * Handles node selection from the user input.
     *
     * @param scanner The Scanner object to read user input.
     * @param graph The graph containing the nodes.
     * @return An array containing the names of the source and destination nodes.
     */
    public static Object[] performNodeSelection(Scanner scanner, Graph graph) {
        // Retrieve and sort node names
        ArrayList<String> names = graph.nodeNamesArray(graph.getNodes());
        MergeSort.mergeSortString(names); // Ensure 'MergeSort' is adapted for sorting 'String' array

        // Print sorted node names
        graph.printNodes(names);

        // Select current location
        int currentLocationIndex = getIntegerInput(scanner, "\nPlease select your current location (by index): ", graph);
        Node currentNode = selectNodeByNameIndex(names, currentLocationIndex, graph);
        if (currentNode == null) {
            System.out.println("Invalid index for current location.");
            return null;
        }
        String sourceName = currentNode.getName();

        // Select destination
        int destinationIndex = getIntegerInput(scanner, "\nSelect your destination (by index): ", graph);

        while (destinationIndex == currentLocationIndex) {
            destinationIndex = getIntegerInput(scanner, "\nSorry, Destination and Current Location cannot be the same. Please select another location (by index): ", graph);
        }
        Node destinationNode = selectNodeByNameIndex(names, destinationIndex, graph);
        if (destinationNode == null) {
            System.out.println("Invalid index for destination.");
            return null;
        }
        String destName = destinationNode.getName();

        // Output selected nodes
        System.out.println("Selected source: " + AsciiColors.getRandomColor() + sourceName + AsciiColors.RESET + ", destination: " + AsciiColors.getRandomColor() + destName + "\n\n");

        return new Object[]{sourceName, destName};
    }

    /**
     * Selects a node based on the provided index in the sorted list of node names.
     *
     * @param names The sorted list of node names.
     * @param index The index of the node to select.
     * @param graph The graph containing the nodes.
     * @return The selected node, or null if the index is out of bounds.
     */
    private static Node selectNodeByNameIndex(ArrayList<String> names, int index, Graph graph) {
        if (index < 0 || index >= names.size()) {
            return null; // Index out of bounds
        }
        String name = names.get(index);
        return graph.getNodeByName(name); // Assuming 'getNodeByName' retrieves a Node by its name
    }

    /**
     * Prompts the user to input an integer within a specified range.
     *
     * @param scanner The Scanner object to read user input.
     * @param prompt The prompt message to display.
     * @param graph The graph used to validate the input.
     * @return The valid integer input from the user.
     */
    public static int getIntegerInput(Scanner scanner, String prompt, Graph graph) {
        int inputValue = -1;  // Initialize to an invalid value
        boolean validInput = false;

        while (!validInput) {
            System.out.println(AsciiColors.GREEN + prompt + AsciiColors.RESET);
            if (scanner.hasNextInt()) {  // Check if the input is an integer
                inputValue = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                // Validate that the input is within the allowed range
                if (inputValue >= 1 && inputValue <= graph.getSize()) {
                    validInput = true;
                } else {
                    System.out.println(AsciiColors.RED + "\tPlease enter a number between 1 and " + graph.getSize() + AsciiColors.RESET);
                }
            } else {
                System.out.println(AsciiColors.RED + "\tSorry, invalid input. Please try again." + AsciiColors.RESET);
                scanner.nextLine(); 
            }
        }
        return inputValue - 1; // Convert to zero-based index
    }

    /**
     * Formats a path as a string with arrows separating node names.
     *
     * @param path The list of nodes representing the path.
     * @return A formatted string representing the path.
     */
    public static String printPath(ArrayList<Node> path) {
        boolean start = true;
        StringBuilder routeBuilder = new StringBuilder();
        for (Node n : path) {
            routeBuilder.append((start ? "" : " ➔ ") + n.getName());
            start = false;
        }
        return routeBuilder.toString();
    }
}
