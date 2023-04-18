package game.level;

import java.util.List;
import java.util.Random;

import game.entity.Crystal;

public class CrystalHandler {

	private Random random = new Random();
	private CrystalMap current;
	private CrystalMap old;
	private CrystalMapValidator validator;
	private boolean updated;
	private boolean needToSpawn = true;
	private int tickCount;
	private int batch;
	private int slowly;
	private Level level;
	private final int iconCount;
	public Crystal selectedCrystal;
	
	
	public CrystalHandler(int w, int h, int batch, int slowly, Level level, int iconCount) {
		this.current = new CrystalMap(w, h);
		this.old = new CrystalMap(w, h);
		this.batch = batch;
		this.slowly = slowly;
		this.level = level;
		this.iconCount = iconCount;
		this.validator = new CrystalMapValidator(this.current);
	}
	
	public void clear() {
		this.old = new CrystalMap(this.current.w, this.current.h, this.current.crystals);
		this.needToSpawn = this.current.clear() != this.current.w * this.current.h;
	}
	
	public void put(Crystal crystal, int x, int y) {
		if(this.old.get(x, y) != crystal) this.updated = true;
		this.current.put(crystal, x, y);
	}
	
	private Crystal getSelectedNeighbor(int x, int y) {
		if(this.selectedCrystal == null) return null;
		
		Crystal cu = this.current.get(x, y - 1);
		Crystal cd = this.current.get(x, y + 1);
		Crystal cl = this.current.get(x - 1, y);
		Crystal cr = this.current.get(x + 1, y);
		
		if(cu == this.selectedCrystal) return cu;
		if(cd == this.selectedCrystal) return cd;
		if(cl == this.selectedCrystal) return cl;
		if(cr == this.selectedCrystal) return cr;
		
		return null;
	}
	
	public void selectCrystal(int x, int y) {
		if(this.needToSpawn) return;
		
		if(this.selectedCrystal != null) {
			int xs = this.selectedCrystal.x + this.selectedCrystal.xr >> 4;
			int ys = this.selectedCrystal.y + this.selectedCrystal.yr >> 4;
			
			if(x == xs && y == ys) {
				this.selectedCrystal = null;
				return;
			}
		}
		
		Crystal crystal = this.current.get(x, y);
		if(crystal == null || crystal.removed) return;
		
		Crystal neighbor = getSelectedNeighbor(x, y);
		if(neighbor != null && !neighbor.removed) {
			this.current.swap(crystal, neighbor);
			List<List<Crystal>> bingoCrystals = this.validator.crystalValidations();
			if(bingoCrystals.isEmpty()) this.selectedCrystal = null;
			else crystal.swap(neighbor);
			
			this.current.swapBack(crystal, neighbor);
		} else this.selectedCrystal = crystal;
	}
	
	public int calculateScore(List<List<Crystal>> bingoCrystals) {
		int result = 0;
		int coefficiency = bingoCrystals.size();
		for(List<Crystal> crystals : bingoCrystals) {
			int count = crystals.size();
			int bonus = count > 3 ? count * (count - 2) * (this.level.gameScreen.player.prevScore > 100 ? 2 : 1) : 0;
			result += coefficiency * count + bonus;
			for(Crystal crystal : crystals) {
				if(crystal.die) continue;
				crystal.die();
			}
		}
		
		return result;
	}
	
	public void tick(boolean crystalSwapping) {
		if(!this.needToSpawn) {
			if(this.updated) {
				int score = calculateScore(this.validator.crystalValidations());
				if(score > 0) {
					score = score * 10 + this.random.nextInt(10);
					this.level.gameScreen.player.addScore(score);
				}
				
				this.updated = false;
			} else if(this.tickCount % 120 == 0) { 
				List<List<Crystal>> moves = this.validator.findAllMoves();
				System.out.println("Moves remaining: " + moves.size() / 2);
				
				if(moves.size() == 0) {
					this.level.gameScreen.player.subScore(500);
					
				}
			}
		} else if(!crystalSwapping) {
			int freeCol = this.validator.getNextFreeCol();
			if(this.tickCount / this.batch % this.slowly == 0 && freeCol >= 0) {
				this.level.add(new Crystal(freeCol << 4, 0, this.random.nextInt(this.iconCount)));
			}
		}
		
		this.tickCount++;
	}
}