package game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	
	public static final Bitmap[][] faces = loadAndCut("/faces.png", 32);
	public static final Bitmap[][] icons = loadAndCut("/icons.png", 16);
	public static final Bitmap[][] font = loadAndCut("/font.png", 8);
	public static final Bitmap background = load("/background.png");
	public static final Bitmap title = load("/title.png");
	
	public static Bitmap load(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return new Bitmap(result);
	}
	
	public static Bitmap[][] loadAndCut(String path, int size) {
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		int sw = sheet.getWidth() / size;
		int sh = sheet.getHeight() / size;
		Bitmap[][] result = new Bitmap[sw][sh];
		for(int y = 0; y < sh; y++) {
			for(int x = 0; x < sw; x++) {
				BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				g.drawImage(sheet, -x * size, -y * size, null);
				g.dispose();
				result[x][y] = new Bitmap(image);
			}			
		}			
		
		return result;
	}
	
}