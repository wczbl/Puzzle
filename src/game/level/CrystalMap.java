package game.level;

import java.util.Arrays;

import game.entity.Crystal;

public class CrystalMap {

	public final int w;
	public final int h;
	protected Crystal[] crystals;
	
	public CrystalMap(int w, int h) {
		this.w = w;
		this.h = h;
		this.crystals = new Crystal[w * h];
	}

	public CrystalMap(int w, int h, Crystal[] crystals) {
		this.w = w;
		this.h = h;
		this.crystals = Arrays.copyOf(crystals, crystals.length);
	}
	
	public int clear() {
		int count = 0;
		for(int i = 0; i < this.w * this.h; i++) {
			Crystal crystal = this.crystals[i];
			if(crystal != null) count++;
			this.crystals[i] = null;
		}
		
		return count;
	}
	
	public void put(Crystal crystal, int x, int y) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.crystals[x + y * this.w] = crystal;
	}
	
	public void remove(int x, int y) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return;
		this.crystals[x + y * this.w] = null;
	}
	
	public Crystal get(int x, int y) {
		if(x < 0 || y < 0 || x >= this.w || y >= this.h) return null;
		return this.crystals[x + y * this.w];
	}
	
	public void swap(Crystal c0, Crystal c1) {
		put(c0, c1.x + c1.xr >> 4, c1.y + c1.xr >> 4);
		put(c1, c0.x + c0.xr >> 4, c0.y + c0.xr >> 4);
	}
	
	public void swapBack(Crystal c0, Crystal c1) {
		put(c0, c0.x + c0.xr >> 4, c0.y + c0.xr >> 4);
		put(c1, c1.x + c1.xr >> 4, c1.y + c1.xr >> 4);
	}
	
	public void dieAll() {
		for(Crystal crystal : this.crystals) {
			crystal.die();
		}
	}
	
}