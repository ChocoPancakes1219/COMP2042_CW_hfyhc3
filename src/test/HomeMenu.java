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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.JOptionPane;

//Main Class for the Main Menu Interface
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

    private boolean startClicked;
    private boolean quitClicked;
    private boolean infoClicked;



    //Create Home Menu
    public HomeMenu(GameFrame owner,Dimension area){

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;



        drawShape(area);

        createComponents(area);

        drawBorder();
        
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);

    }


	private void drawBorder() {
		borderStoke = new BasicStroke(Formatting.BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,Formatting.DASHES,0);
        borderStoke_noDashes = new BasicStroke(Formatting.BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
	}


	private void createComponents(Dimension area) {
		Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        quitButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
	}


	private void drawShape(Dimension area) {
		menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);
	}


    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }


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

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    //Drawing User Interface
    private void drawInterface(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(Formatting.MainMenu_BG_COLOR);
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
    
    //Printing out Text
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
    
    //Printing out Buttons
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
        CreateInfoButton(g2d, iTxtRect, InfoButtonX, InfoButtonY);

        int MenuButtonX=(menuFace.width - infoButton.width) / 2;
        int MenuButtonY=(int) ((menuFace.height - quitButton.height) * 0.9);
        CreateMenuButton(g2d, mTxtRect, MenuButtonX, MenuButtonY);

    }

    private void CreateInfoButton(Graphics2D g2d, Rectangle2D iTxtRect, int InfoButtonX, int InfoButtonY) {
        int x = InfoButtonX;
        int y = InfoButtonY;
        infoButton.setLocation(x,y);
        x = (int)(infoButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - iTxtRect.getHeight()) / 2;

        x += infoButton.x;
        y += infoButton.y + (infoButton.height * 0.9);

        if(infoClicked){
            formatInfoButton(g2d, x, y);
        }
        else{
            g2d.draw(infoButton);
            g2d.drawString(Formatting.INFO_TEXT,x,y);
        }
    }

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

    private void CreateMenuButton(Graphics2D g2d, Rectangle2D mTxtRect, int MenuButtonX, int MenuButtonY) {
        int x;
        int y;


        quitButton.setLocation(MenuButtonX,MenuButtonY);

        x = (int)(quitButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(quitButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += quitButton.x;
        y += quitButton.y + (quitButton.height * 0.9);

        if(quitClicked){
            formatMenuButton(g2d, x, y);
        }
        else{
            g2d.draw(quitButton);
            g2d.drawString(Formatting.MENU_TEXT,x,y);
        }
    }


    private void formatMenuButton(Graphics2D g2d, int x, int y) {
		Color menucolor = g2d.getColor();

		g2d.setColor(Formatting.CLICKED_BUTTON_COLOR);
		g2d.draw(quitButton);
		g2d.setColor(Formatting.CLICKED_TEXT);
		g2d.drawString(Formatting.MENU_TEXT,x,y);
		g2d.setColor(menucolor);
	}

    private void formatInfoButton(Graphics2D g2d, int x, int y) {
        Color infocolor = g2d.getColor();

        g2d.setColor(Formatting.CLICKED_BUTTON_COLOR);
        g2d.draw(infoButton);
        g2d.setColor(Formatting.CLICKED_TEXT);
        g2d.drawString(Formatting.INFO_TEXT,x,y);
        g2d.setColor(infocolor);
    }


	private void formatStartButton(Graphics2D g2d, int x, int y) {
		Color startcolor = g2d.getColor();
		g2d.setColor(Formatting.CLICKED_BUTTON_COLOR);
		g2d.draw(startButton);
		g2d.setColor(Formatting.CLICKED_TEXT);
		g2d.drawString(Formatting.START_TEXT,x,y);
		g2d.setColor(startcolor);
	}
	
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
            JOptionPane.showMessageDialog(null,"This is a simple game\nW & D to move\nSPACE To Pause\nESC To Show Menu\nYour Goal is to catch the ball until it destroy all the bricks.");
        }
    }

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
        if(startButton.contains(p) || quitButton.contains(p)||infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
