package utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




/** 
 * The GUI class provides a graphical user interface for the UG Navigator application.
 * It includes search bars for selecting locations, a table to display routes, and buttons to trigger route calculations.
 */
public class GUI {

    public static ArrayList<Object[]> allRoutes;
    static int heightFromTop;

    /**
     * Initializes and displays the GUI for the UG Navigator.
     *
     * @param graph The graph representing the map of nodes and edges.
     */
    public static void gui(Graph graph) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("UG Navigator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(750, 600);
            frame.setMinimumSize(new Dimension(750, 600));
            frame.setState(JFrame.NORMAL);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null); // Center the frame on the desktop
    
            String placeHolderOne = "Current Location...";
            String placeHolderTwo = "Destination...";
    
            ArrayList<String> nodeNames = graph.getNodeNames();
            RoundedSearchBarExample searchBarPanel1 = new RoundedSearchBarExample(nodeNames, placeHolderOne);
            RoundedSearchBarExample searchBarPanel2 = new RoundedSearchBarExample(nodeNames, placeHolderTwo);
    
            // Create a panel to hold the search bars
            JPanel searchPanel = new JPanel();
            JPanel searchFieldPanel = new JPanel();
            searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
    
            JTextArea optimalRoute = new JTextArea();
            optimalRoute.setEditable(false);
            optimalRoute.setFont(new Font("Monospace", Font.BOLD, 15));
            optimalRoute.setMargin(new Insets(10, 10, 10, 10)); // 10px padding on all sides
     
            JPanel optimalPanel = new JPanel();
            optimalPanel.add(optimalRoute, BorderLayout.CENTER);
    
            JButton actionButton = new JButton();
            actionButton.setText("Search for The Best Route...");
            allRoutes = new ArrayList<>();
    
            // Define column names
            String[] columnNames = {"Alternate Routes", "Distance", "Approximate Time"};
    
