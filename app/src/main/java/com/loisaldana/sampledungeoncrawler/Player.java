package com.loisaldana.sampledungeoncrawler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


public class Player  {

    Bitmap playerBitmap[] = new Bitmap[2];
    Bitmap playerCurrentBitmap;
    Bitmap HPBitmap;

    private int playerX = 50;
    private int playerY = 50;
    private int playerSpeed = 20;

    public int maxPlayerY = 0; // top top position
    public int minPlayerY = 900; // bottom bottom position in case to check player's death

    //Player class
    private int playerLife = 3;
    private int playersHP = 100;
    private int playerScore = 0;

    public boolean sprite_wings_up = false;
    boolean chanceOne = false;
    boolean chanceTwo = false;
    boolean chanceThree = false;

    public void SetPlayerHP(int newHP){ playersHP = newHP; }
    public int GetPlayerHP(){
        return playersHP;
    }

    public void SetLife(int newLife){  playerLife = newLife;}
    public int GetLife(){
        return playerLife;
    }

    public void SetPlayerSpeed(int newSpeed){
        playerSpeed = newSpeed;
    }
    public int GetPlayerSpeed(){
        return playerSpeed;
    }

    public void SetPlayerScore(int newScore){ playerScore = newScore; }
    public int GetPlayerScore(){ return playerScore; }

    public int GetPlayerPosX(){ return playerX; }
    public int GetPlayerPosY(){ return playerY; }

    private Paint plScore = new Paint();



    //Draw player function
    void drawPlayer(Canvas canvas, Bitmap mapBitmap){


        canvas.drawBitmap(mapBitmap, playerX, playerY,null);

        PlayerMovements();

    }

    //All player's movements are here
    void PlayerMovements()
    {

        //if player's sprite hits top top position we switch speed backward
        if(playerY < maxPlayerY - playerCurrentBitmap.getHeight())
        {
            playerSpeed = 20;
        }
        if(playerY > minPlayerY)
        {
            playerSpeed = -50;
            CheckPlayerDeath();
        }


        playerY += playerSpeed;
    }


    //Draw player's lifes
    void drawPlayersLifes(Canvas canvas, Bitmap mapBitmap){

        Bitmap plLife[] = new Bitmap[playerLife];

        for (int i=0; i< plLife.length; i++ ) {

            canvas.drawBitmap(mapBitmap,  canvas.getWidth() - 400 + (i * 100), 50, null);
        }
        System.out.println("CALL PLAYER's LIFE DRAW");
    }

    //Draw player's stats
    void drawPlayersStats(Canvas canvas)
    {
        plScore.setColor(Color.WHITE);
        plScore.setTextSize(64);
        plScore.setTypeface(Typeface.DEFAULT_BOLD);
        plScore.setAntiAlias(true);

        canvas.drawText("Score : " + playerScore, 100, 100, plScore);

    }

    void CheckPlayerDeath()
    {


        if(chanceOne && chanceTwo && !chanceThree)
        {
            playerLife = playerLife - 1;
            chanceThree = true;

            //here is we activate player's death
        }
        if(chanceOne && !chanceTwo && !chanceThree)
        {
            playerLife = playerLife - 1;
            chanceTwo = true;
        }
        if(!chanceOne && !chanceTwo && !chanceThree)
        {
            playerLife = playerLife - 1;
            chanceOne = true;
        }



    }


}
