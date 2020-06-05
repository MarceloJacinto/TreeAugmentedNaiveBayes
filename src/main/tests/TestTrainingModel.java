package main.tests;

import datamodel.DataModel;
import datamodel.TrainingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASS USED ONLY FOR TESTING PURPOSES - THIS IS BASICALLY A UNIT TEST OF THE CODE
 * A class used to test the trainingModel class
 */
public class TestTrainingModel {

    public static void main(String[] args) {
        String[] labels = {"X1","X2","X3"};
        String[] line1 =  {"0","0","0","0"};
        String[] line2 =  {"0","1","1","1"};
        String[] line3 =  {"1","2","0","1"};
        String[] line4 =  {"1","1","1","1"};
        String[] line5 =  {"0","1","0","0"};
        String[] line6 =  {"1","2","0","0"};
        String[] line7 =  {"1","0","1","1"};

        List<String[]> data = new ArrayList<String[]>(7);
        data.add(line1);
        data.add(line2);
        data.add(line3);
        data.add(line4);
        data.add(line5);
        data.add(line6);
        data.add(line7);

        DataModel<String> trainingModel = new TrainingModel<>(labels);
        trainingModel.batchUpdateModel(data);

        for(int i = 0; i < trainingModel.getNumCategories(); i++){
            System.out.println(trainingModel.getCategory(i).toString());
        }

        for(int c = 0; c < trainingModel.getNumClasses(); c++) {
            System.out.println(trainingModel.getNcCount(c));
        }
    }
}
