/*

10 February 2017
Author: Adhiraj Datar
ver 1.0-

QuizActivity.java

Java activity file for quiz activity of IceBreaker -
IceBreaker is a MDB member trivia quiz designed to allow new members to quickly
learn more about MDB members from previous semesters.

The quiz activity (homescreen) of icebreaker contains the game (questions+responses)
and outlines most of the application functionality

possible changes: use a scrollview to make quiz playable on horizontal mode. (OR lock app
in vertical mode for play)
Remove quiz activity and replace with quiz fragment (combine 2 current activities)
implement customview to display all quiz info instead of using separate views for options,
images, textviews, etc.

 */

package com.example.datarsd1.icebreaker;

import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private Button b1,b2,b3,b4;
    private TextView t1;
    private String answer;
    private int score;
    private CountDownTimer timer;
    private boolean cheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        answer = "";
        score = 0;
        cheat = getIntent().getExtras().getBoolean("CHEAT");

        String[] members = {"Jessica Cherny", "Kevin Jiang", "Jared Gutierrez", "Kristin Ho", "Christine Munar", "Mudit Mittal", "Richard Hu", "Shaan Appel", "Edward Liu", "Wilbur Shi", "Young Lin", "Abhinav Koppu", "Abhishek Mangla",  "Akkshay Khoslaa", "Ally Koo", "Andy Wang", "Aneesh Jindal", "Anisha Salunkhe", "Aparna Krishnan", "Ashwin Vaidyanathan", "Cody Hsieh", "Jeffrey Zhang", "Justin Kim", "Krishnan Rajiyah", "Lisa Lee", "Peter Schafhalter", "Sahil Lamba", "Sameer Suresh", "Sirjan Kafle", "Tarun Khasnavis", "Billy Lu", "Aayush Tyagi", "Ben Goldberg", "Candice Ye", "Eliot Han", "Emaan Hariri", "Jessica Chen", "Katharine Jiang", "Kedar Thakkar", "Leon Kwak", "Mohit Katyal", "Rochelle Shen", "Sayan Paul", "Sharie Wang", "Shreya Reddy", "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"};
        Arrays.sort(members, String.CASE_INSENSITIVE_ORDER);
        QuestionGenerator.setNames(members);

        b1 = (Button) findViewById(R.id.option1);
        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.option2);
        b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.option3);
        b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.option4);
        b4.setOnClickListener(this);

        ImageButton exitButton = (ImageButton) findViewById(R.id.returnButton);
        exitButton.setOnClickListener(this);

        t1 = (TextView) findViewById(R.id.scoreView);
        t1.setText("Score: "+score);

        final TextView timerText = (TextView) findViewById(R.id.countdown);
        timer = new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timerText.setText("Seconds Remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish()
            {
                Toast.makeText(getApplicationContext(), "The correct answer was " + answer, Toast.LENGTH_LONG).show();
                askQuestion();
            }
        };

        askQuestion();
    }

    protected void askQuestion()
    {
        t1.setText("Score: "+score);
        Question q = QuestionGenerator.makeQuestion();

        b1.setText(q.getOptions()[0]);
        b2.setText(q.getOptions()[1]);
        b3.setText(q.getOptions()[2]);
        b4.setText(q.getOptions()[3]);
        answer = q.getName();

        if (cheat)
        {
            if (b1.getText().toString().equalsIgnoreCase(answer))
                {b1.setBackgroundResource(R.drawable.cheatbuttonshape); b1.setTextColor(getResources().getColor(R.color.buttonColor));}
            else
                {b1.setBackgroundResource(R.drawable.buttonshape); b1.setTextColor(getResources().getColor(R.color.textColor));}

            if (b2.getText().toString().equalsIgnoreCase(answer))
                {b2.setBackgroundResource(R.drawable.cheatbuttonshape); b2.setTextColor(getResources().getColor(R.color.buttonColor));}
            else
                {b2.setBackgroundResource(R.drawable.buttonshape); b2.setTextColor(getResources().getColor(R.color.textColor));}

            if (b3.getText().toString().equalsIgnoreCase(answer))
                {b3.setBackgroundResource(R.drawable.cheatbuttonshape); b3.setTextColor(getResources().getColor(R.color.buttonColor));}
            else
                {b3.setBackgroundResource(R.drawable.buttonshape); b3.setTextColor(getResources().getColor(R.color.textColor));}

            if (b4.getText().toString().equalsIgnoreCase(answer))
                {b4.setBackgroundResource(R.drawable.cheatbuttonshape); b4.setTextColor(getResources().getColor(R.color.buttonColor));}
            else
                {b4.setBackgroundResource(R.drawable.buttonshape); b4.setTextColor(getResources().getColor(R.color.textColor));}
        }

        b1.setText(q.getOptions()[0]);
        b2.setText(q.getOptions()[1]);
        b3.setText(q.getOptions()[2]);
        b4.setText(q.getOptions()[3]);

        int profile = getResources().getIdentifier(answer.toLowerCase().replaceAll("\\s+", ""), "drawable", getPackageName());
        ImageView pic = (ImageView) findViewById(R.id.imageView);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                contactIntent.putExtra(
                        ContactsContract.Intents.Insert.NAME, answer
                );
                startActivity(contactIntent);
            }
        });
        pic.setImageResource(profile);

        timer.start();
    }

    public void onClick(View v)
    {
        timer.cancel();
        score++;
        switch (v.getId())
        {
            case R.id.option1:
                if (!(answer.equalsIgnoreCase(b1.getText().toString())))
                {
                    score--;
                    Toast.makeText(getApplicationContext(), "The correct answer was " + answer, Toast.LENGTH_LONG).show();
                }
                askQuestion();
                break;
            case R.id.option2:
                if (!(answer.equalsIgnoreCase(b2.getText().toString())))
                {
                    score--;
                    Toast.makeText(getApplicationContext(), "The correct answer was " + answer, Toast.LENGTH_LONG).show();
                }
                askQuestion();
                break;
            case R.id.option3:
                if (!(answer.equalsIgnoreCase(b3.getText().toString())))
                {
                    score--;
                    Toast.makeText(getApplicationContext(), "The correct answer was " + answer, Toast.LENGTH_LONG).show();
                }
                askQuestion();
                break;
            case R.id.option4:
                if (!(answer.equalsIgnoreCase(b4.getText().toString())))
                {
                    score--;
                    Toast.makeText(getApplicationContext(), "The correct answer was " + answer, Toast.LENGTH_LONG).show();
                }
                askQuestion();
                break;
            case R.id.returnButton:
                score = 0;
                timer.cancel();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Log.d("hi","hi");
                break;
        }
    }

    protected void onPause()
    {
        super.onPause();
        timer.cancel();
    }

}
