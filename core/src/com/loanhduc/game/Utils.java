package com.loanhduc.game;

import java.util.ArrayList;

class Function {
    Runnable runnable;
    double delayTime;
    double runTime;
    public Function(Runnable runnable, double delayTime) {
        this.runnable = runnable;
        this.delayTime = delayTime;
        runTime = Utils.TIME + delayTime;
    }
}

public class Utils {
    public static double TIME = 0;
    static ArrayList<Function> functions = new ArrayList<>();

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

    public static void setTime(Runnable runnable, double delayTime) {
        Function newFunction = new Function(runnable, delayTime);
        functions.add(newFunction);
    }

    public static void runFunction() {
        for (Function i : functions) {
            if (TIME>=i.runTime) {
                i.runnable.run();
                functions.remove(i);
            }
        }
    }
}
