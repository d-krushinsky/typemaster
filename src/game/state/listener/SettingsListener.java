package game.state.listener;

import game.TypeMaster;
import game.state.Settings;
import nightingale.ui.NActionListener;
import nightingale.ui.NUIElement;

public class SettingsListener implements NActionListener {
	@Override
	public void actionPerform(NUIElement element) {
		if(element.getName() == "BACK") {
			TypeMaster.stateHandler.setState("MENU_STATE");
		} else if(element.getName() == "NEXT_RESO") {
			Settings.resolution = Settings.resolutions.next();
			synchronized(TypeMaster.canvas) {
				TypeMaster.updateResolution();
			}
		} else if(element.getName() == "PREV_RESO") {
			Settings.resolution = Settings.resolutions.prev();
			synchronized(TypeMaster.canvas) {
				TypeMaster.updateResolution();
			}
		}
	}
}