package com.loisaldana.sampledungeoncrawler;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioManager {

    MediaPlayer bgTheme;
    MediaPlayer playerUP;
    MediaPlayer hit;
    MediaPlayer score;
    MediaPlayer level;
    MediaPlayer bullet;
    MediaPlayer reload;

    public boolean realodIsPlayed = false;

    private int playerMoveUp = 0;

    public void PlayBgTheme(Context context)
    {
        if(bgTheme == null)
        {
            bgTheme = MediaPlayer.create(context, R.raw.bgtheme);
        }
        bgTheme.start();
        bgTheme.setLooping(true);
    }


    public void PlayPlayerMoveUp(Context context)
    {
        if(playerUP == null)
        {
            playerUP = MediaPlayer.create(context, R.raw.player_up);
        }
        if(playerMoveUp == 3)
        {
            playerUP.start();
            playerMoveUp = 0;
        }
        playerMoveUp = playerMoveUp + 1;
    }

    public void PlayHit(Context context)
    {
        if(hit == null)
        {
            hit = MediaPlayer.create(context, R.raw.hit);
        }
        hit.start();
    }

    public void PlayScore(Context context)
    {
        if(score == null)
        {
            score = MediaPlayer.create(context, R.raw.score);
        }
        score.start();
    }


    public void PlayLevel(Context context)
    {
        if(level == null)
        {
            level = MediaPlayer.create(context, R.raw.level);
        }
        level.setLooping(false);
        level.start();

    }

    public void PlayBullet(Context context)
    {
        if(bullet == null)
        {
            bullet = MediaPlayer.create(context, R.raw.bullet);
        }
        bullet.setLooping(false);
        bullet.start();
    }

    public void PlayReload(Context context)
    {
        if(reload == null)
        {
            reload = MediaPlayer.create(context, R.raw.reload);
        }
        if(!realodIsPlayed)
        {
            reload.setLooping(false);
            reload.start();
            realodIsPlayed = true;
        }

    }

    public void Pause()
    {

    }

    public void Stop()
    {

    }
}


