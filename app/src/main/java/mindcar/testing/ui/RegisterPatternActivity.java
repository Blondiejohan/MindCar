package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neurosky.thinkgear.TGDevice;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.sql.Blob;
import java.util.LinkedList;

import mindcar.testing.R;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;
import mindcar.testing.util.NeuralNetworkHelper;

public class RegisterPatternActivity extends AppCompatActivity implements View.OnClickListener {

    public static double[] baseline;
    public static double[] left;
    public static double[] right;
    public static double[] forward;
    public static double[] stop;


    private static final int INPUT_SIZE = 800;
    private static final int HIDDEN_SIZE = 397;
    private static final int OUTPUT_SIZE = 4;

    public static final int PATTERN_SIZE = 100;


    public static LinkedList<double[]> inputs = new LinkedList<>();
    public static LinkedList<double[]> outputs = new LinkedList<>();
    public static boolean  startBoolean, baselineBoolean, leftBoolean, rightBoolean, forwardBoolean, stopBoolean;

    private DatabaseAccess databaseAccess;

    public static Pattern baselinePattern = null;
    public static Pattern tmpPattern;
    public static Eeg tmpEeg;

    public static TrainingSet trainingSet;
    public static NeuralNetwork neuralNetwork;

    private int times;
    public static TextView registerPatternsText;
    private Button registerPatternReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pattern);

        startBoolean = true;

        times = 0;
        tmpEeg = new Eeg();
        tmpPattern = new Pattern(PATTERN_SIZE);
        databaseAccess = DatabaseAccess.getInstance(this);

        populateOutputs();

        initializeArrays();

        trainingSet = new TrainingSet(INPUT_SIZE, OUTPUT_SIZE);
        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.STEP, INPUT_SIZE, HIDDEN_SIZE, OUTPUT_SIZE);
        neuralNetwork.setLearningRule(new MomentumBackpropagation());
        neuralNetwork.setLabel(RegistrationActivity.user_name);

        registerPatternsText = (TextView) findViewById(R.id.registerPatternText);

        registerPatternReady = (Button) findViewById(R.id.registerPatternReady);
        registerPatternReady.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        BluetoothActivity.startLearning = true;
        registerPatternReady.setEnabled(false);
    }

    public static void initializeArrays() {
        baseline = new double[INPUT_SIZE];
        left = new double[INPUT_SIZE];
        right = new double[INPUT_SIZE];
        forward = new double[INPUT_SIZE];
        stop = new double[INPUT_SIZE];
    }

    public static void extendTrainingSet() {
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(0), outputs.get(0)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(1), outputs.get(1)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(2), outputs.get(2)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(3), outputs.get(3)));

        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(4)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(5)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(6)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(7)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(8)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(9)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(10)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(11)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(12)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(13)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(14)));
        trainingSet.addElement(new SupervisedTrainingElement(inputs.get(4), outputs.get(15)));
    }

    public static void populateArray(double[] array) {
        array = tmpPattern.toArray();
        tmpPattern = new Pattern(PATTERN_SIZE);
    }

    public static void populateInputs() {
        inputs.add(left);
        inputs.add(right);
        inputs.add(forward);
        inputs.add(stop);

        inputs.add(baseline);
    }

    public static void populateOutputs() {
        outputs.add(new double[]{1, 0, 0, 0});  //  0
        outputs.add(new double[]{0, 1, 0, 0});  //  1
        outputs.add(new double[]{0, 0, 1, 0});  //  2
        outputs.add(new double[]{0, 0, 0, 1});  //  3

        outputs.add(new double[]{0, 0, 0, 0});  //  4
        outputs.add(new double[]{0, 0, 1, 1});  //  5
        outputs.add(new double[]{0, 1, 1, 0});  //  6
        outputs.add(new double[]{1, 1, 0, 0});  //  7
        outputs.add(new double[]{1, 0, 1, 0});  //  8
        outputs.add(new double[]{0, 1, 0, 1});  //  9
        outputs.add(new double[]{1, 0, 0, 1});  //  10
        outputs.add(new double[]{0, 1, 1, 1});  //  11
        outputs.add(new double[]{1, 1, 1, 0});  //  12
        outputs.add(new double[]{1, 0, 1, 1});  //  13
        outputs.add(new double[]{1, 1, 0, 1});  //  14
        outputs.add(new double[]{1, 1, 1, 1});  //  15
    }


    public static void nextValue() {
        if(startBoolean){
            startBoolean = false;
            baselineBoolean = true;
        } else if (baselineBoolean) {
            baselineBoolean = false;
            leftBoolean = true;
        } else if (leftBoolean){
            leftBoolean = false;
            rightBoolean = true;
        } else if (rightBoolean) {
            rightBoolean = false;
            forwardBoolean = true;
        } else if (forwardBoolean) {
            forwardBoolean = false;
            stopBoolean = true;
        } else {
            stopBoolean = false;
            startBoolean = true;
        }
    }
}
