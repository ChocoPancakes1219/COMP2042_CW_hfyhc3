package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

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

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    private int crackDepth;
    private int steps;
    
    Crack crack = new Crack(crackDepth,steps);


    /**
     * Call createcrack method in crack.java
     * @param point is the point of impact
     * @param direction is the direction to create the crack
     */
   protected void makeCrack(Point2D point, int direction)
   {
       crack.createCrack(Brick.this,point,direction);
    }



    private String name;
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int durability;

    public boolean broken;

    private static Random rnd;

    /**
     * Main class that takes in all the info for the brick
     *
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int durability){
        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.durability = durability;

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
     * @return Info of the brick to be drawn
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
        durability = fullStrength;
    }

    /**
     * When impact is done on the brick, reduce its durability
     * A brick is considered broken if durability reaches 0
     */
    public void impact(){
        durability--;
        broken = (durability == 0);
    }



}

