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
package COMP2042_CW_hfyhc3;

import java.awt.*;

/**
 * Player.java
 * An class that set up object: player
 *
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    /**
     * Constructor of Player
     * @param ballPoint point of ball location
     * @param width width of player
     * @param height height of player
     * @param container model of player
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makePlayer(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * Make a player
     * @param width of player
     * @param height of player
     * @return a new rectangle(player)
     */
    private Rectangle makePlayer(int width, int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * if player impacts with balls
     * @param b ball
     * @return if yes
     */
    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    /**
     * Set player movement
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Set player movement direction to left
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * Set player movement direction to right
     */
    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * Stop player movement
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * Get player shape
     * @return player shape
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * Move player location
     * @param p point of destination
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Draw player
     * @param g2d graphics
     */
    void drawPlayer(Graphics2D g2d){
        Color Playercolor = g2d.getColor();

        Shape s = getPlayerFace();
        g2d.setColor(INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(Playercolor);
    }
}
