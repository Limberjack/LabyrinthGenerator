package fbx.builder.prop;

import fbx.builder.plane.Plane;
import fbx.builder.plane.Point;

import javax.vecmath.Vector3d;
import java.util.Iterator;
import java.util.LinkedList;

public interface Prop {

    public Vector3d getRoot();

    public int getId();

    public void setId(int id);
    public LinkedList<Point> toPoints();

    public String toPointsString();

    public String toPlanesString();
}
