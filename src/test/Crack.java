package test;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

public class Crack {
	 public static final int CRACK_SECTIONS = 3;
     public static final double JUMP_PROBABILITY = 0.7;


     private GeneralPath crack;

     private int crackDepth;
     private int steps;
     
     public Crack(int crackDepth, int steps){

         crack = new GeneralPath();
         this.crackDepth = crackDepth;
         this.steps = steps;

     }


     
     public GeneralPath draw(){
         return crack;
     }
     
     public void reset(){
         crack.reset();
     }
     
     protected void createCrack(Brick b,Point2D point, int direction){
    	 Rectangle bounds = b.brickFace.getBounds();

     Point impact = new Point((int)point.getX(),(int)point.getY());
     Point start = new Point();
     Point end = new Point();


     switch(direction){
         case Direction.LEFT:
             start.setLocation(bounds.x + bounds.width, bounds.y);
             end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
             Point tmp = makeRandomPoint(start,end,Direction.VERTICAL);
             drawCrack(impact,tmp);

             break;
         case Direction.RIGHT:
             start.setLocation(bounds.getLocation());
             end.setLocation(bounds.x, bounds.y + bounds.height);
             tmp = makeRandomPoint(start,end,Direction.VERTICAL);
             drawCrack(impact,tmp);

             break;
         case Direction.UP:
             start.setLocation(bounds.x, bounds.y + bounds.height);
             end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
             tmp = makeRandomPoint(start,end,Direction.HORIZONTAL);
             drawCrack(impact,tmp);
             break;
         case Direction.DOWN:
             start.setLocation(bounds.getLocation());
             end.setLocation(bounds.x + bounds.width, bounds.y);
             tmp = makeRandomPoint(start,end,Direction.HORIZONTAL);
             drawCrack(impact,tmp);

             break;

     }}
     
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

     private int randomInBounds(int bound){
         int n = (bound * 2) + 1;
         return rnd.nextInt(n) - bound;
     }

     private boolean inMiddle(int i,int steps,int divisions){
         int low = (steps / divisions);
         int up = low * (divisions - 1);

         return  (i > low) && (i < up);
     }

     private int jumps(int bound,double probability){

         if(rnd.nextDouble() > probability)
             return randomInBounds(bound);
         return  0;

     }

     public Point makeRandomPoint(Point from,Point to, int direction){

         Point out = new Point();
         int pos;

         switch(direction){
             case Direction.HORIZONTAL:
                 pos = rnd.nextInt(to.x - from.x) + from.x;
                 out.setLocation(pos,to.y);
                 break;
             case Direction.VERTICAL:
                 pos = rnd.nextInt(to.y - from.y) + from.y;
                 out.setLocation(to.x,pos);
                 break;
         }
         return out;
     }

 

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
