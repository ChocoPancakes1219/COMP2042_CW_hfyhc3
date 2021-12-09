package test;

import java.awt.geom.GeneralPath;

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
     
     
     
     
}
