package com.example.afikshani.gameofthrones;

import android.graphics.drawable.Drawable;

public class FamilyMember {

    private String name;
    private Drawable img;

    public FamilyMember(String name, Drawable pic){
        this.name = name;
        img = pic;
    }


    public String getName() {
        return name;
    }

    public Drawable getImg() {
        return img;
    }


}
