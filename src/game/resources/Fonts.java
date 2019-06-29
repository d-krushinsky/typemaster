package game.resources;

import nightingale.graph.NText;

public abstract class Fonts {

	private static String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 1234567890!?()<>[]{}$+-*/\\=&#@:;.,|\"'^";
	
	public static NText uiFont = new NText(symbols, 12, 12, "/res/fonts/font_new.png");
	public static NText extraFont = new NText(symbols, 12, 12, "/res/fonts/font_new.png");
	public static NText gameFont = new NText(symbols, 12, 12, "/res/fonts/font_new.png");
	public static NText inputFont = new NText(symbols, 12, 12, "/res/fonts/font_new.png");
	
	static {
		extraFont.expand(2);
		inputFont.expand(1.5f);
	}
	
}