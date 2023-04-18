package game;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
	
	public int mx;
	public int my;
	
	private List<Key> keys = new ArrayList<Key>();
	public Key mouse = new Key();
	public Key space = new Key();
	
	public InputHandler(Canvas canvas) {
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}
	
	public void releaseAll() {
		for(Key key : this.keys) {
			key.down = false;
		}
	}
	
	public void tick() {
		for(Key key : this.keys) {
			key.tick();
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		this.mx = e.getX() / GameComponent.SCALE;
		this.my = e.getY() / GameComponent.SCALE;
	}

	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX() / GameComponent.SCALE;
		this.my = e.getY() / GameComponent.SCALE;
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) { this.mouse.toggle(true); }
	public void mouseReleased(MouseEvent e) { this.mouse.toggle(false); }
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) { toggle(e, true); }
	public void keyReleased(KeyEvent e) { toggle(e, false); }
	
	public void toggle(KeyEvent e, boolean pressed) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) this.space.toggle(pressed);
	}
	
	public class Key {
		public boolean down;
		public boolean clicked;
		
		private int absorbs;
		private int presses;
		
		public Key() { InputHandler.this.keys.add(this); }
		
		public void tick() {
			if(this.absorbs < this.presses) {
				this.absorbs++;
				this.clicked = true;
			} else this.clicked = false;
		}
		
		public void toggle(boolean pressed) {
			if(pressed != this.down) this.down = pressed;
			if(pressed) this.presses++;
		}
	}

}