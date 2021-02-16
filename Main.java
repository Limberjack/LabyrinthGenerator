import map.floor.Floor;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int x, y, seed;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert X: ");
        x = scanner.nextInt();
        System.out.println("Insert Y: ");
        y = scanner.nextInt();
        System.out.println("Insert World-Seed (if seed is 0, current time will be taken as generation key): ");
        seed = scanner.nextInt();
        Floor flore = new Floor(x, y, seed, 1);
        flore.printMap();
    }
}
