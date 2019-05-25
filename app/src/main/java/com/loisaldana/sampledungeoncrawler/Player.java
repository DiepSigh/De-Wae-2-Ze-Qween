package com.loisaldana.sampledungeoncrawler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;


public class Player  {

    private AudioManager audioManager = new AudioManager();
    Typeface fontFaceLevel;

    Context gameViewContext;
    Bitmap playerBitmap[] = new Bitmap[6];
    Bitmap playerCurrentBitmap;
    Bitmap HPBitmap;
    Bitmap playerScoreSprite10;
    Bitmap playerScoreSprite50;
    Bitmap playerDamageSprite;
    Bitmap hitSprite;
    Bitmap gameOver;
    Bitmap playAgain;
    Bitmap exit;

    public boolean playerHasCannon = true;
    public boolean playerShots = false;
    public boolean hitSpriteIsActive = false;
    public boolean damageSpriteIsActive = false;
    public boolean killScoreIsActive = false;
    public boolean levelUp = false;
    public int damageSpriteCounter = 0;

    public int buttonPlayAgainX;
    public int buttonPlayAgainY;

    public int buttonExitX;
    public int buttonExitY;

    public int spriteHitX;
    public int spriteHitY;

    public int spriteDamageX;
    public int spriteDamageY;

    public int spriteScoreX;
    public int spriteScoreY;
    public int spriteScoreTimer = 0;

    public int spriteKillScoreX;
    public int spriteKillScoreY;
    public int spriteKillScoreTimer = 0;

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
    public  int levelLimit = 100;
    public int playerAmmo = 5;
    private int shotEventCounter = 0;
    public boolean shotIsReady = false;
    public int reload = 0;

    public boolean buttonPlayerAgainIsPressed = false;
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


    public void PlayerStart()
    {
        SetPlayerPosX(playerCurrentBitmap.getWidth() - playerCurrentBitmap.getWidth() / 2); // here we define start position for player on X
        SetPlayerPosY(0); // here we define start position for player on Y
        SetPlayerSpeed(0);
        playerTempScore = GetPlayerScore();
        getRandomIntegerBetweenRange(0,4);
    }

