package test;

import java.awt.*;

public class WallMaker {
    static final int LEVELS_COUNT = 5;
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






    public WallMaker() {
    }

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
            TotalBricks[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < TotalBricks.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            TotalBricks[i] = makeBrick(p, brickSize, typeA);
        }
        return TotalBricks;
    }

    Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        Brick[][] Level = new Brick[LEVELS_COUNT][];
        Level[0] = createWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.CLAY, BrickMaterial.CLAY);
        Level[1] = createWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.CLAY, BrickMaterial.CEMENT);
        Level[2] = createWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.CLAY, BrickMaterial.STEEL);
        Level[3] = createWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.STEEL, BrickMaterial.CEMENT);
        Level[4] = createWall(drawArea, brickCount, lineCount, brickDimensionRatio, BrickMaterial.STEEL, BrickMaterial.STEEL);
        return Level;
    }

    public void nextLevel() {
        bricks = levels[level++];
        this.brickCount = bricks.length;
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
    public int getBrickCount() {
        return brickCount;
    }

    public void setBrickCount(int brickCount) {
        this.brickCount = brickCount;
    }
}