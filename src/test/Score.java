package test;



public class Score {
    private static int HighScore;
    public static int Score;



    public static void AddScore() {
        Score=Score+100;
    }

    public static void DeductScore() {
        Score=Score-500;
    }
    public static void ResetScore() {
        Score=0;
    }

    public static int getScore(){
        return Score;
    }

    public static void Compare(){
        if (Score> HighScore){
            HighScore =Score;

        }
    }


}
