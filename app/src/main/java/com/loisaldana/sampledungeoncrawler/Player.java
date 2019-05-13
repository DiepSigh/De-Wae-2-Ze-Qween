package com.loisaldana.sampledungeoncrawler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


public class Player  {

    Bitmap playerBitmap[] = new Bitmap[6];
    Bitmap playerCurrentBitmap;
    Bitmap HPBitmap;

    private int playerX;
    private int playerY = 0;
    private int playerSpeed = 0;

    public int maxPlayerY = 0; // top top position
    public int minPlayerY; // bottom bottom position in case to check player's death

    //Player class
    private int playerLife = 3;
    private int playerScore = 0;

    public boolean sprite_wings_up = true;
    int spriteStep = 1;
    int tempBitmap = 0;
    int playerTry = 1;


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
    public void SetPlayerPosX(int newPlayerX){ playerX = newPlayerX; }

    public int GetPlayerPosY(){ return playerY; }
    public void SetPlayerPosY(int newPlayerY){ playerY = newPlayerY; }


    private Paint plScore = new Paint();


    //Draw player function
    void drawPlayer(Canvas canvas, Bitmap mapBitmap){

        minPlayerY = canvas.getHeight();
        canvas.drawBitmap(mapBitmap, playerX, playerY,null);
        PlayerMovements();
    }

    //All player's movements are here
    void PlayerMovements()
    {

        //if player's sprite hits top top position we switch speed backward
        if(playerY < maxPlayerY - playerCurrentBitmap.getHeight() / 2)
        {
            playerSpeed = 5;
        }
        if(playerY + playerCurrentBitmap.getHeight() / 2 > minPlayerY)
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
        //System.out.println("CALL PLAYER's LIFE DRAW");
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

        switch (playerTry)
        {

            case 3:
                playerLife = playerLife - 1;
                playerTry = playerTry + 1;
                break;
            case 2:
                playerLife = playerLife - 1;
                playerTry = playerTry + 1;
                break;
            case 1:
                playerLife = playerLife - 1;
                playerTry = playerTry + 1;
                break;
        }


    }


    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

}
