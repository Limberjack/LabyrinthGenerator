package map.floor;

import javax.vecmath.Vector2d;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Labyrinth {

    private class Path {

        private LinkedList<Vector2d> points = new LinkedList<Vector2d>();

        public LinkedList<Vector2d> getPoints() {
            return points;
        }

        public void setPoints(LinkedList<Vector2d> points) {
            this.points = points;
        }

        public void paintPoints(int[][] map, int i) {
            for (Iterator<Vector2d> k = points.iterator(); k.hasNext(); ) {
                Vector2d tmp = k.next();
                map[(int) tmp.x][(int) tmp.y] = i;
            }
        }
        public void paintPoints(int[][] map) {
            paintPoints(map, map[(int)points.getFirst().x][(int)points.getFirst().y]);
        }

        public void mergePaths(Path anotherPath){
            this.points.addAll(anotherPath.getPoints());
        }

        public boolean isYour(Vector2d point){
            for(Iterator<Vector2d> i = points.iterator(); i.hasNext();){
                Vector2d tmp = i.next();
                if(point.x == tmp.x && point.y == tmp.y) {
                    return true;
                }
            }
            return false;
        }
    }

    public void buildLabyrinth(int[][] map, Random random) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (x % 2 * y % 2 != 0)
                    map[x][y] = Obstacle.FREE_SPACE.ordinal();
                else
                    map[x][y] = Obstacle.WALL.ordinal();
            }
        }
        LinkedList<Path> paths = new LinkedList<Path>();
        for (int i = Obstacle.FREE_SPACE.ordinal() + 1; true; i++) {
            Path tmp = makePath(map, random, i);
            if (tmp == null)
                break;
            paths.add(tmp);
        }

        while(craveWall(map,paths.getFirst(), paths, random));

    }

    private Path makePath(int[][] map, Random random, int i) {
        Vector2d root = findMinVal(map);
        if (root == null)
            return null;

        Path path = new Path();
        Vector2d nextStep = findNotVisitedNeighbour(map, root, random);
        map[(int) root.x][(int) root.y] = i;
        path.getPoints().add(root);
        while (nextStep != null) {
            Vector2d mid = new Vector2d((root.x + nextStep.x) / 2, (root.y + nextStep.y) / 2);
            map[(int) mid.x][(int) mid.y] = i;
            map[(int) nextStep.x][(int) nextStep.y] = i;
            root = nextStep;
            nextStep = findNotVisitedNeighbour(map, root, random);
            path.getPoints().add(mid);
            path.getPoints().add(root);
        }
        return path;
    }

    private Vector2d findNotVisitedNeighbour(int[][] map, Vector2d root, Random random) {
        LinkedList<Vector2d> freeNeighbours = new LinkedList<Vector2d>();
        int x = (int) root.x, y = (int) root.y;
        if (x + 2 < map.length && map[x + 2][y] == Obstacle.FREE_SPACE.ordinal())
            freeNeighbours.add(new Vector2d(x + 2, y));
        if (x - 2 > 0 && map[x - 2][y] == Obstacle.FREE_SPACE.ordinal())
            freeNeighbours.add(new Vector2d(x - 2, y));

        if (y + 2 < map[x].length && map[x][y + 2] == Obstacle.FREE_SPACE.ordinal())
            freeNeighbours.add(new Vector2d(x, y + 2));
        if (y - 2 > 0 && map[x][y - 2] == Obstacle.FREE_SPACE.ordinal())
            freeNeighbours.add(new Vector2d(x, y - 2));

        if(freeNeighbours.size() == 0)
            return null;
        int tmp = random.nextInt() % freeNeighbours.size();
        if (tmp < 0)
            tmp *= -1;
        return freeNeighbours.get(tmp);
    }

    private boolean craveWall(int[][]map, Path path, LinkedList<Path> paths, Random random){
        LinkedList<Vector2d> borders = new LinkedList<Vector2d>();
        LinkedList<Vector2d> from = new LinkedList<Vector2d>();
        LinkedList<Vector2d> to = new LinkedList<Vector2d>();

        for(Iterator<Vector2d> i = path.getPoints().iterator(); i.hasNext();){
            Vector2d root = i.next();
            int x = (int) root.x, y = (int) root.y;
            if (x + 2 < map.length && map[x + 2][y] != map[x][y] && map[x + 2][y] != Obstacle.WALL.ordinal()) {
                borders.add(new Vector2d(x + 1, y));
                from.add(new Vector2d(x, y));
                to.add(new Vector2d(x + 2, y));
            }
            if (x - 2 > 0 && map[x - 2][y] != map[x][y] && map[x - 2][y] != Obstacle.WALL.ordinal()) {
                borders.add(new Vector2d(x - 1, y));
                from.add(new Vector2d(x, y));
                to.add(new Vector2d(x - 2, y));
            }
            if (y + 2 < map[x].length && map[x][y + 2] != map[x][y] && map[x][y + 2] != Obstacle.WALL.ordinal()) {
                borders.add(new Vector2d(x, y + 1));
                from.add(new Vector2d(x, y));
                to.add(new Vector2d(x, y + 2));
            }
            if (y - 2 > 0 && map[x][y - 2] != map[x][y] && map[x][y - 2] != Obstacle.WALL.ordinal()) {
                borders.add(new Vector2d(x, y - 1));
                from.add(new Vector2d(x, y));
                to.add(new Vector2d(x, y - 2));
            }
        }

        if(borders.size() != 0) {
            int tmp = random.nextInt() % borders.size();
            if (tmp < 0)
                tmp *= -1;
            path.getPoints().add(borders.get(tmp));

            Vector2d destPoint = to.get(tmp);
            for(Iterator<Path> i = paths.iterator(); i.hasNext();){
                Path nextPath = i.next();
                if( !path.equals(nextPath) && nextPath.isYour(destPoint)){
                    path.mergePaths(nextPath);
                    path.paintPoints(map);
                    paths.remove(nextPath);
                    return true;
                }
            }
        }

        return false;
    }

    private Vector2d findMinVal(int[][] map) {
        Vector2d vector2d;
        for (int x = 1; x < map.length - 1; x++) {
            for (int y = 1; y < map[x].length - 1; y++) {
                if (map[x][y] == Obstacle.FREE_SPACE.ordinal()) {
                    vector2d = new Vector2d(x, y);
                    return vector2d;
                }
            }
        }
        return null;
    }


}
