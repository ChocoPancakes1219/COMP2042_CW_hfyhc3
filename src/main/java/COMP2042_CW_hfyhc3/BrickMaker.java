package COMP2042_CW_hfyhc3;

import java.awt.*;
import java.util.Random;

/**
 * BrickMaker.java
 * Creates Brick that are used to make level for the players
 * Also setting the mutation chance for a brick to change into other type
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 */
public class BrickMaker {


	static final int CLAY = 1;
	static final int STEEL = 2;
	static final int CEMENT = 3;




	/**
	 * Chance for brick to mutate into another type
	 * @return boolean Brick mutation
	 */
	public static boolean Mutate() {
		int MutationChance=10;

		Random random = new Random();
		return MutationChance > (random.nextInt(100 - 0) + 1);
	}

	/**
	 * Create a random type of brick
	 * @return brick type
	 */
	static int RandomType() {
		Random random = new Random();
		return random.nextInt(CEMENT+1-CLAY) + 1;
	}

    /**
     * Make a brick based on material
     * @param point position of brick
     * @param size size of brick
     * @param type type of material
     * @return brick
     */
    static Brick makeBrick(Point point, Dimension size, int type) {
        Brick newBrick;
        switch (type) {
            case CLAY:
                newBrick = new ClayBrick(point, size);
                break;
            case STEEL:
                newBrick = new SteelBrick(point, size);
                break;
            case CEMENT:
                newBrick = new CementBrick(point, size);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        }
        return newBrick;
    }
}





