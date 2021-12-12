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





import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * HomeMeny.java
 * An class that extends JComponent and implements MouseListener and MouseMotionListener
 * Create Interface for main menu
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle quitButton;
    private Rectangle infoButton;


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont= new Font("Noto Mono",Font.PLAIN,25);
    private Font gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
    private Font creditsFont = new Font("Monospaced",Font.PLAIN,10);
    private Font buttonFont;

    private GameFrame owner;
    Image Oribackground;

    private boolean startClicked;
    private boolean quitClicked;
    private boolean infoClicked;




    /**
     * Initializer for main menu
     * @param owner user info
     * @param area area of dimension to create the main menu
     */
    public HomeMenu(GameFrame owner,Dimension area) {



        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;
        try {
            Oribackground = ImageIO.read(new File("src/test/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setLayout( new BorderLayout() );

        drawShape(area);



        createComponents(area);

        drawBorder();

        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);

    }




    /**
     * Draw the border for the window
     */
    private void drawBorder() {
		borderStoke = new BasicStroke(Formatting.BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,Formatting.DASHES,0);
        borderStoke_noDashes = new BasicStroke(Formatting.BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
	}


    /**
     * Create components on the screen
     * @param area dimension of components
     */
    private void createComponents(Dimension area) {
		Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        quitButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
	}


    /**
     *
     * Draw the window
     * @param area dimension of the window
     */
    private void drawShape(Dimension area) {
		menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);
	}


    /**
     * Paint components
     * @param g windows
     */
    public void paint(Graphics g){

        drawMenu((Graphics2D)g);

    }


    /**
     * Draw Main Menu Interface
     * @param g2d windows
     */
    public void drawMenu(Graphics2D g2d){

        drawInterface(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        Image background = Oribackground.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        g2d.drawImage(background, 0, 0, null);
        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * Draw User Interface
     * @param g2d windows
     */
    private void drawInterface(Graphics2D g2d){
        Color prev = g2d.getColor();



        //g2d.setColor(Formatting.MainMenu_BG_COLOR);
        g2d.fill(menuFace);

        Stroke stroke = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(Formatting.DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(Formatting.BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(stroke);

        g2d.setColor(prev);
    }

    /**
     * Print text on main menu screen
     * @param g2d windows
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(Formatting.TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(Formatting.GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(Formatting.GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(Formatting.CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(Formatting.GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(Formatting.GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(Formatting.CREDITS,sX,sY);


    }

    /**
     * Print buttons on the Window
     * @param g2d Window
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(Formatting.START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(Formatting.MENU_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(Formatting.INFO_TEXT,frc);

        g2d.setFont(buttonFont);



        int StartButtonX=(menuFace.width - infoButton.width) / 2;
        int StartButtonY=(int) ((menuFace.height - quitButton.height) * 0.6);
        CreateStartButton(g2d, txtRect, StartButtonX, StartButtonY);

        int InfoButtonX=(menuFace.width - infoButton.width) / 2;
        int InfoButtonY=(int) ((menuFace.height - quitButton.height) * 0.75);
        CreateInfoButton(g2d, iTxtRect,InfoButtonX, InfoButtonY);

        int MenuButtonX=(menuFace.width - infoButton.width) / 2;
        int MenuButtonY=(int) ((menuFace.height - quitButton.height) * 0.9);
        CreateQuitButton(g2d, mTxtRect, MenuButtonX, MenuButtonY);

    }

    /**
     * Create an Info Button
     * @param g2d window
     * @param iTxtRect Shape of button
     * @param InfoButtonX x coordinate of the button
     * @param InfoButtonY y coordinate of the button
     */
    private void CreateInfoButton(Graphics2D g2d, Rectangle2D iTxtRect, int InfoButtonX, int InfoButtonY) {
        int x = InfoButtonX;
        int y = InfoButtonY;
        this.infoButton.setLocation(x,y);
        x = (int)(this.infoButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int)(this.infoButton.getHeight() - iTxtRect.getHeight()) / 2;

        x += this.infoButton.x;
        y += this.infoButton.y + (this.infoButton.height * 0.9);

        if(infoClicked){
            formatInfoButton(g2d, x, y);
        }
        else{
            g2d.draw(this.infoButton);
            g2d.drawString(Formatting.INFO_TEXT,x,y);
        }
    }

    /**
     * Create a Start Button
     * @param g2d window
     * @param txtRect Shape of button
     * @param StartButtonX x coordinate of the button
     * @param StartButtonY y coordinate of the butto
     */
    private void CreateStartButton(Graphics2D g2d, Rectangle2D txtRect, int StartButtonX, int StartButtonY) {
        int x;
        int y;
        x = StartButtonX;
        y = StartButtonY;

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);


        if(startClicked){
            formatStartButton(g2d, x, y);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(Formatting.START_TEXT,x,y);
        }
    }

    /**
     * /**
     * Create a Quit Button
     *
     * @param g2d window
     * @param mTxtRect Shape of button
     * @param QuitButtonX x coordinate of the button
     * @param QuitButtonY y coordinate of the button
     */
    private void CreateQuitButton(Graphics2D g2d, Rectangle2D mTxtRect, int QuitButtonX, int QuitButtonY) {
        int x;
        int y;


        quitButton.setLocation(QuitButtonX,QuitButtonY);

        x = (int)(quitButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(quitButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += quitButton.x;
        y += quitButton.y + (quitButton.height * 0.9);

        if(quitClicked){
            formatQuitButton(g2d, x, y);
        }
        else{
            g2d.draw(quitButton);
            g2d.drawString(Formatting.MENU_TEXT,x,y);
        }
    }


    /**
     * Format Quit button
     * @param g2d window
     * @param x x coordinate of the button
     * @param y y coordinate of the button
     */
    private void formatQuitButton(Graphics2D g2d, int x, int y) {
		Color menucolor = g2d.getColor();

		g2d.setColor(Formatting.CLICKED_BUTTON_COLOR);
		g2d.draw(quitButton);
		g2d.setColor(Formatting.CLICKED_TEXT);
		g2d.drawString(Formatting.MENU_TEXT,x,y);
		g2d.setColor(menucolor);
	}

    /**
     * Format Info button
     * @param g2d window
     * @param x x coordinate of the button
     * @param y y coordinate of the button
     */
    private void formatInfoButton(Graphics2D g2d, int x, int y) {
        Color infocolor = g2d.getColor();

        g2d.setColor(Formatting.CLICKED_BUTTON_COLOR);
        g2d.draw(infoButton);
        g2d.setColor(Formatting.CLICKED_TEXT);
        g2d.drawString(Formatting.INFO_TEXT,x,y);
        g2d.setColor(infocolor);
    }

    /**
     * Format Start button
     * @param g2d window
     * @param x x coordinate of the button
     * @param y y coordinate of the button
     */
	private void formatStartButton(Graphics2D g2d, int x, int y) {
		Color startcolor = g2d.getColor();
		g2d.setColor(Formatting.CLICKED_BUTTON_COLOR);
		g2d.draw(startButton);
		g2d.setColor(Formatting.CLICKED_TEXT);
		g2d.drawString(Formatting.START_TEXT,x,y);
		g2d.setColor(startcolor);
	}







    /**
     *
     * Detects user input
     * @param mouseEvent when mouse clicked
     * detect point of click to see if it overlaps with coordination of button
     * Initialize Game Board when Start Button is Clicked
     * Popup info screen when Info Button is clicked
     * Quit the game and print goodbye to user when Quit Button is clicked
     *
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();

        }
        else if(quitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        else if(infoButton.contains(p)){
            JOptionPane.showMessageDialog(null,"This is a simple game\n\nW & D to move\nSPACE To Pause\nESC To Show Menu\nShift+Alt+F1 to launch Debug Console\n\nYou score points by breaking bricks\nYou lose points and lives when you didn't catch the ball\nYour Goal is to catch the ball until it destroy all the bricks before life runs out.");
        }
    }

    /**
     * detects input from user
     * @param mouseEvent when user mouse is pressed
     * detect point of click to see if it overlaps with coordination of button
     * Paint the button that interacted button to indicate
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(quitButton.contains(p)){
            quitClicked = true;
            repaint(quitButton.x, quitButton.y, quitButton.width+1, quitButton.height+1);
        }
        else if(infoButton.contains(p)){
            infoClicked = true;
            repaint(infoButton.x, infoButton.y, infoButton.width+1, infoButton.height+1);
        }
    }

    /**
     * detects input from user
     * @param mouseEvent when user mouse is released
     * detect point of release to see if it overlaps with coordination of button
     * Revert changes from Mouse Event: when mouse pressed
     * call off execution process if the mouse didn't stay on the same button upon release
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(quitClicked){
            quitClicked = false;
            repaint(quitButton.x, quitButton.y, quitButton.width+1, quitButton.height+1);
        }
        else if(infoClicked){
            infoClicked = false;
            repaint(infoButton.x, infoButton.y, infoButton.width+1, infoButton.height+1);
        }
    }

    /**
     * detects input from user
     * @param mouseEvent when mouse entered the window
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    /**
     * detects input from user
     * @param mouseEvent when mouse exited the window
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * detects input from user
     * @param mouseEvent when mouse dragged the window
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * detects input from user
     * @param mouseEvent when mouse moves on the window
     * Change user cursor to hand cursor when hovering over buttons
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || quitButton.contains(p)||infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }

}
