package exceptions;

/**
 * A class that extends the IllegalArgumentException class. This exception should be used
 * when an element of an array is repeated (aka, in this program when a label is repeated)
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public class RepeatedLabelException extends IllegalArgumentException{
    /**
     * Constructor that receives a message and is directly passed to super
     * @param message       A String with the error description
     */
    public RepeatedLabelException(String message) {
        super(message);
    };
}
