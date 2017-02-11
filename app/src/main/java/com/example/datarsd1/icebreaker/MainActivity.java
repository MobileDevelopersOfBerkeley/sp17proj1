/*

10 February 2017
Author: Adhiraj Datar
ver 1.0-

MainActivity.java

Java activity file for main activity of IceBreaker -
IceBreaker is a MDB member trivia quiz designed to allow new members to quickly
learn more about MDB members from previous semesters.

The main activity (homescreen) of icebreaker contains static instructions and
a button to start the quiz along with basic app settings. Sends intent to
QuizActivity.

 */

package com.example.datarsd1.icebreaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox cheat = (CheckBox) findViewById(R.id.checkBox);

        Button start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                boolean enableCheatMode = cheat.isChecked();
                intent.putExtra("CHEAT",enableCheatMode);
                startActivity(intent);
            }
        });
    }



}
