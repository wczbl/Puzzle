package game.gfx;

public class Sprite {

	public int xp;
	public int yp;
	public Bitmap b;
	
	public void tick() {}
	public void render(Bitmap screen) { screen.draw(this.b, this.xp, this.yp); }
	public void render(Bitmap screen, int xOffs, int yOffs) {}
}