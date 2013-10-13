package io.github.danilobezerra.ld24;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GamePlay extends BasicGameState {
	private int width;
	private int height;
	int stateID = -1;

	private static final int nOfMolecules = 10;
	private int difficultLevel = 3;

	Rectangle block;
	Line horizontLine;
	Circle[] goodTopMolecule = new Circle[nOfMolecules - difficultLevel];
	Circle[] badTopMolecule = new Circle[nOfMolecules + difficultLevel];
	Circle[] goodBottomMolecule = new Circle[nOfMolecules - difficultLevel];
	Circle[] badBottomMolecule = new Circle[nOfMolecules + difficultLevel];

	Sound fxUp, fxDown;
	Image background = null;

	Sound winFx, loseFx;

	public GamePlay(int width, int height, int stateID) {
		this.width = width;
		this.height = height;
		this.stateID = stateID;
	}
	
//	public void setDifficultLevel(int difficultLevel) {
//		this.difficultLevel = difficultLevel;
//		goodTopMolecule = new Circle[nOfMolecules - difficultLevel];
//		badTopMolecule = new Circle[nOfMolecules + difficultLevel];
//		goodBottomMolecule = new Circle[nOfMolecules - difficultLevel];
//		badBottomMolecule = new Circle[nOfMolecules + difficultLevel];
//	}

	public Circle randomTopMolecule() {
		return new Circle(
				(float) (Math.random() * width - 10), // X position
				(float) ((float) (height / 2) - ((Math.random() * height) * 1)), // Y position
				(float) ((float) 3 + (Math.random() * 5)) // radius
				);
	}

	public Circle randomBottomMolecule() {
		return new Circle(
				(float) (Math.random() * width - 10), // X position
				(float) ((float) (height / 2) + ((Math.random() * height) * 1)), // Y position
				(float) ((float) 3 + (Math.random() * 5)) // radius
				);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		background = new Image("images/bg.png");

		block = new Rectangle((width / 2) - 15, (height / 2) - 30, 30, 60);
		horizontLine = new Line(0, height / 2, width, height / 2);

		for (int i = 0; i < nOfMolecules - difficultLevel; i++) {
			goodTopMolecule[i] = randomTopMolecule();
			goodBottomMolecule[i] = randomBottomMolecule();
		}

		for (int j = 0; j < nOfMolecules + difficultLevel; j++) {
			badTopMolecule[j] = randomTopMolecule();
			badBottomMolecule[j] = randomBottomMolecule();
		}

		fxUp = new Sound("sounds/up.wav");
		fxDown = new Sound("sounds/down.wav");

		winFx = new Sound("sounds/win.wav");
		loseFx = new Sound("sounds/lose.wav");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw(0, 0);

		g.fill(block);
		g.draw(horizontLine);

		for (int i = 0; i < nOfMolecules - difficultLevel; i++) {
			g.fill(goodTopMolecule[i]);
			g.fill(goodBottomMolecule[i]);
		}

		for (int j = 0; j < nOfMolecules + difficultLevel; j++) {
			g.draw(badTopMolecule[j]);
			g.draw(badBottomMolecule[j]);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_LEFT) || gc.getInput().isKeyDown(Input.KEY_A)) {
			if (block.getMinX() > 31) {
				block.setX(block.getX() - 3);
			}
		}

		if (gc.getInput().isKeyDown(Input.KEY_RIGHT) || gc.getInput().isKeyDown(Input.KEY_D)) {
			if (block.getMaxX() < width - 31) {
				block.setX(block.getX() + 3);
			}
		}

		if (gc.getInput().isKeyDown(Input.KEY_F4)) {
			if (gc.isFullscreen()) {
				gc.setFullscreen(false);	
			} else {
				gc.setFullscreen(true);
			}
		}
		
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}

		for (int i = 0; i < nOfMolecules - difficultLevel; i++) {
			if (!goodTopMolecule[i].intersects(block) && !goodTopMolecule[i].intersects(horizontLine)) {
				goodTopMolecule[i].setY(goodTopMolecule[i].getY() + 1);
			} else if (goodTopMolecule[i].intersects(block)) {
				goodTopMolecule[i] = randomTopMolecule();
				fxUp.play();
				block.grow(0, goodTopMolecule[i].radius);
			} else {
				goodTopMolecule[i] = randomTopMolecule();
			}

			if (!goodBottomMolecule[i].intersects(block) && !goodBottomMolecule[i].intersects(horizontLine)) {
				goodBottomMolecule[i].setY(goodBottomMolecule[i].getY() - 1);
			} else if (goodBottomMolecule[i].intersects(block)) {
				goodBottomMolecule[i] = randomBottomMolecule();
				fxUp.play();
				block.grow(0, goodBottomMolecule[i].radius);
			} else {
				goodBottomMolecule[i] = randomBottomMolecule();
			}
		}

		for (int j = 0; j < nOfMolecules + difficultLevel; j++) {
			if (!badTopMolecule[j].intersects(block) && !badTopMolecule[j].intersects(horizontLine)) {
				badTopMolecule[j].setY(badTopMolecule[j].getY() + 1);
			} else if (badTopMolecule[j].intersects(block)) {
				badTopMolecule[j] = randomTopMolecule();
				fxDown.play();
				block.grow(0, -badTopMolecule[j].radius);
			} else {
				badTopMolecule[j] = randomTopMolecule();
			}

			if (!badBottomMolecule[j].intersects(block) && !badBottomMolecule[j].intersects(horizontLine)) {
				badBottomMolecule[j].setY(badBottomMolecule[j].getY() - 1);
			} else if (badBottomMolecule[j].intersects(block)) {
				badBottomMolecule[j] = randomBottomMolecule();
				fxDown.play();
				block.grow(0, -badBottomMolecule[j].radius);
			} else {
				badBottomMolecule[j] = randomBottomMolecule();
			}
		}

		if (block.getHeight() < 2) {
			loseFx.play();
			sbg.enterState(Game.LOSESTATE);
			block = new Rectangle((width / 2) - 15, (height / 2) - 30, 30, 60);
		}

		if (block.getHeight() > height - 64) {
			winFx.play();
			sbg.enterState(Game.WINSTATE);
			block = new Rectangle((width / 2) - 15, (height / 2) - 30, 30, 60);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}
}
