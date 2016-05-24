package mindcar.testing.util;

import android.content.Context;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import mindcar.testing.ui.StartActivity;

/**
 * Created by Mattias on 5/11/16.
 */
public class NeuralNetworkHelper {

    public static NeuralNetwork createNetwork(TrainingSet trainingSet, int inputSize, int outputSize) {
        MultiLayerPerceptron network = new MultiLayerPerceptron(TransferFunctionType.STEP, inputSize, 19, outputSize);
        network.setLearningRule(new MomentumBackpropagation());
        network.learnInNewThread(trainingSet);
        testNeuralNetwork(network, trainingSet);
        return network;
    }

    public static TrainingSet createTrainingSet(List<double[]> input, int inputSize, int outputSize) {
        TrainingSet trainingSet = new TrainingSet(inputSize, outputSize);

        // Wanted scenarios
        trainingSet.addElement(new SupervisedTrainingElement(input.get(0), new double[]{1,0,0,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(1), new double[]{0,1,0,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(2), new double[]{0,0,1,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(3), new double[]{0,0,0,1}));

        // Baseline scenarios
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{0,0,0,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{0,0,1,1}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{0,1,1,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{1,1,0,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{1,0,1,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{0,1,0,1}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{1,0,0,1}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{0,1,1,1}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{1,1,1,0}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{1,0,1,1}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{1,1,0,1}));
        trainingSet.addElement(new SupervisedTrainingElement(input.get(4), new double[]{1,1,1,1}));

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
     * as input parameters.
     *
     * @param neuralNetwork
     * @param trainingSet
     */
    public static void testNeuralNetwork(NeuralNetwork neuralNetwork, TrainingSet trainingSet) {
        Iterator var3 = trainingSet.elements().iterator();

        while (var3.hasNext()) {
            SupervisedTrainingElement trainingElement = (SupervisedTrainingElement) var3.next();
            neuralNetwork.setInput(trainingElement.getInput());
            neuralNetwork.calculate();
        }
    }

    public static void saveNetwork(Context context, NeuralNetwork neuralNetwork, String name) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            File nnet = new File(name);
            fileOutputStream = context.openFileOutput(nnet.getName(), 0);
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(fileOutputStream));
            objectOutputStream.writeObject(neuralNetwork);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    ;
                }
            }

        }
    }

    public static NeuralNetwork loadNetwork(Context context, byte[] bytes) {
        ObjectInputStream objectInputStream = null;
        NeuralNetwork neuralNetwork = null;

        try {
            File file = new File(StartActivity.un + ".nnet");
            FileInputStream fileInputStream = context.openFileInput(file.getName());
            fileInputStream.read(bytes);
            objectInputStream = new ObjectInputStream(new BufferedInputStream(fileInputStream));
            neuralNetwork = (NeuralNetwork) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return neuralNetwork;
    }


}
