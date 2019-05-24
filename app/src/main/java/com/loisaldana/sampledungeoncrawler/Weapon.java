package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Weapon {

    Bitmap weaponBitmap;
    Bitmap weaponReadyIcon;
    Bitmap weaponActiveButton;
    Bitmap weaponNotactiveButton;
    Bitmap buttonCurrent;

    public int buttonX;
    public int buttonY;

    public boolean weaponButtonClicked = false;
    public boolean weaponIsReady = false;
    private int weaponX;
    private int weaponY;

    private  Canvas indicatorCanvas;
    private Canvas buttonBGCanvas;
    private Canvas textBGCanvas;
    private Paint indicatorWeapon = new Paint();
    private Paint buttonBG = new Paint();
    private Paint textButton = new Paint();

    public int GetWeaponPosX(){ return weaponX; }
    public void SetWeaponPosX(int newWeaponX){ weaponX = newWeaponX; }

    public int GetWeaponPosY(){ return weaponY; }
    public void SetWeaponPosY(int newWeaponY){ weaponY = newWeaponY; }


    //Constructor
    public Weapon(Context context)
    {
        weaponBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_cannon);
        weaponReadyIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bicon);
        weaponActiveButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_bt_ac);
        weaponNotactiveButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_bt_bw);
    }


    public void WeaponStart(Player character)
    {
        SetWeaponPosX(character.GetPlayerPosX());
        SetWeaponPosY(character.GetPlayerPosY());
    }

    //Draw button on canvas
    void drawButtonWeapon(Canvas canvas, int x, int y, boolean status, boolean statusCharacter)
    {
        buttonX = x - 200;
        buttonY = y - 200;

        //System.out.println(status);

        if(statusCharacter)
        {
            if(!weaponButtonClicked && status)
            {
                buttonCurrent = weaponActiveButton;
                canvas.drawBitmap(weaponActiveButton, buttonX, buttonY,null);
            }
            else
            {
                buttonCurrent = weaponNotactiveButton;
                canvas.drawBitmap(weaponNotactiveButton, buttonX, buttonY,null);
                if(!status)
                {
                    weaponButtonClicked = false;
                }
            }
        }

    }

    void drawButtonBG(Canvas canvas, Player character)
    {
        if(character.playerHasCannon)
        {
            buttonBGCanvas = canvas;


            if(weaponIsReady)
            {
                buttonBG.setColor(Color.RED);
                buttonBG.setAlpha(150);

            }
            else
            {

                buttonBG.setColor(Color.LTGRAY);
                buttonBG.setAlpha(50);
            }

            buttonBG.setShadowLayer(5,5,5, Color.BLACK);
            buttonBGCanvas.drawCircle(buttonX + 90,buttonY + 90,90, buttonBG);


        }

    }

    void drawTextButton(Canvas canvas, Player character)
    {
        textBGCanvas = canvas;
        if(weaponIsReady)
        {
            textButton.setColor(Color.YELLOW);
            textButton.setTextSize(60);
            textButton.setTypeface(character.fontFaceLevel);
            textButton.setShadowLayer(5,5,5, Color.BLACK);
            textButton.setAntiAlias(true);

            textBGCanvas.drawText("FIRE", buttonX + 25, buttonY + 115, textButton);
        }

    }

    //Draw weapon function
    void drawWeapon(Canvas canvas, Bitmap mapBitmap){

        canvas.drawBitmap(mapBitmap, weaponX, weaponY,null);
    }

    //Draw weapon indicator function
    void drawWeaponReadyIcon(Canvas canvas, Bitmap mapBitmap, int x,int y){

        canvas.drawBitmap(mapBitmap, x, y,null);
    }



    //Draw weapon indicator on HUD
    void drawIndicator(Canvas canvas, int x, int y, int r){

        indicatorCanvas = canvas;

        indicatorWeapon.setColor(Color.WHITE);
        indicatorCanvas.drawCircle(x + 50,y + 50,55,indicatorWeapon);
            if(r < 360)
            {
                indicatorWeapon.setColor(Color.RED);
                weaponIsReady = false;
            }
            else
            {
                indicatorWeapon.setColor(Color.GREEN);
                weaponIsReady = true;
            }

        indicatorWeapon.setShadowLayer(5,5,5, Color.BLACK);
        indicatorCanvas.drawArc(x,y, x+100, y+100, 0, r, true, indicatorWeapon);

        }



}
