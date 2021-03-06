package mindcar.testing.objects;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Johan Laptop on 2016-02-29.
 */

/**
 * This class takes a BluetoothActivity Device as input and creates a connection between the phone and the
 * chosen device.
 */
//Johan
public class Connection extends Thread {

    public BluetoothSocket bluetoothSocket;
    private final BluetoothDevice bluetoothDevice;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    /**
     * Constructor for creating a new connection.
     *
     * @param device
     */

    public Connection(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        bluetoothDevice = device;


        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
        }
        bluetoothSocket = tmp;
    }

    // run method that creates a new connected and starts the connection to the socket.
    public void run() {

        Connected connected = new Connected(bluetoothSocket);
        connected.start();

        try {
            bluetoothSocket.connect();

        } catch (IOException connectException) {
            try {
                bluetoothSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }
    }

    // Closes the connection to the socket.
    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
        }
    }
}

