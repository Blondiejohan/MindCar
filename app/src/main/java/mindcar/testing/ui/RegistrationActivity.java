package mindcar.testing.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.R;

public class RegistrationActivity extends Activity implements View.OnClickListener {

    Button bRegister;
    EditText ET_USER_NAME, ET_PASS;
    String user_name, user_pass;
    //nikos
    Button userSettings;


    //Madisen's
        //Choosing and taking the Image
    private static final int SELECTED_PICTURE = 1;
    Button chooseImage, takePhoto;
    public static final int CAMERA_REQUEST = 1313;
    ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASS = (EditText) findViewById(R.id.ET_PASS);
        bRegister = (Button) findViewById(R.id.bRegister);

        //nikos
        userSettings = (Button) findViewById(R.id.userSettings);

        bRegister.setOnClickListener(this);

        //Madisen's shit for choosing a photo
            //Instantiate button, image view and listen for a button click.
        chooseImage = (Button) findViewById(R.id.chooseImage);

        chooseImage.setOnClickListener(this);

        //Madisen's shit for taking a new photo
            //Instantiate button, image view and listen for a button click.
        takePhoto = (Button) findViewById(R.id.takePhoto);
        imgPhoto = (ImageView) findViewById(R.id.imgPicture);
        takePhoto.setOnClickListener(this);
    }

    public void userReg(View view) {
        user_name = ET_USER_NAME.getText().toString();
        user_pass = ET_PASS.getText().toString();

    }

    @Override
    public void onClick(View v) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        switch (v.getId()) {
            case R.id.bRegister:
                if (databaseAccess.checkAvailability(ET_USER_NAME.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Welcome " + ET_USER_NAME.getText().toString() + " " + ET_PASS.getText().toString(), Toast.LENGTH_SHORT).show();
                    databaseAccess.addRegistration(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString());
                    //databaseAccess.close();
                    startActivity(new Intent(this, StartActivity.class));
                } else
                    Toast.makeText(getApplicationContext(), "Username not available", Toast.LENGTH_SHORT).show();
                break;
            //Madisen's shit
                //If the button chooseImage is clicked, access external data and go to next method
            case R.id.chooseImage:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
                break;
                //if takePhoto button is clicked, open the camera.
            case R.id.takePhoto:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            //nikos
            case R.id.userSettings:


        }

    }
    //Madisen's shit
        //Method for telling the takePhoto and chooseImage buttons what to do.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //The identifier between buttons is the request code.
        switch (requestCode) {
            //if you want to select a picture and you successfully do so...
            //the photo you select will be stored in a drawable file and the
            //background of the ImageView will be set to the picture you choose.
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        imgPhoto.setBackground(d);
                    }

                }
                break;
            //if you choose to select a photo and successfully do so..
            //The Image view will be set to the picture you take
            case CAMERA_REQUEST:
                System.out.println("we got to the take photo request");
                if (resultCode == RESULT_OK){
                    System.out.println("The result = ok");
                    //if(requestCode == CAMERA_REQUEST){
                        //we are hearing back from the camera
                        Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                        //Now we have the image from the camera
                        imgPhoto.setImageBitmap(cameraImage);
                    //}

                }
                else Toast.makeText(getApplicationContext(), "Picture was not successfully chosen", Toast.LENGTH_LONG);

                break;


        }
    }
}