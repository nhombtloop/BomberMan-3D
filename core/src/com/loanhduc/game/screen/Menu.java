package com.loanhduc.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.loanhduc.game.BoomGame;
import com.loanhduc.game.screen.MyGdxGame;
import com.loanhduc.game.util.SoundEffect;

public class Menu implements Screen {
    Stage stage;
    Skin skin;
    BoomGame game;

    public Menu(BoomGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        int buttonOffset = 20;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createBasicSkin();
        TextButton newGameButton = new TextButton("Start Game", skin);
        newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
//        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/8 + (newGameButton.getHeight() + buttonOffset));
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundEffect.stopMenuSound();
                game.setScreen(new MyGdxGame(game));
            }
        });

        stage.addActor(newGameButton);
        TextButton exitButton = new TextButton("Exit", skin);
//        exitButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/2);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 8 + (newGameButton.getHeight() + buttonOffset));

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    public void createBasicSkin() {
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);
        Pixmap.Format format;
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 4, (int) Gdx.graphics.getHeight() / 10, Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        SoundEffect.playMenuSound();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}