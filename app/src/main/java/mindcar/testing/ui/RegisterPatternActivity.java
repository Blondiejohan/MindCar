package mindcar.testing.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import java.util.LinkedList;

import mindcar.testing.R;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.util.MessageParser;

public class RegisterPatternActivity extends Activity implements View.OnClickListener {

    public static double[] baseline;
    public static double[] left;
    public static double[] right;
    public static double[] forward;
    public static double[] stop;


    private static final int INPUT_SIZE = 800;
    private static final int HIDDEN_SIZE = 37;
    private static final int OUTPUT_SIZE = 4;

    public static final int PATTERN_SIZE = 100;

    public static final int BASELINE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int FORWARD = 3;
    public static final int STOP = 4;


    public static LinkedList<double[]> inputs = new LinkedList<>();
    public static LinkedList<double[]> outputs = new LinkedList<>();
    public static boolean startBoolean, baselineBoolean, leftBoolean, rightBoolean, forwardBoolean, stopBoolean, endBoolean;

    public static Pattern baselinePattern = null;
    public static Pattern tmpPattern;
    public static Eeg tmpEeg;

    public static TrainingSet trainingSet;
    public static NeuralNetwork neuralNetwork;

    private static int times = 0;
    public static TextView registerPatternsText;
    private Button registerPatternReady;

    //Sanja
    static ImageView forwardIcon;
    static ImageView rightIcon;
    static ImageView leftIcon;
    static ImageView stopIcon;

