package parser;

/**
 * An interface that provides that provides 2 methods: readData and readLabels. The method readData should process an
 * array of a generic type of data. The readLabels should process an array of String of data.
 * @see DataParser
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface DataHolder<T> {

    /**
     * This method is responsible for processing an array of generic type of data
     * @param data      An array of generic type
     */
    void readData(T[] data);

    /**
     * This method is responsible for processing an array of labels of type String
     * @param data      An array of String
     */
    void readLabels(String[] data);
}
