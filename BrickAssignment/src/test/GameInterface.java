/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.io.FileWriter;
import java.io.IOException;

import static test.Score.UpdateLeaderboard;


/**
 * GameInterface.java
 * An class that extends JComponent and implements KeyListener,MouseListener and MouseMotionListener
 * Set up the display of Main Game
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class GameInterface extends JComponent implements KeyListener,MouseListener,MouseMotionListener {


    private Timer gameTimer;

    private MainGame mainGame;

    private String message;
    private String message2;

    private boolean showPauseMenu;

    private final Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;


    /**
     * Innitializer for Game Board
     */
    public GameInterface(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;



        menuFont = new Font("Monospaced",Font.PLAIN, Formatting.TEXT_SIZE);


        this.initialize();
        Setup(owner);





    }



    /**
     * Set up the objects in the scene
     * @param owner information of uss\er
     */
    private void Setup(JFrame owner) {
        message = "";
        message2 = "";
        mainGame = new MainGame(new Rectangle(0,0, Formatting.GameBoard_WIDTH, Formatting.GameBoard_HEIGHT),30,3,6/2,new Point(300,430));

        debugConsole = new DebugConsole(owner, mainGame,this);
        mainGame.nextLevel();

        StartGameTimer();
    }

    /**
     * Start the timer of the game
     * Start display and tracking for level completion
     */
    private void StartGameTimer() {
        gameTimer = new Timer(10,e ->{
            mainGame.move();
            mainGame.findImpacts();
            boolean NolivesLeft = mainGame.isBallLost();
            boolean LevelComplete = mainGame.isDone();

            DisplayVariables();

            if(NolivesLeft){
                GameOver();
            }
            else if(LevelComplete){
                CheckNextLevel();
            }

            try {
                FileWriter writer = new FileWriter("leaderboard.txt");
                int len = Score.Leaderboard.length;
                for (int j = 0; j < len; j++) {
                    writer.write(Score.Leaderboard[j] + "\n");

                }
                writer.flush();

            } catch(Exception ex) {
                ex.printStackTrace();
            }

            repaint();
        });
    }

    /**
     * Display method for the main game
     */
    private void DisplayVariables() {

        message = String.format("Bricks: %d Balls: %d", mainGame.getBrickCount(), mainGame.getBallCount());
        message2 = String.format("Score: %d", Score.getScore());

    }

    /**
     * Method when game ends
     */
    private void GameOver() {
        if(mainGame.ballEnd()){
            mainGame.wallReset();
            if(Score.getScore()>Score.getHighScore())
            {   message = "New High Score!";
                message2 = String.format("%d",Score.getHighScore());
            }
            else{
                message = "Game over";
                message2=" ";
            }
            Score.ResetScore();

        }

        mainGame.ballReset();
        gameTimer.stop();
    }

    /**
     * Check if this is the last level
     */
    private void CheckNextLevel() {
        if(mainGame.levelBuilder.hasLevel()){
            NextLevel();
        }
        else{
            FinishGame();
        }
    }

    /**
     * Ends the whole game
     */
    private void FinishGame() {
        message = "ALL WALLS DESTROYED";
        message2 = String.format("%d",Score.getScore());
        gameTimer.stop();
    }

    /**
     * Switch to next level
     */
    private void NextLevel() {
        message = "Go to Next Level";
        gameTimer.stop();
        mainGame.ballReset();
        mainGame.wallReset();
        mainGame.nextLevel();
    }


    /**
     * Initialize sensors
     */
    private void initialize(){
        this.setPreferredSize(new Dimension(Formatting.GameBoard_WIDTH, Formatting.GameBoard_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    /**
     * draw the graphics
     * @param g sprite
     */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);
        g2d.drawString(message2,250,235);

        drawBall(mainGame.ball,g2d);

        for(Brick b : mainGame.levelBuilder.getBricks())
            if(!b.isBroken())
                drawBrick(b,g2d);

        mainGame.player.drawPlayer(g2d);

        if(showPauseMenu)
            CallMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Reset the sprite
     * @param g2d sprite info
     */
    private void clear(Graphics2D g2d){
        Color resetcolor = g2d.getColor();
        g2d.setColor(Formatting.GameBoard_BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(resetcolor);
    }

    /**
     * Create a brick object
     * @param brick info of brick type
     * @param g2d sprite
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color brickcolor = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(brickcolor);
    }

    /**
     * Create a brick object
     * @param ball info of ball type
     * @param g2d sprite
     */
    private void drawBall(Ball ball,Graphics2D g2d){
        Color ballcolor = g2d.getColor();

        Shape s = ball.getBallpath();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(ballcolor);
    }

    /**
     * Call pause menu
     * @param g2d sprite
     */
    private void CallMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * Change the backgroud when pausemenu is present
     * @param g2d sprite
     */
    private void obscureGameBoard(Graphics2D g2d){

        Composite tmpcomposite = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0, Formatting.GameBoard_WIDTH, Formatting.GameBoard_HEIGHT);

        g2d.setComposite(tmpcomposite);
        g2d.setColor(tmpColor);
    }

    /**
     * Draw pause menu
     * @param g2d sprite
     */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(Formatting.MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(Formatting.PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(Formatting.PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            CreateContinueButton(x, y, frc);
        }

        g2d.drawString(Formatting.CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            CreateRestartButton(x, y);
        }

        g2d.drawString(Formatting.RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            CreateExitButton(x, y);
        }

        g2d.drawString(Formatting.EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * Create the button for Continue
     * @param x position on x axis
     * @param y position on y axis
     * @param frc font
     */
    private void CreateContinueButton(int x, int y, FontRenderContext frc) {
        continueButtonRect = menuFont.getStringBounds(Formatting.CONTINUE, frc).getBounds();
        continueButtonRect.setLocation(x, y -continueButtonRect.height);
    }

    /**
     * Create the button for Restart using info of Continue button
     * @param x position on x axis
     * @param y position on y axis
     *
     */
    private void CreateRestartButton(int x, int y) {
        restartButtonRect = (Rectangle) continueButtonRect.clone();
        restartButtonRect.setLocation(x, y -restartButtonRect.height);
    }

    /**
     * Create the button for Exit using info of Continue button
     * @param x position on x axis
     * @param y position on y axis
     *
     */
    private void CreateExitButton(int x, int y) {
        exitButtonRect = (Rectangle) continueButtonRect.clone();
        exitButtonRect.setLocation(x, y -exitButtonRect.height);
    }


    /**
     * Sensor to detect input from keyboard
     * @param keyEvent key typed by suer
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * Sensor to detect input from keyboard
     * Detect the key that user pressed and acts accordingly
     * @param keyEvent key pressed by suer
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                mainGame.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                mainGame.player.movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                mainGame.player.stop();
        }
    }

    /**
     * Sensor to detect input from keyboard
     * Stop player movement when user no longer holds the movement key
     * @param keyEvent when user stop holding the key
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        mainGame.player.stop();
    }


    /**
     * Sensor to detect user input from mouse
     * Check the point of click of user to see if user is interacting with buttons and act accordingly
     * @param mouseEvent when mouse is clicked
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            message2 = " ";
            mainGame.ballReset();
            mainGame.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }

    }

    /**
     * Sensor to detect user input from mouse
     * @param mouseEvent when mouse is pressed and hold
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
    /**
     * Sensor to detect user input from mouse
     * @param mouseEvent when mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /**
     * Sensor to detect user input from mouse
     * @param mouseEvent when cursor entered the application screen
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    /**
     * Sensor to detect user input from mouse
     * @param mouseEvent when cursor exited the application screen
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * Sensor to detect user input from mouse
     * @param mouseEvent when cursor is dragging
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * Sensor to detect user input from mouse
     * @param mouseEvent when is moving
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Sensor to detect when user switch to a different application
     */
    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        message2 = " ";
        repaint();
    }

}