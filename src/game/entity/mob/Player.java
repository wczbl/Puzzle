package game.entity.mob;

import game.entity.Entity;
import game.gfx.Art;

public class Player extends Entity {

	public int score;
	public int prevScore;
	public int animTime;
	
	public Player() {
		this.xp = 5;
		this.yp = 10;
		this.score = 0;
		this.prevScore = 0;
		this.b = Art.faces[0][0];
	}
	
	public void setAnim(int anim) {
		if(this.animTime > 0) return;
		
		anim = Math.min(3, anim);
		if(anim != 0) this.animTime = 30;
		
		this.b = Art.faces[0][anim];
	}
	
	public void tick() {
		if(this.animTime > 0) {
			this.animTime--;
			if(this.animTime <= 0) this.b = Art.faces[0][0];
		}
	}
	
	public void addScore(int amount) {
		if(amount > 35) setAnim(Math.max(1, amount / 50));
		
		this.score += amount;
		this.prevScore = amount;
	}
	
	public void subScore(int amount) { this.score = Math.max(0, this.score - amount); }
	
}