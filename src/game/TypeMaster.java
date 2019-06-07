package game;

import java.awt.Toolkit;

import javax.swing.JFrame;

import game.state.Game;
import game.state.Menu;
import game.state.ModeSelection;
import game.state.Settings;
import game.state.Training;
import nightingale.graph.NCanvas;
import nightingale.input.NInput;
import nightingale.state.NStateHandler;
import nightingale.thread.NThread;
import nightingale.util.NCamera;

public class TypeMaster {
	public static JFrame gameFrame = new JFrame("Type master");
	public static NInput in = new Input();
	public static NCanvas canvas = new NCanvas(new Drawer());
	public static Toolkit kit = Toolkit.getDefaultToolkit();
	public static NStateHandler stateHandler = new NStateHandler();
	static NThread renderThread = new NThread("RENDER_THREAD", canvas);
	static NThread updateThread = new NThread("UPDATE_THREAD", new Runnable() {
		public void run() {
			stateHandler.updateCurrentState();
		}
	});
	
	public static int getFPS() { return renderThread.getTicks(); }
	public static int getUPS() { return updateThread.getTicks(); }
	
	public static NCamera uiCamera = new NCamera();
	public static NCamera gameCamera = new NCamera();
	
	public static void updateResolution() {
		canvas.setSize(Settings.resolution.width, Settings.resolution.height);
		updateCams();
		frameRefresh();
	}
	
	public static void frameRefresh() {
		gameFrame.pack();
		gameFrame.setLocation(kit.getScreenSize().width /2 - gameFrame.getWidth()/2, 
	  			  kit.getScreenSize().height/2 - gameFrame.getHeight()/2);
	}
	
	public static void updateCams() {
		uiCamera.delta = canvas.getHeight() / (1.0f*Settings.DEFAULT_HEIGHT);
		gameCamera.delta = canvas.getHeight() / (1.0f*Settings.DEFAULT_HEIGHT);
	}
	
	public static void init() {
		//Settings.load();		
		canvas.setSize(Settings.resolution);
		canvas.addKeyListener(in);
		canvas.addMouseListener(in);
		canvas.addMouseMotionListener(in);
		
		stateHandler.addState("MENU_STATE", new Menu());
		stateHandler.addState("SETTINGS_STATE", new Settings());
		stateHandler.addState("MODE_SELECTION_STATE", new ModeSelection());
		stateHandler.addState("TRAINING_STATE", new Training());
		stateHandler.addState("GAME_STATE", new Game());
		
		stateHandler.setState("MENU_STATE");
		
		gameFrame.add(canvas);
		gameFrame.pack();
		gameFrame.setResizable(false);
		gameFrame.setFocusable(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLocation(kit.getScreenSize().width /2 - gameFrame.getWidth()/2, 
				  			  kit.getScreenSize().height/2 - gameFrame.getHeight()/2);
		gameFrame.setVisible(true);
		
		renderThread.maxRate = 60;
		updateThread.maxRate = 60;
		renderThread.start();
		updateThread.start();
	}

	public static void setFullScreen(int value) {}

	public static void setVolume(int i) {}
}