package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//TO DO
//Set up animation and class to work with 2 different spritesheets

public class Enemy {
    private Bitmap img[] = new Bitmap[3];
    private int x, y;
    private int count;
    private int xVel;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean hitPlayer;
    private boolean active;
    public boolean passPlayer = false; // <-- added by Andrey


    //Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean getHit() { return hitPlayer; }
    public boolean getActive() { return active; }

    //Setters
    public void setHit(boolean hit){ hitPlayer = hit;}
    public void setActive(boolean value) { active = value;}

    public void increaseVel(int vel){xVel += vel;}

    //Init enemy
    public Enemy(Context context, boolean sonic) {
        //Assign images to array for animation
        if (sonic) {
            img[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonic);
            img[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonic2);
            img[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonic3);
        } else { //tails
            img[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tails);
            img[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tails2);
            img[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tails3);
        }
        //keeps track of current img for animation
        count = 0;
        //speed of bitmap?
        xVel = 50;
        //pos of enemy
        x = screenWidth;
        y = screenHeight / 2;

        //Hits player once per spawn
        hitPlayer = false;
    }

    //draws enemy
    public void draw(Canvas canvas, int playerY){
        if (count == 2){
            count = 0;
        }
        canvas.drawBitmap(img[count], x, y, null);
        update(playerY);
        count++;
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

    //Collision check of bullet and enemy
    public void BulletCheck(int bulletX, int bulletY, Projectile projectile) {
        //Check if in range of pixels

        if (x <= (bulletX + 150) && x >= bulletX && y >= bulletY && y < (bulletY + 150) && projectile.isActive) {
            projectile.isActive = false;
            projectile.SetBulletPosX(projectile.bulletStartPositionX);
            projectile.SetBulletPosY(projectile.bulletStartPositionY);
            //Reset enemy to random position at screenWidth
            x = screenWidth;
            double temp;
            temp = RNG(0, screenHeight-200);
            y = (int)Math.round(temp);
        }
    }

}