package game.gfx;

public class Font {

	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      0123456789.,!?'\"/\\%-+=()<>:;    ";

	public static void draw(String text, Bitmap screen, int xp, int yp) {
		text = text.toUpperCase();
		for(int i = 0; i < text.length(); i++) {
			int ix = chars.indexOf(text.charAt(i));
			if(ix < 0) continue;
			int xx = ix % 32;
			int yy = ix / 32;
			screen.draw(Art.font[xx][yy], xp + i * 8, yp);
		}
	}
	
}