package mindcar.testing.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import mindcar.testing.R;

public class UserImageActivity extends Activity{} //implements View.OnClickListener {

    /*private static final int SELECTED_PICTURE = 1;
    Button chooseImage;

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        chooseImage = (Button) findViewById(R.id.chooseImage);

        iv = (ImageView) findViewById(R.id.imgPicture);
        chooseImage.setOnClickListener(this);

    }

    public void onClick(View v){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex=cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage=BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        iv.setBackground(d);
                    }

                }
                break;
            default:

                break;


        }
    }
}


///////////////////////////////////////////////////////////////////
    //Failed attempts that might still be useful


    public static final int IMAGE_GALLERY_REQUEST = 20;

    private ImageView imgPicture;

    Button chooseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);

        //get a reference to an image view that hold the reference that the user will see
        imgPicture = (ImageView) findViewById(R.id.imgPicture);

        //View.OnClickListener chooseImage;
        //chooseImage = (Button) findViewById(R.id.chooseImage);

        //View.OnClickListener l = chooseImage;
        chooseImage.setOnClickListener(this);

        //onImageGalleryClicked(findViewById(R.id.chooseImage));


    }



    public void onImageGalleryClicked(View v){
        //Invoke image gallery using implicit intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        //Where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        //Get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        //set data and type. Get all image types (image/*)
        photoPickerIntent.setDataAndType(data, "image/*");

        //We will invoke this activity and get something back from it
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            //if we are here, everything processed successfully
            if(requestCode == IMAGE_GALLERY_REQUEST) {
                //if we are here, we are hearing back from the imageGallery

                //the address of the image on the SD card
                Uri imageUri = data.getData();

                //declare a stream to read the image data from the SD card
                InputStream inputStream;

                //we are getting an input stream, based on the URI of the Image
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    //Get a bitmap from the stream
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    //show the image to the user
                    imgPicture.setImageBitmap(image);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //Show an error message in case the image in unavailable
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //@Override
    public void onClick(View v) {

        onImageGalleryClicked(v);

        }

}
*/




