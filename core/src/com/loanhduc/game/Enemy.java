package com.loanhduc.game;

import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;

public class Enemy extends MovingEntity {
    private static ArrayList<Enemy> enemies = new ArrayList<>();

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Enemy(MyGdxGame game) {
        super(game);
    }

    public boolean collisionWithFire() {
        for (Explode.Fire fire : game.getExplode().getFire()) {
            if(this.collisionWith(fire)) {
                return true;
            }
        }
        return false;
    }


    public void createEnemy() {
        for (int i = 0; i < Map.ROWS; i++) {
            for (int j = 0; j < Map.COLUMNS; j++) {
                if (Map.map[i][j] == '1') {
                    Robot newRb = new Robot(game);
                    newRb.x = j * Map.CELL_WIDTH;
                    newRb.z = i * Map.CELL_WIDTH;
                    newRb.create();
                    enemies.add(newRb);
                    Map.map[i][j] = ' ';
                }
                if (Map.map[i][j] == '2') {
                    Monster newMonster = new Monster(game);
                    newMonster.x = j * Map.CELL_WIDTH;
                    newMonster.z = i * Map.CELL_WIDTH;
                    newMonster.create();
                    enemies.add(newMonster);
                    Map.map[i][j] = ' ';
                }
            }
        }
    }

    @Override
    public void update() {

    }

    public void renderEnemy() {
        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }
}
