package fbx.builder.plane;

import java.util.LinkedList;

public class Plane {
    LinkedList<Point> points;
     public int id;
     public static int lastId = 0;

    public Plane(LinkedList<Point> points) {
        this.points = points;
        lastId++;
        id = lastId;
    }

    public String toString(){
        return "f " + points.get(0).id + " " + points.get(1).id + " "  + points.get(2).id + " " + points.get(3).id + "\n";
    }
}
