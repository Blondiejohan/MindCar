package mindcar.testing.util;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import java.util.List;

/**
 * Created by sid on 5/11/16.
 */
public class NeuralNetworkHelper {

    public static NeuralNetwork createNetwork(TrainingSet dataSet, int inputSize, int outputSize){
        MultiLayerPerceptron network = new MultiLayerPerceptron(TransferFunctionType.TANH, inputSize, 3, outputSize);
        network.setLearningRule(new MomentumBackpropagation());
        //network.learnInNewThread(dataSet);
    //    testNeuralNetwork(network,dataSet);
        return network;
    }

    public static TrainingSet createDataSet(List<double[]> input, int inputSize, int outputSize){
        TrainingSet dataSet = new TrainingSet(inputSize,outputSize);
        int i = 1;
        for(double[] d : input){
            dataSet.addElement(new TrainingElement(d));
            i++;
        }
        return dataSet;
    }

//    public static void saveNetwork(NeuralNetwork neuralNetwork, String name){
//        neuralNetwork.save(name + ".nnet");
//    }
//
//    public static NeuralNetwork loadNetwork(String name){
//        return NeuralNetwork.createFromFile(name);
//    }

    /**
     * Calculates the scenario with a NeuralNetwork and a DataSet as input parameters.
     * @param neuralNetwork
     * @param trainingSet
     */
//    public static void testNeuralNetwork(NeuralNetwork neuralNetwork, TrainingSet trainingSet) {
//        for(DataSetRow dataSetRow:trainingSet.getRows()){
//            neuralNetwork.setInput(dataSetRow.getInput());
//            neuralNetwork.calculate();
//        }
//    }


}
