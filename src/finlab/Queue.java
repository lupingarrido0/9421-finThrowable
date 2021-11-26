package finlab;

/**
 * LinkedQueue represents a linked implementation of a queue.
 * @param <T> Parameterized type of Queue elements
 */
public class Queue<T> {
    private int count;
    private LinearNode<T> head, tail;

    /**
     * Creates an empty queue.
     */
    public Queue() {
        count = 0;
        head = tail = null;
    }

    /**
     * Adds tbe specified element to the tail of this queue.
     * @param element the element to be added to the rear of the queue
     */
    public void enqueue(T element) {
        LinearNode<T> node = new LinearNode<>(element);
        if (isEmpty()) head = node;
        else tail.setNext(node);
        tail = node;
        count++;
    }

    /**
     * Removes the element at the head of this queue and returns
     * a reference to it.
     * @return the element at the head of this queue.
     * @throws EmptyCollectionException if the queue is empty
     */
    public T poll() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty queue.");

        T result = head.getElement();
        head = head.getNext();
        count--;

        if (isEmpty()) tail = null;

        return result;
    }

    /**
     * Returns the element at the head of the queue
     * without removing it
     * @return element at the head of the queue
     */
    public T peek() {
        if (isEmpty()) throw new EmptyCollectionException("Empty queue.");
        return head.getElement();
    }

    /**
     * Tests if the queue is empty
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the size of the queue
     * @return the size of the queue
     */
    public int size() {
        return count;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        LinearNode<T> temp = head;

        while (temp.getNext() != null) {
            result.append(temp.getElement().toString());
            result.append(" ");
        }
        return result.toString();
    }
}
