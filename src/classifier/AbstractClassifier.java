package classifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract class that implements the Classifier interface. In this class the only method that is implemented is
 * the predictMultipleExamples method.
 * @param <T>       A generic type
 * @see Classifier
 * @see AbstractBayes
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class AbstractClassifier<T> implements Classifier<T>{

    /**
     * An implementation for the predictMultipleExamples method. When receiving a list of arrays of data (of generic type)
     * calls the method predictOneExample (defined in the Classifier interface) for each element of the list.
     * @param data      A list of arrays of generic type. This data should be used to make several predictions
     * @return          A list of generic type with the predictions
     */
    @Override
    public List<T> predictMultipleExamples(List<T[]> data) {
        List<T> predictions = new ArrayList<>(data.size());
        int i = 0;

        Iterator<T[]> it = data.iterator();
        while(it.hasNext()) {
            T[] oneData = it.next();
            predictions.add(i, predictOneExample(oneData));
            i++;
        }
        return predictions;
    }
}
