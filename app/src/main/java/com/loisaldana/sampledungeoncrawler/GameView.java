package com.loisaldana.sampledungeoncrawler;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/*
Created by Canados
*/

public class GameView extends View {

    Activity activity;

    Context gameViewContext;
    private int canvasWidth;
    private int canvasHeight;
    boolean gameRun = false;
    Typeface fontFaceLevel;
    Button button;

    Player character;
    Weapon laserCannon;
    Projectile bullet; // creating bullet object
    AudioManager audioManager = new AudioManager();
    Loot coin =  new Loot(); // creates items object
    Bitmap mapBitmap; // this is bitmap we using for background

    Enemy enemy;
    Enemy tails;

    public GameView(Context context) {
        super(context);
        gameViewContext = context;
        coin.coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin3);
        coin.scoreCoin = BitmapFactory.decodeResource(getResources(), R.drawable.point20);

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
        character.onDraw(canvas, enemy, tails, laserCannon);

        //Draws and respawns enemy when it reaches the end
        enemy.draw(canvas, character.GetPlayerPosY());
        if (tails.getActive()) {
            int temp = (int)tails.RNG(50, canvasHeight-500);
            tails.draw(canvas, temp);
        }

        bullet.onDraw(canvas, bullet.GetBulletPosX(), bullet.GetBulletPosY());
        laserCannon.drawButtonBG(canvas, character);
        laserCannon.drawButtonWeapon(canvas, canvasWidth, canvasHeight, character.shotIsReady, character.playerHasCannon);
        laserCannon.drawTextButton(canvas, character);

        if(!coin.isActive && !coin.onReset){
            coin.canvasW = canvas.getWidth();
            coin.cX = coin.canvasW;
            coin.getRandomY(0, canvas.getHeight());
        }
        if(!coin.onReset){
            coin.drawCoin(canvas,coin.coin);
        }
        if(coin.col){
            coin.drawScore(canvas, coin.scoreCoin);
            coin.col = false;
        }
        if(!gameRun)
        {OnStart(); gameRun = true;}


    }

    void OnStart()
    {
        character.fontFaceLevel = fontFaceLevel;
        character.PlayerStart();
        bullet.ProjectileStart(character);
        laserCannon.WeaponStart(character);
        audioManager.PlayBgTheme(gameViewContext);
    }

    //Update function is here
    void Update()
    {

        //hitbox check with enemy and tails
        character.enemyPlayerCheck(enemy, enemy.getX(), enemy.getY());
        character.enemyPlayerCheck(tails, tails.getX(), tails.getY());

        //hitbox check with enemy and projectile
        enemy.BulletCheck(bullet.GetBulletPosX(), bullet.GetBulletPosY(), bullet, character, audioManager, character.gameViewContext);
        tails.BulletCheck(bullet.GetBulletPosX(), bullet.GetBulletPosY(), bullet, character, audioManager, character.gameViewContext);

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

        if(character.isDead)
        {
            audioManager.bgTheme.stop();
        }
        coin.playerSpriteX = character.GetPlayerPosX();
        coin.playerSpriteY = character.GetPlayerPosY();
        coin.playerSpriteWidth = character.GetPlayerPosX() + 100;
        coin.playerSpriteHeight = character.GetPlayerPosY() + 100;
        coin.CheckCollision(character);
        coin.update();
    }

    //If we touch screen we changing player's movement...
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int)event.getX();
        int y = (int)event.getY();

        if(x > laserCannon.buttonX && y > laserCannon.buttonY && x < laserCannon.buttonX + laserCannon.buttonCurrent.getWidth() &&
                y < laserCannon.buttonY + laserCannon.buttonCurrent.getHeight() && character.reload >= 360 && !laserCannon.weaponButtonClicked)
        {
            bullet.SetBulletPosX(character.GetPlayerPosX());
            bullet.SetBulletPosY(character.GetPlayerPosY() + 50);
            bullet.isActive = true;
            audioManager.PlayBullet(gameViewContext);
            character.playerShots = true;
            character.shotIsReady = false;
            laserCannon.weaponButtonClicked = true;
            //System.out.println("BUTTON IS PRESSED");
        }

        //Reset Button
        if(x > character.buttonPlayAgainX && y > character.buttonPlayAgainY && x < character.buttonPlayAgainX + character.playAgain.getWidth() &&
               y < character.buttonPlayAgainY + character.playAgain.getHeight() && character.isDead && !character.buttonPlayerAgainIsPressed)
        {

            audioManager.PlayRestart(gameViewContext);
            character.buttonPlayerAgainIsPressed = true; // <--- we need to turn it back to false after
            PlayAgain();
        }

        //Exit Button
        if(x > character.buttonExitX && y > character.buttonExitY && x < character.buttonExitX + character.exit.getWidth() &&
                y < character.buttonExitY + character.exit.getHeight() && character.isDead)
        {
            audioManager.PlayRestart(gameViewContext);
            activity.finish();
            System.exit(0);
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

    //Reset all
    void PlayAgain() {
        tails.ResetAll();
        tails.setActive(false);
        enemy.ResetAll();
        character.ResetAll();
        character.buttonPlayerAgainIsPressed = false;
    }
}