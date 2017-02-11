package com.example.eric.memberquiz;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    CountDownTimer timer, timer2;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button endButton;
    TextView score;
    TextView countdownText;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String[] members = {"Jessica Cherny", "Kevin Jiang", "Jared Gutierrez", "Kristin Ho", "Christine Munar", "Mudit Mittal", "Richard Hu", "Shaan Appel", "Edward Liu", "Wilbur Shi", "Young Lin", "Abhinav Koppu", "Abhishek Mangla", "Akkshay Khoslaa", "Andy Wang", "Aneesh Jindal", "Anisha Salunkhe", "Ashwin Vaidyanathan", "Cody Hsieh", "Justin Kim", "Krishnan Rajiyah", "Lisa Lee", "Peter Schafhalter", "Sahil Lamba", "Sirjan Kafle", "Tarun Khasnavis", "Billy Lu", "Aayush Tyagi", "Ben Goldberg", "Candice Ye", "Eliot Han", "Emaan Hariri", "Jessica Chen", "Katharine Jiang", "Kedar Thakkar", "Leon Kwak", "Mohit Katyal", "Rochelle Shen", "Sayan Paul", "Sharie Wang", "Shreya Reddy", "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"};
        ArrayList<String>image_names = new ArrayList<>();
        Field[] drawables = com.example.eric.memberquiz.R.drawable.class.getFields();
        for (Field f : drawables) {
            try {
                String name = f.getName();
                if (!name.contains("_") && !name.contains("$") && !name.equals("serialVersionUID")){
                    System.out.println(name);
                    image_names.add(name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(image_names.size());

        // Get random image
        Random rand = new Random();
        int rand_img_num = rand.nextInt(44);
        String rand_img = image_names.get(rand_img_num);

        // Setting the random image
        ImageView image = (ImageView) findViewById(R.id.imageView);
        int id = getApplicationContext().getResources().getIdentifier("drawable/"+rand_img, null, getApplicationContext().getPackageName());
        image.setImageResource(id);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        endButton = (Button) findViewById(R.id.endButton);
        score = (TextView) findViewById(R.id.score);
        countdownText = (TextView) findViewById(R.id.countdownText);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        int new_score = pref.getInt("score", 0);
        score.setText(Integer.toString(new_score));

        Button[]answer_buttons = new Button[4];
        answer_buttons[0] = button1;
        answer_buttons[1] = button2;
        answer_buttons[2] = button3;
        answer_buttons[3] = button4;

        //Clear All Button Text
        for(int i = 0; i < answer_buttons.length; i++){
            answer_buttons[i].setText("");
        }

        int rand_but_num = rand.nextInt(4);


        //Gets other 3 names randomly
        int rand_name1 = rand.nextInt(44);
        while (rand_name1 == rand_img_num){
            rand_name1 = rand.nextInt(44);
        }
        String wrong_name1 = image_names.get(rand_name1);

        int rand_name2 = rand.nextInt(44);
        while (rand_name2 == rand_img_num || rand_name2 == rand_name1){
            rand_name2 = rand.nextInt(44);
        }
        String wrong_name2 = image_names.get(rand_name2);

        int rand_name3 = rand.nextInt(44);
        while (rand_name3 == rand_img_num || rand_name3 == rand_name1 || rand_name3 == rand_name2){
            rand_name3 = rand.nextInt(44);
        }
        String wrong_name3 = image_names.get(rand_name3);


        // Gets properly formatted name of the Image
        for(int i = 0; i < members.length; i++){
            if(members[i].replaceAll("\\s+","").equalsIgnoreCase(rand_img)){
                rand_img = members[i];
            }
            if(members[i].replaceAll("\\s+","").equalsIgnoreCase(wrong_name1)){
                wrong_name1 = members[i];
            }
            if(members[i].replaceAll("\\s+","").equalsIgnoreCase(wrong_name2)){
                wrong_name2 = members[i];
            }
            if(members[i].replaceAll("\\s+","").equalsIgnoreCase(wrong_name3)){
                wrong_name3 = members[i];
            }
        }

        String[]button_options = new String[3];
        button_options[0] = wrong_name1;
        button_options[1] = wrong_name2;
        button_options[2] = wrong_name3;

        // Set Button Texts
        answer_buttons[rand_but_num].setText(rand_img);
        for(int i = 0, j = 0; i < answer_buttons.length && j < button_options.length; i++){
            if (answer_buttons[i].getText() == ""){
                answer_buttons[i].setText(button_options[j]);
                j++;
            }
        }

        //Count Down Timer
        timer = new CountDownTimer(5500, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownText.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownText.setText("Times Up!");
                if (countdownText.getText().toString().equals("Times Up!")) {
                    finish();
                    startActivity(getIntent());
                }
            }
        }.start();

        // Create Contact when clicking on the image
        final String contactName = rand_img;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName);
                timer.cancel();
                if (timer2 != null){
                    timer2.cancel();
                }
                startActivity(intent);
            }
        });

        //Button Click Listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = button1.getText().toString();
                if (!buttonText.equalsIgnoreCase(contactName)){
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    // SharedPrefs Object to save score
                    int prefScore = pref.getInt("score", 0) + 1; // getting last score from the SharedPrefs, if there is no previous score it sets it to 0
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("score", prefScore); // Storing the new user score
                    editor.apply(); //saving the changes
                }
                timer.cancel();
                finish();
                startActivity(getIntent());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = button2.getText().toString();
                if (!buttonText.equalsIgnoreCase(contactName)){
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    // SharedPrefs Object to save score
                    int prefScore = pref.getInt("score", 0) + 1; // getting last score from the SharedPrefs, if there is no previous score it sets it to 0
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("score", prefScore); // Storing the new user score
                    editor.apply(); //saving the changes
                }
                timer.cancel();
                finish();
                startActivity(getIntent());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = button3.getText().toString();
                if (!buttonText.equalsIgnoreCase(contactName)){
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    // SharedPrefs Object to save score
                    int prefScore = pref.getInt("score", 0) + 1; // getting last score from the SharedPrefs, if there is no previous score it sets it to 0
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("score", prefScore); // Storing the new user score
                    editor.apply(); //saving the changes
                }
                timer.cancel();
                finish();
                startActivity(getIntent());
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = button4.getText().toString();
                if (!buttonText.equalsIgnoreCase(contactName)){
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    // SharedPrefs Object to save score
                    int prefScore = pref.getInt("score", 0) + 1; // getting last score from the SharedPrefs, if there is no previous score it sets it to 0
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("score", prefScore); // Storing the new user score
                    editor.apply(); //saving the changes
                }
                timer.cancel();
                finish();
                startActivity(getIntent());
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
                alertDialog.setTitle("End Game");
                alertDialog.setMessage("Are you sure you want to quit?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("score", 0);
                                editor.apply();
                                finish();
                                timer.cancel();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            }
                        });
                alertDialog.show();

            }
        });
    }

    @Override
    public void onStop(){
        timer.cancel();
        super.onStop();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        timer2 = new CountDownTimer(5500, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownText.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownText.setText("Times Up!");
                if (countdownText.getText().toString().equals("Times Up!")) {
                    finish();
                    startActivity(getIntent());
                }
            }
        }.start();
    }
}
