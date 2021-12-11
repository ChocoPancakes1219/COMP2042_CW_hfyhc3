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



public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {


    private Timer gameTimer;

    private LevelFrame levelFrame;

    private String message;
    private String message2;

    private boolean showPauseMenu;

    private final Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;


    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;



        menuFont = new Font("Monospaced",Font.PLAIN, Formatting.TEXT_SIZE);


        this.initialize();
        Setup(owner);

    }

    private void Setup(JFrame owner) {
        message = "";
        message2 = "";
        levelFrame = new LevelFrame(new Rectangle(0,0, Formatting.GameBoard_WIDTH, Formatting.GameBoard_HEIGHT),30,3,6/2,new Point(300,430));

        debugConsole = new DebugConsole(owner, levelFrame,this);
        levelFrame.nextLevel();

        StartGameTimer();
    }

    private void StartGameTimer() {
        gameTimer = new Timer(10,e ->{
            levelFrame.move();
            levelFrame.findImpacts();
            boolean NolivesLeft = levelFrame.isBallLost();
            boolean LevelComplete = levelFrame.isDone();

            DisplayLivesAndBrickCount();

            if(NolivesLeft){
                GameOver();
            }
            else if(LevelComplete){
                CheckNextLevel();
            }

            repaint();
        });
    }

    private void DisplayLivesAndBrickCount() {

        message = String.format("Bricks: %d Balls: %d", levelFrame.getBrickCount(), levelFrame.getBallCount());
        message2 = String.format("Score: %d High Score : %d", Score.getScore(),Score.getHighScore());

    }

    private void GameOver() {
        if(levelFrame.ballEnd()){
            levelFrame.wallReset();
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

        levelFrame.ballReset();
        gameTimer.stop();
    }

    private void CheckNextLevel() {
        if(levelFrame.hasLevel()){
            NextLevel();
        }
        else{
            FinishGame();
        }
    }

    private void FinishGame() {
        message = "ALL WALLS DESTROYED";
        message2 = String.format("%d",Score.getScore());
        gameTimer.stop();
    }

    private void NextLevel() {
        message = "Go to Next Level";
        gameTimer.stop();
        levelFrame.ballReset();
        levelFrame.wallReset();
        levelFrame.nextLevel();
    }


    private void initialize(){
        this.setPreferredSize(new Dimension(Formatting.GameBoard_WIDTH, Formatting.GameBoard_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);
        g2d.drawString(message2,250,235);

        drawBall(levelFrame.ball,g2d);

        for(Brick b : levelFrame.wallMaker.getBricks())
            if(!b.isBroken())
                drawBrick(b,g2d);

        levelFrame.player.drawPlayer(g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d){
        Color resetcolor = g2d.getColor();
        g2d.setColor(Formatting.GameBoard_BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(resetcolor);
    }

    private void drawBrick(Brick brick,Graphics2D g2d){
        Color brickcolor = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(brickcolor);
    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color ballcolor = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(ballcolor);
    }

    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

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

    private void CreateContinueButton(int x, int y, FontRenderContext frc) {
        continueButtonRect = menuFont.getStringBounds(Formatting.CONTINUE, frc).getBounds();
        continueButtonRect.setLocation(x, y -continueButtonRect.height);
    }

    private void CreateRestartButton(int x, int y) {
        restartButtonRect = (Rectangle) continueButtonRect.clone();
        restartButtonRect.setLocation(x, y -restartButtonRect.height);
    }

    private void CreateExitButton(int x, int y) {
        exitButtonRect = (Rectangle) continueButtonRect.clone();
        exitButtonRect.setLocation(x, y -exitButtonRect.height);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                levelFrame.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                levelFrame.player.movRight();
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
                levelFrame.player.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        levelFrame.player.stop();
    }

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
            levelFrame.ballReset();
            levelFrame.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

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

    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        message2 = " ";
        repaint();
    }

}