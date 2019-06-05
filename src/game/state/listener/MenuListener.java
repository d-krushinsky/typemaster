package game.state.listener;
import nightingale.ui.NActionListener;
import nightingale.ui.NUIElement;

public class MenuListener implements NActionListener {
	@Override
	public void actionPerform(NUIElement element) {
		if(element.getName() == "EXIT") System.exit(1);
	}
}
