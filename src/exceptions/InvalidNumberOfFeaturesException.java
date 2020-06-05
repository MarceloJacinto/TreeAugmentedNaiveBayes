package exceptions;

/**
 * A class that extends the IllegalArgumentException class. This exception should be used
 * when the Number of Elements of features/number of elements in an array is not the expected one
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public class InvalidNumberOfFeaturesException extends IllegalArgumentException {
    /**
     * Constructor that receives a message and is directly passed to super
     * @param message       A String with the error description
     */
    public InvalidNumberOfFeaturesException(String message){
        super(message);
    }
}
