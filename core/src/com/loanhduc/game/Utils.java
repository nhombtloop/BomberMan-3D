package com.loanhduc.game;

import java.util.ArrayList;

public class Utils {
     static class Function {
        Runnable runnable;
        double delayTime;
        double runTime;
        public Function(Runnable runnable, double delayTime) {
            this.runnable = runnable;
            this.delayTime = delayTime;
            runTime = Utils.TIME + delayTime;
        }
    }

    public static double DELTA_TIME = 0;
    public static double TIME = 0;
    static ArrayList<Function> functions = new ArrayList<>();

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

    public static Function setTime(Runnable runnable, double delayTime) {
        Function newFunction = new Function(runnable, delayTime);
        functions.add(newFunction);
        return newFunction;
    }

    public static void runNow(Function function) {
        function.runTime = 0;
    }

    public static void runFunction() {
        for(int i = 0; i< functions.size(); i++) {
            if (TIME>=functions.get(i).runTime) {
                functions.get(i).runnable.run();
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
