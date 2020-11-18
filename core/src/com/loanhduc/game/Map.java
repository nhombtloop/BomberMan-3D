package com.loanhduc.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private static int ROWS;
    private static int COLUMNS;
    private static char[][] map;

    public static void loadMap(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        ROWS = sc.nextInt();
        COLUMNS = sc.nextInt();
        map = new char[ROWS][COLUMNS];
        sc.nextLine();
        String line = "";
        for (int i = 0; i < ROWS; i++) {
            line = sc.nextLine();
            for (int j = 0; j < COLUMNS; j++) {
                map[i][j] = line.charAt(j);
            }
        }
        Map.render();
    }

    public static void render() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                char c = map[i][j];
                switch (c) {
                    case '#': // wall
                        Wall.spawn(j * 200 - 2000, 100, i * 200 - 1400);
                        break;
                    case '*': // brick
                        break;
                    case 'x': // portal
                        break;
                    case 'b': // bomb item
                        break;
                    case 'f': // flame Item
                        break;
                    case 's': // speed Item
                        break;
                    case ' ':
                        Solid.spawn(j * 200 - 2000, 100, i * 200 - 1400);
                        break;
                }
            }
        }
    }

    public static char[][] getMap() {
        return map;
    }

}
