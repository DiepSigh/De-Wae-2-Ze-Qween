package com.loisaldana.sampledungeoncrawler;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Projectile {


    Bitmap bulletBitmap[] = new Bitmap[4];
    Bitmap bulletCurrentBitmap;


    private int bulletX;
    private int bulletY;
    int tempBitmap = 0;
    int spriteStep = 1;
    int bulletSpeed = 25;

    public boolean isActive = false;
    public boolean setPlayerPosition = false;

    public int GetBulletPosX(){ return bulletX; }
    public void SetBulletPosX(int newBulletX ){ bulletX = newBulletX; }

    public int GetBulletPosY(){ return bulletY; }
    public void SetBulletPosY( int newBulletY ){ bulletY = newBulletY; }

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
