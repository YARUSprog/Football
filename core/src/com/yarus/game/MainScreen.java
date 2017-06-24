package com.yarus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import static com.yarus.game.Constants.*;

public class MainScreen implements Screen {

	private OrthographicCamera camera;
	private Vector3 touchPos;

	private Texture paddleImg;
	private Texture ballImg;

	private Rectangle paddle;

	private ArrayList<Ball> balls;
	private Football game;
	private boolean gameOver;

	private float time = 0;
	private int touch = 0;
	Rectangle button;
	Texture end_btnImg;

	public MainScreen (Football game) {
		this.game = game;
		balls = new ArrayList<Ball>();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		ballImg = new Texture("ball.png");
		balls.add(new Ball());


		paddleImg = new Texture("horizontal_wall.png");
		paddle = new Rectangle();
		paddle.x = SCREEN_WIDTH/2 - PADDlE_WIDTH/2;
		paddle.y = PADDlE_HEIGHT;
		paddle.width = PADDlE_WIDTH;
		paddle.height = PADDlE_HEIGHT;

		gameOver = false;

		touchPos = new Vector3();

		button = new Rectangle();
		button.x = SCREEN_WIDTH/2 - 20;
		button.y = SCREEN_HEIGHT/2;
		button.width = 100;
		button.height = 50;
		end_btnImg = new Texture("end_btn.png");
	}

	public void addBall(){
		balls.add(new Ball());
	}

	public void drawBalls(){
		for(Ball ball : balls){
			game.batch.draw(ballImg, ball.getRect().x, ball.getRect().y);
		}
	}

	public void updateBalls(){
		Ball ball;
		Ball ball2;
		for(int i = 0; i < balls.size(); i++){
			ball = balls.get(i);
			ball.update(FPS);

			if(ball.getRect().x + ball.getRect().width >= SCREEN_WIDTH){
				ball.reverseXVelocity();
				ball.getRect().x = SCREEN_WIDTH - ball.getRect().width;
			} else if( ball.getRect().x <= 0){
				ball.reverseXVelocity();
				ball.getRect().x = 0;
			}

			if(ball.getRect().y + ball.getRect().height >= SCREEN_HEIGHT){
				ball.reverseYVelocity();
				ball.getRect().y = SCREEN_HEIGHT - ball.getRect().height;
			}

			if(ball.getRect().y <= 0){
				gameOver = true;
			}

			if(ball.getRect().overlaps(paddle)){
				touch++;
				ball.reverseYVelocity();
				ball.getRect().y = paddle.y + paddle.height;
			}

			for(int j = i+1; j < balls.size(); j++){
				ball2 = balls.get(j);
				if(ball.getRect().overlaps(ball2.getRect())){
					ball.reverseXVelocity();
				}
			}
		}
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
		if(!gameOver){
			updateBalls();
		}
		game.batch.begin();
		drawBalls();
		game.batch.draw(paddleImg, paddle.x, paddle.y);
		time += Gdx.graphics.getDeltaTime();
		if(time > 1 && ((int)time)%5 == 0 ){
			time = 0;
			addBall();
		}
		game.font.draw(game.batch, "touch: " + touch + "   balls: " + balls.size(), 10, SCREEN_HEIGHT-10);

		if(gameOver) {
			game.batch.draw(end_btnImg, button.x, button.y);
			game.font.draw(game.batch, "GAME OVER", SCREEN_WIDTH/2 - 10, SCREEN_HEIGHT/2 + 80);
		}

		game.batch.end();

		if(Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if(touchPos.x  - (PADDlE_WIDTH/2) > paddle.x || touchPos.x  - (PADDlE_WIDTH/2) < paddle.x){
				paddle.x = touchPos.x - PADDlE_WIDTH/2;
			}
			if(paddle.x < 0){
				paddle.x = 0;
			} else if(paddle.x + PADDlE_WIDTH > SCREEN_WIDTH ){
				paddle.x = SCREEN_WIDTH - PADDlE_WIDTH;
			}

			if(gameOver && touchPos.x > button.x && touchPos.y > button.y && touchPos.x < button.x  + 100 && touchPos.y < button.y  + 50)
				game.setScreen(new MainMenuScreen(game));

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
		end_btnImg.dispose();
		paddleImg.dispose();
		ballImg.dispose();
		game.dispose();
	}
}
