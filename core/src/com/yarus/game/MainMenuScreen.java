package com.yarus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.yarus.game.Constants.*;

/**
 * Created by YARUS
 */

public class MainMenuScreen implements Screen {

    Vector3 touchPos;

    private final Football game;
    private OrthographicCamera camera;
    Rectangle button;
    Texture start_btnImg;

    public MainMenuScreen(Football game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH , SCREEN_HEIGHT);
        touchPos = new Vector3();
        button = new Rectangle();
        button.x = SCREEN_WIDTH/2 - 20;
        button.y = SCREEN_HEIGHT/2;
        button.width = 100;
        button.height = 50;
        start_btnImg = new Texture("start_btn.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0.2f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(start_btnImg, button.x, button.y);
        game.font.draw(game.batch, "Spend the flower through the labyrinth", 100, 150);
        game.font.draw(game.batch, "Click on button to start", 100, 100);
        game.batch.end();

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(touchPos.x > button.x && touchPos.y > button.y && touchPos.x < button.x  + 100 && touchPos.y < button.y  + 50)
                game.setScreen(new MainScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
