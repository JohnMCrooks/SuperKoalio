package com.crooks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion stand;
	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	float x,y,xv,yv;
	boolean canJump;

	static final float MAX_JUMP_VELOCITY = 1000;
	static final float maxVelocity = 200;    //create constants for values that will be used multiple times in many places
	static final float decelaration = 0.94f;
	static final int GRAVITY = -50;       //Gravity Constant
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture sheet = new Texture("koalio.png");
		TextureRegion[][] tiles = TextureRegion.split(sheet,WIDTH, HEIGHT);
		stand = tiles[0][0];
	}

	@Override
	public void render () {
		move();

		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(stand,x,y, WIDTH*3, HEIGHT*3);
		batch.end();

	}

	public void move(){
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && canJump) {
			canJump=false;
			yv = MAX_JUMP_VELOCITY;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			yv= -maxVelocity;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			xv = -maxVelocity;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			xv = maxVelocity;
		}

		yv += GRAVITY;
		float delta = Gdx.graphics.getDeltaTime();   //Amount of time passed since the last frame
		y += yv * delta;
		x += xv * delta;
		yv = decelarate(yv);  //Changes velocity by 5% per frame
		xv = decelarate(xv);

		if (y<0){
			y=0;
			canJump=true;
		}

	}

	public float decelarate(float velocity){ //Slows velocity by 5% per frame
		velocity *= decelaration;
		if (Math.abs(velocity)<1){			//once it slows down to an extreme degree make it stop so the calculation is constantly running in the background.
			velocity = 0;
		}
		return velocity;
	}

}
