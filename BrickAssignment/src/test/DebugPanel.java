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
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * DebugPanel.java
 * An class that controls over variables in the game
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;


    private JButton skipLevel;
    private JButton resetBalls;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    private MainGame mainGame;

    /**
     * Main class for DebugPanel.java
     * @param mainGame info of current level
     */
    public DebugPanel(MainGame mainGame){

        this.mainGame = mainGame;

        InitializeInterface();

        CreateComponents(mainGame);

    }

    /**
     * Create Buttons and slider for the interface
     * @param mainGame info of current level
     */
    private void CreateComponents(MainGame mainGame) {
        skipLevel = makeButton("Skip Level",e -> mainGame.nextLevel());
        resetBalls = makeButton("Reset Balls",e -> mainGame.resetBallCount());

        ballXSpeed = makeSlider(-4,4,e -> mainGame.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(-4,4,e -> mainGame.setBallYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);
    }

    /**
     * Create the base interface of the Panel
     */
    private void InitializeInterface(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * Make a button based in info given
     * @param title Title of button
     * @param e actions of the button when interacted
     * @return out
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    /**
     * Make a slider based in info given
     * @param min minimum int for the variable
     * @param max maximum int for the variable
     * @param e Actions effect when interacted with the slider
     * @return out
     */
    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * Set values of ball speed & direction
     * @param x ball direction and speed on x axis
     * @param y ball direction and speed on y axis
     */
    public void setValues(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
