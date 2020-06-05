package main.tests;

import classifier.Classifier;
import classifier.TreeAugmentedNayveBayes;
import datamodel.DataModel;
import datamodel.TrainingModel;
import metrics.*;
import parser.facades.TestDataHolderFacade;
import parser.facades.TrainingDataHolderFacade;
import parser.DataParser;
import score.LLScore;
import score.MDLScore;
import score.Score;
import utils.Utils;

import java.util.List;

/**
 * THIS CLASS IS A DEMO OF HOW THE CLASSIFIER COULD BE USED IN BATCH TRAINING
 * NOT THE MAIN CLASS
 * THIS IS JUST A DEMO - NOT PROTECTED WITH TRY CATCH CLAUSES
 * EXPECTS ONLY DATA FULFILLING THE PRE-SPECIFIED FORMAT
 */
public class BatchMain {

    public static void main(String[] args) {
        /* ----Validate the input arguments---- */
        Utils.checkArguments(args);

        /* ----Read the training data from the file all at once---- */
        long startBuildTime = System.nanoTime();
        TrainingDataHolderFacade trainingDataHolder = new TrainingDataHolderFacade();
        DataParser.readFile(args[0], ",", trainingDataHolder);

        /* Generate a counting model from the data in memory */
        DataModel<String> dataCounts = new TrainingModel<String>(trainingDataHolder.getLabels());
        dataCounts.batchUpdateModel(trainingDataHolder.getData());

        /* Create a score to use with the classfier */
        Score score = (args[2].equals("LL")) ? new LLScore() : new MDLScore();

        /* Create a classifier and fit it to the counts */
        Classifier<String> classifier = new TreeAugmentedNayveBayes<String>(score, dataCounts);
        classifier.train();

        long endBuildTime = System.nanoTime();
        long buildTime = endBuildTime - startBuildTime;
        System.out.println(classifier.toString());
        System.out.println("Time to build: " + buildTime / 1000000 + " ms");

        /* ----Read the testing data from the file all at once---- */
        long startTestTime = System.nanoTime();
        TestDataHolderFacade testingDataHolder = new TestDataHolderFacade();
        DataParser.readFile(args[1], ",", testingDataHolder);

        /* ---Predict the results all at once---- */
        List<String> trueClasses = testingDataHolder.getClassifications();
        List<String> predictedClasses = classifier.predictMultipleExamples(testingDataHolder.getData());

        long endTestTime = System.nanoTime();
        long testTime = endTestTime - startTestTime;

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
        ConcreteConfusionMatrix<String> confusionMatrix = new ConcreteConfusionMatrix<>(predictedClasses, trueClasses);
        AccuracyScore<String> accuracyScore = new AccuracyScore<>(predictedClasses, trueClasses);
        MultipleScore<String> specificityScore = new SpecificityScore<>(confusionMatrix);
        MultipleScore<String> sensitivityScore = new SensitivityScore<>(confusionMatrix);
        MultipleScore<String> f1Score = new F1Score<>(confusionMatrix);

        System.out.print("Resume: ");
        System.out.println("Accuracy: " + accuracyScore.getScore());
        System.out.println(specificityScore.toString());
        System.out.println(sensitivityScore.toString());
        System.out.println(f1Score.toString());
    }
}