            // Create and keep a reference to the table model
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make all cells uneditable
                }
            };
    
            // Create a table using the table model
            JTable table = new JTable(tableModel);
            setColumnWidths(table);
            setColumnHeaderAlignment(table);
    
            // Set custom cell renderer for wrapping text
            table.setDefaultRenderer(Object.class, new TextAreaRenderer());
            table.setRowHeight(200); // Modify the table height when neccesary.

            TableColumn column = table.getColumnModel().getColumn(0); 
            column.setPreferredWidth(650); 
            // Add the table to a scroll pane
            JScrollPane scrollPane = new JScrollPane(table);
    
            // Add the scroll pane to the frame (in the center of the BorderLayout)
            frame.add(scrollPane, BorderLayout.CENTER);
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String currentLocationName = searchBarPanel1.searchBar.getText();
                    String destinationName = searchBarPanel2.searchBar.getText();
            
                    if (currentLocationName.equals(destinationName)) {
                        JOptionPane.showMessageDialog(null, "Please choose another Location, Since Both Current\nLocation and Destination Can Not Be The Same.", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
            
                    // Retrieve the corresponding Node objects
                    Node currentLocation = graph.getNodeByName(currentLocationName);
                    Node destination = graph.getNodeByName(destinationName);
            
                    if (currentLocation == null || destination == null) {
                        JOptionPane.showMessageDialog(null, "One or both locations are invalid. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
            
                    // Set the message in the JTextArea
                    optimalRoute.setText("Please wait while calculating the routes...");
                    actionButton.setEnabled(false);

            
                    // Use a SwingWorker to perform background tasks
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            ArrayList<ArrayList<Node>> allPaths = BFS.findAllPaths(graph, currentLocation, destination);
                            allRoutes.clear();
            
                            // Iterate over all paths
                            for (ArrayList<Node> nodes : allPaths) {
                                allRoutes.add(getAllRoutes(nodes, ((graph.calculateDistance(nodes)) * 100), ((graph.calculateDistance(nodes) * 100 / 10f / CSVParser.WALKING_SPEED_MPS))));
                            }
            
                            MergeSort.mergeSort(allRoutes);
                            return null;
                        }
            
                        @Override
                        protected void done() {
                            try {
                                // Update the table model and JTextArea when background task is finished
                                tableModel.setRowCount(0); // Clear existing rows
                                for (Object[] route : allRoutes) {
                                    route[1] = String.format("%.2f", route[1]) + "m";
                                    route[2] = String.format("%.2f", (route[2])) + "min(s)";
                                    tableModel.addRow(route); // Add updated rows
                                }
            
                                optimalRoute.setText("Optimal Route: " + printPath(Dijkstra.findShortestPath(graph, currentLocation, destination)) + "\nDistance: " + String.format("%.2f", (Dijkstra.getDistance(destination))*1000) + "m \nApproximate Time: " + String.format("%.2f", Dijkstra.getDistance(destination) * 1000 / 10f / CSVParser.WALKING_SPEED_MPS) + " min(s)\n");
            
                            } finally {
                                // Re-enable the action button after processing
                                actionButton.setEnabled(true);
                            }
                        }
                    };
            
                    // Execute the SwingWorker
                    worker.execute();
                }
            });
            
            searchFieldPanel.add(searchBarPanel1, BorderLayout.CENTER);
            searchFieldPanel.add(searchBarPanel2, BorderLayout.CENTER);
            searchPanel.add(searchFieldPanel);
    
            // Add some space between the search bars and the button
            searchPanel.add(Box.createVerticalStrut(10)); // 10 pixels of vertical space
    
            // Center the button and add it to the searchPanel
            actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            searchPanel.add(actionButton);
    
            // Add the search bar panel to the frame
            frame.add(searchPanel, BorderLayout.NORTH);
            frame.add(optimalPanel, BorderLayout.SOUTH);
    
            // Make the frame visible
            frame.setVisible(true);
        });
    }
    


    /**
     * Constructs an array representing a route with its distance and time.
     *
     * @param path     The list of nodes representing the route.
     * @param distance The distance of the route in meters.
     * @param time     The time to traverse the route in seconds.
     * @return An array containing the formatted route string, distance, and time.
     */
    public static Object[] getAllRoutes(ArrayList<Node> path, double distance, double time) {
        ArrayList<String> routes = new ArrayList<>();
        for (Node n : path) {
            routes.add(n.getName());
        }

        StringBuilder pathBuilder = new StringBuilder();
        boolean start = true;
        for (String s : routes) {
            pathBuilder.append((start ? "" : " ➔  ") + s);
            start = false;
        }
        return new Object[] { pathBuilder.toString(), distance, time };

    }

    /**
     * Custom JTextField with rounded corners and placeholder text.
     */
    static class RoundedTextField extends JTextField {
        private final int radius;
        private final String placeholder;
        private boolean isPlaceholderVisible = true;

        public RoundedTextField(int radius, String placeholder) {
            super();
            this.radius = radius;
            this.placeholder = placeholder;
            setOpaque(false);

            // Add document listener to manage placeholder visibility
            getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updatePlaceholderVisibility();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updatePlaceholderVisibility();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updatePlaceholderVisibility();
                }
            });

            // Initially update the placeholder visibility
            updatePlaceholderVisibility();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

            super.paintComponent(g);

            if (isPlaceholderVisible) {
                g2d.setColor(Color.GRAY);
                g2d.drawString(placeholder, 10, getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 2);
            }

            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border painting
        }

        @Override
        public Insets getInsets() {
            return new Insets(10, 10, 10, 10);
        }

        private void updatePlaceholderVisibility() {
            isPlaceholderVisible = getText().isEmpty();
            repaint();
        }
    }

    // Custom JPanel with search bar and suggestion list
    static class RoundedSearchBarExample extends JPanel {
        private final JList<String> suggestionList;
        private final DefaultListModel<String> listModel;
        private final RoundedTextField searchBar;
        private final List<String> allSuggestions; // To keep the original list of suggestions




        public RoundedSearchBarExample(List<String> suggestions, String placeholder) {
            setLayout(null); // Use absolute positioning

            // Initialize list model and list
            listModel = new DefaultListModel<>();
            suggestionList = new JList<>(listModel);
            suggestionList.setVisibleRowCount(5);
            
            JScrollPane scrollPane = new JScrollPane(suggestionList);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setOpaque(false); // Make scroll pane transparent
            scrollPane.getViewport().setOpaque(false); // Make viewport transparent
            scrollPane.setPreferredSize(new Dimension(200, 0)); // Height will be adjusted dynamically

            // Initialize rounded search bar with placeholder
            searchBar = new RoundedTextField(40, placeholder);
            searchBar.setPreferredSize(new Dimension(600, 30));
            searchBar.setBorder(BorderFactory.createEmptyBorder()); // Remove default border

            // Store all suggestions
            allSuggestions = new ArrayList<>(suggestions);

            // Add document listener to the search bar
            searchBar.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    filterList();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    filterList();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    filterList();
                }
            });

            // Add key listener to hide the list when text matches any item
            searchBar.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        checkMatchAndHide();
                    }
                }
            });

            // Add mouse listener to hide the list when an item is selected
            suggestionList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        String selectedValue = suggestionList.getSelectedValue();
                        if (selectedValue != null) {
                            searchBar.setText(selectedValue);
                            hideList();
                        }
                    }
                }
            });

            // Add components to panel
            searchBar.setBounds(0, 0, 350, 40); // Set position and size of search bar
            scrollPane.setBounds(0, 40, 350, 150); // Set position and size of the suggestion list

            add(searchBar);
            add(scrollPane);

            // Update list with provided suggestions
            updateSuggestions(allSuggestions);

            // Set size and visibility for testing
            heightFromTop = 190;
            setPreferredSize(new Dimension(360, heightFromTop));
        }





        private void filterList() {
            String query = searchBar.getText().toLowerCase();
            // Filter the list based on the query
            List<String> filteredSuggestions = allSuggestions.stream()
                    .filter(suggestion -> suggestion.toLowerCase().contains(query))
                    .collect(Collectors.toList());
            updateSuggestions(filteredSuggestions);

            // Adjust the height of the scroll pane based on the number of items
            int itemCount = filteredSuggestions.size();
            int itemHeight = suggestionList.getFixedCellHeight();
            int listHeight = itemCount > 0 ? itemCount * itemHeight : 0;
            ((JScrollPane) getComponent(1)).setPreferredSize(new Dimension(200, listHeight));

            // Show the list if there are matching items
            if (!filteredSuggestions.isEmpty()) {
                ((JScrollPane) getComponent(1)).setVisible(true);
                heightFromTop = 190;
                setPreferredSize(new Dimension(350, heightFromTop));

                
            } else {
                hideList();
            }
        }

        private void checkMatchAndHide() {
            String text = searchBar.getText().toLowerCase();
            if (allSuggestions.stream().anyMatch(suggestion -> suggestion.toLowerCase().equals(text))) {
                hideList();
            }
        }

        private void hideList() {
            ((JScrollPane) getComponent(1)).setVisible(false);
            heightFromTop = 50;
            setPreferredSize(new Dimension(350, heightFromTop));

        }

        private void updateSuggestions(List<String> suggestions) {
            listModel.clear();
            for (String suggestion : suggestions) {
                listModel.addElement(suggestion);
            }
            // Trigger a repaint to update the list height
            revalidate();
            repaint();
        }
    }

    /**
     * Renderer for JTextPane cells in a JTable.
     */
    static class TextAreaRenderer extends JTextPane implements TableCellRenderer {
        public TextAreaRenderer() {
            
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText(value.toString());
            setMargin(new Insets(10, 10, 10, 10)); // 10px padding on all sides
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            

            // Center text horizontally
            StyledDocument doc = getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, column == 0 ? StyleConstants.ALIGN_JUSTIFIED : StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);


            return this;
        }
    }

     /**
     * Sets the preferred width of columns in a table.
     *
     * @param table The table whose columns' widths need to be set.
     */
    private static void setColumnWidths(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }
    }

    /**
     * Sets the alignment of the column headers in the table.
     *
     * @param table The table whose column headers' alignment needs to be set.
     */
    private static void setColumnHeaderAlignment(JTable table) {
        JTableHeader header = table.getTableHeader();

        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = header.getColumnModel().getColumn(i);
            TableCellRenderer renderer = column.getHeaderRenderer();
            if (renderer == null) {
                renderer = header.getDefaultRenderer();
            }
            Component comp = renderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, i);
            ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
        }
    }

    /**
     * Prints a formatted path for the given list of nodes.
     *
     * @param path The list of nodes representing the path.
     * @return A string representation of the path.
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
