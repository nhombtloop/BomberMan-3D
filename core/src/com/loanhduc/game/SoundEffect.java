package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffect {
    private static Sound boom = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
    // fix later with boom sound
    private static Sound inGamePlay = Gdx.audio.newSound(Gdx.files.internal("ingameplay.mp3"));

    public static void playSoundBoom() {
        boom.play(1.5f);
    }
    public static void playSoundInGame() {
        inGamePlay.loop(0.7f);
    }
}
