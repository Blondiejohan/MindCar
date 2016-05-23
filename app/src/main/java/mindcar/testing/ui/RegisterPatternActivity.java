package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
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
import java.io.RandomAccessFile;
import java.util.LinkedList;

import mindcar.testing.R;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;
import mindcar.testing.util.NeuralNetworkHelper;

public class RegisterPatternActivity extends AppCompatActivity implements View.OnClickListener {

    private double[] baseline;
    private double[] left;
    private double[] right;
    private double[] forward;
    private double[] stop;

    private final int INPUT_SIZE = 800;
    private final int OUTPUT_SIZE = 4;


    private LinkedList<double[]> inputs = new LinkedList<>();
    private LinkedList<double[]> outputs = new LinkedList<>();

    private DatabaseAccess databaseAccess;

    private Pattern tmpPattern;
    private Eeg tmpEeg;

    private TGDevice tgDevice;
    private TrainingSet trainingSet;
    private NeuralNetwork neuralNetwork;

    private int times;
    private TextView registerPatternsText;
    private Button registerPatternReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pattern);

        times = 0;

        databaseAccess = DatabaseAccess.getInstance(this);

        populateOutputs();

        initializeArrays();

        trainingSet = new TrainingSet(INPUT_SIZE, OUTPUT_SIZE);
        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.STEP, INPUT_SIZE, 400, OUTPUT_SIZE);
        neuralNetwork.setLearningRule(new MomentumBackpropagation());
        neuralNetwork.setLabel(RegistrationActivity.user_name);

        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
        if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
            tgDevice.connect(true);
        }

        registerPatternsText = (TextView) findViewById(R.id.registerPatternText);

        registerPatternReady = (Button) findViewById(R.id.registerPatternReady);
        registerPatternReady.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        tgDevice.start();
        registerPatternReady.setEnabled(false);
    }


    public final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case TGDevice.MSG_STATE_CHANGE:
                    switch (msg.arg1) {
                        case TGDevice.STATE_CONNECTED:
                            tgDevice.start();
                            Log.i("wave", "Connecting");
                            break;
                    }
                    break;

                case TGDevice.MSG_RAW_DATA:
                    MessageParser.parseRawData(msg, tmpEeg);
                    tmpPattern.add(tmpEeg);
                    if (tmpPattern.isFull()) {

                        if (!isFull(baseline)) {
                            registerPatternsText.setText("Establishing baseline /n please relax");
                            populateArray(baseline);

                        } else if (!isFull(left)) {
                            registerPatternsText.setText("Think about going left /n now saving");
                            populateArray(left);

                        } else if (!isFull(right)) {
                            registerPatternsText.setText("Think about going right /n now saving");
                            populateArray(right);

                        } else if (!isFull(forward)) {
                            registerPatternsText.setText("Think about going forward /n now saving");
                            populateArray(forward);

                        } else if (!isFull(stop)) {
                            registerPatternsText.setText("Think about stopping /n now saving");
                            populateArray(stop);

                        } else {
                            populateInputs();
                            extendTrainingSet();
                            neuralNetwork.learnInNewThread(trainingSet);

                            if (times < 5) {
                                initializeArrays();
                                inputs = new LinkedList<>();
                                times++;
                            } else {
                                try {

                                    RandomAccessFile nnet = new RandomAccessFile(RegistrationActivity.user_name + ".nnet", "r");
                                    byte[] b = new byte[(int)nnet.length()];
                                    nnet.read(b);

                                    databaseAccess.open();
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("neuralnetwork", b);
                                    databaseAccess.update("Users", contentValues, RegistrationActivity.user_name);
                                    databaseAccess.close();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
            }
        }
    };

    private void initializeArrays() {
        baseline = new double[INPUT_SIZE];
        left = new double[INPUT_SIZE];
        right = new double[INPUT_SIZE];
        forward = new double[INPUT_SIZE];
        stop = new double[INPUT_SIZE];
    }

    private void extendTrainingSet() {
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

    private void populateArray(double[] array) {
        array = tmpPattern.toArray();
        tmpPattern = new Pattern();
    }

    public void populateInputs() {
        inputs.add(left);
        inputs.add(right);
        inputs.add(forward);
        inputs.add(stop);

        inputs.add(baseline);
    }

    private void populateOutputs() {
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

    private boolean isFull(double[] array) {
        if (array[INPUT_SIZE - 1] != 0) {
            return true;
        }
        return false;
    }

}
