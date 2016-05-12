//package mindcar.testing.util;

//import org.neuroph.core.NeuralNetwork;
//import org.neuroph.core.data.DataSet;
//import org.neuroph.core.data.DataSetRow;
//import org.neuroph.nnet.MultiLayerPerceptron;
//import org.neuroph.nnet.learning.MomentumBackpropagation;
//import org.neuroph.util.TransferFunctionType;

//import java.util.List;

/**
 * Created by sid on 5/11/16.
 */
//public class NeuralNetworkHelper {

   // public NeuralNetwork createNetwork(DataSet dataSet, int inputSize, int outputSize){
    //    MultiLayerPerceptron network = new MultiLayerPerceptron(TransferFunctionType.TANH, inputSize, 1, outputSize);
     //   network.setLearningRule(new MomentumBackpropagation());
     //   network.learnInNewThread(dataSet);
     //   return network;
   // }

   // public DataSet createDataSet(List<double[]> input, int inputSize, int outputSize){
   //     DataSet dataSet = new DataSet(inputSize,outputSize);
   //     int i = 1;
    //    for(double[] d : input){
    //        dataSet.addRow(new DataSetRow(d,new double[]{i}));
     //       i++;
     //   }
     //   return dataSet;
  //  }

   // public void saveNetwork(NeuralNetwork neuralNetwork, String name){
     //   neuralNetwork.save(name + ".nnet");
   // }

    //public NeuralNetwork loadNetwork(String name){
    //    return NeuralNetwork.createFromFile(name);
   // }
//}
