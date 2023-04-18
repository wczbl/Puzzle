package game;

import game.gfx.Bitmap;
import game.screen.Screen;
import game.screen.TitleScreen;

public class Game {

	private Screen screen;
	
	public Game() { setScreen(new TitleScreen()); }
	
	public void setScreen(Screen screen) {
		this.screen = screen;
		this.screen.init(this);
	}
	
	public void tick(InputHandler input) { this.screen.tick(input);  }
	public void render(Bitmap screen) { this.screen.render(screen); }
}