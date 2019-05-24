package com.loisaldana.sampledungeoncrawler;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.graphics.Rect;


//Added by Loi
public class Loot {


    Bitmap coin;
    Bitmap scoreCoin;
    GameView gmView;
    double rX, rY;
    public boolean isActive = false, onReset = false, col = false;
    private boolean coinHit = true;

    public int playerSpriteX, playerSpriteY, playerSpriteWidth, playerSpriteHeight;

    public int cX, cY, coinSpd = 50;
    public int canvasW;
    public int timerS = 50, timerE = 100, realTimer;



    public void SetCoinB(boolean checkHit){ coinHit = checkHit;}
    public boolean GetCoinB(){return coinHit;}

    void drawCoin(Canvas canvas, Bitmap mapBitmap){

        if(!isActive && !onReset){
            canvas.drawBitmap(mapBitmap, canvasW, (int)rY,null);
            cY = (int)rY;
            isActive = true;
        }else{
            canvas.drawBitmap(mapBitmap, cX, cY,null);

        }
        //update();
        //System.out.println("I'm drawing");

    }

    void drawScore(Canvas canvas, Bitmap bitmap){

        canvas.drawBitmap(bitmap, playerSpriteX-=25, playerSpriteY, null);



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

    public void CheckCollision(Player character){

        //System.out.println("PLAYER X    " + playerSpriteX + "   ");
        //System.out.println("PLAYER Y    " + playerSpriteY + "   ");
        //System.out.println("COIN X    " + cX + "   ");
        //System.out.println("COIN Y    " + cY + "   ");


        //cX >= player.GetPlayerPosX() && cX + 35 <= (player.GetPlayerPosX() + 100) && (cY + 50) <= player.GetPlayerPosY()

        //enemyX <= (playerX + 150) && enemyX >= playerX && enemyY >= playerY && enemyY < (playerY+150)
        if (cX <= (playerSpriteX + 200) && cX >= playerSpriteX && cY >= playerSpriteY && cY < (playerSpriteY + 150) && isActive && !onReset) {

            character.SetPlayerScore(character.GetPlayerScore() + 20);
            isActive = false;
            onReset = true;
            col = true;

        }

           //System.out.println("Player Rect: " + playerRect);

        /*
        //scoreC = player.GetPlayerScore();

        if(cX <= player.GetPlayerPosX() && rY <= player.GetPlayerPosY() + 150){
            coinHit = true;
            System.out.println("I'm working");
        }

        if(coinHit){
            //scoreC += 20;
        }

        //System.out.print("Score: " + player.GetPlayerScore());
        //System.out.print("Player pos XY: " + player.GetPlayerPosY() + " " + player.GetPlayerPosY());
        //System.out.print("  Coin pos: " + cX + " " + rY);*/
    }

    void update(){

        if(cX < 0 - 200){
            isActive = false;
            onReset = true;
            cX = canvasW + coin.getWidth();
            getRandomTimer(timerS, timerE);

        }

        if(isActive && !onReset){
            cX -= coinSpd;
        }

        //System.out.println("Coin X: " + (cX + 35));
        //System.out.println("Coin Y: " + (cY + 50));
        //System.out.println("PLAYER X:   " + playerSpriteWidth + "   ");
        //System.out.println("PLAYER Y:   " + playerSpriteY + "   ");


        //cX <= (player.GetPlayerPosX() + 150) && cX >= player.GetPlayerPosX() && cY >= player.GetPlayerPosY() && cY <= (player.GetPlayerPosY() + 150)
        //cX <= playerSpriteWidth && cY + 100 >= playerSpriteY && isActive && !onReset
        //playerSpriteWidth > cX && playerSpriteY < (cY + 50) || playerSpriteWidth > cX && playerSpriteHeight < cY
        //cX < playerSpriteWidth && (cX + 35) > playerSpriteY && cY < playerSpriteHeight && (cY + 50) > playerSpriteY




        if(onReset && !isActive){
            realTimer = realTimer - 1;
            System.out.println(realTimer);
        }
        if(realTimer <= 0){
            onReset = false;
            realTimer = 0;
        }

        if(col){

        }

        //System.out.print("PLAYER X   " + player.GetPlayerPosX());

        //CheckCollision(gmView.character);
        //System.out.println("yeah  " + isActive + "  " + onReset);
        //System.out.println("Player Score: " + scoreC);
        //System.out.println("Coin X pos: " + cX + " Coin Y pos: " + cY);
        //System.out.println("Character posX: " + player.GetPlayerPosX());
        //System.out.println("Character posY: " + player.GetPlayerPosY());

        //System.out.println("Hit: " + player.spriteHitX + " " + player.spriteHitY);
    }

}

