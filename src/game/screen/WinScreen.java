package game.screen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class WinScreen extends Screen {

	private int tick;
	private List<String> texts = new ArrayList<String>();
	
	public WinScreen() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(WinScreen.class.getResourceAsStream("/win.txt")));
			String line = "";
			while((line = br.readLine()) != null) {
				this.texts.add(line);
			}
			
			br.close();
		} catch(Exception e) {
			throw new RuntimeException("Failed to load win text");
		}
	}
	
	public void tick(InputHandler input) {
		this.tick++;
		if(this.tick >= this.texts.size() * 30 + 180 || input.space.clicked) setScreen(new TitleScreen());
	}

	public void render(Bitmap screen) {
		screen.draw(Art.background, 0, 0);
		int yo = this.tick / 2;
		for(int y = 0; y <= 20; y++) {
			int yl = yo / 8 - 20 + y;
			if(yl < 0 || yl >= this.texts.size()) continue;
			Font.draw(this.texts.get(yl), screen, (GameComponent.WIDTH - this.texts.get(yl).length() * 8) / 2, y * 8 - yo % 8);
		}
	}

	
}