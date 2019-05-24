package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/*
Created by Canados
*/

public class GameView extends View {

    Context gameViewContext;
    private int canvasWidth;
    private int canvasHeight;
    boolean gameRun = false;
    Typeface fontFaceLevel;


    Player character;
    Weapon laserCannon;
    Projectile bullet; // creating bullet object
    AudioManager audioManager = new AudioManager();
    Bitmap mapBitmap; // this is bitmap we using for background

    Enemy enemy;
    Enemy tails;

    public GameView(Context context) {
        super(context);
        gameViewContext = context;

        character = new Player(context);
        laserCannon = new Weapon(context);
        bullet = new Projectile(context);
        mapBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.map);
        enemy = new Enemy(context, true);
        tails = new Enemy(context, false);
     }

    /* Here we can add images that we want to draw. This function also updates */
    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth(); // if we needed to reference to it
        canvasHeight = canvas.getHeight(); // if we needed to reference to it

        canvas.drawBitmap(GetResizeBitmap(mapBitmap, canvasWidth, canvasHeight), 0, 0, null); // call background image on canvas with resize
        character.onDraw(canvas);

        if(enemy.passPlayer && !character.isDead)
        {
            character.drawSrore(canvas, character.playerScoreSprite); // call player's score on HUD
        }

        if(character.damageSpriteIsActive && character.spriteDamageY > 0  && !character.isDead)
        {
            character.drawDamage(canvas, character.playerDamageSprite);
        }

        //Draws and respawns enemy when it reaches the end
        enemy.draw(canvas, character.GetPlayerPosY());
        if (tails.getActive()) {
            int temp = (int)tails.RNG(50, canvasHeight-500);
            tails.draw(canvas, temp);
        }


        bullet.onDraw(canvas, character.GetPlayerPosX(), character.GetPlayerPosY());
        laserCannon.drawButtonWeapon(canvas, canvasWidth, canvasHeight, character.shotIsReady, character.playerHasCannon);

        //Draw Weapon sprite
        if(character.playerHasCannon && !character.isDead)
        {
            laserCannon.drawWeapon(canvas, GetRotateBitmap(laserCannon.weaponBitmap, character.GetAngleRotation()));
        }

        if(character.playerAmmo >0)
        {
            laserCannon.drawIndicator(canvas, canvasWidth/2 + 425, 25, character.reload);
            if(laserCannon.weaponIsReady)
            {
                laserCannon.drawWeaponReadyIcon(canvas, laserCannon.weaponReadyIcon, canvasWidth/2 + 405, 20);
            }
        }

        // Draw score sprite on HUD
        if(character.playerTempScore == character.GetPlayerScore() - 100 && character.timerForLvlMsg < 20)
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

            //Increases Speed every level
            enemy.increaseVel(4);

            //Set tails to active when x level and increase vel if already active
            if (tails.getActive()){
                tails.increaseVel(3);
            }else {
                if (character.GetPlayerLevel() == 2) {
                    tails.setActive(true);
                }
            }
        }

        //System.out.println("PLAYER'S REAL SCORE  " + character.GetPlayerScore());
        character.drawPlayersLifes(canvas, character.HPBitmap); // call player's HP on HUD
        character.drawPlayersStats(canvas, fontFaceLevel); // call player's score on HUD and passing font Family

        if(!gameRun)
        {OnStart(); gameRun = true;}

    }

    void OnStart()
    {
        character.SetPlayerPosX(character.playerCurrentBitmap.getWidth() - character.playerCurrentBitmap.getWidth() / 2); // here we define start position for player on X
        character.SetPlayerPosY(0); // here we define start position for player on Y
        character.SetPlayerSpeed(0);
        laserCannon.SetWeaponPosX(character.GetPlayerPosX());
        laserCannon.SetWeaponPosY(character.GetPlayerPosY());
        bullet.SetBulletPosX(character.GetPlayerPosX());
        bullet.SetBulletPosY(character.GetPlayerPosY());
        bullet.bulletStartPositionX = bullet.GetBulletPosX();
        bullet.bulletStartPositionY = bullet.GetBulletPosY();
        character.playerTempScore = character.GetPlayerScore();
        character.getRandomIntegerBetweenRange(0,4);
        audioManager.PlayBgTheme(gameViewContext);
    }

    //Update function is here
    void Update()
    {

        System.out.println(bullet.isActive);
        //hitbox check with enemy and tails
        character.enemyPlayerCheck(enemy, enemy.getX(), enemy.getY());
        character.enemyPlayerCheck(tails, tails.getX(), tails.getY());

        //hitbox check with enemy and projectile
        enemy.BulletCheck(bullet.GetBulletPosX(), bullet.GetBulletPosY(), bullet);
        tails.BulletCheck(bullet.GetBulletPosX(), bullet.GetBulletPosY(), bullet);

        if(character.GetPlayerSpeed() < 0 && !character.isDead)
        {
            audioManager.PlayPlayerMoveUp(gameViewContext);
        }
        if(character.playerHasCannon)
        {
            laserCannon.SetWeaponPosX(character.GetPlayerPosX());
            laserCannon.SetWeaponPosY(character.GetPlayerPosY());
        }

            character.SetPlayerSpeed(character.GetPlayerSpeed() + 2); // imitation player's gravity.

    }

    //If we touch screen we changing player's movement...
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int)event.getX();
        int y = (int)event.getY();

        if(x > laserCannon.buttonX && y > laserCannon.buttonY && x < laserCannon.buttonX + laserCannon.buttonCurrent.getWidth() &&
                y < laserCannon.buttonY + laserCannon.buttonCurrent.getHeight() && character.reload >= 360)
        {
            bullet.SetBulletPosX(character.GetPlayerPosX());
            bullet.SetBulletPosY(character.GetPlayerPosY() + 50);
            bullet.isActive = true;
            audioManager.PlayBullet(gameViewContext);
            laserCannon.weaponButtonClicked = true;
            character.playerShots = true;
            character.shotIsReady = false;
            //System.out.println("BUTTON IS PRESSED");
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN && !character.playerShots)
        {

            character.SetPlayerSpeed(-30);
            character.SetAngleRotation(-35);

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

    //Sprite rotation
    public static Bitmap GetRotateBitmap(Bitmap src, float degree)
    {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.setRotate(degree, src.getWidth()/2, src.getHeight()/2);
        Bitmap rotateBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return rotateBitmap;
    }


}


