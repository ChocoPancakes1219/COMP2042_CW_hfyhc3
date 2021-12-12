package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


/**
 * CementBrick.java
 * A class that extends Brick
 * To set up the Brick with material : Cement
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 *  * @author Chong Hao Wei
 *  * Date: 12/12/2021
 *
 */
public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color CementBrick_Inner_Color = new Color(147, 147, 147);
    private static final Color CementBrick_Border_Color = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    /**
     * Constructor for Cement brick object
     * @param point is the position of the brick
     * @param size is the size of the brick
     */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size, CementBrick_Border_Color, CementBrick_Inner_Color,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }


    /**
     * Create individual info to identify each brick
     * @param pos is the position of the brick
     * @param size is the size of the brick
     * @return brick info
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     *
     * Make a crack on the brick instead of breaking it on initial impact
     * Upon second impact, brick is considered broken
     *
     * @param point is the point of impact
     * @param dir is the direction where the impact comes from
     * @return true if the brick is broken
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }


    /**
     *
     * @return info of the brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * Update the brick model if a crack is created
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * Reset the brick
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
