package mindcar.testing.objects;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Johan Laptop on 2016-02-29.
 */

public class Connected extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private final InputStream bluetoothInStream;
    static OutputStream bluetoothOutStream;


    // Constructor for creating a new connected object.
    /**
     * Constructor for creating a new connected object.
     *
     * @param socket
     */
    public Connected(BluetoothSocket socket) {
        bluetoothSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }
        bluetoothInStream = tmpIn;
        bluetoothOutStream = tmpOut;
    }

    // Dont know how mutch of this method is needed. Might need to clean it up.
    // Right now it seems to read answers from the device.
    public void run() {
        byte[] buffer = new byte[1024];
        int begin = 0;
        int bytes = 0;
        while (true) {
            try {
                bytes += bluetoothInStream.read(buffer, bytes, buffer.length - bytes);
                for (int i = begin; i < bytes; i++) {
                    if (buffer[i] == "#".getBytes()[0]) {
                        // mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                        begin = i + 1;
                        if (i == bytes - 1) {
                            bytes = 0;
                            begin = 0;
                        }
                    }
                }
            } catch (IOException e) {
                break;
            }
        }
    }




    /**
     * Sends an array of bytes through the outstream to the chosen device.
     *
     * @param message
     */
    public static void write(String message) {
        try {
            byte[] bytes = message.getBytes();
            bluetoothOutStream.write(bytes);
        } catch (IOException e) {
            Log.i("this", e.getMessage());
        }
    }

    // Closes the socket.
    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
        }
    }
}
