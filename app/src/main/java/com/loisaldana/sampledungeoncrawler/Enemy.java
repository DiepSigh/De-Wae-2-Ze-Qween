package com.loisaldana.sampledungeoncrawler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

//TO DO
//Set up animation and class to work with 2 different spritesheets

public class Enemy {
    private Bitmap img;
    private int x, y;
    private int xVel, yVel;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean hitPlayer;
    public boolean passPlayer = false; // <-- added by Andrey

    //Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean getHit() { return hitPlayer; }

    //Setters
    public void setHit(boolean hit){ hitPlayer = hit;}

    public void increaseVel(int vel){xVel += vel;}

    //Init enemy
    public Enemy(Bitmap bmp) {
        img = bmp;
        //speed of bitmap?
        xVel = 20;
        yVel = 10;
        //pos of enemy
        x = screenWidth;
        y = screenHeight / 2;

        //Hits player once per spawn
        hitPlayer = false;
    }

    //draws enemy
    public void draw(Canvas canvas, int playerY){

        canvas.drawBitmap(img, x, y, null);
        update(playerY);
    }

    public void update(int playerY){

        //movement of enemy here
        if (x<0 || hitPlayer) {
            double temp;
            //Reset pos/respawn
            x = screenWidth;
            //temp = RNG(0, screenHeight-150);
            //y = (int) Math.round(temp);
            y = playerY;
            hitPlayer = false;
            passPlayer = false;
        } else {
            x -= xVel;
            //y += yVel;
        }
    }

    public static double RNG(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

}