package finlab;

/**
 * Represents a node in a linked list.
 * @param <T>
 */
public class LinearNode<T> {
    private LinearNode<T> next;
    private T element;

    /**
     * Creates an empty node.
     */
    public LinearNode() {
        next = null;
        element = null;
    }

    /**
     * Creates a node storing the specified element.
     * @param elem element to be stored
     */
    public LinearNode(T elem) {
        next = null;
        element = elem;
    }

    /**
     * Returns the node that follows this one
     * @return reference to the next node
     */
    public LinearNode<T> getNext() {
        return next;
    }

    /**
     * Sets the node that follows this one
     * @param next node to follow this one
     */
    public void setNext(LinearNode<T> next) {
        this.next = next;
    }

    /**
     * Returns the element stored in this node.
     * @return element stored in this node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in this node
     * @param element to be stored at this node
     */
    public void setElement(T element) {
        this.element = element;
    }
}
