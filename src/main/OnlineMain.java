package main;

import classifier.Classifier;
import classifier.TreeAugmentedNayveBayes;
import metrics.*;
import parser.facades.OnlinePredictingFacade;
import parser.facades.TrainingModelFacade;
import parser.DataParser;
import score.LLScore;
import score.MDLScore;
import score.Score;
import utils.Utils;

import java.util.List;

/**
 * The main program class. The entrance point to the program
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class OnlineMain {

    /**
     * The main function. The entry point for the program
     * @param args      Trainfile Testfile LL/MDL
     */
    public static void main(String[] args) {

        /* ----Validate the input arguments---- */
        Utils.checkArguments(args);

        /* Create a facade that will be used to create a data model and that is updated dynamically*/
        long startBuildTime = System.nanoTime();

        TrainingModelFacade trainingModelProducer = new TrainingModelFacade();
        DataParser.readFile(args[0], ",", trainingModelProducer);

        /* ----Create the score to be used---- */
        Score score = (args[2].equals("LL")) ? new LLScore() : new MDLScore();

        /* ----Create the classifier and fit it to the trainingParameters---- */
        Classifier<String> classifier;
        try {
            classifier = new TreeAugmentedNayveBayes<>(score, trainingModelProducer.getTrainingModel());
            classifier.train();

            long endBuildTime = System.nanoTime();
            long buildTime = endBuildTime - startBuildTime;
            System.out.println(classifier.toString());
            System.out.println("Time to build: " + buildTime / 1000000 + " ms");

            /* ----Create a facade that will be used to make predictions "online"---- */
            OnlinePredictingFacade onlinePredicting = new OnlinePredictingFacade(classifier);

            /* ----Predict the results as it is read from the files---- */
            long startTestTime = System.nanoTime();

            DataParser.readFile(args[1], ",", onlinePredicting);
            List<String> predictedClasses = onlinePredicting.getResults();
            List<String> trueClasses = onlinePredicting.getTrueClasses();

            long endTestTime = System.nanoTime();
            long testTime = endTestTime - startTestTime;

            if(predictedClasses.size() == 0) {
                System.out.println("There were no predictions made on the test set. Was the test file empty? Or all the data was garbage?");
                System.exit(1);
            }

            /* Print the resulting classifications */
            System.out.println("Testing the classfier:");
            int count = 1;
            String format = "%-20s%s%n";
            for(String oneResult : predictedClasses) {
                System.out.printf(format, "-> instance " + count + ": ", oneResult);
                count++;
            }
            System.out.println("Time to test: " + testTime/ 1000000 + " ms");

            /* Calculate the Metrics for the classification */
            ConfusionMatrix<String> confusionMatrix = new ConcreteConfusionMatrix<>(predictedClasses, trueClasses);
            AverageScore accuracyScore = new AccuracyScore<String>(predictedClasses, trueClasses);
            MultipleScore<String> specificityScore = new SpecificityScore<>(confusionMatrix);
            MultipleScore<String> sensitivityScore = new SensitivityScore<>(confusionMatrix);
            MultipleScore<String> f1Score = new F1Score<>(confusionMatrix);

            /* Calculate the Metrics for the classification */
            System.out.print("Resume: ");
            System.out.println("Accuracy: " + String.format("%.2f",accuracyScore.getScore()));
            System.out.println(specificityScore.toString());
            System.out.println(sensitivityScore.toString());
            System.out.println(f1Score.toString());

            if(args.length == 4) {
                System.out.println(confusionMatrix.toString());
            }

        } catch(IllegalArgumentException e) {
            System.out.println("Could not generate a classifier! The file used for training was either in a wrong format, empty or with corrupted data");
            System.exit(1);
        }
    }
}
