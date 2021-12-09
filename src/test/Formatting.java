package test;

import java.awt.Color;

public class Formatting {

	static final Color BG_COLOR = Color.GREEN.darker();
	static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
	static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
	static final Color TEXT_COLOR = new Color(16, 52, 166);//egyptian blue
	static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
	static final Color CLICKED_TEXT = Color.WHITE;
	
	static final String GREETINGS = "Welcome to:";
	static final String GAME_TITLE = "Brick Destroy";
	static final String CREDITS = "Version 0.1";
	static final String START_TEXT = "Start";
	static final String MENU_TEXT = "Exit";
	
	static final int BORDER_SIZE = 5;
	static final float[] DASHES = {12,6};

}
