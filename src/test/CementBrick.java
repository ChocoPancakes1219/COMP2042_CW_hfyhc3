package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color CementBrick_Inner_Color = new Color(147, 147, 147);
    private static final Color CementBrick_Border_Color = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    public CementBrick(Point point, Dimension size){
        super(NAME,point,size, CementBrick_Border_Color, CementBrick_Inner_Color,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

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


    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
