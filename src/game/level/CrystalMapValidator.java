package game.level;

import java.util.ArrayList;
import java.util.List;

import game.entity.Crystal;

public class CrystalMapValidator {

	private int checkCount;
	public CrystalMap current;
	
	public CrystalMapValidator(CrystalMap current) { setCurrentMap(current); }
	
	public void setCurrentMap(CrystalMap current) { this.current = current; }
	
	private List<List<Crystal>> checkVerticals(int maxCount){
		ArrayList<List<Crystal>> result = new ArrayList<List<Crystal>>();
		for(int x = 0; x < this.current.w; x++) {
			int count = 0;
			int oldIcon = -1;
			Crystal crystal;
			for(int y = 0; y < this.current.h && (crystal = this.current.get(x, y)) != null; y++) {
				count = oldIcon == crystal.icon ? ++count : 0;
				oldIcon = crystal.icon;
				if(count != maxCount) continue;
				ArrayList<Crystal> combination = new ArrayList<Crystal>();
				for(int i = 0; i <= count; i++) {
					combination.add(this.current.get(x, y - i));
				}
				
				result.add(combination);
				count = 0;
			}
			
		}
		
		return result;
	}
	
	private List<List<Crystal>> checkHorizontals(int maxCount){
		ArrayList<List<Crystal>> result = new ArrayList<List<Crystal>>();
		for(int y = 0; y < this.current.h; y++) {
			int count = 0;
			int oldIcon = -1;
			Crystal crystal;
			for(int x = 0; x < this.current.w && (crystal = this.current.get(x, y)) != null; x++) {
				count = oldIcon == crystal.icon ? ++count : 0;
				oldIcon = crystal.icon;
				if(count != maxCount) continue;
				ArrayList<Crystal> combination = new ArrayList<Crystal>();
				for(int i = 0; i <= count; i++) {
					combination.add(this.current.get(x - i, y));
				}
				
				result.add(combination);
				count = 0;
			}
			
		}
		
		return result;
	}
	
	private boolean hasAllEquals(List<Crystal> srcList, List<Crystal> validateList) {
		int found = 0;
		search: for(Crystal src: srcList) {
			for(Crystal validate : validateList) {
				if(validate != src) continue;
				found++;
				continue search;
			}
		}
		
		return found == validateList.size();
	}
	
	private List<List<Crystal>> filterEquals(List<List<Crystal>> bingoCrystals) {
		ArrayList<List<Crystal>> result = new ArrayList<List<Crystal>>(bingoCrystals);
		for(List<Crystal> srcList : bingoCrystals) {
			for(List<Crystal> validateList : bingoCrystals) {
				if(srcList.size() <= validateList.size() || !hasAllEquals(srcList, validateList)) continue;
				result.remove(validateList);
			}			
		}
		
		return result;
	}
	
	public List<List<Crystal>> crystalValidations() {
		int maxCombinations = Math.max(this.current.w, this.current.h);
		List<List<Crystal>> result = new ArrayList<List<Crystal>>();
		for(int i = maxCombinations; i >= 2; i--) {
			result.addAll(checkVerticals(i));
			result.addAll(checkHorizontals(i));
		}
		result = filterEquals(result);
		return result;
	}
	
	public int getFreeRow(int col) {
		if(col < 0 || col >= this.current.w) return -2;
		
		for(int y = this.current.h - 1; y >= 0; y--) {
			Crystal crystal = this.current.get(col, y);
			if(crystal != null) continue;
			return y;
		}
		
		return -1;
	}
	
	public int getNextFreeCol() {
		int result = -1;
		for(int y = this.checkCount; y < this.checkCount + this.current.w; y++) {
			int freeRow = getFreeRow(y % this.current.w);
			if(freeRow < 0) continue;
			result = y % this.current.w;
			break;
		}
		
		this.checkCount++;
		return result;
	}
	
	
	public List<List<Crystal>> findAllMoves() {
		ArrayList<List<Crystal>> result = new ArrayList<List<Crystal>>();
		for(int y = 0; y < this.current.h; y++) {
			for(int x = 0; x < this.current.w; x++) {
				Crystal c = this.current.get(x, y);
				Crystal cu = this.current.get(x, y - 1);
				Crystal cd = this.current.get(x, y + 1);
				Crystal cl = this.current.get(x - 1, y);
				Crystal cr = this.current.get(x + 1, y);
				
				
				if(c == null) continue;
				
				if(cl != null) {
					this.current.swap(c, cl);
					if(crystalValidations().size() > 0) {
						ArrayList<Crystal> swappedList = new ArrayList<Crystal>();
						swappedList.add(c);
						swappedList.add(cl);
						result.add(swappedList);
					}
					
					this.current.swapBack(c, cl);
				}
				
				if(cr != null) {
					this.current.swap(c, cr);
					if(crystalValidations().size() > 0) {
						ArrayList<Crystal> swappedList = new ArrayList<Crystal>();
						swappedList.add(c);
						swappedList.add(cr);
						result.add(swappedList);
					}
					
					this.current.swapBack(c, cr);
				}
				
				if(cu != null) {
					this.current.swap(c, cu);
					if(crystalValidations().size() > 0) {
						ArrayList<Crystal> swappedList = new ArrayList<Crystal>();
						swappedList.add(c);
						swappedList.add(cu);
						result.add(swappedList);
					}
					
					this.current.swapBack(c, cu);
				}
				
				if(cd != null) {
					this.current.swap(c, cd);
					if(crystalValidations().size() > 0) {
						ArrayList<Crystal> swappedList = new ArrayList<Crystal>();
						swappedList.add(c);
						swappedList.add(cd);
						result.add(swappedList);
					}
					
					this.current.swapBack(c, cd);
				}
			}
		}
		
		return result;
	}
	
}