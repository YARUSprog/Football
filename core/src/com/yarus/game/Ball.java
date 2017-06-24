package com.yarus.game;

import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static com.yarus.game.Constants.*;

/**
 * Created by YARUS
 */

public class Ball {
    Rectangle rect;
    float xVelocity;
    float yVelocity;

    public Ball(){
        xVelocity = 200;
        yVelocity = 200;
        rect = new Rectangle();
        rect.width = BALL_SIZE;
        rect.height = BALL_SIZE;

        Random generator = new Random();
        rect.x = generator.nextInt(SCREEN_WIDTH - BALL_SIZE + 1);
        rect.y = generator.nextInt(SCREEN_HEIGHT - BALL_SIZE + 1);

    }

    public Rectangle getRect(){
        return rect;
    }

    public void update(long fps){
        rect.x = rect.x + (xVelocity / fps);
        rect.y = rect.y + (yVelocity / fps);
    }

    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }
}
