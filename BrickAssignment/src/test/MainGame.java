/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * MainGame.java
 * An class that Initialize and contains physics of the level
 *
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class MainGame {

    final LevelBuilder levelBuilder = new LevelBuilder();

    private Random rnd;
    private Rectangle area;

    private static final int UP_IMPACT = 100;
    private static final int DOWN_IMPACT = 200;
    private static final int LEFT_IMPACT = 300;
    private static final int RIGHT_IMPACT = 400;

    private static final int LEFT = 10;
    private static final int RIGHT = 20;
    private static final int UP = 30;
    private static final int DOWN = 40;


    RubberBall ball;
    Player player;

    private Point startPoint;
    private int ballCount;
    private boolean ballLost;

    /**
     * Constructor For Main Game
     * @param drawArea area of wall
     * @param brickCount brick count of level
     * @param lineCount lines of brick
     * @param brickDimensionRatio brick's dimension
     * @param ballPos position of ball
     */
    public MainGame(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){




        this.startPoint = new Point(ballPos);

        levelBuilder.setLevels(levelBuilder.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio));
        levelBuilder.setLevel(0);

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);

        CreatePlayer(drawArea, ballPos);


    }

    /**
     * Create a player model at position of ball
     * @param drawArea Area of player model
     * @param ballPos position of ball
     */
    private void CreatePlayer(Rectangle drawArea, Point ballPos) {
        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;
    }


    /**
     * make a ball
     * @param ballPos position of ball
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }


    /**
     * InitializeMovement for player and ball
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * Find point of impacts
     */
    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            levelBuilder.setBrickCount(levelBuilder.getBrickCount() - 1);
            GameInterface.ScorePoint();

        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            GameInterface.LosePoint();

            ballCount--;
            ballLost = true;
        }
    }

    /**
     * When ball impacts wall
     * @return SetImpact on brick
     */
    private boolean impactWall(){
        for(Brick b : levelBuilder.getBricks()){
            switch(b.findImpact( ball)) {
                //Vertical Impact
                case UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, UP);
                case DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,DOWN);

                //Horizontal Impact
                case LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,RIGHT);
                case RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,LEFT);
            }
        }
        return false;
    }

    /**
     * When ball impacts border
     * @return x coordinate of impact
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * Get current Brick count
      * @return Brick count
     */
    public int getBrickCount(){
        return levelBuilder.getBrickCount();
    }

    /**
     * Get current Ball count
     * @return Ball count
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * Check if no more balls left
     * @return state of balls
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * Reset ball
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    /**
     * Reset wall
     */
    public void wallReset(){
        for(Brick b : levelBuilder.getBricks())
            b.repair();
        levelBuilder.setBrickCount(levelBuilder.getBricks().length);
        ballCount = 3;
    }

    /**
     * Check if ball count reaches 0
     * @return if ball count reaches 0
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * Check if level is done
     * @return if brick count reaches 0
     */
    public boolean isDone(){
        return levelBuilder.getBrickCount() == 0;
    }

    /**
     * Generate next level
     */
    public void nextLevel(){
        levelBuilder.nextLevel();
    }

    /**
     * Set ball speed on x axis
     * @param s speed
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * Set ball speed on y axis
     * @param t speed
     */
    public void setBallYSpeed(int t){
        ball.setYSpeed(t);
    }

    /**
     * Reset ball count
     */
    public void resetBallCount(){
        ballCount = 3;
    }



}
