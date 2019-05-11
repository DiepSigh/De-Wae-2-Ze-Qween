package com.loisaldana.sampledungeoncrawler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

//TO DO
//Randomize position on respawn

public class Enemy {
    private Bitmap img;
     int x, y;
    private int xVel, yVel;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    //Init enemy
    public Enemy(Bitmap bmp) {
        img = bmp;
        //speed of bitmap?
        xVel = 10;
        yVel = 10;
        //pos of enemy
        x = screenWidth;
        y = screenHeight / 2;
    }

    //draws enemy
    public void draw(Canvas canvas){

        canvas.drawBitmap(img, x, y, null);
        update();
    }

    public void update(){

        //movement of enemy here
        if (x<0) {
            //Reset pos/respawn
            x = screenWidth;
            y = screenHeight / 2;
        } else {
            x -= xVel;
            //y += yVel;
        }

    }
}