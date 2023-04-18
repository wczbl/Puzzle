package game.entity;

import java.util.List;

import game.gfx.Art;
import game.gfx.Bitmap;

public class Crystal extends Entity {

	public final int icon;
	public double gravity;
	public boolean stand;
	public int checkCount;
	public int yt = 1;
	public int xTarget;
	public int yTarget;
	public boolean swap;
	public boolean die;
	
	
	public Crystal(int x, int y, int icon) {
		this.x = x;
		this.y = y;
		this.icon = icon;
		this.gravity = 0.21;
	}
	
	public void tick() {
		super.tick();
		int xo = this.x;
		int yo = this.y;
		if(this.swap) {
			double speed = 2;
			int xd = this.xTarget - this.x;
			int yd = this.yTarget - this.y;
			double m = Math.sqrt(xd * xd + yd * yd);
			
			if(m < 2) {
				this.swap = false;
				stop();
				return;
			}
			
			double xxa = xd / m;
			double yya = yd / m;
			
			this.xa = xxa * speed;
			this.ya = yya * speed;
			this.xa *= 0.99;
			this.ya *= 0.99;
		} else {
			this.ya *= 0.99;
			this.ya += this.gravity;
			if(isRested()) {
				collide();
				if(Math.abs(this.ya) < 0.5) stop();
			}
		}
		
		if(this.die) {
			if(this.tickCount % 7 == 0) this.yt++;
			
			if(this.yt > 9) {
				remove();
				return;
			}
			
		} else {
			if(this.tickCount % 15 == 0) this.yt++;
			if(this.yt > 4) this.yt = 1;
		}
		
		this.x = (int)(this.x + this.xa);
		this.y = (int)(this.y + this.ya);
		this.checkCount = this.y - yo < 1 && this.x - xo < 1 ? ++this.checkCount : --this.checkCount;
		this.stand = this.checkCount > 3;
	}
	
	public void render(Bitmap screen, int xOffs, int yOffs) {
		screen.draw(Art.icons[this.icon][this.yt], this.x + xOffs, this.y + yOffs);
	}
	
	public void swap(Crystal crystal) {
		crystal.xTarget = this.x;
		crystal.yTarget = this.y;
		this.xTarget = crystal.x;
		this.yTarget = crystal.y;
		this.swap = true;
		crystal.swap = true;
	}
	
	public void stop() {
		this.x = this.x + this.xr >> 4 << 4;
		this.y = this.y + this.yr >> 4 << 4;
		this.xa = 0;
		this.ya = 0;
	}
	
	public void die() {
		this.yt = 7;
		this.die = true;
	}
	
	public boolean isRested() {
		boolean result = false;
		if((int)((this.y + this.yr) + Math.abs(this.ya) + 8) >> 4 >= this.level.mh) {
			result = true;
		} else {
			List<Crystal> crystals = this.level.getCrystal((int)((this.x + this.xr) + this.xa) >> 4, (int)((this.y + this.yr) + Math.abs(this.ya) + 8) >> 4);
			for(Crystal crystal : crystals) {
				if(crystal == null || crystal == this) continue;
				result = true;
				break;
			}
		}
		
		return result;
	}
}