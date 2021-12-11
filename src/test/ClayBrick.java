package test;

import java.awt.*;
import java.awt.Point;


/**
 * Created by filippo on 04/09/16.
 *
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final int CLAY_STRENGTH = 1;






    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size, Formatting.CLAY_BRICK_BORDER, Formatting.CLAY_BRICK_INNER,CLAY_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
