package game;

import java.awt.event.KeyEvent;

import expansion.TypeInput;
import nightingale.input.NKey;
import nightingale.input.NMouse;

public class Input extends TypeInput{
	public static NMouse mouse = new NMouse();
	
	public Input() {
		setMouse(mouse);
	}
	
	public static NKey ESC_KEY   = new NKey();
	public static NKey ENTER_KEY = new NKey();
	
	@Override
	public void toggleKey(int keyCode, boolean isPressed) {
		switch(keyCode) {
		case KeyEvent.VK_ESCAPE:
			ESC_KEY.toggle(isPressed);
			break;
		case KeyEvent.VK_ENTER:
			ENTER_KEY.toggle(isPressed);
			break;
		}
	}
}
