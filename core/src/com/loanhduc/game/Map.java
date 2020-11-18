package com.loanhduc.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    public static int ROWS;
    public static int COLUMNS;
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
    }



    public static char[][] getMap() {
        return map;
    }

}
