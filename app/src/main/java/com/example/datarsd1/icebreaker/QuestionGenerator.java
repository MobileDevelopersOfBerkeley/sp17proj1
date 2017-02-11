package com.example.datarsd1.icebreaker;


import java.util.Arrays;

/*

10 February 2017
Author: Adhiraj Datar
ver 1.0-

QuestionGenerator.java

Static Java class that uses input of a string of MDB member
names to generate Question class objects that are used in the
QuizActivity to show questions to the user.

*/

public class QuestionGenerator {

    private static String[] names;

    public static void setNames(String[] n)
    {
        names = n;
    }

//    public static void setImages(int[] i)
//    {
//        images = i;
//    }

    public static Question makeQuestion()
    {
        String name = names[(int)(Math.random()*names.length)];
        int answer = (int)(Math.random()*4);

        String[] options = new String[4];
        for (int i = 0; i < 4; i++)
        {
            int randomAnswer = (int)(Math.random()*names.length);
            while (names[randomAnswer].equals(name) || Arrays.asList(options).contains(names[randomAnswer]) || names[randomAnswer] == null || names[randomAnswer].trim().equals(""))
                randomAnswer = (int)(Math.random()*names.length);
            options[i] = names[randomAnswer];
        }

        options[answer] = name;
        return new Question(name, options);
    }

}
