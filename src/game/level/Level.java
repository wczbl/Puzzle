package game.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.entity.Crystal;
import game.entity.mob.Mob;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.screen.GameScreen;

public class Level {

	private List<Crystal> crystals = new ArrayList<Crystal>();
	private List<Crystal>[] crystalMap;
	public CrystalHandler crystalHandler;
	private Random random = new Random();
	public GameScreen gameScreen;
	public int tickCount;
	public final int w;
	public final int h;
	public final int mw;
	public final int mh;
	public final int xOffs;
	public final int yOffs;
	public Mob mob;
	
	@SuppressWarnings("unchecked")
	public Level(int w, int h, GameScreen gameScreen, Mob mob) {
		this.w = w;
		this.h = h;
		this.mw = 8;
		this.mh = 8;
		this.mob = mob;
		this.xOffs = w - (this.mw << 4) >> 1;
		this.yOffs = h - (this.mh << 4) >> 1;
		this.gameScreen = gameScreen;
		
		int slowly = this.random.nextInt(3) + 1;
		int batch = Math.min(this.mh, (this.random.nextInt(this.mh) + 2) / 2 * 2);
		int iconCount = gameScreen.levelNum + 5;
		this.crystalHandler = new CrystalHandler(this.mw, this.mh, batch, slowly, this, iconCount);
		this.crystalMap = new ArrayList[this.mw * this.mh];
		
		for(int i = 0; i < this.mw * this.mh; i++) {
			this.crystalMap[i] = new ArrayList<Crystal>();
		}
	}
	
	public void tick() {
		this.crystalHandler.clear();
		boolean crystalSwapping = false;
		for(int i = 0; i < this.crystals.size(); i++) {
			Crystal crystal = this.crystals.get(i);
			
			int xto = crystal.x + crystal.xr >> 4;
			int yto = crystal.y + crystal.yr >> 4;
			
			crystal.tick();
			if(crystal.removed) {
				this.crystals.remove(i--);
				removeCrystal(crystal, xto, yto);
				continue;
			}
			
			int xtn = crystal.x + crystal.xr >> 4;
			int ytn = crystal.y + crystal.yr >> 4;
			
			if(xtn != xto || ytn != yto) {
				removeCrystal(crystal, xto, yto);
				insertCrystal(crystal, xtn, ytn);
			}
			
			if(crystal.stand && !crystal.removed) this.crystalHandler.put(crystal, xtn, ytn);
			if(!crystal.swap) continue;
			crystalSwapping = true;
		}
		
		this.crystalHandler.tick(crystalSwapping);
		this.gameScreen.player.tick();
		
		if(this.mob != null) this.mob.tick();
		
		this.tickCount++;
	}
	
	public void render(Bitmap screen) {		
		this.gameScreen.player.render(screen);
		
		for(Crystal crystal : this.crystals) {
			crystal.render(screen, this.xOffs, this.yOffs);
		}
		
		if(this.crystalHandler.selectedCrystal != null && !this.crystalHandler.selectedCrystal.removed) {
			screen.draw(Art.icons[0][0], this.crystalHandler.selectedCrystal.x + this.xOffs, this.crystalHandler.selectedCrystal.y + this.yOffs);
		}
		
		if(this.mob != null) this.mob.render(screen);
	}
	
	public void add(Crystal crystal) {
		List<Crystal> crystalsInCell = getCrystal(crystal.x + crystal.xr >> 4, crystal.y + crystal.yr >> 4);
		if(crystalsInCell == null || !crystalsInCell.isEmpty()) return;
		
		insertCrystal(crystal, crystal.x + crystal.xr >> 4, crystal.y + crystal.yr >> 4);
		this.crystals.add(crystal);
		crystal.init(this);
	}
	
	public void insertCrystal(Crystal crystal, int x, int y) {
		if(x < 0 || y < 0 || x >= this.mw || y >= this.mh) return;
		this.crystalMap[x + y * this.mw].add(crystal);
	}
	
	public void removeCrystal(Crystal crystal, int x, int y) {
		if(x < 0 || y < 0 || x >= this.mw || y >= this.mh) return;
		this.crystalMap[x + y * this.mw].remove(crystal);
	}
	
	public List<Crystal> getCrystal(int x, int y){
		if(x < 0 || y < 0 || x >= this.mw || y >= this.mh) return null;
		return this.crystalMap[x + y * this.mw];
	}
	
}