    private static ProgressBar activityProgress;
    public static ProgressBar patternProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pattern);

        //create image view for saving patterns
        forwardIcon = (ImageView) findViewById(R.id.forwartIcon);
        rightIcon = (ImageView) findViewById(R.id.rightIcon);
        leftIcon = (ImageView) findViewById(R.id.leftIcon);
        stopIcon = (ImageView) findViewById(R.id.stopIcon);

        activityProgress = (ProgressBar) findViewById(R.id.activityProgress);
        patternProgress = (ProgressBar) findViewById(R.id.patternProgress);

        times = 0;
        tmpEeg = new Eeg();
        tmpPattern = new Pattern(PATTERN_SIZE);
        populateOutputs();

        initializeArrays();

        trainingSet = new TrainingSet(INPUT_SIZE, OUTPUT_SIZE);
        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.STEP, INPUT_SIZE, HIDDEN_SIZE, OUTPUT_SIZE);
        neuralNetwork.setLearningRule(new MomentumBackpropagation());

        registerPatternsText = (TextView) findViewById(R.id.registerPatternText);

        registerPatternReady = (Button) findViewById(R.id.registerPatternReady);
        registerPatternReady.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BluetoothActivity.startLearning = true;
        baselineBoolean  = true;
        registerPatternReady.setEnabled(false);
    }

    public static void initializeArrays() {
        baseline = new double[INPUT_SIZE];
        left = new double[INPUT_SIZE];
        right = new double[INPUT_SIZE];
        forward = new double[INPUT_SIZE];
        stop = new double[INPUT_SIZE];
    }


    public static void populateArray(int i) {
        if (i == BASELINE) {
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

    public static void populateInputs(int i) {
        if (i == BASELINE) {
            inputs.add(baseline);
        } else if (i == LEFT) {
            inputs.add(left);
        } else if (i == RIGHT) {
            inputs.add(right);
        } else if (i == FORWARD) {
            inputs.add(forward);
        } else if (i == STOP) {
            inputs.add(stop);
        }
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
        if (baselineBoolean) {
            if (times >= 0) {
                populateInputs(BASELINE);
                Log.i("inputs", MessageParser.toString(inputs.get(0)));
                for(double[] doubles: inputs) {
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(4)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(5)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(6)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(7)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(8)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(9)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(10)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(11)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(12)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(13)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(14)));
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(15)));
                }
                baselineBoolean = false;
                leftBoolean = true;
                inputs = new LinkedList<>();
                activityProgress.setProgress(activityProgress.getProgress() + 20);
                patternProgress.setProgress(0);
                times = 0;
            } else {
                populateInputs(BASELINE);
                baseline = new double[INPUT_SIZE];
                times++;
                patternProgress.setProgress(times * 20);
            }


        } else if (leftBoolean) {
            if (times >= 2) {
                populateInputs(LEFT);
                Log.i("inputs", MessageParser.toString(inputs.get(0)));
                for(double[] doubles: inputs) {
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(0)));
                }
                leftBoolean = false;
                rightBoolean = true;
                inputs = new LinkedList<>();
                activityProgress.setProgress(activityProgress.getProgress() + 20);
                patternProgress.setProgress(0);
                times = 0;
            } else {
                populateInputs(LEFT);
                left = new double[INPUT_SIZE];
                times++;
                patternProgress.setProgress(times * 20);
            }


        } else if (rightBoolean) {
            if (times >= 2) {
                populateInputs(RIGHT);
                Log.i("inputs", MessageParser.toString(inputs.get(0)));
                for(double[] doubles: inputs) {
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(1)));
                }

                rightBoolean = false;
                forwardBoolean = true;
                inputs = new LinkedList<>();
                activityProgress.setProgress(activityProgress.getProgress() + 20);
                patternProgress.setProgress(0);
                times = 0;
            } else {
                populateInputs(RIGHT);
                right = new double[INPUT_SIZE];
                times++;
                patternProgress.setProgress(times * 20);
            }
        } else if (forwardBoolean) {
            if (times >= 2) {
                populateInputs(FORWARD);
                Log.i("inputs", MessageParser.toString(inputs.get(0)));
                for(double[] doubles: inputs) {
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(2)));
                }
                forwardBoolean = false;
                stopBoolean = true;
                inputs = new LinkedList<>();
                activityProgress.setProgress(activityProgress.getProgress() + 20);
                patternProgress.setProgress(0);
                times = 0;
            }else {
                populateInputs(FORWARD);
                forward = new double[INPUT_SIZE];
                times++;
                patternProgress.setProgress(times * 20);
            }
        } else if (stopBoolean) {
            if (times >= 2) {
                populateInputs(STOP);
                Log.i("inputs", MessageParser.toString(inputs.get(0)));
                for(double[] doubles: inputs) {
                    trainingSet.addElement(new SupervisedTrainingElement(doubles, outputs.get(3)));
                }
                stopBoolean = false;
                endBoolean = true;
                inputs = new LinkedList<>();
                activityProgress.setProgress(activityProgress.getProgress() + 20);
                neuralNetwork.learnInNewThread(trainingSet);
                changeText();
                patternProgress.setProgress(0);
                times = 0;
            } else {
                populateInputs(STOP);
                stop = new double[INPUT_SIZE];
                times++;
                patternProgress.setProgress(times * 20);
            }
        }
    }

    public static void changeText(){
        if (baselineBoolean) {
            registerPatternsText.setText("Establishing baseline");
        } else if (leftBoolean) {
            registerPatternsText.setText("Think about going left");
            stopIcon.setVisibility(View.GONE);
            leftIcon.setVisibility(View.VISIBLE);
        } else if (rightBoolean) {
            registerPatternsText.setText("Think about going right");
            leftIcon.setVisibility(View.GONE);
            rightIcon.setVisibility(View.VISIBLE);
        } else if (forwardBoolean) {
            registerPatternsText.setText("Think about going forward");
            rightIcon.setVisibility(View.GONE);
            forwardIcon.setVisibility(View.VISIBLE);
        } else if (stopBoolean) {
            registerPatternsText.setText("Think about stopping");
            forwardIcon.setVisibility(View.GONE);
            stopIcon.setVisibility(View.VISIBLE);
        } else {
            registerPatternsText.setText("Processing");
            stopIcon.setVisibility(View.GONE);
        }
    }
}
