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
    public static double DELTA_TIME = 0;
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

    public static void setTimeout2(Runnable runnable1, int delay1, Runnable runnable2, int delay2){
        new Thread(() -> {
            try {
                Thread.sleep(delay1);
                runnable1.run();
                Thread.sleep(delay2);
                runnable2.run();
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

    public static ArrayList<Character> getCoordinate(char[][] map, int cell, int x, int z, int size) {
        ArrayList<Character> result = new ArrayList<>();
        result.add(map[z/cell][x/cell]);
        result.add(map[(z+size)/cell][x/cell]);
        result.add(map[z/cell][(x+size)/cell]);
        result.add(map[(z+size)/cell][(x+size)/cell]);
        return result;
    }
}
