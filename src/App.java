import java.io.IOException;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import utils.AsciiColors;
import utils.CLI;
import utils.CSVParser;
import utils.GUI;
import utils.Graph;

/**
 * The main application class that initializes and manages the user interface of the application.
 * It provides options for the user to choose between a graphical user interface (GUI) or a command line interface (CLI).
 */
public class App {

    // Instance of Graph to hold data from the CSV file
    static Graph graph = new Graph();

    /**
     * The entry point of the application. It reads the CSV file, initializes the graph,
     * and provides options for the user to choose the interface they wish to use.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        // Create path to the CSV file
        String csvFilePath = Paths.get(Paths.get(System.getProperty("user.dir")).getParent().toString(), "data", "landmarksAdjacencyMatrix.csv").toString();

        // Parse the CSV file to populate the graph
        try {
            CSVParser.parseCSV(csvFilePath, graph);
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        
        // Get user input to choose the interface
        int viewChoice = getViewChoiceSelection(scanner, AsciiColors.colorWrap(AsciiColors.GREEN, "\n\t\tWhich Interface Would You Like To Use? Please Select By Index.\n\n\t1. Graphical User Interface (GUI).\n\t2. Command Line Interface (CLI). \n\n"));

        // Initialize the chosen interface
        if (viewChoice == 1) {
            GUI.gui(graph);
        } else {
            CLI.cli(graph);
        }
        
        scanner.close();
    }

    /**
     * Prompts the user for integer input and validates the input.
     * Ensures that the input is either 1 or 2.
     *
     * @param scanner Scanner object to read user input.
     * @param prompt  The message to display to the user.
     * @return The valid integer input from the user.
     */
    public static int getViewChoiceSelection(Scanner scanner, String prompt) {
        int userInput = -1; 
        while (true) {
            System.out.print(prompt);
            try {
                userInput = scanner.nextInt(); 
                if (userInput == 1 || userInput == 2) {
                    break; 
                } else {
                    System.out.println("Error: Input must be 1 or 2. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid integer.");
                scanner.next(); 
            }
        }
        return userInput;
    }
}
