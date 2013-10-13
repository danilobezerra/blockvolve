package io.github.danilobezerra.ld24;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverWinner extends BasicGameState {
	int stateID = -1;

	Image winScreen = null;

	public GameOverWinner(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		winScreen = new Image("images/win.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
			winScreen.draw(0, 0);
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
		
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			sbg.enterState(Game.MAINMENUSTATE);
		}
		
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
