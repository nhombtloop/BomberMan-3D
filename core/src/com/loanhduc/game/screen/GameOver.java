package com.loanhduc.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.loanhduc.game.BoomGame;
import com.loanhduc.game.util.SoundEffect;

public class GameOver extends ScreenAdapter {
    private BoomGame game;
    private SpriteBatch batch = new SpriteBatch();
    private Sprite sprite;
    private Texture backgroundImg;
    Stage stage;
    Skin skin;

    public GameOver(BoomGame game) {
        this.game = game;
    }
    public void show() {
        int buttonOffset = 20;
        backgroundImg = new Texture(Gdx.files.internal("gameover.jpg"));
        sprite = new Sprite(backgroundImg);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createBasicSkin();
        TextButton newGameButton = new TextButton("Play again", skin);
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/2);
//        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/8 + (newGameButton.getHeight() + buttonOffset));
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Menu(game));
            }
        });

        stage.addActor(newGameButton);
        TextButton exitButton = new TextButton("Exit", skin);
//        exitButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/2);
        exitButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/8 + (newGameButton.getHeight() + buttonOffset));

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }
    @Override
    public void render(float delta) {
        batch.begin();
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.draw(batch);
        batch.end();
        stage.act();
        stage.draw();
    }
    public void createBasicSkin() {
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default",font);
        Pixmap.Format format;
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/4, (int) Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background",Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background",Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background",Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default",textButtonStyle);

    }
}
