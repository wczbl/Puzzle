package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.entity.mob.Ghost;
import game.entity.mob.Player;
import game.gfx.Bitmap;
import game.gfx.Font;
import game.level.Level;

public class GameScreen extends Screen {

	public static final int LEVELS = 6;
	public Level level;
	public Player player = new Player();
	public int levelNum = 1;
	
	public GameScreen() { this.level = new Level(GameComponent.WIDTH, GameComponent.HEIGHT, this, new Ghost()); }
	
	
	public void tick(InputHandler input) {
		this.level.tick();
		
		int currLevel = this.player.score / 2000 + 1;
		if(this.levelNum != currLevel) {
			changeLevel(currLevel);
			return;
		}
		
		if(input.mouse.clicked) {
			int mx = input.mx - this.level.xOffs >> 4;
			int my = input.my - this.level.yOffs >> 4;
			this.level.crystalHandler.selectCrystal(mx, my); 
		}
	}

	public void render(Bitmap screen) {
		this.level.render(screen);		

		String text = "Score: " + this.player.score;
		Font.draw(text, screen, GameComponent.WIDTH - text.length() * 8 - 2, GameComponent.HEIGHT - 10);
	
		if(this.player.prevScore > 0) {
			text = (this.player.prevScore > 300 ? "Last Record: "  : "") + this.player.prevScore;
			Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 8) / 2, 2);
		}
		
		text = "Level " + this.levelNum + "/" + LEVELS;
		Font.draw(text, screen, 2, GameComponent.HEIGHT - 10);
	}
	
	public void restartLevel() {
		setScreen(new FadeScreen(120, "Ran out of moves", this));
		this.level = new Level(GameComponent.WIDTH, GameComponent.HEIGHT, this, new Ghost());
	}
	
	public void reset() {
		this.player = new Player();
		changeLevel(1);
	}
	
	public void changeLevel(int levelNum) {
		if(levelNum > LEVELS) {
			setScreen(new WinScreen());
			return;
		}
		
		this.levelNum = levelNum;
		setScreen(new FadeScreen(120, "Level " + levelNum, this));
		this.level = new Level(GameComponent.WIDTH, GameComponent.HEIGHT, this, new Ghost());
	}

}