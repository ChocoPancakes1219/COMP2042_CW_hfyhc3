## Notable Changes

1. Added meaningful javadocs to all classes and methods
2. Removed Unecessary Classes
	- Graphics Main was only used as a main class and have no other functions
		- Removed GraphicsMain and use Game Frame as the Main Class
2. Renamed some class and variables
	- Previous name does not suit the fields
		- For example, Menu Button for Main Menu is confusing, thus renamed to "Quit Button" to specify its functionality
		- Another example, GameFrame and GameBoard is confusing thus renamed to Game Interface and MainGame
3. Refactored the code
	- Extracted method from large methods 
		- Clearer explanations on how code works
		- Seperate Funtionality of method
	- Removed/Renamed all temporary variables
		- Prevent error when multiple tmp variable are changed at the same time
		- Better description of the function of the variable
		- For example, Random rnd used to generate crack has been seperated and renamed to: Randompoint, randombound and random pos
		- All the "tmp" variables has been renamed and seperated according to their own function
	- Moving variables to different class for easier maintainance
		- Brick variable is under BrickMaker for easier reference. 
		- Moving variables to each of their corresponding class when splitting a big class
4. Seperated Some of the classes so each classes only performs a specific function
	- Previous classes contains multiple functions
		-For Example, GameBoard was used to initialize Main Game Interface, create levels and handles the game physics
			- Seperated function into Game Interface(For Interface), Main Game (For Game Mechanics), Level Builder(To Create Levels)
	- Previous Classes Have Subclass under same .java
		- Seperated Crack.java from Brick.java
5. Create Hub of access to reduce dependencies
	- Wall.java was previously accessing Player, brick, crack and ball directly
		- Created BrickMaker.java to handle all functions that require interaction with Bricks
		- Change access for Ball to Rubber Ball to accessing only from RubberBall
5. Created a permanent high score list, along with a dialog box that pops up showing current leaderboard bt the end of each round
6. Implementation of Main Menu Screen
	- Main menu screen now display an image that is related to the game upon launch
	- Changed Title Text Colour to suit the background more
	- Added Button to Show Info Popup
		- Moved Start and Quit Button to give space for the new button
		- Added Sensors
7. Implementation of Info Button
	Added a new button to main menu, which when clicked will popup a dialog box displaying a brief information of the game
8. Added a few levels, and a mutator to bricks
	- Originally the code only allows 2 maximum types of brick on one wall
		Mutator will randomly change the brick type when creating walls
		Mutation Chance can be adjusted in BrickMaker.java
		Make Every level unique everytime even if it's the same level
	- New Level have a higher brick count and more lines, which means player have to 
	     Player have to break more bricks compared to original layout
9. Added JUnit test for Score
10. Used Maven to build file for the project
	- Jar File is built, and located under "target" folder named "CSF-Ex4-20194465.jar"
	
	