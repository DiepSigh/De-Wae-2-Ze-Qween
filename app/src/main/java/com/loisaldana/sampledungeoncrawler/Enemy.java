package com.loisaldana.sampledungeoncrawler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

//TO DO
//Randomize position on respawn
//if enemy is within 50 pixels (x and y) of player, lose a life

public class Enemy {
    private Bitmap img;
    private int x, y;
    private int xVel, yVel;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean hitPlayer;
    public  boolean passPlayer = false; // <-- added by Andrey
    //Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean getHit() { return hitPlayer; }

    //Setters
    public void setHit(boolean hit){ hitPlayer = hit;}

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
    public void draw(Canvas canvas){

        canvas.drawBitmap(img, x, y, null);
        update();
    }

    public void update(){

        //movement of enemy here
        if (x<0 || hitPlayer) {
            double temp;
            //Reset pos/respawn
            x = screenWidth;
            temp = RNG(0, screenHeight-150);
            y = (int) Math.round(temp);
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