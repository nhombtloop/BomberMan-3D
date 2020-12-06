package com.loanhduc.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundEffect {
    private static Sound boom = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
    // fix later with boom sound
    private static Music inGamePlay = Gdx.audio.newMusic(Gdx.files.internal("ingameplay.mp3"));

    private static Music menuSound = Gdx.audio.newMusic(Gdx.files.internal("menu.mp3"));

    public static void playSoundBoom() {
        boom.play(1.5f);
    }
    public static void playSoundInGame() {
        inGamePlay.play();
    }

    public static void stopInGameSound() {
        inGamePlay.stop();
        inGamePlay.dispose();
    }

    public static void playMenuSound() {
        menuSound.play();
        menuSound.setVolume(1.3f);
    }
    public static void stopMenuSound() {
        menuSound.stop();
        menuSound.dispose();
    }
}
