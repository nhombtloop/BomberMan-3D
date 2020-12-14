package com.loanhduc.game;

import com.loanhduc.game.screen.MyGdxGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    public static int ROWS;
    public static int COLUMNS;
    public static char[][] map;
    public static final int CELL_WIDTH = 200;

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

    public static void renderMap(MyGdxGame game) {
        for (int i = 0; i < Map.ROWS; i++) {
            for (int j = 0; j < Map.COLUMNS; j++) {
                char c = Map.map[i][j];
                int x = j * Map.CELL_WIDTH;
                int z = i * Map.CELL_WIDTH;
                game.getSolid().spawn(x, 0, z);
                switch (c) {
                    case '#': // wall
                        game.getWall().spawn(x, 100, z);
                        break;
                    case '*':
                        game.getBrick().spawn(x + 30,0, z - 10);
                        break;
                    case 'x': // portal
                        game.getPortal().spawn(x, 0, z);
                        break;
                    case 'b': // bomb item
                        game.getBombItem().spawn(x, 0, z);
                        break;
                    case 'f': // flame Item
                        game.getFlameItem().spawn(x, 0, z);
                        break;
                    case 's': // speed Item
                        game.getSpeedItem().spawn(x, 0, z);
                        break;
                    case 'w':
                        game.getWinPortal().spawn(x, 0, z);
                        break;
                }
            }
        }
    }


}
