package test;

import java.awt.Point;
import java.awt.Rectangle;
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
	 public static final int CRACK_SECTIONS = 3;
     public static final double JUMP_PROBABILITY = 0.7;


     private GeneralPath crack;

     private int crackDepth;
     private int steps;

    /**
     * Main class of Crack.java
     * @param crackDepth in how deep the crack will be on the brick
     * @param steps is the times where each individual small crack occurs
     */
    public Crack(int crackDepth, int steps){

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
     * @param b is the info of the specific brick
     * @param point is the point of impact with the ball
     * @param direction is the direction that the impact comes from
     */
    protected void createCrack(Brick b,Point2D point, int direction){
    	 Rectangle bounds = b.brickFace.getBounds();

     Point impact = new Point((int)point.getX(),(int)point.getY());
     Point start = new Point();
     Point end = new Point();
         Point CrackPoint;


     switch(direction){
         case Direction.LEFT:
             start.setLocation(bounds.x + bounds.width, bounds.y);
             end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
             CrackPoint = makeRandomPoint(start,end,Direction.VERTICAL);
             drawCrack(impact,CrackPoint);

             break;
         case Direction.RIGHT:
             start.setLocation(bounds.getLocation());
             end.setLocation(bounds.x, bounds.y + bounds.height);
             CrackPoint = makeRandomPoint(start,end,Direction.VERTICAL);
             drawCrack(impact,CrackPoint);

             break;
         case Direction.UP:
             start.setLocation(bounds.x, bounds.y + bounds.height);
             end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
             CrackPoint = makeRandomPoint(start,end,Direction.HORIZONTAL);
             drawCrack(impact,CrackPoint);
             break;
         case Direction.DOWN:
             start.setLocation(bounds.getLocation());
             end.setLocation(bounds.x + bounds.width, bounds.y);
             CrackPoint = makeRandomPoint(start,end,Direction.HORIZONTAL);
             drawCrack(impact,CrackPoint);

             break;

     }}

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

             if(inMiddle(i, steps))
                 y += jumps(jump);

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
         int n = (bound * 2) + 1;
         return rnd.nextInt(n) - bound;
     }

    /**
     * Start division of the crack to random direction
     * @param i section of the crack
     * @param divisions division of the crack
     * @return direction of crack division
     */
    private boolean inMiddle(int i, int divisions){
         int low = (Crack.CRACK_SECTIONS / divisions);
         int up = low * (divisions - 1);

         return  (i > low) && (i < up);
     }

     private int jumps(int bound){

         if(rnd.nextDouble() > Crack.JUMP_PROBABILITY)
             return randomInBounds(bound);
         return  0;

     }

     public Point makeRandomPoint(Point from,Point to, int direction){

         Point out = new Point();
         int pos;

         switch (direction) {
             case Direction.HORIZONTAL -> {
                 pos = rnd.nextInt(to.x - from.x) + from.x;
                 out.setLocation(pos, to.y);
             }
             case Direction.VERTICAL -> {
                 pos = rnd.nextInt(to.y - from.y) + from.y;
                 out.setLocation(to.x, pos);
             }
         }
         return out;
     }


    /**
     * Find the impact point of the ball on the brick
     * @param brick is the info of the brick
     * @param b is the infor of the ball
     * @return direction where the impact comes from
     */
    public final int findImpact(Brick brick, Ball b){
	    if(brick.broken)
	        return 0;
	    int out  = 0;
	    if(brick.brickFace.contains(b.right))
	        out = Direction.LEFT_IMPACT;
	    else if(brick.brickFace.contains(b.left))
	        out = Direction.RIGHT_IMPACT;
	    else if(brick.brickFace.contains(b.up))
	        out = Direction.DOWN_IMPACT;
	    else if(brick.brickFace.contains(b.down))
	        out = Direction.UP_IMPACT;
	    return out;
	}



private Random rnd = new Random();
     
}
