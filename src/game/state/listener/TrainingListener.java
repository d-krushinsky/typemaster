package game.state.listener;

import game.TypeMaster;
import nightingale.ui.NActionListener;
import nightingale.ui.NUIElement;

public class TrainingListener implements NActionListener {
	@Override
	public void actionPerform(NUIElement element) {
		if(element.getName() == "TO_MENU") {
			TypeMaster.stateHandler.setState("MENU_STATE");
		}
	}
}