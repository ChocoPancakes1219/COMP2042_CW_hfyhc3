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
package COMP2042_CW_hfyhc3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.*;

/**
 * GameFrame.java
 * Main class of the application
 * An class that extends JFrame and implements WindowFocusListener
 * Initializer for Home Menu and Game Interface
 * Initialize the window of the application
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */

public class GameFrame extends JFrame implements WindowFocusListener {


    private static final String DEF_TITLE = "Brick Destroy";

    private GameInterface gameInterface;
    private HomeMenu homeMenu;

    private boolean gaming;

    /**
     * Constructor of Main Method
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            try {
                new GameFrame().initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructor for Game Frame
     */
    public GameFrame() throws IOException {
        super();

        gaming = false;

        this.setLayout(new BorderLayout());


        gameInterface = new GameInterface(this);

        homeMenu = new HomeMenu(this,new Dimension(450,300));

        this.add(homeMenu,BorderLayout.CENTER);




        this.setUndecorated(true);



    }






    /**
     * Initialize the main interface
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**\
     * Enable Game Board to be visible and intractable with the user
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameInterface,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    /**
     * Locate the location to initialize the main interface on the screen
      */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }


    /**
     * Detects when user is using the application
     * @param windowEvent when Window is focused
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    /**
     * Detects when user is no longer using the application
     * @param windowEvent when Window lost focus
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameInterface.onLostFocus();

    }
}
