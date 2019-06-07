package game.state.listener;

import game.TypeMaster;
import nightingale.ui.NActionListener;
import nightingale.ui.NUIElement;

public class ModeSelectionListener implements NActionListener {
	@Override
	public void actionPerform(NUIElement element) {
		if(element.getName() == "BACK") {
			TypeMaster.stateHandler.setState("MENU_STATE");
		}else if(element.getName() == "TRAINING") {
			//TypeMaster.stateHandler.setState("TRAINING_STATE");
		}else if(element.getName() == "SURVIVE") {
			
		}
	}
}
