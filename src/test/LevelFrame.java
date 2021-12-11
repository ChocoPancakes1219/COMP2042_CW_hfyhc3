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


public class LevelFrame {

    final WallMaker wallMaker = new WallMaker();

    private Random rnd;
    private Rectangle area;

    Ball ball;
    Player player;

    private Point startPoint;
    private int ballCount;
    private boolean ballLost;

    public LevelFrame(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        wallMaker.setLevels(wallMaker.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio));
        wallMaker.setLevel(0);

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

    private void CreatePlayer(Rectangle drawArea, Point ballPos) {
        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;
    }


    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }



    public void move(){
        player.move();
        ball.move();
    }

    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            wallMaker.setBrickCount(wallMaker.getBrickCount() - 1);
            Score.AddScore();
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            Score.DeductScore();
            ballCount--;
            ballLost = true;
        }
    }

    private boolean impactWall(){
        for(Brick b : wallMaker.getBricks()){
            switch(b.crack.findImpact(b, ball)) {
                //Vertical Impact
                case Direction.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, Direction.UP);
                case Direction.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Direction.DOWN);

                //Horizontal Impact
                case Direction.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Direction.RIGHT);
                case Direction.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Direction.LEFT);
            }
        }
        return false;
    }

    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public int getBrickCount(){
        return wallMaker.getBrickCount();
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
        for(Brick b : wallMaker.getBricks())
            b.repair();
        wallMaker.setBrickCount(wallMaker.getBricks().length);
        ballCount = 3;
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return wallMaker.getBrickCount() == 0;
    }

    public void nextLevel(){
        wallMaker.nextLevel();
    }

    public boolean hasLevel(){
        return wallMaker.getLevel() < wallMaker.getLevels().length;
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
