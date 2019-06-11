package game.resources;

import nightingale.graph.NText;

public abstract class Fonts {

	private static String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 1234567890!?()<>[]{}$+-*/\\=&#@:;.,|\"'^";
	
	public static NText uiFont = new NText(symbols, 12, 16, "/res/fonts/ui_font.png");
	public static NText extraFont = new NText(symbols, 24, 32, "/res/fonts/extra_ui_font.png");
	public static NText gameFont = new NText(symbols, 10, 14, "/res/fonts/game_font.png");
	//public static NText inputFont = new NText(symbols, ?, ?, ?);
	//public static NText monsterFont = new NText(symbols, ?, ?, ?);
	
}
