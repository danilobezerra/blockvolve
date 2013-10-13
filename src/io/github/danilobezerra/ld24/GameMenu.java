package io.github.danilobezerra.ld24;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMenu extends BasicGameState {
	int stateId = -1;

	Image titleScreen = null;
	Image startOption = null;
	Image exitOption = null;
//	Image diffcultLevelImg = null;

	boolean startCursor = true;
	boolean exitCursor = false;
//	int difficultLevel = 5;

	Sound select, start;

	public GameMenu(int stateId) {
		this.stateId = stateId;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {	
		titleScreen = new Image("images/title.png");

		startOption = new Image("images/menuItem.png").getSubImage(11, 11, 179, 40);
		exitOption = new Image("images/menuItem2.png").getSubImage(11, 61, 179, 40);

		select = new Sound("sounds/select.wav");
		start = new Sound("sounds/start.wav");

//		diffcultLevelImg = new Image("images/numbers.png").getSubImage(2, 2, 48, 38);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		titleScreen.draw(0, 0);

		startOption.draw(380, 260);
		exitOption.draw(380, 310);
//		diffcultLevelImg.draw(116, 321);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
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

		if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
			select.play();
			startCursor = true;
			exitCursor = false;
		} else if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
			select.play();
			startCursor = false;
			exitCursor = true;
		}

//		if (gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
//			if (!(difficultLevel == 0)) {
//				select.play();
//				difficultLevel--;
//			}
//		} else if (gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
//			if (!(difficultLevel == 9)) {
//				select.play();
//				difficultLevel++;
//			}
//		}

		if (startCursor) {
			startOption = new Image("images/menuItem.png").getSubImage(11, 11, 179, 40);

			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
				start.play();
				sbg.enterState(Game.GAMEPLAYSTATE);
			}
		} else {
			startOption = new Image("images/menuItem2.png").getSubImage(11, 11, 179, 40);

			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
				gc.exit();
			}
		}

//		switch(difficultLevel) {
//		case 0:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(2, 2, 48, 38);
//			break;
//		case 1:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(52, 2, 48, 38);
//			break;
//		case 2:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(103, 2, 48, 38);
//			break;
//		case 3:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(2, 42, 48, 38);
//			break;
//		case 4:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(52, 42, 48, 38);
//			break;
//		case 5:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(103, 42, 48, 38);
//			break;
//		case 6:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(2, 83, 48, 38);
//			break;
//		case 7:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(52, 83, 48, 38);
//			break;
//		case 8:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(103, 83, 48, 38);
//			break;
//		case 9:
//			diffcultLevelImg = new Image("images/numbers.png").getSubImage(52, 124, 48, 38);
//			break;
//		}

		if (exitCursor) {
			exitOption = new Image("images/menuItem.png").getSubImage(11, 61, 179, 40);
		} else {
			exitOption = new Image("images/menuItem2.png").getSubImage(11, 61, 179, 40);
		}
	}


	@Override
	public int getID() {
		return stateId;
	}
}
