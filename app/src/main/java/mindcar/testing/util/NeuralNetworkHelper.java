package mindcar.testing.util;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import java.util.Iterator;
import java.util.List;

/**
 * Created by sid on 5/11/16.
 */
public class NeuralNetworkHelper {

    public static NeuralNetwork createNetwork(TrainingSet trainingSet, int inputSize, int outputSize){
        MultiLayerPerceptron network = new MultiLayerPerceptron(TransferFunctionType.TANH, inputSize, 3, outputSize);
        network.setLearningRule(new MomentumBackpropagation());
        network.learnInNewThread(trainingSet);
        testNeuralNetwork(network,trainingSet);
        return network;
    }

    public static TrainingSet createTrainingSet(List<double[]> input, int inputSize, int outputSize){
        TrainingSet trainingSet = new TrainingSet(inputSize,outputSize);
        int i = 1;
        for(double[] d : input){
            trainingSet.addElement(new SupervisedTrainingElement(d, new double[]{i}));
            i++;
        }
        return trainingSet;
    }

//    public static void saveNetwork(NeuralNetwork neuralNetwork, String name){
//        neuralNetwork.save(name + ".nnet");
//    }
//
//    public static NeuralNetwork loadNetwork(String name){
//        return NeuralNetwork.createFromFile(name);
//    }

    /**
     * Calculates the scenario with a NeuralNetwork and a trainingSet
     *as input parameters.
     * @param neuralNetwork
     * @param trainingSet
     */
    public static void testNeuralNetwork(NeuralNetwork neuralNetwork, TrainingSet trainingSet) {
        Iterator var3 = trainingSet.elements().iterator();

        while(var3.hasNext()) {
            SupervisedTrainingElement trainingElement = (SupervisedTrainingElement)var3.next();
            neuralNetwork.setInput(trainingElement.getInput());
            neuralNetwork.calculate();
        }
    }


}
