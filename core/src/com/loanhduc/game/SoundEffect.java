package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundEffect {
    private static Sound boom = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
    // fix later with boom sound
    private static Sound inGamePlay = Gdx.audio.newSound(Gdx.files.internal("ingameplay.mp3"));

    private static Music menuSound = Gdx.audio.newMusic(Gdx.files.internal("menu.mp3"));

    public static void playSoundBoom() {
        boom.play(1.5f);
    }
    public static void playSoundInGame() {
        inGamePlay.loop(0.7f);
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