    //Constructor
    public Player (Context context)
    {
        gameViewContext = context;
        playerBitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.knuckles1);
        playerBitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.knuckles2);
        playerBitmap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.knuckles3);
        playerBitmap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.knuckles4);
        playerBitmap[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.knuckles5);
        playerBitmap[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.knuckles6);

        HPBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        playerScoreSprite10 = BitmapFactory.decodeResource(context.getResources(), R.drawable.score10);
        playerScoreSprite50 = BitmapFactory.decodeResource(context.getResources(), R.drawable.score50);
        playerDamageSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.damage150);
        hitSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.hit200);
        gameOver = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
        playAgain = BitmapFactory.decodeResource(context.getResources(), R.drawable.playagain);
        exit = BitmapFactory.decodeResource(context.getResources(), R.drawable.exit);
    }


    public void onDraw(Canvas canvas, Enemy enemy, Enemy tails, Weapon laserCannon)
    {
        //Draw GAME OVER MENU
        if(isDead)
        {
            drawGameOver(canvas, gameOver);
            drawPlayAgain(canvas, playAgain);
            drawExit(canvas, exit);
        }

        //Draw player's spite with animation
        if(sprite_wings_up && !isDead)
        {
            if(spriteStep == 1 || spriteStep == 3 || spriteStep == 5 && GetPlayerSpeed() < 0)
            {
                playerCurrentBitmap = playerBitmap[tempBitmap];
                drawPlayer(canvas, GetRotateBitmap(playerBitmap[tempBitmap], GetAngleRotation()));
                sprite_wings_up = false;
                spriteStep = spriteStep + 1;
                tempBitmap = tempBitmap + 1;
            }
            else
            {
                drawPlayer(canvas, GetRotateBitmap(playerBitmap[tempBitmap], GetAngleRotation()));
            }

        }
        else if(!sprite_wings_up && !isDead)
        {
            if(spriteStep == 2 || spriteStep == 4 && GetPlayerSpeed() < 0)
            {
                playerCurrentBitmap = playerBitmap[tempBitmap];
                drawPlayer(canvas, GetRotateBitmap(playerBitmap[tempBitmap], GetAngleRotation()));
                sprite_wings_up = true;
                spriteStep = spriteStep + 1;
                tempBitmap = tempBitmap + 1;
            }
            else if(spriteStep == 6 && GetPlayerSpeed() < 0)
            {
                playerCurrentBitmap = playerBitmap[tempBitmap];
                drawPlayer(canvas, GetRotateBitmap(playerBitmap[tempBitmap], GetAngleRotation()));
                sprite_wings_up = true;
                spriteStep = 1;
                tempBitmap = 0;
            }
            else
            {
                drawPlayer(canvas, GetRotateBitmap(playerBitmap[tempBitmap], GetAngleRotation()));
            }
        }

        //Draw damage sprite on HUD
        if(hitSpriteIsActive)
        {
            if(damageSpriteCounter < 3)
            {
                canvas.drawBitmap(hitSprite, spriteHitX, spriteHitY, null);
                damageSpriteCounter = damageSpriteCounter + 1;
            }
            if(damageSpriteCounter >= 3)
            {
                damageSpriteCounter = 0;
                hitSpriteIsActive = false;
            }

        }

        //Draw Weapon sprite
        if(playerHasCannon && !isDead)
        {
            laserCannon.drawWeapon(canvas, GetRotateBitmap(laserCannon.weaponBitmap, GetAngleRotation()));
        }

        if(playerAmmo >0)
        {
            laserCannon.drawIndicator(canvas, canvas.getWidth()/2 + 425, 25, reload);
            if(laserCannon.weaponIsReady)
            {
                laserCannon.drawWeaponReadyIcon(canvas, laserCannon.weaponReadyIcon, canvas.getWidth()/2 + 405, 20);
            }
        }


        if(damageSpriteIsActive && spriteDamageY > 0  && !isDead)
        {
            drawDamage(canvas, playerDamageSprite);
        }

        if(!isDead && spriteScoreTimer < 10)
        {
            drawSrore(canvas, playerScoreSprite10, enemy, tails); // call player's score on HUD
        }
        drawKillScore(canvas, playerScoreSprite50, enemy, tails);

        //System.out.println("PLAYER'S REAL SCORE  " + character.GetPlayerScore());
        drawPlayersLifes(canvas, HPBitmap); // call player's HP on HUD
        drawPlayersStats(canvas, fontFaceLevel); // call player's score on HUD and passing font Family

        // Draw score sprite on HUD
        if(playerTempScore == GetPlayerScore() - levelLimit)
        {
            levelUp = true;
            audioManager.PlayLevel(gameViewContext);

        }
        if(levelUp && timerForLvlMsg < 20)
        {
            drawLevelUp(canvas, fontFaceLevel);
            timerForLvlMsg = timerForLvlMsg + 1;
        }
        if(timerForLvlMsg >= 20)
        {
            levelUp = false;
            levelLimit = levelLimit + 100;
            timerForLvlMsg = 0;
            sizeLevelMsg = 10;
            playerTempScore = GetPlayerScore();
            getRandomIntegerBetweenRange(0,3);
            SetPlayerLevel(GetPlayerLevel() + 1);
            //Increases Speed every level
            enemy.increaseVel(4);

            //Set tails to active when x level and increase vel if already active
            if (tails.getActive()){
                tails.increaseVel(3);
            }else {
                if (GetPlayerLevel() == 2) {
                    tails.setActive(true);
                }
            }
        }

    }

    void drawExit(Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, canvas.getWidth() / 2 - exit.getWidth() / 2, canvas.getHeight() - exit.getHeight() * 2 + 50,null);
        buttonExitX = canvas.getWidth() / 2 - exit.getWidth() / 2;
        buttonExitY = canvas.getHeight() - exit.getHeight() * 2 + 50;
    }

    void drawPlayAgain(Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, canvas.getWidth() / 2 - playAgain.getWidth() / 2, canvas.getHeight() / 2 + playAgain.getHeight() / 2 + 100,null);
        buttonPlayAgainX = canvas.getWidth() / 2 - playAgain.getWidth() / 2;
        buttonPlayAgainY = canvas.getHeight() / 2 + playAgain.getHeight() / 2 + 100;
    }

    void drawGameOver(Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, canvas.getWidth() / 2 - gameOver.getWidth() / 2, canvas.getHeight() / 2 - gameOver.getHeight() - gameOver.getHeight() / 2,null);

    }

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

    void drawKillScore(Canvas canvas, Bitmap mapBitmap, Enemy enemy, Enemy tails)
    {
        if(killScoreIsActive)
        {
            canvas.drawBitmap(mapBitmap, spriteKillScoreX, spriteKillScoreY,null);
            scoreSpriteMovements(enemy, tails);
        }

    }

    //Draw score sprite
    void drawSrore (Canvas canvas, Bitmap mapBitmap, Enemy enemy, Enemy tails)
    {
        if(enemy.passPlayer || tails.passPlayer)
        {
            canvas.drawBitmap(mapBitmap, spriteScoreX, spriteScoreY,null);
            scoreSpriteMovements(enemy, tails);
        }

    }

    void scoreSpriteMovements(Enemy enemy, Enemy tails)
    {
        if(spriteScoreTimer < 5 && enemy.passPlayer || spriteScoreTimer < 5 && tails.passPlayer)
        {
            spriteScoreY -= 35;
            spriteScoreTimer = spriteScoreTimer +1;
        }
        if(spriteScoreTimer == 5)
        {
            spriteScoreTimer = 0;
            enemy.passPlayer = false;
            tails.passPlayer = false;
        }

        if(killScoreIsActive)
        {
            spriteKillScoreY -=35;
            spriteKillScoreTimer = spriteKillScoreTimer + 1;
        }
        if(spriteKillScoreTimer > 8)
        {
            killScoreIsActive = false;
            spriteKillScoreTimer = 0;
        }
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
            playerSpeed = -55;
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
            reload = reload + 5;
        }

        if(reload >= 360)
        {
            audioManager.PlayReload(gameViewContext);
            shotIsReady = true;
            if (playerAmmo > 0 && playerShots)
            {
                shotEventCounter = shotEventCounter + 1;
                playerSpriteAngle = 0;
                playerSpeed = playerSpeed - 1;

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
        if(reload == 20)
        {
            playerShots = false;
            shotIsReady = false;
            audioManager.realodIsPlayed = false;
        }

        //System.out.println("PLAYER'S RELOAD    " + reload);
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
        if(isDead)
        {
            plScore.setColor(Color.GREEN);
            plScore.setTextSize(128);
            plScore.setTypeface(typeface);
            plScore.setShadowLayer(1,1,1, Color.BLACK);
            plScore.setAntiAlias(true);

            canvas.drawText("" + playerScore, canvas.getWidth() / 2 - 100, canvas.getHeight() / 2, plScore);
        }

        if(!isDead)
        {
            plScore.setColor(Color.WHITE);
            plScore.setTextSize(64);
            plScore.setTypeface(typeface);
            plScore.setShadowLayer(5,5,5, Color.BLACK);
            plScore.setAntiAlias(true);

            canvas.drawText("Score : " + playerScore, 100, 100, plScore);

            plLevel.setColor(Color.YELLOW);
            plLevel.setTextSize(64);
            plLevel.setTypeface(typeface);
            plLevel.setShadowLayer(5,5,5, Color.BLACK);
            plLevel.setTextAlign(Paint.Align.CENTER);
            plLevel.setAntiAlias(true);

            canvas.drawText("Level : " + playerLevel, canvas.getWidth() / 2 - 225 , 100, plLevel);

            if(playerHasCannon)
            {
                if(playerAmmo > 0)
                {plAmmo.setColor(Color.GREEN);}
                else
                {plAmmo.setColor(Color.RED);}
                plAmmo.setTextSize(64);
                plAmmo.setTypeface(typeface);
                plAmmo.setShadowLayer(5,5,5, Color.BLACK);
                plAmmo.setTextAlign(Paint.Align.CENTER);
                plAmmo.setAntiAlias(true);

                canvas.drawText("Ammo : " + playerAmmo, canvas.getWidth() / 2 + 225, 100, plAmmo);
            }
        }
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
                playerHasCannon = false;
                playerAmmo = 0;
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
                playerHasCannon = false;
                playerAmmo = 0;
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
                playerHasCannon = false;
                playerAmmo = 0;
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
            if (enemyX <= (playerX + 150) && enemyX >= playerX && enemyY >= playerY && enemyY < (playerY+150) && !isDead) {
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


    //Player sprite rotation
    public static Bitmap GetRotateBitmap(Bitmap src, float degree)
    {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.setRotate(degree, src.getWidth()/2, src.getHeight()/2);
        Bitmap rotateBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return rotateBitmap;
    }

    //Reset player lives and score
    public void ResetAll(){
        SetPlayerPosX(playerCurrentBitmap.getWidth() - playerCurrentBitmap.getWidth() / 2); // here we define start position for player on X
        SetPlayerPosY(0); // here we define start position for player on Y
        SetPlayerSpeed(0);
        SetPlayerScore(0);
        playerLife = 3;
        isDead = false;
        playerHasCannon = true;
        playerShots = false;
        hitSpriteIsActive = false;
        damageSpriteIsActive = false;
        killScoreIsActive = false;
        levelUp = false;
    }

}
