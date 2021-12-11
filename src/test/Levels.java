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


public class Levels {

    final LevelGenerator levelGenerator = new LevelGenerator();

    private Random rnd;
    private Rectangle area;

    Ball ball;
    Player player;

    private Point startPoint;
    private int ballCount;
    private boolean ballLost;

    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levelGenerator.setLevels(levelGenerator.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio));
        levelGenerator.setLevel(0);

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

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


    }





    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /*Create Levels*/
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        return levelGenerator.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
    }


    public void move(){
        player.move();
        ball.move();
    }

    /*Look for ball impacts*/
    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(ball.impactWall(this)){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            levelGenerator.setBrickCount(levelGenerator.getBrickCount() - 1);
            Score.AddScore();
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            Score.DeductScore();
            ballLost = true;
        }
    }


    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public int getBrickCount(){
        return levelGenerator.getBrickCount();
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

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

    public void wallReset(){
        for(Brick b : levelGenerator.getBricks())
            b.repair();
        levelGenerator.setBrickCount(levelGenerator.getBricks().length);
        ballCount = 3;
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return levelGenerator.isDone();
    }

    public void nextLevel(){
        levelGenerator.nextLevel();
    }

    public boolean hasLevel(){
        return levelGenerator.hasLevel();
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCount(){
        ballCount = 3;
    }



}
