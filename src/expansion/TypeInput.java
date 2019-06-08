package expansion;

import java.awt.event.KeyEvent;

import nightingale.input.NInput;

public abstract class TypeInput extends NInput{
	
	protected String typedString = "";
	protected String currentString = "";
	protected boolean canTyping = false;
	
	public String getTypedString() {
		return typedString;
	}
	
	public String getCurrentString() {
		return currentString;
	}
	
	public void typingOff() { canTyping = false; }
	public void typingOn()  { canTyping = true;  }
	
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if(canTyping) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				typedString = currentString;
				currentString = "";
			}else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if(currentString.length() > 0) {
					currentString = currentString.substring(0, currentString.length()-1);
				}
			}else {
				currentString += e.getKeyChar();
			}
		}
	}
	
}