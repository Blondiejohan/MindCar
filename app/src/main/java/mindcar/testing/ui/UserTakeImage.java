package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import mindcar.testing.R;

/**
 * Created by madiseniman on 03/05/16.
 *
 */



public class UserTakeImage extends Activity{

    public static final int CAMERA_REQUEST = 10;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);

        //Get access to the image view
        imgPhoto = (ImageView) findViewById(R.id.imgPicture);

    }


    public void btnTakePhotoClicked(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode, data);
        //Did the user choose ok? If so, the code inside there curly braces will execute
        if (resultcode == RESULT_OK){
            if(requestcode == CAMERA_REQUEST){
                //we are hearing back from the camera
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                //Now we have the image from the camera
                imgPhoto.setImageBitmap(cameraImage);
            }

        }

    }
}
