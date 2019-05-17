package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

/*
Created by Canados
*/

public class GameView extends View {

    Context gameViewContext;
    private int canvasWidth;
    private int canvasHeight;
    boolean gameRun = false;
    Typeface fontFaceLevel;

    Player character = new Player(); // creating player object here
    Loot coin =  new Loot(); // creates items object
    AudioManager audioManager= new AudioManager();
    Bitmap mapBitmap; // this is bitmap we using for background

    Enemy enemy = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.sonic));

    public GameView(Context context) {
        super(context);
        gameViewContext = context;
        character.gameViewContext= context;
        character.playerBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.knuckles1);
        character.playerBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.knuckles2);
        character.playerBitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.knuckles3);
        character.playerBitmap[3] = BitmapFactory.decodeResource(getResources(), R.drawable.knuckles4);
        character.playerBitmap[4] = BitmapFactory.decodeResource(getResources(), R.drawable.knuckles5);
        character.playerBitmap[5] = BitmapFactory.decodeResource(getResources(), R.drawable.knuckles6);
        character.HPBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        character.playerScoreSprite = BitmapFactory.decodeResource(getResources(), R.drawable.point80);
        character.playerDamageSprite = BitmapFactory.decodeResource(getResources(), R.drawable.damage150);
        character.hitSprite = BitmapFactory.decodeResource(getResources(), R.drawable.hit200);
        mapBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.map);
        coin.coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin3);

     }

    /* Here we can add images that we want to draw. This function also updates */
    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth(); // if we needed to reference to it
        canvasHeight = canvas.getHeight(); // if we needed to reference to it


        canvas.drawBitmap(GetResizeBitmap(mapBitmap, canvasWidth, canvasHeight), 0, 0, null); // call background image on canvas with resize


        //Draw player's spite with animation
        if(character.sprite_wings_up && !character.isDead)
        {
            if(character.spriteStep == 1 || character.spriteStep == 3 || character.spriteStep == 5 && character.GetPlayerSpeed() < 0)
            {
                character.playerCurrentBitmap = character.playerBitmap[character.tempBitmap];
                character.drawPlayer(canvas, GetRotateBitmap(character.playerBitmap[character.tempBitmap], character.GetAngleRotation()));
                character.sprite_wings_up = false;
                character.spriteStep = character.spriteStep + 1;
                character.tempBitmap = character.tempBitmap + 1;

            }
            else
            {
                character.drawPlayer(canvas, GetRotateBitmap(character.playerBitmap[character.tempBitmap], character.GetAngleRotation()));
            }

        }
        else if(!character.sprite_wings_up && !character.isDead)
        {
            if(character.spriteStep == 2 || character.spriteStep == 4 && character.GetPlayerSpeed() < 0)
            {
                character.playerCurrentBitmap = character.playerBitmap[character.tempBitmap];
                character.drawPlayer(canvas, GetRotateBitmap(character.playerBitmap[character.tempBitmap], character.GetAngleRotation()));
                character.sprite_wings_up = true;
                character.spriteStep = character.spriteStep + 1;
                character.tempBitmap = character.tempBitmap + 1;
            }
            else if(character.spriteStep == 6 && character.GetPlayerSpeed() < 0)
            {
                character.playerCurrentBitmap = character.playerBitmap[character.tempBitmap];
                character.drawPlayer(canvas, GetRotateBitmap(character.playerBitmap[character.tempBitmap], character.GetAngleRotation()));
                character.sprite_wings_up = true;
                character.spriteStep = 1;
                character.tempBitmap = 0;

            }
            else
            {
                character.drawPlayer(canvas, GetRotateBitmap(character.playerBitmap[character.tempBitmap], character.GetAngleRotation()));
            }
        }

        character.drawPlayersLifes(canvas, character.HPBitmap); // call player's HP on HUD
        character.drawPlayersStats(canvas, fontFaceLevel); // call player's score on HUD and passing font Family

        if(enemy.passPlayer && !character.isDead)
        {
            character.drawSrore(canvas, character.playerScoreSprite); // call player's score on HUD
        }

        if(character.damageSpriteIsActive && character.spriteDamageY > 0  && !character.isDead)
        {
            character.drawDamage(canvas, character.playerDamageSprite);
        }

        enemy.draw(canvas);

        if(character.hitSpriteIsActive)
        {

            if(character.damageSpriteCounter < 3)
            {

                canvas.drawBitmap(character.hitSprite, character.spriteHitX, character.spriteHitY, null);
                character.damageSpriteCounter = character.damageSpriteCounter + 1;
            }
            if(character.damageSpriteCounter >= 3)
            {
                character.damageSpriteCounter = 0;
                character.hitSpriteIsActive = false;
            }

        }

        if(character.playerTempScore == character.GetPlayerScore() - 10 && character.timerForLvlMsg < 20)
        {
            character.drawLevelUp(canvas, fontFaceLevel);
            character.timerForLvlMsg = character.timerForLvlMsg + 1;
            audioManager.PlayLevel(gameViewContext);
        }
        if(character.timerForLvlMsg >= 20)
        {
            character.timerForLvlMsg = 0;
            character.sizeLevelMsg = 10;
            character.playerTempScore = character.GetPlayerScore();
            character.SetPlayerLevel(character.GetPlayerLevel() + 1);
            character.getRandomIntegerBetweenRange(0,3);
        }

        if(!coin.isActive && !coin.onReset){
            coin.canvasW = canvas.getWidth();
            coin.cX = coin.canvasW;
            coin.getRandomY(0, canvas.getHeight());
        }

        if(!coin.onReset){
            coin.drawCoin(canvas,coin.coin);
        }


        //System.out.println("PLAYER'S REAL SCORE  " + character.GetPlayerScore());

        if(!gameRun)
        {OnStart(); gameRun = true;}


    }

    //Start is here (we can deleted if we don't need it)
    void OnStart()
    {

        character.SetPlayerPosX(character.playerCurrentBitmap.getWidth() - character.playerCurrentBitmap.getWidth() / 2); // here we define start position for player on X
        character.SetPlayerPosY(0); // here we define start position for player on Y
        character.SetPlayerSpeed(0);
        character.playerTempScore = character.GetPlayerScore();
        character.getRandomIntegerBetweenRange(0,4);
        audioManager.PlayBgTheme(gameViewContext);
    }

    //Update function is here
    void Update()
    {
        character.enemyPlayerCheck(enemy, enemy.getX(), enemy.getY());
        //System.out.println("PLAYER'S X    " + character.GetPlayerPosX());
        character.SetPlayerSpeed(character.GetPlayerSpeed() + 2); // imitation player's gravity.

        if(character.GetPlayerSpeed() < 0 && !character.isDead)
        {
            audioManager.PlayPlayerMoveUp(gameViewContext);
        }

        if(coin.onReset){
            coin.update();
        }
    }

    //If we touch screen we changing player's movement...
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            character.SetPlayerSpeed(-30);
            character.SetAngleRotation(-45);

        }
        return true;
    }

    // Function for resizing the bitmap before drawing on the canvas
    public Bitmap GetResizeBitmap(Bitmap bm, int newWidth, int newHeight)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth / width);
        float scaleHeight = ((float) newHeight / height);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizeBitmap = Bitmap.createBitmap(bm, 0,0,width, height, matrix, false);
        return resizeBitmap;
    }


    //Player sprite rotation
    public static Bitmap GetRotateBitmap(Bitmap src, float degree)
    {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        Bitmap rotateBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return rotateBitmap;
    }

}


