package game.state.listener;

import game.TypeMaster;
import game.state.ModeSelection;
import nightingale.ui.NActionListener;
import nightingale.ui.NLabel;
import nightingale.ui.NUIElement;

public class ModeSelectionListener implements NActionListener {
	
	private ModeSelection ms;
	public ModeSelectionListener(ModeSelection ms) {
		this.ms = ms;
	}
	
	@Override
	public void actionPerform(NUIElement element) {
		if(element.getName() == "BACK") {
			TypeMaster.stateHandler.setState("MENU_STATE");
		}else if(element.getName() == "TRAINING") {
			TypeMaster.stateHandler.setState("TRAINING_STATE");
		}else if(element.getName() == "SURVIVE") {
			ms.survive = true;
		}else if(element.getName() == "Back_to_selection") {
			ms.survive = false;
		}else if(element.getName() == "down_speed") {
			if(ms.speed > 1) ms.speed--;
			((NLabel)ms.ssUI.getElement("speed")).setText("Speed: " + ms.speed);
		}else if(element.getName() == "up_speed") {
			if(ms.speed < 10) ms.speed++;
			((NLabel)ms.ssUI.getElement("speed")).setText("Speed: " + ms.speed);
		}
	}
}