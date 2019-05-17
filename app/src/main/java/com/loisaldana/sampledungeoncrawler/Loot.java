package com.loisaldana.sampledungeoncrawler;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Loot {

    Bitmap coin;
    double rX, rY;
    public boolean isActive = false, onReset = false;

    public int cX, cY, coinSpd = 20;
    public int canvasW;
    public int timerS = 100, timerE = 200, realTimer;

    void drawCoin(Canvas canvas, Bitmap mapBitmap){

        if(!isActive && !onReset){
            canvas.drawBitmap(mapBitmap, canvasW, (int)rY,null);
            cY = (int)rY;
            isActive = true;
        }else{
            canvas.drawBitmap(mapBitmap, cX, cY,null);

        }
        update();
        System.out.println("I'm drawing");

    }

    public double getRandomTimer(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        realTimer = (int)x;
        return x;
    }

    public double getRandomY(double min, double max){
        double y = (int)(Math.random()*((max-min))+1)+min;
        rY = y;
        return y;
    }

    void update(){

        if(cX < 0 - coin.getWidth()){
            isActive = false;
            onReset = true;
            cX = canvasW + coin.getWidth();
            getRandomTimer(timerS, timerE);

        }

        if(isActive && !onReset){
            cX -= coinSpd;
        }

        if(onReset && !isActive){
            realTimer = realTimer - 1;
            System.out.println(realTimer);
        }
        if(realTimer <= 0){
            onReset = false;
            realTimer = 0;
        }

        System.out.println("yeah  " + isActive + "  " + onReset);

    }




}
