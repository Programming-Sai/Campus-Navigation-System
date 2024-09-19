package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Utility class for performing Breadth-First Search (BFS) on a graph to find all paths 
 * from a source node to a destination node.
 */
public class BFS {

    /**
     * Finds all possible paths from the source node to the destination node using BFS.
     *
     * @param graph The graph in which the search is to be performed.
     * @param source The starting node for the search.
     * @param destination The target node for the search.
     * @return A list of all paths from the source to the destination.
     */
    public static ArrayList<ArrayList<Node>> findAllPaths(Graph graph, Node source, Node destination) {
        ArrayList<ArrayList<Node>> allPaths = new ArrayList<>();
        Set<Node> visited = new HashSet<>();

        Stack<PathNode> stack = new Stack<>();
        stack.push(new PathNode(source, new ArrayList<>()));

        while (!stack.isEmpty()) {
            PathNode pathNode = stack.pop();
            Node currentNode = pathNode.node;
            ArrayList<Node> currentPath = pathNode.path;

            if (currentNode.equals(destination)) {
                ArrayList<Node> pathToAdd = new ArrayList<>(currentPath);
                pathToAdd.add(currentNode);
                allPaths.add(pathToAdd);
                continue;
            }

            visited.add(currentNode);
            currentPath.add(currentNode);

            for (Node neighbor : graph.getNeighbourNodes(currentNode)) {
                if (!visited.contains(neighbor)) {
                    ArrayList<Node> newPath = new ArrayList<>(currentPath);
                    stack.push(new PathNode(neighbor, newPath));
                }
            }

            currentPath.remove(currentPath.size() - 1);
        }

        return allPaths;
    }

    /**
     * Helper class to store a node and the path leading to that node.
     */
    private static class PathNode {
        Node node;
        ArrayList<Node> path;

        PathNode(Node node, ArrayList<Node> path) {
            this.node = node;
            this.path = path;
        }
    }
}
