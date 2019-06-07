package game.state.listener;

import game.TypeMaster;
import nightingale.ui.NActionListener;
import nightingale.ui.NUIElement;

public class SettingsListener implements NActionListener {
	@Override
	public void actionPerform(NUIElement element) {
		if(element.getName() == "BACK") TypeMaster.stateHandler.setState("MENU_STATE");
	}
}
