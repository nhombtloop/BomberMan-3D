package com.loanhduc.game;

import com.badlogic.gdx.Game;
import com.loanhduc.game.screen.GameOver;
import com.loanhduc.game.screen.Menu;

public class BoomGame extends Game {
    @Override
    public void create() {
        setScreen(new Menu(this));
    }
}
