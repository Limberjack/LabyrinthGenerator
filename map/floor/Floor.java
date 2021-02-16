package map.floor;

import fbx.builder.prop.Prop;
import fbx.builder.prop.Wall;

import javax.vecmath.Vector3d;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Floor {
    Random random;
    private int map[][];

    public Floor(int X, int Y, int seed, int flore_number) throws IOException {
        if (X % 2 == 0)
            X++;
        if (Y % 2 == 0)
            Y++;
        map = new int[X][Y];

        if (seed == 0)
            random = new Random(System.currentTimeMillis());
        else
            random = new Random(seed + flore_number);

        new Labyrinth().buildLabyrinth(map, random);

        LinkedList<Prop> props = new LinkedList<Prop>();

        props.add(new fbx.builder.prop.Floor(new Vector3d(0,0,0), map));

        for(int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                if(map[i][j] == 0){
                    props.add(new Wall(new Vector3d(i, j, 1), map));
                }
            }
        }
        String fileString = "";
        for(Iterator<Prop> i = props.iterator(); i.hasNext();){
            Prop tmp = i.next();
            fileString += tmp.toPointsString();
        }

        fileString+="\n\n";

        for(Iterator<Prop> i = props.iterator(); i.hasNext();){
            Prop tmp = i.next();
            fileString += tmp.toPlanesString();
        }

        String fileId;
        for (int i = 0;; i++ ){
            if(! new File("Labyrinth_" + i).exists()){
                fileId = i + "";
                break;
            }
        }

        File dir = new File("Labyrinth_"+ fileId);
        dir.mkdir();
        File objFile = new File(dir.getName() + "/Labyrinth_" + fileId + ".obj");
        objFile.createNewFile();
        FileWriter fileWriter = new FileWriter(objFile);
        fileWriter.write(fileString);
        fileWriter.flush();
        fileWriter.close();
        File modelSdf = new File(dir.getName() + "/model.sdf");
        modelSdf.createNewFile();
        fileWriter = new FileWriter(modelSdf);
        fileWriter.write("<?xml version='1.0' ?>\n" +
                "<sdf version='1.6'>\n" +
                "  <model name='labyrinth " + fileId + "'>\n" +
                "    <static>true</static>\n" +
                "    <link name='link'>\n" +
                "      <pose>0 0 0 0 0 0</pose>\n" +
                "      <collision name='collision'>\n" +
                "        <geometry>\n" +
                "          <mesh>\n" +
                "            <uri>model://" + dir.getName() + "/" + objFile.getName() +"</uri>\n" +
                "          </mesh>\n" +
                "        </geometry>\n" +
                "      </collision>\n" +
                "      <visual name='visual'>\n" +
                "        <geometry>\n" +
                "          <mesh>\n" +
                "            <uri>model://" + dir.getName() + "/" + objFile.getName() +"</uri>\n" +
                "          </mesh>\n" +
                "        </geometry>\n" +
                "      </visual>\n" +
                "    </link>\n" +
                "  </model>\n" +
                "</sdf>");
        fileWriter.flush();
        fileWriter.close();

        File modelConf = new File(dir.getName() + "/model.conf");
        fileWriter = new FileWriter(modelConf);
        fileWriter.write(
                "<?xml version=\"1.0\"?>\n" +
                "\n" +
                "<model>\n" +
                "  <name>test block</name>\n" +
                "  <version>1.0</version>\n" +
                "  <sdf version=\"1.5\">model.sdf</sdf>\n" +
                "\n" +
                "  <description>\n" +
                "    labyrinth\n" +
                "  </description>\n" +
                "</model>\n");
        fileWriter.flush();
        fileWriter.close();
    }

    public void printMap() {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y] == 0)
                    System.out.printf("██");
                else if (map[x][y] != Obstacle.FREE_SPACE.ordinal() + 1)
                    System.out.printf("%2d", map[x][y]);
                else
                    System.out.printf("  ");
            }
            System.out.printf("\n");
        }
    }
}
