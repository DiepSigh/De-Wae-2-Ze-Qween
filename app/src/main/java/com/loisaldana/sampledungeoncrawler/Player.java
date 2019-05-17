package com.loisaldana.sampledungeoncrawler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


public class Player  {

    private AudioManager audioManager = new AudioManager();

    Context gameViewContext;
    Bitmap playerBitmap[] = new Bitmap[6];
    Bitmap playerCurrentBitmap;
    Bitmap HPBitmap;
    Bitmap playerScoreSprite;
    Bitmap playerDamageSprite;
    Bitmap hitSprite;

    public boolean playerHasCannon = true;
    public boolean playerShots = false;
    public boolean hitSpriteIsActive = false;
    public boolean damageSpriteIsActive = false;
    public int damageSpriteCounter = 0;

    public int spriteHitX;
    public int spriteHitY;

    public int spriteDamageX;
    public int spriteDamageY;

    public int spriteScoreX;
    public int spriteScoreY;

    private int playerX;
    private int playerY;
    public int playerSpeed = 0;
    private int playerSpriteAngle = 0;

    public int maxPlayerY = 0; // top top position
    public int minPlayerY; // bottom bottom position in case to check player's death

    //Player class
    private int playerLife = 3;
    private int playerScore = 0;
    public int playerTempScore;
    private int playerLevel = 1;
    public int playerAmmo = 5;
    private int shotEventCounter = 0;

    public int reload = 0;

    public boolean sprite_wings_up = true;
    public boolean isDead = false;
    int spriteStep = 1;
    int tempBitmap = 0;
    int playerTry = 1;

    public int GetAngleRotation(){
        return playerSpriteAngle;
    }
    public void SetAngleRotation(int newAngle){  playerSpriteAngle = newAngle;}

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

    public int GetPlayerLevel(){ return playerLevel; }
    public void SetPlayerLevel(int newPlayerLevel){ playerLevel = newPlayerLevel; }

    public int GetPlayerPosX(){ return playerX; }
    public void SetPlayerPosX(int newPlayerX){ playerX = newPlayerX; }

    public int GetPlayerPosY(){ return playerY; }
    public void SetPlayerPosY(int newPlayerY){ playerY = newPlayerY; }

    private Paint plScore = new Paint();
    private Paint plLevel = new Paint();
    private Paint plAmmo = new Paint();
    private Paint plLevelMsg = new Paint();

    public int sizeLevelMsg = 10;
    public int timerForLvlMsg = 0;
    public double randomColorMsg;

