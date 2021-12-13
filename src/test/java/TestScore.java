import COMP2042_CW_hfyhc3.Score;

import static org.junit.jupiter.api.Assertions.*;

class TestScore {

    /**
     * Test for Score.AddScore() function
     */
    @org.junit.jupiter.api.Test
    void addScore() {
        int ATestScore= Score.getScore();
        Score.AddScore();
        boolean b = (ATestScore<=(Score.getScore()));
        assertTrue(b);
    }

    /**
     * Test for Score.DeductScore() function
     */
    @org.junit.jupiter.api.Test
    void deductScore() {
        int DTestScore= Score.getScore();
        Score.DeductScore();
        System.out.print(DTestScore);
        boolean b = (DTestScore>=(Score.getScore()));
        assertTrue(b);
    }

    /**
     * Test for Score.ResetScore() function
     */
    @org.junit.jupiter.api.Test
    void resetScore() {
        Score.ResetScore();
        boolean b = (0==(Score.getScore()));
        assertTrue(b);

    }

    /**
     * Test for Score.getHighScore() function
     */
    @org.junit.jupiter.api.Test
    void getHighScore() {
        boolean b = (Score.getHighScore()==(Score.Leaderboard[4]));
        assertTrue(b);

    }



}