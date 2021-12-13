package COMP2042_CW_hfyhc3;

import java.awt.*;

/**
 * LevelBuilder.java
 * A class that Build the level by making a wall using Brick Maker
 * Acts as the game indicator of the level
 *
 * Created: by filippo on 04/09/16.
 *
 * Refactored by:
 * @author Chong Hao Wei
 * Date: 12/12/2021
 *
 */
public class LevelBuilder {
    static final int LEVELS_COUNT = 6;
    Brick[] bricks;
    Brick[][] levels;
    int level;
    int brickCount;

    public Brick[] getBricks() {
        return bricks;
    }


    public Brick[][] getLevels() { return levels; }
    public int getLevel() {
        return level;
    }

    public void setLevels(Brick[][] levels) {
        this.levels = levels;
    }
    public void setLevel(int level) {
        this.level = level;
    }


    /**
     * Default constructor of LevelBuilder
     */
    public LevelBuilder() {
    }

    /**
     * Create a wall based on info
     * @param drawArea area of wall
     * @param brickCnt brick count
     * @param lineCnt line of bricks
     * @param brickSizeRatio size ratio of bricks
     * @param typeA Brick material A
     * @param typeB Brick Material B
     * @return Wall
     */
    Brick[] createWall(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] TotalBricks = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < TotalBricks.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));


            if(BrickMaker.Mutate()){
                TotalBricks[i] = BrickMaker.makeBrick(p, brickSize, BrickMaker.RandomType());
            }else{
            TotalBricks[i] = b ? BrickMaker.makeBrick(p, brickSize, typeA) : BrickMaker.makeBrick(p, brickSize, typeB);}
        }

        for (double y = brickHgt; i < TotalBricks.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            if(BrickMaker.Mutate()){TotalBricks[i] = BrickMaker.makeBrick(p, brickSize, BrickMaker.RandomType());
            }else{
            TotalBricks[i] = BrickMaker.makeBrick(p, brickSize, typeA);}
        }
        return TotalBricks;
    }



    /**
     * Make a level
     * @param drawArea Area of wall
     * @param DefaultBrickCount Brick count
     * @param DeafaultLineCount Line of bricks
     * @param brickDimensionRatio brick dimension ratio
     * @return Level
     */
    Brick[][] makeLevels(Rectangle drawArea, int DefaultBrickCount, int DeafaultLineCount, double brickDimensionRatio) {
        Brick[][] Level = new Brick[LEVELS_COUNT][];
        Level[0] = createWall(drawArea, DefaultBrickCount, DeafaultLineCount, brickDimensionRatio, BrickMaker.CLAY, BrickMaker.CLAY);
        Level[1] = createWall(drawArea, DefaultBrickCount, DeafaultLineCount, brickDimensionRatio, BrickMaker.CLAY, BrickMaker.CEMENT);
        Level[2] = createWall(drawArea, DefaultBrickCount, DeafaultLineCount, brickDimensionRatio, BrickMaker.CLAY, BrickMaker.STEEL);
        Level[3] = createWall(drawArea, DefaultBrickCount, DeafaultLineCount, brickDimensionRatio, BrickMaker.STEEL, BrickMaker.CEMENT);
        Level[4] = createWall(drawArea, DefaultBrickCount*3, DeafaultLineCount*3, brickDimensionRatio, BrickMaker.CEMENT, BrickMaker.CLAY);
        Level[5] = createWall(drawArea, DefaultBrickCount*4, DeafaultLineCount*4, brickDimensionRatio*1.3, BrickMaker.CEMENT, BrickMaker.STEEL);
        return Level;
    }



    /**
     * Jump to next level
     */
    public void nextLevel() {
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    /**
     * Get Brick count
     * @return brick count
     */
    public int getBrickCount() {
        return brickCount;
    }

    /**
     * Set brick count
     * @param brickCount brick count
     */
    public void setBrickCount(int brickCount) {
        this.brickCount = brickCount;
    }

    /**
     * Check if this is last level
     * @return if next level exceed current max level
     */
    public boolean hasLevel(){
        return getLevel() < getLevels().length;
    }
}