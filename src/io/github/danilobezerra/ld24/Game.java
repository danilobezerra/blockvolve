package io.github.danilobezerra.ld24;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	private static final String title = "BLOCKVOLVE";
	private static final int width = 640;
	private static final int height = 480;
	private static final int fps = 60;
	private static boolean fullscreen = false;
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int LOSESTATE = 2;
	public static final int WINSTATE = 3;
	GameMenu gamemenu = new GameMenu(MAINMENUSTATE);
	GamePlay gameplay = new GamePlay(width, height, GAMEPLAYSTATE);

	public Game(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(gamemenu);
		this.addState(gameplay);
		this.addState(new GameOverLoser(LOSESTATE));
		this.addState(new GameOverWinner(WINSTATE));	
	}
	
//	public void enterState(int stateId) {
//		if (stateId == 1) {
//			gameplay.setDifficultLevel(gamemenu.difficultLevel);
//		}
//		super.enterState(stateId);
//	}

	public static void main(String[] args) throws SlickException {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true"); 
		
		AppGameContainer app = new AppGameContainer(new Game(title), width, height, fullscreen);
		app.setTargetFrameRate(fps);
		app.setShowFPS(false);
		app.start();
	}

}
