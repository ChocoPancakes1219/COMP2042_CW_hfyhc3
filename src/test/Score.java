package test;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileReader;

import static javax.swing.JOptionPane.showMessageDialog;


public class Score {
    private static int HighScore;
    public static int Score;



    public static void AddScore() {
        Score=Score+100;
    }

    public static void DeductScore() {
        Score=Score-500;
    }
    public static void ResetScore() {  Score=0;
    if(Score>HighScore)
    {HighScore=Score;}
    }

    public static int getScore(){
        return Score;
    }
    public static int getHighScore(){
        return HighScore;
    }





}
