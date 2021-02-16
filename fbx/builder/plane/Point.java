package fbx.builder.plane;

import javax.vecmath.Vector3d;
import java.util.LinkedList;

public class Point {
    public int id;
    public static int lastId = 0;
    public Vector3d pos;
    private static LinkedList<Point> points = new LinkedList<Point>();

    public Point(Vector3d pos) {
        this.pos = pos;
        lastId++;
        id = lastId;
        points.add(this);
    }
    public String toString(){
        return "v " + pos.x + " " + pos.y + " " + pos.z + "\n";
    }

    public static LinkedList<Point> getPoints() {
        return points;
    }
}


