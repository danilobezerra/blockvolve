package io.github.danilobezerra.ld24;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

public class OldGame extends BasicGame {
	private static final String title = "LD24_EVOLUTION";
	private static final int width = 640;
	private static final int height = 480;
	private static final int fps = 60;
	private static boolean fullscreen = false;

	private static final int nOfMolecules = 10;
	private static final int difficultLevel = 3;

	Rectangle plant;
	Line earth;
	Circle[] co2 = new Circle[nOfMolecules - difficultLevel];
	Circle[] so2 = new Circle[nOfMolecules + difficultLevel];
	Circle[] nutrients = new Circle[nOfMolecules - difficultLevel];
	Circle[] agrotoxics = new Circle[nOfMolecules + difficultLevel];

	Sound fxUp, fxDown;
	Image background = null;

	public OldGame(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		background.draw(0, 0);
		
		g.fill(plant);
		g.draw(earth);

		for (int i = 0; i < nOfMolecules - difficultLevel; i++) {
			g.fill(co2[i]);
			g.fill(nutrients[i]);
		}

		for (int j = 0; j < nOfMolecules + difficultLevel; j++) {
			g.draw(so2[j]);
			g.draw(agrotoxics[j]);
		}

	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		background = new Image("images/bg.png");
		
		plant = new Rectangle((width / 2) - 15, (height / 2) - 30, 30, 60);
		earth = new Line(0, height / 2, width, height / 2);

		for (int i = 0; i < nOfMolecules - difficultLevel; i++) {
			co2[i] = randomSkyMolecule();
			nutrients[i] = randomSoilMolecule();
		}

		for (int j = 0; j < nOfMolecules + difficultLevel; j++) {
			so2[j] = randomSkyMolecule();
			agrotoxics[j] = randomSoilMolecule();
		}

		fxUp = new Sound("sounds/up.wav");
		fxDown = new Sound("sounds/down.wav");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_LEFT) || gc.getInput().isKeyDown(Input.KEY_A)) {
			if (plant.getMinX() > 31) {
				plant.setX(plant.getX() - 3);
			}
		}

		if (gc.getInput().isKeyDown(Input.KEY_RIGHT) || gc.getInput().isKeyDown(Input.KEY_D)) {
			if (plant.getMaxX() < width - 31) {
				plant.setX(plant.getX() + 3);
			}
		}
		
		if (gc.getInput().isKeyDown(Input.KEY_LALT) && gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			if (gc.isFullscreen()) {
				gc.setFullscreen(false);	
			} else {
				gc.setFullscreen(true);
			}
		}

		for (int i = 0; i < nOfMolecules - difficultLevel; i++) {
			if (!co2[i].intersects(plant) && !co2[i].intersects(earth)) {
				co2[i].setY(co2[i].getY() + 1);
			} else if (co2[i].intersects(plant)) {
				co2[i] = randomSkyMolecule();
				fxUp.play();
				plant.grow(0, co2[i].radius);
			} else {
				co2[i] = randomSkyMolecule();
			}

			if (!nutrients[i].intersects(plant) && !nutrients[i].intersects(earth)) {
				nutrients[i].setY(nutrients[i].getY() - 1);
			} else if (nutrients[i].intersects(plant)) {
				nutrients[i] = randomSoilMolecule();
				fxUp.play();
				plant.grow(0, nutrients[i].radius);
			} else {
				nutrients[i] = randomSoilMolecule();
			}
		}

		for (int j = 0; j < nOfMolecules + difficultLevel; j++) {
			if (!so2[j].intersects(plant) && !so2[j].intersects(earth)) {
				so2[j].setY(so2[j].getY() + 1);
			} else if (so2[j].intersects(plant)) {
				so2[j] = randomSkyMolecule();
				fxDown.play();
				plant.grow(0, -so2[j].radius);
			} else {
				so2[j] = randomSkyMolecule();
			}

			if (!agrotoxics[j].intersects(plant) && !agrotoxics[j].intersects(earth)) {
				agrotoxics[j].setY(agrotoxics[j].getY() - 1);
			} else if (agrotoxics[j].intersects(plant)) {
				agrotoxics[j] = randomSoilMolecule();
				fxDown.play();
				plant.grow(0, -agrotoxics[j].radius);
			} else {
				agrotoxics[j] = randomSoilMolecule();
			}
		}

		if (plant.getHeight() < 1) {
			System.exit(0);
		}

		if (plant.getHeight() > height - 64) {
			System.exit(0);
		}
	}
	
	public Circle randomSkyMolecule() {
		return new Circle(
				(float) (Math.random() * width - 10), // X position
				(float) ((float) (height / 2) - ((Math.random() * height) * 1)), // Y position
				(float) ((float) 3 + (Math.random() * 5)) // radius
				);
	}

	public Circle randomSoilMolecule() {
		return new Circle(
				(float) (Math.random() * width - 10), // X position
				(float) ((float) (height / 2) + ((Math.random() * height) * 1)), // Y position
				(float) ((float) 3 + (Math.random() * 5)) // radius
				);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new OldGame(title), width, height, fullscreen);
		app.setTargetFrameRate(fps);
		app.setShowFPS(false);
		app.start();
	}
}