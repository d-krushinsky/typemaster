package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import game.TypeMaster;
import game.entity.Castle;
import game.entity.Monster;
import game.entity.Wizard;
import game.state.listener.SurviveListener;
import nightingale.state.NState;
import nightingale.ui.NButton;
import nightingale.ui.NUIGroup;

public class Survive implements NState{

	protected float speed = 0;
	
	protected SurviveListener listener = new SurviveListener();
	
	public NUIGroup ui = new NUIGroup();
	
	public Survive() {
		ui.addElement("TO_MENU", new NButton("To Menu", 10, 10, 90, 30));
		
		ui.setCamera(TypeMaster.uiCamera);
		ui.setActionListener(listener);
	}
	
	// Entites
	Castle castle = new Castle();
	Wizard wizard = new Wizard();
	List<Monster> monsters = new ArrayList<Monster>();
	
	@Override
	public void install() {
		TypeMaster.in.typingOn();
		speed = ModeSelection.speed;
		
		castle.setWidth(800);
		castle.setHeight((600*25)/100);
		castle.setX(0);
		castle.setY(600-((600*25)/100));
		
		//DELETE THIS
		wizard.setWidth(45);
		wizard.setHeight(64);
		//DELETE THIS
		
		wizard.setX(castle.getWidth()/2-wizard.getWidth()/2);
		wizard.setY(castle.getY() - wizard.getHeight()/2);
	}

	@Override
	public void update() {
		ui.perform(TypeMaster.in);
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		//background
		for(Monster monster : monsters) {
			monster.draw(g2d, TypeMaster.gameCamera);
		}
		//draw whizzbangs
		g.setColor(Color.DARK_GRAY);
		castle.draw(g2d, TypeMaster.gameCamera);
		g.setColor(Color.GREEN);
		wizard.draw(g2d, TypeMaster.gameCamera);
		
		ui.draw(g);
	}
	
}