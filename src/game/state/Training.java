package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Input;
import game.TypeMaster;
import game.entity.Monster;
import game.entity.Wizard;
import game.state.listener.TrainingListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NUIGroup;
import util.Random;

public class Training implements NState{
	protected NActionListener listener = new TrainingListener();
	public NUIGroup ui = new NUIGroup();
	
	Wizard wizard = new Wizard();
	Monster doll = new Monster(-1, "doll");
	{
		wizard.setX(240); wizard.setY(340);
		wizard.setWidth(30); wizard.setHeight(45);
		
		doll.setX(615); doll.setY(340);
		doll.setWidth(40); doll.setHeight(45);
	}
	
	public Training() {
		ui.addElement("TO_MENU", new NButton("To Menu", 10, 10, 60, 40));
		
		ui.setCamera(TypeMaster.uiCamera);
		ui.setActionListener(listener);
	}
	
	@Override
	public void install() {
		TypeMaster.in.typingOn();
		
		wizard = new Wizard();
		doll = new Monster(-1, "doll");
		
		wizard.setX(240); wizard.setY(340);
		wizard.setWidth(30); wizard.setHeight(45);
		doll.setX(615); doll.setY(340);
		doll.setWidth(40); doll.setHeight(45);
	}

	// !REMOVE IT WHEN REFACTORING!
	public String[] words = new String[] {
		"biba", "jopa", "KAVO i SHO", "Glad", "Dima", "Vlad", "Krol'"
	};
	// !REMOVE IT WHEN REFACTORING!
	
	@Override
	public void update() {
		ui.perform(TypeMaster.in);
		if(Input.ENTER_KEY.isClicked())
			if(TypeMaster.in.getTypedString().equals(doll.getName())) {
				doll.setName(words[Random.randomInt(words.length)]);
			}else {
				TypeMaster.in.setCurrentString(TypeMaster.in.getTypedString());
				TypeMaster.in.restoreTypedString();
			}
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		ui.draw(g, g2d, at);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine((int)TypeMaster.gameCamera.getX(0), (int)TypeMaster.gameCamera.getY(385),
				(int)TypeMaster.gameCamera.scale(2000), (int)TypeMaster.gameCamera.scale(385));
		g.setColor(Color.GREEN);
		g.drawRect(
				(int)wizard.getX(TypeMaster.gameCamera),
				(int)wizard.getY(TypeMaster.gameCamera),
				(int)wizard.getWidth(TypeMaster.gameCamera),
				(int)wizard.getHeight(TypeMaster.gameCamera));
		g.setColor(Color.RED);
		g.drawString(doll.getName(), (int)doll.getX(TypeMaster.gameCamera),	(int)doll.getY(TypeMaster.gameCamera)-1);
		g.drawRect(
				(int)doll.getX(TypeMaster.gameCamera),
				(int)doll.getY(TypeMaster.gameCamera),
				(int)doll.getWidth(TypeMaster.gameCamera),
				(int)doll.getHeight(TypeMaster.gameCamera));
		
		if(TypeMaster.in.getCurrentString().equals(doll.getName())) {
			g.setColor(Color.GREEN);
		}else if(doll.getName().length() > TypeMaster.in.getCurrentString().length() &&
				doll.getName().substring(0, TypeMaster.in.getCurrentString().length()).equals(TypeMaster.in.getCurrentString())){
			g.setColor(Color.YELLOW);
		}else {
			g.setColor(Color.RED);
		}
		g.drawString(
				TypeMaster.in.getCurrentString(),
				(int)TypeMaster.gameCamera.getX(300), 
				(int)TypeMaster.gameCamera.getY(450));
	}
	
}
