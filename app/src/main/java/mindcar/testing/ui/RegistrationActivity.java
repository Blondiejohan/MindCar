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
import java.io.ByteArrayOutputStream;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.R;

//Sanja & Madisen
    //This handles registration of new users
public class RegistrationActivity extends Activity implements View.OnClickListener {

    //Sanja
        //Connection to the layout xml for the registration of new users
    Button bRegister;
    EditText ET_USER_NAME, ET_PASS;
    static String user_name, user_pass;


    //Madisen
        //Choosing and taking the Image
    private static final int SELECTED_PICTURE = 1;
    Button chooseImage, takePhoto;
    public static final int CAMERA_REQUEST = 1313;
    ImageView imgPhoto;
    public static byte[] myPic, chosenPic, takenPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASS = (EditText) findViewById(R.id.ET_PASS);
        bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);

        //Madisen's code for choosing a photo
            //Instantiate button, image view and listen for a button click.
        chooseImage = (Button) findViewById(R.id.chooseImage);
        chooseImage.setOnClickListener(this);

        //Madisen's code for taking a new photo
             //Instantiate button, image view and listen for a button click.
        takePhoto = (Button) findViewById(R.id.takePhoto);
        imgPhoto = (ImageView) findViewById(R.id.imgPicture);
        takePhoto.setOnClickListener(this);
    }
        //Sanja
            //Variables for extracting user name and password from the input fields
    public void userReg(View view) {
        user_name = ET_USER_NAME.getText().toString();
        user_pass = ET_PASS.getText().toString();

    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        myPic = stream.toByteArray();
        return myPic;
    }

    //Madisen
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
                    else Toast.makeText(getApplicationContext(),"Your Android SDK version is not recent enough, please upgrade your software and try again.", Toast.LENGTH_LONG).show();
                    //Store image as byte for later saving it into the database
                    chosenPic = getBytes(yourSelectedImage);
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
                    takenPic = getBytes(cameraImage);
                }
                else Toast.makeText(getApplicationContext(), "Picture was not successfully chosen", Toast.LENGTH_LONG).show();

                break;


        }
    }
    //Sanja & Madisen
    // Registration handling
    @Override
    public void onClick(View v) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        switch (v.getId()) {
            case R.id.bRegister:
                if (databaseAccess.checkAvailability(ET_USER_NAME.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Welcome " + ET_USER_NAME.getText().toString() + " " + ET_PASS.getText().toString(), Toast.LENGTH_SHORT).show();
                   if (takenPic != null){
                                //databaseAccess.addPhoto(takenPic);
                        databaseAccess.addRegistration(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString(), myPic);
                        }
                    if (chosenPic != null){
                        //databaseAccess.addPhoto(chosenPic);
                        databaseAccess.addRegistration(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString(), myPic);

                    }
                    else if ((takenPic == null) || (chosenPic == null)){
                        databaseAccess.addRegistration(ET_USER_NAME.getText().toString(), ET_PASS.getText().toString(), null);
                    }
                    //Remember username and password to avoid re login
                    StartActivity.un = ET_USER_NAME.getText().toString();
                    StartActivity.pw = ET_PASS.getText().toString();

                    startActivity(new Intent(this, RegisterPatternActivity.class));
                } else
                    Toast.makeText(getApplicationContext(), "Username not available", Toast.LENGTH_SHORT).show();
                break;
            //Madisen
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

        }

    }
}