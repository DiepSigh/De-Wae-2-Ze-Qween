package com.loisaldana.sampledungeoncrawler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemy {
    private Bitmap img;
    private int x, y;
    private int xVel, yVel;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    //Init enemy
    public Enemy(Bitmap bmp) {
        img = bmp;
        //Spawn positions on map
        x = 100;
        y = 100;
        //speed of bitmap?
        xVel = 3;
        yVel = 3;
    }

    //draws enemy
    public void draw(Canvas canvas){
        canvas.drawBitmap(img, x, y, null);
    }

    public void update(){
        //movement of enemy here
        if (x<0 && y<0) {
            x = screenWidth / 2;
            y = screenHeight / 2;
        } else {
            x += xVel;
            y += yVel;
            //bounce
            if ((x > screenWidth - img.getWidth()) || (x < 0)) {
                xVel = xVel*-1;
            }
            if ((y > screenHeight - img.getHeight()) || (y < 0)) {
                yVel = yVel*-1;
            }
        }
    }
}
