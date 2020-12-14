package com.loanhduc.game.util;

public class Utils {
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public static int generateRandom() {
        return (int) Math.round(Math.random() * 3) + 1;
    }
}
