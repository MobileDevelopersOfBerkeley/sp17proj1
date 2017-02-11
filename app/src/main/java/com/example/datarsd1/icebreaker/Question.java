package com.example.datarsd1.icebreaker;

import android.media.Image;

/*

10 February 2017
Author: Adhiraj Datar
ver 1.0-

Question.java

Basic java class modelling an IceBreaker quiz question. Includes
fields for name (answer) and randomly generated options.

*/

public class Question {
    private String name;
    private String[] options;

    public Question(String n, String[] opt)
    {
        name = n;
        options = opt;
    }

    public String getName()
    {
        return name;
    }

    public String[] getOptions()
    {
        return options;
    }

}
