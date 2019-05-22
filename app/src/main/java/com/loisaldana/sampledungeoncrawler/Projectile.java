package com.loisaldana.sampledungeoncrawler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public Projectile (Context context)
    {
        bulletBitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet1);
        bulletBitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet2);
        bulletBitmap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet3);
        bulletBitmap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet4);
    }

    public void onDraw(Canvas canvas, int posX, int posY)
    {
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
