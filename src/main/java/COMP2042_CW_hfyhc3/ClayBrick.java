package COMP2042_CW_hfyhc3;

import java.awt.*;
import java.awt.Point;


/**
 * CementBrick.java
 * A class that extends Brick
 * To set up the Brick with material : Clay
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 *  * @author Chong Hao Wei
 *  * Date: 12/12/2021
 *
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color ClayBrick_Inner_Color = new Color(178, 34, 34).darker();
    private static final Color ClayBrick_Border_Color = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;


    /**
     * Constructor for Clay brick object
     * @param point is the position of the brick
     * @param size is the size of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size, ClayBrick_Border_Color, ClayBrick_Inner_Color,CLAY_STRENGTH);
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
     * @return info of the brick
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
