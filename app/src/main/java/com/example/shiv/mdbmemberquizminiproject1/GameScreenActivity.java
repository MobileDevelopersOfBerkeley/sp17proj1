package com.example.shiv.mdbmemberquizminiproject1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameScreenActivity extends AppCompatActivity {

    final Context context = this;
    private Button correctButton;
    private static CountDownTimer timer;
    private int score = 0;
    //public String[] members = {"Jessica Cherny", "Jared Gutierrez", "Kristin Ho", "Christine Munar", "Mudit Mittal", "Richard Hu", "Shaan Appel", "Edward Liu", "Wilbur Shi", "Young Lin", "Abhinav Koppu", "Abhishek Mangla",  "Akkshay Khoslaa", "Ally Koo", "Andy Wang", "Aneesh Jindal", "Anisha Salunkhe", "Aparna Krishnan", "Ashwin Vaidyanathan", "Cody Hsieh", "Jeffrey Zhang", "Justin Kim", "Krishnan Rajiyah", "Lisa Lee", "Peter Schafhalter", "Sahil Lamba", "Sameer Suresh", "Sirjan Kafle", "Tarun Khasnavis", "Billy Lu", "Aayush Tyagi", "Ben Goldberg", "Candice Ye", "Eliot Han", "Emaan Hariri", "Jessica Chen", "Katharine Jiang", "Kedar Thakkar", "Leon Kwak", "Mohit Katyal", "Rochelle Shen", "Sayan Paul", "Sharie Wang", "Shreya Reddy", "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"};
    public String[] members = {"Jessica Cherny", "Kevin Jiang", "Jared Gutierrez", "Kristin Ho", "Christine Munar", "Mudit Mittal", "Richard Hu", "Shaan Appel", "Edward Liu", "Wilbur Shi", "Young Lin", "Abhinav Koppu", "Abhishek Mangla", "Akkshay Khoslaa", "Andy Wang", "Aneesh Jindal", "Anisha Salunkhe", "Ashwin Vaidyanathan", "Cody Hsieh", "Jeffrey Zhang", "Justin Kim", "Krishnan Rajiyah", "Lisa Lee", "Peter Schafhalter", "Sahil Lamba", "Sameer Suresh", "Sirjan Kafle", "Tarun Khasnavis", "Billy Lu", "Aayush Tyagi", "Ben Goldberg", "Candice Ye", "Eliot Han", "Emaan Hariri", "Jessica Chen", "Katharine Jiang", "Kedar Thakkar", "Leon Kwak", "Mohit Katyal", "Rochelle Shen", "Sayan Paul", "Sharie Wang", "Shreya Reddy", "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        reset();

        cycleGame();


    }

    private void reset() {

        if (timer != null) {
            timer.cancel();
        }
        timer = null;

        Button button1 = (Button) findViewById(R.id.button2);
        Button button2 = (Button) findViewById(R.id.button3);
        Button button3 = (Button) findViewById(R.id.button4);
        Button button4 = (Button) findViewById(R.id.button5);
        Button endGame = (Button) findViewById(R.id.button6);
        //ImageView img= (ImageView) findViewById(R.id.imageView3);
        ImageButton imgbutton = (ImageButton) findViewById(R.id.imageButton2);

        TextView scoreText = (TextView)findViewById(R.id.textView);
        scoreText.setText(Integer.toString(score));

        final TextView countdown = (TextView)findViewById(R.id.textView2);
        //Log.v("value", "" + R.drawable.younglin);

        //Initialize Defaults, let button 2 be answer initially




        int option1, option2, option3;

        option1 = getRandomMemberNumber();
        button1.setText(members[option1]);

        option2 = getRandomMemberNumber();
        while (option2 == option1) {
            option2 = getRandomMemberNumber();
        }
        button3.setText(members[option2]);

        option3 = getRandomMemberNumber();
        while (option3 == option2 || option3 == option1) {
            option3 = getRandomMemberNumber();
        }
        button4.setText(members[option3]);

        int option4 = getRandomMemberNumber();
        while (option4 == option2 || option4 == option1 || option4 == option3) {
            option4 = getRandomMemberNumber();
        }
        button2.setText(members[option4]);

        final int answer;
        double x = Math.random();
        if (x < 0.25) {
            correctButton = button1;
            answer = option1;
        } else if (x >= 0.25 && x < 0.50)
        {
            correctButton = button2;
            answer = option4;
        } else if (x >= 0.50 && x < 0.75) {
            correctButton = button3;
            answer = option2;
        } else {
            correctButton = button4;
            answer = option3;
        }

        String str = members[answer].replaceAll("\\s+","");
        str = str.toLowerCase();
        int id = getImageId(context, str);
        imgbutton.setImageResource(id);
        //img.setImageResource(id);

        timer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdown.setText("Time is up");
                reset();
            }
        };
        timer.start();

        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, members[answer]);
                context.startActivity(intent);


            }
        });

        cycleGame();
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    private int getRandomMemberNumber() {
        return 0 + (int)(Math.random() * ((members.length - 1 + 0) + 1));
    }



    private void cycleGame() {

        final Button button1 = (Button) findViewById(R.id.button2);
        final Button button2 = (Button) findViewById(R.id.button3);
        final Button button3 = (Button) findViewById(R.id.button4);
        final Button button4 = (Button) findViewById(R.id.button5);
        final ImageButton imgbutton = (ImageButton) findViewById(R.id.imageButton2);

        Button endGame = (Button) findViewById(R.id.button6);





        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (correctButton != button1) {
                    Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    reset();
                }
                else {
                    score++;
                    reset();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (correctButton != button2) {
                    Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    reset();
                }
                else {
                    score++;
                    reset();
                }


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (correctButton != button3) {
                    Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    reset();
                }
                else {
                    score++;
                    reset();
                }

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (correctButton != button4) {
                    Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    reset();
                }
                else {
                    score++;
                    reset();
                }

            }
        });

        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to end this game?");

                builder.setPositiveButton("End Game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });




                AlertDialog adialog = builder.create();
                adialog.show();

            }
        });

    }
}
