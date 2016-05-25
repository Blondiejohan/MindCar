package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    public static final int BASELINE    =   0;
    public static final int LEFT        =   1;
    public static final int RIGHT       =   2;
    public static final int FORWARD     =   3;
    public static final int STOP        =   4;


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
    public static String name, password;

    //Sanja
    static ImageView forwardIcon;
    static ImageView rightIcon;
    static ImageView leftIcon;
    static ImageView stopIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pattern);

        //create image view for saving patterns
        forwardIcon = (ImageView) findViewById(R.id.forwartIcon);
        rightIcon = (ImageView) findViewById(R.id.rightIcon);
        leftIcon = (ImageView) findViewById(R.id.leftIcon);
        stopIcon = (ImageView) findViewById(R.id.stopIcon);

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
        neuralNetwork.setLabel(name);

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


    public static void populateArray(int i) {
        if(i == BASELINE){
            baseline = tmpPattern.toArray();
        } else if (i == LEFT) {
            left = tmpPattern.toArray();
        } else if (i == RIGHT) {
            right = tmpPattern.toArray();
        } else if (i == FORWARD) {
            forward = tmpPattern.toArray();
        } else if (i == STOP) {
            stop = tmpPattern.toArray();
        }

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
            registerPatternsText.setText("Establishing baseline /n please relax");


        } else if (baselineBoolean) {
            baselineBoolean = false;
            leftBoolean = true;
            registerPatternsText.setText("Think about going left /n now saving");
            stopIcon.setVisibility(View.GONE);
            leftIcon.setVisibility(View.VISIBLE);

        } else if (leftBoolean){
            leftBoolean = false;
            rightBoolean = true;
            registerPatternsText.setText("Think about going right /n now saving");
            leftIcon.setVisibility(View.GONE);
            rightIcon.setVisibility(View.VISIBLE);
        } else if (rightBoolean) {
            rightBoolean = false;
            forwardBoolean = true;
            registerPatternsText.setText("Think about going forward /n now saving");
            rightIcon.setVisibility(View.GONE);
            forwardIcon.setVisibility(View.VISIBLE);
        } else if (forwardBoolean) {
            forwardBoolean = false;
            stopBoolean = true;
            registerPatternsText.setText("Think about stopping /n now saving");
            forwardIcon.setVisibility(View.GONE);
            stopIcon.setVisibility(View.VISIBLE);

        } else {
            stopBoolean = false;
            startBoolean = true;
        }
    }

    //Madisen
    //get user name and password from global settings
    public void getUNPW(){
        SharedPreferences sharedPref = getSharedPreferences("registrationInfo", Context.MODE_PRIVATE);
        name = sharedPref.getString("username", "");
        password = sharedPref.getString("password","");
    }
}
