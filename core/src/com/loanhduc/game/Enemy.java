package com.loanhduc.game;

import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;

public class Enemy extends MovingEntity {
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Enemy(MyGdxGame game) {
        super(game);
    }

    protected boolean checkCollisionWithFire() {
        return collisionWithFire();
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
                if (Map.map[i][j] == '3') {
                    Crispy newMonster = new Crispy(game);
                    newMonster.x = j * Map.CELL_WIDTH;
                    newMonster.z = i * Map.CELL_WIDTH;
                    newMonster.create();
                    enemies.add(newMonster);
                    Map.map[i][j] = '*';
                }
            }
        }
    }

    @Override
    public void update() {

    }

    public void renderEnemy() {
        for (int i=0; i < enemies.size(); i++) {
            enemies.get(i).render();
        }
    }
}
