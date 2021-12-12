package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Crack.java
 * A class that stores the behavior of the crack
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class Crack {

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    private static final int LEFT = 10;
    private static final int RIGHT = 20;
    private static final int UP = 30;
    private static final int DOWN = 40;

    private static final int VERTICAL = 100;
    private static final int HORIZONTAL = 200;

    private final Brick brick;
    private GeneralPath crack;

    private int crackDepth;
    private int steps;


    /**
     * Constructor for Crack
     * @param brick info of brick
     * @param crackDepth depth of crack
     * @param steps points of crack seperate
     */
    public Crack(Brick brick, int crackDepth, int steps){
        this.brick = brick;

        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;

    }


    /**
     *
     * @return path of the crack
     */
    public GeneralPath draw(){

        return crack;
    }

    /**
     * Reset all cracks on a brick
     */
    public void reset(){
        crack.reset();
    }

    /**
     * Create crack on a brick
     * @param point is the point of impact with the ball
     * @param direction is the direction that the impact comes from
     */
    protected void makeCrack(Point2D point, int direction){
        Rectangle bounds = brick.brickFace.getBounds();


        Point impact = new Point((int)point.getX(),(int)point.getY());
        Point start = new Point();
        Point end = new Point();


        switch(direction){
            case LEFT:
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Point Randompoint = makeRandomPoint(start,end,VERTICAL);
                drawCrack(impact,Randompoint);

                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                Randompoint = makeRandomPoint(start,end,VERTICAL);
                drawCrack(impact,Randompoint);

                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Randompoint = makeRandomPoint(start,end,HORIZONTAL);
                drawCrack(impact,Randompoint);
                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                Randompoint = makeRandomPoint(start,end,HORIZONTAL);
                drawCrack(impact,Randompoint);

                break;

        }
    }

    /**
     * Draw crack on the brick
     * @param start start point of the crack
     * @param end end point of the crack
     */
    protected void drawCrack(Point start, Point end){

        GeneralPath path = new GeneralPath();


        path.moveTo(start.x,start.y);

        double w = (end.x - start.x) / (double)steps;
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < steps;i++){

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if(inMiddle(i,CRACK_SECTIONS,steps))
                y += jumps(jump,JUMP_PROBABILITY);

            path.lineTo(x,y);

        }

        path.lineTo(end.x,end.y);
        crack.append(path,true);
    }

    /**
     * Random point where the crack extends in other direction
     * @param bound bound of the crack
     * @return the random integer generated
     */
    private int randomInBounds(int bound){

        Random randombound = new Random();
        int n = (bound * 2) + 1;
        return randombound.nextInt(n) - bound;
    }

    /**
     * Start division of the crack to random direction
     * @param i section of the crack
     * @param divisions division of the crack
     * @return direction of crack division
     */
    private boolean inMiddle(int i,int steps,int divisions){
        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    /**
     * potential point to extend crack
     * @param bound jump point
     * @param probability probability of jumps
     * @return
     */
    private int jumps(int bound,double probability){

        Random randomdouble=new Random();
        if(randomdouble.nextDouble() > probability)
            return randomInBounds(bound);
        return  0;

    }

    /**
     * Create random line of cracks
     * @param from start location
     * @param to end location
     * @param direction direction of extension
     * @return new location
     */
    private Point makeRandomPoint(Point from,Point to, int direction){
        Random randompos = new Random();
        Point out = new Point();
        int pos;

        switch(direction){

            case HORIZONTAL:
                pos = randompos.nextInt(to.x - from.x) + from.x;
                out.setLocation(pos,to.y);
                break;
            case VERTICAL:
                pos = randompos.nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x,pos);
                break;
        }
        return out;
    }


}
