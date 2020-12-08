package com.loanhduc.game;

public class Brick extends StaticEntity {
    public Brick() {
        path = "brick.g3db";
    }

    @Override
    public void render() {
        checkBurned(Map.map, Map.CELL_WIDTH);
        super.render();
    }
}
