package COMP2042_CW_hfyhc3;



import javax.swing.*;


/**
 * Player.java
 * An class that records score of the game
 *
 *
 * Created by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class Score extends JComponent{
    public static int Score;
    public static int[] Leaderboard={0,0,0,0,0};





    /**
     * Addition to score
     */
    public static void AddScore() {
        Score=Score+200;
    }

    /**
     * Deduction from score
     */
    public static void DeductScore() {
        Score=Score-500;
    }

    /**
     * Reset Score
     */
    public static void ResetScore() {
        checkHighScore(Score);
    Score=0;
    }

    /**
     * Get Score
     * @return Score
     */
    public static int getScore(){
        return Score;
    }

    /**
     * Get High Score
     * @return High Score
     */
    public static int getHighScore(){
        return Leaderboard[4];
    }



    /**
     * Check for high score and replace leaderboard
     * display leaderboard with top 5 score stored
     * @param s	Current Score
     */
    public static void checkHighScore(int s){
        if( s > Leaderboard[4]) {

            Leaderboard[4] = s;

            if (s > Leaderboard[3]) {

                Leaderboard[4] = Leaderboard[3];
                Leaderboard[3] = s;

                if (s > Leaderboard[2]) {

                    Leaderboard[3] = Leaderboard[2];
                    Leaderboard[2] = s;

                    if (s > Leaderboard[1]) {

                        Leaderboard[2] = Leaderboard[1];
                        Leaderboard[1] = s;

                        if (s > Leaderboard[0]) {

                            Leaderboard[1] = Leaderboard[0];
                            Leaderboard[0] = s;
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null,"   LeaderBoard \n1. "+Leaderboard[0] + "\n"+" 2. "+Leaderboard[1] + "\n"+" 3. "+Leaderboard[2] + "\n"+" 4. "+Leaderboard[3] + "\n"+" 5. "+Leaderboard[4] + "\n\n"+" Your Score - "+s );

        }



    }







