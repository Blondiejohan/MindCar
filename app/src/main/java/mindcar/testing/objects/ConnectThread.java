package mindcar.testing.objects;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/*
 * Written by Johan and Sarah
 */


public class ConnectThread extends Thread {
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    //BluetoothActivity stuff1 = new BluetoothActivity();
    //protected static final int SUCCESS_CONNECT = 0;

    public ConnectThread(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {}
        mmSocket = tmp;
    }

    public void run() {
       //stuff1.theAdapter.cancelDiscovery();

        try {
            mmSocket.connect();

        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) {}
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //stuff1.mHandler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
    }


    // Will cancel the connection, and close the socket
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {}
    }
}
