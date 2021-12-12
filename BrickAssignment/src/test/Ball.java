package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;


 /**
 * Ball.java
 * An abstract class that is the superclass of all Ball classes
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
abstract public class Ball {

    private Shape Ballpath;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;


     /**
      * Constructor for Game Object Ball
      * @param center is the center point of the ball
      * @param radiusA is the distance from ball center to border in x axis
      * @param radiusB is the distance from ball center to border in y axis
      * @param inner is the inner colour fill for the ball
      * @param border is the outer colour on the ball border
      */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        SetBallLocation(center, radiusA, radiusB);


        Ballpath = drawBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

     /**
      * Set up the parameters point of the ball
      *
      * @param center is the center point of the ball
      * @param radiusA is the distance from ball center to border in x axis
      * @param radiusB is the distance from ball center to border in y axis
      *
      */
    private void SetBallLocation(Point2D center, int radiusA, int radiusB) {
        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(), center.getY()-(radiusB / 2));
        down.setLocation(center.getX(), center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2), center.getY());
        right.setLocation(center.getX()+(radiusA /2), center.getY());
    }



     /**
      * Draw The Ball Object
      * @param center center of point
      * @param radiusA is the distance from ball center to border in x axis
      * @param radiusB is the distance from ball center to border in y axis
      * @return the ball
      */
     protected abstract Shape drawBall(Point2D center, int radiusA, int radiusB);


     /**
      * Controls the movement of the ball
      */

     public void move(){
        RectangularShape path = (RectangularShape) Ballpath;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = path.getWidth();
        double h = path.getHeight();

        path.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        Ballpath = path;
    }

     /**
      * Change the speed and direction of the ball
      * @param xSpeed is the movement on x axis
      * @param ySpeed is the movement on y axis
      */
    public void setSpeed(int xSpeed,int ySpeed){
        speedX = xSpeed;
        speedY = ySpeed;
    }

     /**
      * Change the ball movement in only x axis
      * @param SpeedX speed
      */
    public void setXSpeed(int SpeedX){
        speedX = SpeedX;
    }

     /**
      * Change the ball movement in only y axis
      * @param SpeedY speed
      */
    public void setYSpeed(int SpeedY){
        speedY = SpeedY;
    }

     /**
      * Reverse ball movement in only x axis
      */
    public void reverseX(){
        speedX *= -1;
    }

     /**
      * Reverse ball movement in only y axis
      */
    public void reverseY(){
        speedY *= -1;
    }


     /**
      * @return Border Colour of the ball
      */
    public Color getBorderColor(){
        return border;
    }

     /**
      * @return Inner Colour of the ball
      */
    public Color getInnerColor(){
        return inner;
    }


     /**
      * @return Center point coordinates of the ball
      */
    public Point2D getPosition(){
        return center;
    }

     /**
      *
      * @return the path of the ball
      */
    public Shape getBallpath(){
        return Ballpath;
    }

     /**
      * Set a point where the ball will move to
      * @param p is the point of destination
      */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape movepath = (RectangularShape) Ballpath;
        double w = movepath.getWidth();
        double h = movepath.getHeight();

        movepath.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        Ballpath = movepath;
    }

     /**
      * Set the pathway to coordinate the ball
      * @param width is the distance of the ball to the goal on x axis
      * @param height is the distance of the ball to the goal on y axis
      */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

     /**
      *
      * @return the ball direction and speed on x axis
      */
    public int getSpeedX(){
        return speedX;
    }

     /**
      *
      * @return the ball direction and speed on y axis
      */
    public int getSpeedY(){
        return speedY;
    }



}
