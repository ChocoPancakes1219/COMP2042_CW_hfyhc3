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
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * SteelBrick.java
 * A class that extends Brick
 * To set up the Brick with material : Steel
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 *  * @author Chong Hao Wei
 *  * Date: 12/12/2021
 *
 */
public class SteelBrick extends Brick {

    private static final String NAME = "Steel Brick";
    private static final Color SteelBrick_Inner_Color = new Color(203, 203, 201);
    private static final Color SteelBrick_Border_Color = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    /**
     * Constructor of Steel Brick object
     * @param point coordinate of initialization
     * @param size size of brick
     */
    public SteelBrick(Point point, Dimension size){
        super(NAME,point,size, SteelBrick_Border_Color, SteelBrick_Inner_Color,STEEL_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }


    /**
     * Make shape of brick
      * @param pos is the position of the brick
     * @param size is the size of the brick
     * @return shape
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * Get info of brick shape
     * @return shape
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * Set the impact physics of steel brick
     * @param point is the point of impact
     * @param dir is the direction where the impact comes from
     * @return
     */
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

    /**
     * Game physics when steel brick is impacted by ball
     * random chance of the brick to no break
     */
    public void impact(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }

}
