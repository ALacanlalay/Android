package com.alacanlalay.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter{
	SpriteBatch batch;
	Texture background;

	private static Preferences prefs;

	//ShapeRenderer shapeRenderer;

	Texture gameOver;

	Texture[] birds;
	int flapState = 0;
	float birdY;
	float velocity = 0;
	Circle birdCircle;
	int score = 0;
	int scoringTube = 0;

	BitmapFont fontCurrentScore;
	BitmapFont fontHighestScore;

	int gameState = 0;
	float gravity = 2;

	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	float maxTubeOffset;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;

	@Override
	public void create () {

		batch = new SpriteBatch();
		background = new Texture("bg.png");

		gameOver = new Texture("gameover.jpg");

		//shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		fontCurrentScore = new BitmapFont();
		fontCurrentScore.setColor(Color.WHITE);
		fontCurrentScore.getData().setScale(10);

		fontHighestScore = new BitmapFont();
		fontHighestScore.setColor(Color.WHITE);
		fontHighestScore.getData().setScale(10);

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");

		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;

		randomGenerator = new Random();

		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];


		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		prefs = Gdx.app.getPreferences("FlappyBird");

		if (!prefs.contains("highScore")) {

			prefs.putInteger("highScore", score);

		} else {

			if(score >= prefs.getInteger("highScore")) {

				prefs.getInteger("highScore");

			} else {

				//do ntohing

			}


		}

		Gdx.app.log("HighScore", String.valueOf(prefs.getInteger("highScore")));



		startGame();

	}

	public void startGame() {

		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		for(int i = 0; i < numberOfTubes; i++) {

			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();

		}

	}

	@Override
	public void render () {

		batch.begin();

		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gameState == 1) {

			if(tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {

				//Gdx.app.log("width", String.valueOf(Gdx.graphics.getWidth() / 2));

				score++;

				if(score >= prefs.getInteger("highScore")) {

					prefs.putInteger("highScore", score);
					prefs.flush();

				}

				Gdx.app.log("Score", String.valueOf(score));

				if(scoringTube < numberOfTubes - 1) {

					scoringTube++;

				} else {

					scoringTube = 0;

				}

			}


			if(Gdx.input.justTouched()) {

                velocity = -20;

			}

			for(int i = 0; i < numberOfTubes; i++) {

				//Gdx.app.log("tubeXVal", String.valueOf(tubeX[i]));

				if(tubeX[i] < -topTube.getWidth()) {

					tubeX[i] += numberOfTubes * distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);


				} else {

					tubeX[i] = tubeX[i] - tubeVelocity;


				}

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());



				//(x-coordinate, y-coordinate, width, height) parameters
			}

			if(birdY > 0) {

				velocity = velocity + gravity;
				birdY -= velocity;

			} else {

				gameState = 2;

			}

		} else if (gameState == 0){

			fontHighestScore.draw(batch, String.valueOf(prefs.getInteger("highScore")), (Gdx.graphics.getWidth() / 2) - fontHighestScore.getXHeight() / 2  ,  (Gdx.graphics.getHeight() / 2) + (Gdx.graphics.getHeight() / 2) / 2 );


			if(Gdx.input.justTouched()) {

				gameState = 1;

			}

		} else if(gameState == 2) {

			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);

			fontHighestScore.draw(batch, String.valueOf(prefs.getInteger("highScore")), (Gdx.graphics.getWidth() / 2) - fontHighestScore.getXHeight() / 2  ,  (Gdx.graphics.getHeight() / 2) + (Gdx.graphics.getHeight() / 2) / 2 );


			Gdx.app.log("HighScore", String.valueOf(prefs.getInteger("highScore")));

			if(Gdx.input.justTouched()) {

				gameState = 1;

				startGame();
				score = 0;
				scoringTube = 0;
				velocity = 0;

			}

		}

        if (flapState == 0) {

            flapState = 1;

        } else {

            flapState = 0;

        }


        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);

		fontCurrentScore.draw(batch, String.valueOf(score), 100, 200);

		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2 , birds[flapState].getWidth() /2 );

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for(int i = 0; i < numberOfTubes; i++) {

			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

			if(Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {

				gameState = 2;

			}


		}


		batch.end();
			//shapeRenderer.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
