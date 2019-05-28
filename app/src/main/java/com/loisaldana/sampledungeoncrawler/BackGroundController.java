package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;


public class BackGroundController {

    private int canvasWidth;
    private int canvasHeight;

    Bitmap Bg1BitMap; // this is bitmap we using for background
    Bitmap Bg2BitMap; // this is bitmap we using for background
    Bitmap Bg3BitMap; // this is bitmap we using for background
    Bitmap Bg4BitMap; // this is bitmap we using for background

    private int BGx = 0;
    private int bg1Left = 0;
    private int bg2Left = 0;
    private int bg3Left = 0;
    private int bg4Left = 0;
    public int GetBGPosX(){ return BGx; }
    public void SetBGPosX(int newBGy){ BGx = newBGy; }

    public Bitmap Bg1;
    public Bitmap Bg2;

    public int newScale;


public void Start(Canvas canvas,View v)
{
    canvasWidth = canvas.getWidth();
    canvasHeight = canvas.getHeight();
    ///GET CANVAS HEIGHT
//    canvasHeight = canvas
    Bg1BitMap = BitmapFactory.decodeResource(v.getResources(), R.drawable.background1);
    Bg2BitMap = BitmapFactory.decodeResource(v.getResources(), R.drawable.background1);

    Log.d("Width","Canvas: " + canvasWidth);
    Bg1 = Bitmap.createScaledBitmap(Bg1BitMap, canvasWidth, canvasHeight +200 , false);
    Bg2 = Bitmap.createScaledBitmap(Bg2BitMap, canvasWidth, canvasHeight +200, false);
    bg1Left = 0;
    bg2Left = Bg1.getScaledWidth(canvas);
    newScale =  Bg1.getScaledWidth(canvas);


}
    public void drawBackgrounds(Canvas canvas, View v){
        bg1Left-=50;
        bg2Left-=50;

        if((bg1Left <= 0 - newScale)) {
            bg1Left = 0 + newScale;
           // Log.d("Width","Bg1Left: " + bg1Left);
            Log.e("asda", "BG1");
            Log.e("asda", "newpos" + bg1Left);
        }
        if((bg2Left <= 0 - newScale)) {
            bg2Left = 0 + newScale;
            Log.e("asda", "BG2");
            Log.e("asda", "newpos" + bg2Left);
        }
//
//        if((bg2Left <= 0 - canvasWidth*2))
//        { bg2Left = 0+ canvasWidth * 2 ;
//            Log.e("asda", "BG1");}
//
//        if((bg2Left <= 0 - canvasWidth*2))
//        { bg2Left = 0+ canvasWidth * 2 ;
//            Log.e("asda", "BG1");}

        canvas.drawBitmap(Bg1, bg1Left, 0,null);
        canvas.drawBitmap(Bg2, bg2Left, 0,null);

    }
}
