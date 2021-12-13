package COMP2042_CW_hfyhc3;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Brick.java
 * An abstract class that is the superclass of all Brick classes
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
abstract public class Brick  {




    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;


    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;



    /**
     * Constuctor for Brick
     * @param name Name of Brick
     * @param pos position of brick
     * @param size size of brick
     * @param border border colour of brick
     * @param inner inner fill colour of brick
     * @param Durability durability of brick
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int Durability){
        broken = false;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = Durability;

    }

    /**
     * Seperate identification of each individual bricks
     *
     * @param pos is the position of the brick
     * @param size is the size of the brick
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * When the ball impact the brick
     * @param point is the point of impact
     * @param dir is the direction where the impact comes from
     * @return broken if the brick breaks from the impact
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    /**
     * @return Shape of the brick to be drawn
     */
    public abstract Shape getBrick();


    /**
     * @return Border Colour of the brick
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * @return Inner Colour of the brick
     */
    public Color getInnerColor(){
        return inner;
    }


    /**
     * Find the impact point of the ball on the brick
     * @param b is the info of the ball
     * @return direction where the impact comes from
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     *
     * @return if the brick is broken
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * Reset the state and durability of the brick
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * When impact is done on the brick, reduce its durability
     * A brick is considered broken if durability reaches 0
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }



}
