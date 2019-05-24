package com.loisaldana.sampledungeoncrawler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Projectile {


    Bitmap bulletBitmap[] = new Bitmap[4];
    Bitmap explosion[] = new Bitmap[5];
    Bitmap bulletCurrentBitmap;
    Bitmap explosionCurrentBitmap;

    public int bulletStartPositionX;
    public int bulletStartPositionY;

    private int bulletX;
    private int bulletY;
    int tempBitmap = 0;
    int spriteStep = 1;
    int bulletSpeed = 25;

    private int explosionX;
    private int explosionY;
    int tempExplosionBitmap = 0;
    int spriteExplosionStep = 1;

    public boolean isActive = false;
    public boolean explosionIsActive = false;
    public boolean setPlayerPosition = false;

    public int GetBulletPosX(){ return bulletX; }
    public void SetBulletPosX(int newBulletX ){ bulletX = newBulletX; }

    public int GetBulletPosY(){ return bulletY; }
    public void SetBulletPosY( int newBulletY ){ bulletY = newBulletY; }

    public int GetExplosionPosX(){ return explosionX; }
    public void SetExplosionPosX(int newExplosionX ){ explosionX = newExplosionX;}

    public int GetExplosionPosY(){ return explosionY; }
    public void SetExplosionPosY( int newExplosionY ){ explosionY = newExplosionY; }

    public Projectile (Context context)
    {
        bulletBitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet1);
        bulletBitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet2);
        bulletBitmap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet3);
        bulletBitmap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet4);

        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_4);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.expl_5);
    }

    public void ProjectileStart(Player character)
    {
        SetBulletPosX(character.GetPlayerPosX());
        SetBulletPosY(character.GetPlayerPosY());
        bulletStartPositionX = GetBulletPosX();
        bulletStartPositionY = GetBulletPosY();
    }

    public void onDraw(Canvas canvas, int posX, int posY)
    {
        //System.out.println("EXPLOSION IS ACTIVE    " + explosionIsActive);
        //Draw bullet here
        if(isActive)
        {
            if(!setPlayerPosition)
            {
                SetBulletPosX(posX + 25);
                SetBulletPosY(posY - 25);
                setPlayerPosition = true;
            }
            if(spriteStep < 4)
            {
                bulletCurrentBitmap = bulletBitmap[tempBitmap];
                drawBullet(canvas, bulletCurrentBitmap);
                tempBitmap = tempBitmap + 1;
                spriteStep = spriteStep + 1;
            }
            if(spriteStep == 4)
            {
                bulletCurrentBitmap = bulletBitmap[tempBitmap];
                drawBullet(canvas, bulletCurrentBitmap);
                tempBitmap = 0;
                spriteStep = 1;
            }
        }

        if(explosionIsActive)
        {
            //System.out.println("EXPLOSION IS ACTIVE");
            if(spriteExplosionStep < 5)
            {
                explosionCurrentBitmap = explosion[tempExplosionBitmap];
                drawExplosion(canvas, explosionCurrentBitmap);
                tempExplosionBitmap = tempExplosionBitmap + 1;
                spriteExplosionStep = spriteExplosionStep + 1;
            }
            if(spriteExplosionStep == 5)
            {

                explosionCurrentBitmap = explosion[tempExplosionBitmap];
                drawExplosion(canvas, explosionCurrentBitmap);
                tempExplosionBitmap = 0;
                spriteExplosionStep = 1;
                explosionX = bulletX;
                explosionY = bulletY;
                explosionIsActive = false;
            }
        }
    }


    void drawExplosion(Canvas canvas, Bitmap mapBitmap)
    {
        canvas.drawBitmap(mapBitmap, explosionX, explosionY,null);
    }

    void drawBullet(Canvas canvas, Bitmap mapBitmap){

        //System.out.println("BULLET X    " + bulletX);
        //System.out.println("BULLET Y    " + bulletY);

        canvas.drawBitmap(mapBitmap, bulletX, bulletY,null);

        //Reset bullet position
        if(bulletX > canvas.getWidth() + bulletCurrentBitmap.getWidth())
        {
            isActive = false;
            bulletX = 0;
            bulletY = 0;
            bulletSpeed = 5;
            setPlayerPosition = false;
        }

        Update();
    }

    void Update()
    {
        bulletSpeed = bulletSpeed + 5;
        bulletX += bulletSpeed;

    }
}
