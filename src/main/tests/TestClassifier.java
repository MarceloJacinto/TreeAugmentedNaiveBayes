package main.tests;

import score.MDLScore;
import score.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASS USED ONLY FOR TESTING PURPOSES - THIS IS BASICALLY A UNIT TEST OF THE CODE
 * A class used to test the score of the classifier
 */
public class TestClassifier {
    String[] labels = {"X1","X2","X3"};
    String[] line1 =  {"0","0","0","0"};
    String[] line2 =  {"0","1","1","1"};
    String[] line3 =  {"1","2","0","1"};
    String[] line4 =  {"1","1","1","1"};
    String[] line5 =  {"0","1","0","0"};
    String[] line6 =  {"1","2","0","0"};
    String[] line7 =  {"1","0","1","1"};

    String[] predict1 = {"0","0","0"};
    String[] predict2 = {"0","1","1"};
    String[] predict3 = {"1","1","1"};

    List<String[]> data = new ArrayList<>(7);


    Score score = new MDLScore();
    //Classifier<String> network = new OnlineBayes<>(score, labels);
    //network.train(trainingData);
}
