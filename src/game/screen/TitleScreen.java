package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class TitleScreen extends Screen {

	private int tick;
	
	public void tick(InputHandler input) {
		this.tick++;
		if(this.tick > 240 && input.space.clicked) {
			setScreen(new FadeScreen(120, "Level 1", new GameScreen()));
			input.releaseAll();
		}
	}

	public void render(Bitmap screen) {
		int yOffs = this.tick * 2;
		if(yOffs > 560) yOffs = 560;
		
		screen.draw(Art.background, 0, 0);
		screen.draw(Art.title, 0, 0, 0, yOffs, GameComponent.WIDTH, GameComponent.HEIGHT);
		
		if(this.tick > 240) {
			String text = "Press SPACE to start";
			Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 8) / 2, (GameComponent.HEIGHT - 28) - (int)Math.abs(Math.sin(this.tick * 0.1) * 10));
		}
	}

}