package game.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bitmap {

	public final int w;
	public final int h;
	public int[] pixels;

	public Bitmap(BufferedImage image) {
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		this.pixels = new int[w * h];
	}

	public Bitmap(int w, int h, int[] pixels) {
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}
	
	public void clear(int col) { 
		for(int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = col;
		}
	}
	
	public void setPixel(int xp, int yp, int col) {
		if(xp < 0 || yp < 0 || xp >= this.w || yp >= this.h) return;
		this.pixels[xp + yp * this.w] = col;
	}
	
	public void draw(Bitmap b, int xp, int yp) {
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + b.w;
		int y1 = yp + b.h;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > this.w) x1 = this.w;
		if(y1 > this.h) y1 = this.h;
		
		for(int y = y0; y < y1; y++) {
			int sp = (y - y0) * b.w;
			int dp = y * this.w;
			for(int x = x0; x < x1; x++) {
				int col = b.pixels[sp++];
				if(col >= 0) continue;
				this.pixels[dp + x] = col;
			} 
		}
	}
	
	public void draw(Bitmap b, int xp, int yp, int xOffs, int yOffs, int w, int h) {
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + w;
		int y1 = yp + h;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > this.w) x1 = this.w;
		if(y1 > this.h) y1 = this.h;
		
		for(int y = y0; y < y1; y++) {
			int sp = (y - y0 + yOffs) * b.w + xOffs;
			int dp = y * this.w;
			for(int x = x0; x < x1; x++) {
				int col = b.pixels[sp++];
				if(col >= 0) continue;
				this.pixels[dp + x] = col;
			} 
		}
	}
	
}