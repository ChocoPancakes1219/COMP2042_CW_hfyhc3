package test;

import java.awt.*;
import java.awt.Point;


/**
 * Created by filippo on 04/09/16.
 *
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color ClayBrick_Inner_Color = new Color(178, 34, 34).darker();
    private static final Color ClayBrick_Border_Color = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;






    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size, ClayBrick_Border_Color, ClayBrick_Inner_Color,CLAY_STRENGTH);
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
