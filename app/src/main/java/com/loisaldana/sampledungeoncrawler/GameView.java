package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;


/*
Created by Canados
*/

public class GameView extends View {

    private int canvasWidth;
    private int canvasHeight;
    Canvas canvas;

    Player character = new Player(); // creating player object here
    Bitmap mapBitmap; // this is bitmap we using for background

    Enemy enemy = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.player1));

    public GameView(Context context) {
        super(context);

        character.playerBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
        character.playerBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
        character.HPBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        mapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);

        OnStart(); // if we want to call something else
    }


    /* Here we can add images that we want to draw. This function also updates */
    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth(); // if we needed to reference to it
        canvasHeight = canvas.getHeight(); // if we needed to reference to it

        canvas.drawBitmap(mapBitmap, 0, 0, null); // call background image on canvas

        //Draw player's spite with animation
        if(character.sprite_wings_up)
        {
            character.playerCurrentBitmap = character.playerBitmap[0];
            character.drawPlayer(canvas, character.playerBitmap[0]); // sprite #1
            character.sprite_wings_up = false;
        }
        else
        {   character.playerCurrentBitmap = character.playerBitmap[0];
            character.drawPlayer(canvas, character.playerBitmap[1]); // sprite #2
            character.sprite_wings_up = true;
        }
        character.drawPlayersLifes(canvas, character.HPBitmap); // call player's HP on HUD
        character.drawPlayersStats(canvas); // call player's score on HUD

        //Draw enemy
        enemy.draw(canvas);
    }


    //Start is here (we can deleted if we don't need it)
    void OnStart()
    {

    }

    //Update function is here
    void Update()
    {

        System.out.println(character.GetLife());
        character.SetPlayerSpeed(character.GetPlayerSpeed() + 2); // imitation player's gravity
        character.SetPlayerScore(character.GetPlayerScore() + 5); // Testing Get() Set() for player's score
    }


    //If we touch screen we changing player's movement...
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            character.sprite_wings_up = true;
            character.SetPlayerSpeed(-30);
        }
        return true;
    }
}


