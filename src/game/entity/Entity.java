package game.entity;

import java.util.Random;

import game.gfx.Sprite;
import game.level.Level;

public class Entity extends Sprite {

	public Random random = new Random();
	public int x;
	public int y;
	public double xa;
	public double ya;
	public int xr = 8;
	public int yr = 8;
	public Level level;
	public int tickCount;
	public boolean removed;
	
	public void init(Level level) { this.level = level; }
	public void tick() { this.tickCount++; }
	public void collide() { this.ya *= -0.1; }
	public void remove() { this.removed = true; }
}