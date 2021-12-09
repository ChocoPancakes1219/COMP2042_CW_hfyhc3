package test;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class Crack {
	 public final int CRACK_SECTIONS = 3;
     public final double JUMP_PROBABILITY = 0.7;

     public final int LEFT = 10;
     public final int RIGHT = 20;
     public final int UP = 30;
     public final int DOWN = 40;
     public final int VERTICAL = 100;
     public final int HORIZONTAL = 200;



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
             case HORIZONTAL:
                 pos = rnd.nextInt(to.x - from.x) + from.x;
                 out.setLocation(pos,to.y);
                 break;
             case VERTICAL:
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
	        out = Brick.LEFT_IMPACT;
	    else if(brick.brickFace.contains(b.left))
	        out = Brick.RIGHT_IMPACT;
	    else if(brick.brickFace.contains(b.up))
	        out = Brick.DOWN_IMPACT;
	    else if(brick.brickFace.contains(b.down))
	        out = Brick.UP_IMPACT;
	    return out;
	}



private static Random rnd = new Random();
     
}
