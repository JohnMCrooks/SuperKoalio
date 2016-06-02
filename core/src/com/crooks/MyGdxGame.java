package com.crooks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion stand,jump;
	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	float x,y,xv,yv;
	boolean canJump, faceRight = true;
	float time;
	Animation walk;

	static final float MAX_JUMP_VELOCITY = 1300;
	static final float maxVelocity = 200;    //create constants for values that will be used multiple times in many places
	static final float decelaration = 0.90f;
	static final int GRAVITY = -50;       //Gravity Constant
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture sheet = new Texture("koalio.png");
		TextureRegion[][] tiles = TextureRegion.split(sheet,WIDTH, HEIGHT);
		stand = tiles[0][0];
		jump = tiles[0][1];
		walk = new Animation(0.2f,tiles[0][2],tiles[0][3],tiles[0][4]);  // Defines the walking animation---0.2 is how long it shows each frame, the tiles are individual frames of the animation

	}

	@Override
	public void render () {
		move();
		time+=Gdx.graphics.getDeltaTime();        // This time controls the Animation Frame rate

		TextureRegion spriteState;               // this If controls the image used of the koala depending on it's current state.
		if (y>0){
			spriteState = jump;
		}else if(xv!=0) {
			spriteState = walk.getKeyFrame(time, true);
		} else{
			spriteState = stand;
		}

		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (faceRight) {													//Determine Direction the Character is facing.
			batch.draw(spriteState, x, y, WIDTH * 3, HEIGHT * 3);
		} else {
			batch.draw(spriteState, x + WIDTH * 3, y, WIDTH * -3, HEIGHT * 3);  //have to compensate for the flipped image by adding the Width to the x location
		}
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
			faceRight = false;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			xv = maxVelocity;
			faceRight = true;
		}

		yv += GRAVITY;
		float delta = Gdx.graphics.getDeltaTime();   //Amount of time passed since the last frame
		y += yv * delta;
		x += xv * delta;
		yv = decelarate(yv);  //Changes velocity by 5% per frame
		xv = decelarate(xv);

		if (y<0){			//This if statement doesn't allow the character to fall off the bottom of the screen
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