    void drawDamage(Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, spriteDamageX, spriteDamageY,null);
        damageSpriteMovement();
    }

    void damageSpriteMovement()
    {
        if(spriteDamageY > 0)
        {
            spriteDamageY -= 35;
        }
    }

    //Draw score sprite
    void drawSrore (Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, spriteScoreX, spriteScoreY,null);
        scoreSpriteMovements();
    }

    void scoreSpriteMovements()
    {
        spriteScoreY -= 35;
    }

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
        if(playerY + playerCurrentBitmap.getHeight() > minPlayerY)
        {
            playerSpeed = -45;
            CheckPlayerDeath();

        }

        //Settings for player rotation
        if(playerSpeed < 0 && playerSpriteAngle < - 45 && !playerShots)
        {
            playerSpriteAngle = playerSpriteAngle - 20;
        }
        if(playerSpeed > -5 && playerSpriteAngle <= 35 && !playerShots)
        {
            playerSpriteAngle = playerSpriteAngle + 15;
        }

        if(!playerShots)
        { playerY += playerSpeed; }

        if(playerAmmo > 0)
        {
            reload = reload + 2;
        }

        if(reload >= 360)
        {
            if (playerAmmo > 0)
            {
                shotEventCounter = shotEventCounter + 1;
                playerSpriteAngle = 0;
                playerShots = true;

                if(shotEventCounter >= 5)
                {
                    shotEventCounter = 0;
                    reload = 0;
                    playerAmmo = playerAmmo - 1;
                }
            }
            if (playerAmmo == 0)
            {
                reload = 0;
                playerShots = false;
            }
        }
        if(reload == 8)
        {
            playerShots = false;

        }

        System.out.println("PLAYER'S RELOAD    " + reload);
    }

    //Draw player's lifes
    void drawPlayersLifes(Canvas canvas, Bitmap mapBitmap){

        Bitmap plLife[] = new Bitmap[playerLife];

        for (int i=0; i< plLife.length; i++ ) {

            canvas.drawBitmap(mapBitmap,  canvas.getWidth() - 400 + (i * 100), 25, null);
        }
        //System.out.println("CALL PLAYER's LIFE DRAW");
    }

    //Draw player's stats
    void drawPlayersStats(Canvas canvas, Typeface typeface)
    {
        plScore.setColor(Color.WHITE);
        plScore.setTextSize(64);
        plScore.setTypeface(typeface);
        plScore.setShadowLayer(5,5,5, Color.BLACK);
        plScore.setAntiAlias(true);

        canvas.drawText("Score :  " + playerScore, 100, 100, plScore);

        plLevel.setColor(Color.YELLOW);
        plLevel.setTextSize(64);
        plLevel.setTypeface(typeface);
        plLevel.setShadowLayer(5,5,5, Color.BLACK);
        plLevel.setTextAlign(Paint.Align.CENTER);
        plLevel.setAntiAlias(true);

        canvas.drawText("Level :  " + playerLevel, canvas.getWidth() / 2 - 225 , 100, plLevel);

        if(playerAmmo > 0)
        {plAmmo.setColor(Color.GREEN);}
        else
        {plAmmo.setColor(Color.RED);}
        plAmmo.setTextSize(64);
        plAmmo.setTypeface(typeface);
        plAmmo.setShadowLayer(5,5,5, Color.BLACK);
        plAmmo.setTextAlign(Paint.Align.CENTER);
        plAmmo.setAntiAlias(true);

        canvas.drawText("Ammo :  " + playerAmmo, canvas.getWidth() / 2 + 225, 100, plAmmo);


    }

    //Draw player's level up
    void drawLevelUp(Canvas canvas, Typeface typeface)
    {
        if(randomColorMsg == 0)
        {plLevelMsg.setColor(Color.GREEN);}
        if(randomColorMsg == 1)
        {plLevelMsg.setColor(Color.YELLOW);}
        if(randomColorMsg == 2)
        {plLevelMsg.setColor(Color.RED);}
        if(randomColorMsg == 3)
        {plLevelMsg.setColor(Color.BLUE);}
        if(randomColorMsg == 4)
        {plLevelMsg.setColor(Color.WHITE);}
        plLevelMsg.setTextSize(sizeLevelMsg);
        plLevelMsg.setTypeface(typeface);
        plLevelMsg.setShadowLayer(10,5,5, Color.BLACK);
        plLevelMsg.setTextAlign(Paint.Align.CENTER);
        plLevelMsg.setAntiAlias(true);
        sizeLevelMsg = sizeLevelMsg + 15;
        canvas.drawText("LEVEL UP!", canvas.getWidth()/ 2, canvas.getHeight()/ 2, plLevelMsg);
    }

    //Checking GameOver condition
    void CheckPlayerDeath()
    {

        switch (playerTry)
        {

            case 3:
                audioManager.PlayHit(gameViewContext);
                hitSpriteIsActive = true;
                spriteHitX = playerX;
                spriteHitY = playerY;
                playerLife = playerLife - 1;
                playerTry = playerTry + 1;
                SetAngleRotation(-45);
                isDead = true;
                spriteDamageX = playerCurrentBitmap.getWidth();
                spriteDamageY = playerY;
                break;
            case 2:
                audioManager.PlayHit(gameViewContext);
                hitSpriteIsActive = true;
                spriteHitX = playerX;
                spriteHitY = playerY;
                playerLife = playerLife - 1;
                playerTry = playerTry + 1;
                SetAngleRotation(-45);
                spriteDamageX = playerCurrentBitmap.getWidth();
                spriteDamageY = playerY;
                break;
            case 1:
                audioManager.PlayHit(gameViewContext);
                hitSpriteIsActive = true;
                spriteHitX = playerX;
                spriteHitY = playerY;
                playerLife = playerLife - 1;
                playerTry = playerTry + 1;
                SetAngleRotation(-45);
                damageSpriteIsActive = true;
                spriteDamageX = playerCurrentBitmap.getWidth();
                spriteDamageY = playerY;

                break;
        }

    }

    //Why is this function name so long
    public double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        randomColorMsg = x;
        return x;
    }

    //added by Stephen
    //Checks range of Player and Enemy and lose life
    public void enemyPlayerCheck(Enemy enemy, int enemyX, int enemyY){
        //Check if in range of pixels
        if (!enemy.getHit()) {
            //IF STATEMENT WIP
            if (enemyX <= (playerX + 150) && enemyX >= playerX && enemyY >= playerY && enemyY < (playerY+150)) {
                CheckPlayerDeath();
                enemy.setHit(true);
                playerSpeed = -25;
                hitSpriteIsActive = true;
                spriteHitX = playerX;
                spriteHitY = playerY;
            }
        }
        //Add one score for player (Added by Andrey)
        if(enemy.getX() < playerX && !enemy.passPlayer && !isDead)
        {
            playerScore = playerScore + 10;
            audioManager.PlayScore(gameViewContext);
            if(!enemy.passPlayer)
            {
                spriteScoreX = playerX - playerX / 2;
                spriteScoreY = playerY + playerCurrentBitmap.getHeight() / 2;
            }
            enemy.passPlayer = true;
        }
    }

    public void GameOver(){

    }

}
