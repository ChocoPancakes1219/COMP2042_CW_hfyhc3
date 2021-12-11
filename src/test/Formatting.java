package test;

import java.awt.Color;

public class Formatting {

	//Home Menu Formatting
	static final Color MainMenu_BG_COLOR = Color.GREEN.darker();
	static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
	static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
	static final Color TEXT_COLOR = new Color(16, 52, 166);//egyptian blue
	static final Color CLICKED_BUTTON_COLOR = MainMenu_BG_COLOR.brighter();
	static final Color CLICKED_TEXT = Color.WHITE;
	
	//Home Menu Text
	static final String GREETINGS = "Welcome to:";
	static final String GAME_TITLE = "Brick Destroy";
	static final String CREDITS = "Version 0.1";
	static final String START_TEXT = "Start";
	static final String MENU_TEXT = "Exit";
	static final String INFO_TEXT = "Info";
	
	//Home Menu Border Frame Formatting
	static final int BORDER_SIZE = 5;
	static final float[] DASHES = {12,6};

	//Game Board Formatting
	static final String CONTINUE = "Continue";
	static final String RESTART = "Restart";
	static final String EXIT = "Exit";
	static final String PAUSE = "Pause Menu";
	static final int TEXT_SIZE = 30;
	static final Color MENU_COLOR = new Color(0,255,0);
	static final Color GameBoard_BG_COLOR = Color.WHITE;
	static final int GameBoard_WIDTH = 600;
	static final int GameBoard_HEIGHT = 450;
}
