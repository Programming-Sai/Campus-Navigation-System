package utils;

/**
 * The Node class represents a node in a graph or network with a unique name.
 * It provides methods to access the node's name, compare nodes for equality,
 * and get a string representation of the node.
 */
public class Node {
    private String name;

    /**
     * Constructs a Node with the specified name.
     *
     * @param name The name of the node.
     */
    public Node(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the node.
     *
     * @return The name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if this node is equal to another object.
     * Nodes are considered equal if they have the same name.
     *
     * @param obj The object to compare this node with.
     * @return true if the object is a Node with the same name; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node other = (Node) obj;
            return other.getName().equals(getName());
        }
        return false;
    }

    /**
     * Returns a string representation of the node, which is its name.
     *
     * @return The name of the node.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
