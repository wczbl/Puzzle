package game.screen;

import game.Game;
import game.GameComponent;
import game.InputHandler;
import game.gfx.Bitmap;
import game.gfx.Font;

public class FadeScreen extends Screen {

	private int tick;
	private final int duration;
	private Screen parent;
	private final String text;
	
	public FadeScreen(int duration, String text, Screen parent) {
		this.duration = duration;
		this.parent = parent;
		this.text = text;
	}
	
	public void init(Game game) {
		super.init(game);
		if(this.parent != null) this.parent.init(game);
	}
	
	public void tick(InputHandler input) {
		this.tick++;
		if(this.tick >= this.duration) setScreen(this.parent);
		if(this.parent != null) this.parent.tick(input);
		
	}

	public void render(Bitmap screen) {
		if(this.parent != null) this.parent.render(screen);
		
		for(int i = 0; i < GameComponent.WIDTH * GameComponent.HEIGHT; i++) {
			int a = (screen.pixels[i] >> 24) & 0xFF;
			int r = (screen.pixels[i] >> 16) & 0xFF;
			int g = (screen.pixels[i] >> 8) & 0xFF;
			int b = (screen.pixels[i] >> 0) & 0xFF;
			
			int rr = r * this.tick / this.duration;
			int gg = g * this.tick / this.duration;
			int bb = b * this.tick / this.duration;
			
			screen.pixels[i] = a << 24 | rr << 16 | gg << 8 | bb;
		}
		
		Font.draw(this.text, screen, (GameComponent.WIDTH - this.text.length() * 8) / 2, GameComponent.HEIGHT - 84);
	}

}