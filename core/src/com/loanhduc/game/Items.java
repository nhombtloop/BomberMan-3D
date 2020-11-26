package com.loanhduc.game;


import java.util.ArrayList;
import java.util.List;

public abstract class Items extends StaticEntity {
    static List<Items> items = new ArrayList<>();

    public static List<Items> getItems() {
        return items;
    }
}
