package com.demo.mdb.intentsworksheet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Part 1: These five questions will create an Intent that will open the OtherActivity when
        // a button is pressed.
        //Question 1: create a button in the layout file. Do not delete the "Hello World" TextView.
        //Question 2: create the Button object below. How do you find it tho? if only there was a
        // way to find a view by its ID...

        /* your code here */
        Button button = (Button) findViewById(R.id.button);
        // R links Java to XML
        //Question 3: find a way for the Button to LISTEN ON the CLICKs. Use Google for guidance.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Question 4: every time the button is clicked, create and execute an Intent to go to the
                // OtherClass.
                Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
                startActivity(intent); // pauses current activity and starts the otheractivity
            }
        });


        /* your code here */


        //Part 3: create an Intent that has a button that opens the camera app, takes a picture, and
        // displays the picture. Note: this requires a line in the manifest, but that comes later,
        // so I hooked yall up nbd

        //Question 10: Create the button in the layout and add the listener with the Intent. What
        // does the Intent look like? If you're not opening a class, then what do you pass in?
        // Google it bb

        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1); // the latter is the resultCode
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Question 9: Every time this Activity is resumed, set the text of the TextView to the extra
        // value from the intent you got from OtherActivity.
        String newText = getIntent().getStringExtra("Input");
        if (newText != null) {
            ((TextView) findViewById(R.id.textView)).setText(newText); // handles case when you havent switched
            // to anotehr activity and set a newText yet
        }
    }
    // oncreate, onstart, onresume all run at the beginning

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ImageView.setImageBitmap(imageBitmap);
            //Question 11: retrieve the result from the intent. Do not use get Intent, use the parameter
            // data.

            /* your code here */

            //Question 12: Create a Bitmap object and set it to the data from the Intent. Use the key
            // "data" to retrieve the Bitmap.

            /* your code here */

            //Question 13: create an imageView in the layout
            //Question 14: set the imageView to the Bitmap

            /* your code here */
        }
    }
}

