package test;

import java.awt.*;

public class LevelGenerator {
    static final int LEVELS_COUNT = 5;
    Brick[] bricks;

    public Brick[] getBricks() {
        return bricks;
    }

    Brick[][] levels;

    public Brick[][] getLevels() {
        return levels;
    }

    public void setLevels(Brick[][] levels) {
        this.levels = levels;
    }

    int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    int brickCount;

    public void setBrickCount(int brickCount) {
        this.brickCount = brickCount;
    }

    public LevelGenerator() {
    }


    /*Make Wall for the level*/
    Brick[] MakeWall(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {
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
        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        DrawWall(lineCnt, typeA, typeB, brickOnLine, centerLeft, centerRight, brickLen, brickHgt, tmp, brickSize, p);

        return tmp;
    }/*Create Levels*/

    private void DrawWall(int lineCnt, int typeA, int typeB, int brickOnLine, int centerLeft, int centerRight, double brickLen, double brickHgt, Brick[] tmp, Dimension brickSize, Point p) {
        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, typeA);
        }
    }

    Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = MakeWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.CLAY, BrickMaterial.CLAY);
        tmp[1] = MakeWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.CLAY, BrickMaterial.CEMENT);
        tmp[2] = MakeWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.CLAY, BrickMaterial.STEEL);
        tmp[3] = MakeWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.STEEL, BrickMaterial.CEMENT);
        tmp[4] = MakeWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.STEEL, BrickMaterial.STEEL);
        return tmp;
    }

    public int getBrickCount() {
        return brickCount;
    }

    public boolean isDone() {
        return brickCount == 0;
    }

    public void nextLevel() {
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel() {
        return level < levels.length;
    }

    Brick makeBrick(Point point, Dimension size, int type) {
        Brick out;
        switch (type) {
            case BrickMaterial.CLAY:
                out = new ClayBrick(point, size);
                break;
            case BrickMaterial.STEEL:
                out = new SteelBrick(point, size);
                break;
            case BrickMaterial.CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        }
        return out;
    }
}