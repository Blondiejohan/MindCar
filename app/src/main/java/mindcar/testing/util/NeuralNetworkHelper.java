package mindcar.testing.util;

import android.content.Context;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.TrainingSet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Helper class for saving and loading NeuralNetworks and TrainingSets to and from Files.
 * Created by Mattias on 5/11/16.
 */
public class NeuralNetworkHelper {

    /**
     * Creates a file from the input NeuralNetwork with name as name.
     * @param context
     * @param neuralNetwork
     * @param name
     */
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

    /**
     * Converts a byte array into a File with name as name and returns it through and ObjectInputStream as a NeuralNetwork
     * @param context
     * @param bytes
     * @param name
     * @return a NeuralNetwork from a byte array
     */
    public static NeuralNetwork loadNetwork(Context context, byte[] bytes, String name) {
        ObjectInputStream objectInputStream = null;
        NeuralNetwork neuralNetwork = null;

        try {
            File file = new File(name + ".nnet");
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

    /**
     * Creates a file from the input TrainingSet with name as name.
     * @param context
     * @param trainingSet
     * @param name
     */
    public static void saveTrainingSet(Context context, TrainingSet trainingSet, String name) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            File tset = new File(name);
            fileOutputStream = context.openFileOutput(tset.getName(), 0);
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(fileOutputStream));
            objectOutputStream.writeObject(trainingSet);
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

    /**
     * Converts a byte array into a File with name as name and returns it through and ObjectInputStream as a TrainingSet
     * @param context
     * @param bytes
     * @param name
     * @return  a TrainingSet from a byte array
     */
    public static TrainingSet loadTrainingSet(Context context, byte[] bytes, String name) {
        ObjectInputStream objectInputStream = null;
        TrainingSet trainingSet = null;

        try {
            File file = new File(name + ".tset");
            FileInputStream fileInputStream = context.openFileInput(file.getName());
            fileInputStream.read(bytes);
            objectInputStream = new ObjectInputStream(new BufferedInputStream(fileInputStream));
            trainingSet = (TrainingSet) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return trainingSet;
    }
}
