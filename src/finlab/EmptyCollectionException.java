package finlab;

/**
 * Thrown when an implementation of a collection
 * does not contain an element
 */
public class EmptyCollectionException extends RuntimeException {
    EmptyCollectionException(String message) {
        super(message);
    }
}
