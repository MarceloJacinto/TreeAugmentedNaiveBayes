package exceptions;

/**
 * A class that extends the IllegalArgumentException class. This exception should be used
 * when a String is empty an that is not expected.
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public class EmptyStringException extends IllegalArgumentException {
    /**
     * Constructor that receives a message and is directly passed to super
     * @param message       A String with the error description
     */
    public EmptyStringException(String message){
        super(message);
    }
}
