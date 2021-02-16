package map.floor;

public enum Obstacle {
    WALL(1,1),
    WINDOW(1,1),
    STAIRS_LEFT_TO_RIGHT(4,6),
    STAIRS_RIGHT_TO_LEFT(4,6),
    STAIRS_TOP_TO_DOWN(6,4),
    STAIRS_DOWN_TO_TOP(6,4),
    STAIR_HOLE_LEFT_TO_RIGHT(4,6),
    STAIR_HOLE_RIGHT_TO_LEFT(4,6),
    STAIR_HOLE_TOP_TO_DOWN(6,4),
    STAIR_HOLE_DOWN_TO_TOP(6,4),
    FREE_SPACE(1,1);

    public final int x, y;
    Obstacle(int x, int y){
        this.x = x;
        this.y = y;
    }

}
