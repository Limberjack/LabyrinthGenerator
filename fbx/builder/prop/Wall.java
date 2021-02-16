package fbx.builder.prop;

import fbx.builder.plane.Plane;
import fbx.builder.plane.Point;

import javax.vecmath.Vector3d;
import java.util.Iterator;
import java.util.LinkedList;

public class Wall implements Prop {
    private Vector3d root;
    private static int globalId = 1; //just let it be 3. more than 0 and 1
    int id;
    LinkedList<Point> points = null;

    public Wall(Vector3d begin, int[][] map) {
        this.root = begin;
        points = new LinkedList<Point>();
        points.add(new Point(new Vector3d(begin.x, begin.y, begin.z))); //0
        points.add(new Point(new Vector3d(begin.x, begin.y, begin.z + 3))); //1

        points.add(new Point(new Vector3d(begin.x, begin.y + 1, begin.z))); //2
        points.add(new Point(new Vector3d(begin.x, begin.y + 1, begin.z + 3))); //3

        points.add(new Point(new Vector3d(begin.x + 1, begin.y, begin.z))); //4
        points.add(new Point(new Vector3d(begin.x + 1, begin.y, begin.z + 3))); //5

        points.add(new Point(new Vector3d(begin.x + 1, begin.y + 1, begin.z))); //6
        points.add(new Point(new Vector3d(begin.x + 1, begin.y + 1, begin.z + 3))); //7
    }

    public Vector3d getRoot() {
        return root;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Point> toPoints() {
        return points;
    }

    public String toPointsString() {
        String str = new String();
        for(Iterator<Point> i = points.iterator(); i.hasNext();)
        {
            str+=i.next().toString();
        }
        return str;
    }

    public String toPlanesString() {
        String str = new String();
        LinkedList<Point> tmp = new LinkedList<Point>();
        tmp.add(points.get(2));
        tmp.add(points.get(6));
        tmp.add(points.get(4));
        tmp.add(points.get(0));
        str+= new Plane(tmp).toString();

        tmp = new LinkedList<Point>();
        tmp.add(points.get(5));
        tmp.add(points.get(7));
        tmp.add(points.get(3));
        tmp.add(points.get(1));
        str+= new Plane(tmp).toString();

        tmp = new LinkedList<Point>();
        tmp.add(points.get(1));
        tmp.add(points.get(3));
        tmp.add(points.get(2));
        tmp.add(points.get(0));
        str+= new Plane(tmp).toString();

        tmp = new LinkedList<Point>();
        tmp.add(points.get(4));
        tmp.add(points.get(6));
        tmp.add(points.get(7));
        tmp.add(points.get(5));
        str+= new Plane(tmp).toString();

        tmp = new LinkedList<Point>();
        tmp.add(points.get(4));
        tmp.add(points.get(5));
        tmp.add(points.get(1));
        tmp.add(points.get(0));
        str+= new Plane(tmp).toString();

        tmp = new LinkedList<Point>();
        tmp.add(points.get(2));
        tmp.add(points.get(3));
        tmp.add(points.get(7));
        tmp.add(points.get(6));
        str+= new Plane(tmp).toString();

        return str;
    }
}
