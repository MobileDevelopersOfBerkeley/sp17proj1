package com.example.sarahtang.memberquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.lang.Math;
import org.w3c.dom.Text;

public class OtherActivity extends AppCompatActivity implements View.OnClickListener {
    private String currMember;
    private int score;
    private CountDownTimer countdowntimer;
    String[] members = {"Jessica Cherny", "Kevin Jiang",
            "Jared Gutierrez", "Kristin Ho",
            "Christine Munar", "Mudit Mittal",
            "Richard Hu", "Shaan Appel",
            "Edward Liu", "Wilbur Shi",
            "Young Lin", "Abhinav Koppu",
            "Abhishek Mangla", "Akkshay Khoslaa",
            "Andy Wang", "Aneesh Jindal",
            "Anisha Salunkhe", "Ashwin Vaidyanathan",
            "Cody Hsieh", "Justin Kim",
            "Krishnan Rajiyah", "Lisa Lee",
            "Peter Schafhalter", "Sahil Lamba",
            "Sirjan Kafle", "Tarun Khasnavis",
            "Billy Lu", "Aayush Tyagi",
            "Ben Goldberg", "Candice Ye",
            "Eliot Han", "Emaan Hariri",
            "Jessica Chen", "Katharine Jiang",
            "Kedar Thakkar", "Leon Kwak",
            "Mohit Katyal", "Rochelle Shen",
            "Sayan Paul", "Sharie Wang", "Shreya Reddy",
            "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"};
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button endGame;
    private ImageView memberPic;

    public void onClick (View v) {
        //based on what button text set to
        if (((Button)v).getText().equals(currMember)) {
            //Call next member - got it right so continue
            score++;
            TextView score = (TextView) findViewById(R.id.score);
            ((Button)v).setBackgroundColor(Color.parseColor("#006600"));
            score.setText("Score: " + this.score);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    nextMember();
                }
            }, 500);
        }
        else {
            if (v.getId() == button2.getId()) {
                button2.setBackgroundColor(Color.RED);
            }
            if (v.getId() == button3.getId()) {
                button3.setBackgroundColor(Color.RED);
            }
            if (v.getId() == button4.getId()) {
                button4.setBackgroundColor(Color.RED);
            }
            if (v.getId() == button5.getId()) {
                button5.setBackgroundColor(Color.RED);
            }
            Toast.makeText(this, "WRONG!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        endGame = (Button) findViewById(R.id.endGame);
        memberPic = (ImageView) findViewById(R.id.memberPic);
        getSupportActionBar().hide();

        memberPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addContactIntent = new Intent(Intent.ACTION_INSERT);
                addContactIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME,currMember.toString());
                startActivity(addContactIntent);
            }
        });

        final TextView timer = (TextView) findViewById(R.id.timer);

        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        endGame.setOnClickListener(this);

        countdowntimer = new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                nextMember();
                //timer.setText("done!");
            }
        };
        countdowntimer.start();
        nextMember();


        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtherActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null);

                // Create the AlertDialog object and return it
                builder.show();
            }
        });

    }
    private void nextMember() {
        //calling for picture, each turn
        String[] options = getOptions();
        button2.setText(options[0]);
        button3.setText(options[1]);
        button4.setText(options[2]);
        button5.setText(options[3]);
        button2.setBackgroundColor(Color.parseColor("#fdfffc"));
        button3.setBackgroundColor(Color.parseColor("#fdfffc"));
        button4.setBackgroundColor(Color.parseColor("#fdfffc"));
        button5.setBackgroundColor(Color.parseColor("#fdfffc"));
        int currMemberIndex = (int) (Math.random() * options.length);
        currMember = options[currMemberIndex];
        String imgName = currMember.toLowerCase().replace(" ", "");
        int id = getResources().getIdentifier(imgName,
                "drawable", getPackageName());
        memberPic.setImageResource(id);
        countdowntimer.start();
    }

    private String[] getOptions() {
        //Four button names things
        String[] options = new String[4];
        for (int i = 0; i < options.length; i++) {
            while (options[i] == null) {
                int random = (int) (Math.random() * members.length);
                if (!containsMember(members[random], options)) {
                    options[i] = members[random];
                }
            }
        }
        return options;
    }

    private boolean containsMember(String member, String[] options) {
        //Does this contain member
        for (int i = 0; i < options.length; i++) {
            if (options[i] != null && options[i].equals(member)) {
                return true;
            }
        }
        return false;
    }

}
