package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import game.Input;
import game.TypeMaster;
import game.entity.Castle;
import game.entity.Monster;
import game.entity.MonsterType;
import game.entity.Whizzbang;
import game.entity.WhizzbangType;
import game.entity.Wizard;
import game.resources.Fonts;
import game.resources.Images;
import game.resources.Words;
import game.state.listener.SurviveListener;
import nightingale.state.NState;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIGroup;
import util.Random;

public class Survive implements NState{

	protected float speed = 0;
	protected int kills = 0;
	
	protected SurviveListener listener = new SurviveListener();
	
	public NUIGroup ui = new NUIGroup();
	
	public Survive() {
		ui.addElement("TO_MENU", new NButton("To Menu", 10, 10, 90, 30));
		
		ui.setCamera(TypeMaster.uiCamera);
		ui.setActionListener(listener);
		
		ui.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
				((NButton)element).setImages(Images.pressedButton, Images.focusedButton, Images.calmButton);
			}else if(element instanceof NLabel) {
				((NLabel)element).setFont(Fonts.uiFont);
			}
		} );
	}
	
	// Entites
	Castle castle = new Castle();
	Wizard wizard = new Wizard(true);
	List<Monster> monsters = new ArrayList<Monster>();
	List<Whizzbang> whizzbangs = new ArrayList<Whizzbang>();
	
	List<Monster> killed = new ArrayList<Monster>();
	List<Whizzbang> hitted = new ArrayList<Whizzbang>();
	
	@Override
	public void install() {
		TypeMaster.in.typingOn();
		speed = ModeSelection.speed;
		monsters.clear();
		whizzbangs.clear();
		startTime = 0;
		
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

	private long startTime = 0;
	private float time = 2.6f;
	
	@Override
	public void update() {
		killed.clear();
		hitted.clear();
		synchronized(monsters) {
			ui.perform(TypeMaster.in);
			if(startTime == 0 || System.currentTimeMillis()-startTime >= time*1000) {
				startTime = System.currentTimeMillis();
				monsters.add(new Monster(MonsterType.Goblin, Words.getRandomWord()));
				monsters.get(monsters.size()-1).setX(Random.randomInt(40, 740));
				monsters.get(monsters.size()-1).setY(-100);
			}
			
			for(Monster monster : monsters) {
				monster.update();
				monster.move(speed);
			}
			
			synchronized(whizzbangs){
				for(Whizzbang whizzbang : whizzbangs) {
					whizzbang.update();
					if(whizzbang.check()) {
						killed.add(whizzbang.getTarget());
						hitted.add(whizzbang);
					}
				}
				whizzbangs.removeAll(hitted);
				monsters.removeAll(killed);
				kills += killed.size(); //FIX!!!!!
			}
			
			if(Input.ENTER_KEY.isClicked()) {
				boolean yep = false;
				for(Monster monster : monsters)
					if(monster.getName().equals(TypeMaster.in.getTypedString())) {
						//monsters.remove(monster);
						whizzbangs.add(new Whizzbang(monster, WhizzbangType.Fireball, (int)wizard.getX(), (int)wizard.getY()));
						yep = true;
						break;
					}
				if(!yep) {
					TypeMaster.in.setCurrentString(TypeMaster.in.getTypedString());
					TypeMaster.in.restoreTypedString();
				}
			}
		}
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		//background
		g.drawImage(Images.surviveBackground, 0, 0,
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getWidth()),
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getHeight()), null);
		
		synchronized(monsters) {
			g.setColor(Color.RED);
			for(Monster monster : monsters) {
				monster.draw(g2d, TypeMaster.gameCamera);
			}
		}
		
		g.setColor(Color.DARK_GRAY);
		castle.draw(g2d, TypeMaster.gameCamera);
		
		//draw whizzbangs
		g.setColor(Color.GREEN);
		wizard.draw(g2d, TypeMaster.gameCamera);
		synchronized(whizzbangs){
			for(Whizzbang whizzbang : whizzbangs) {
				whizzbang.draw(g2d, TypeMaster.gameCamera, at);
			}
		}
		synchronized(monsters) {
			for(Monster monster : monsters) {
				Fonts.gameFont.draw(
						monster.getName(),
						(int)(monster.getCenterX()-(Fonts.gameFont.getStringWidth(monster.getName()))/2),
						(int)(monster.getY()-Fonts.gameFont.getHeight()), 
						g2d, TypeMaster.gameCamera);
			}
		}
		Fonts.extraFont.draw(
				TypeMaster.in.getCurrentString(),
				(int)(castle.getCenterX() - Fonts.extraFont.getStringWidth(TypeMaster.in.getCurrentString())/2),
				(int)(castle.getCenterY() - Fonts.extraFont.getHeight()/2),
				g2d, TypeMaster.gameCamera);
		Fonts.uiFont.draw("Kills: "+kills, 40, (int)(castle.getY()+(Fonts.uiFont.getHeight()*4.5)), g2d, TypeMaster.gameCamera);
		ui.draw(g);
	}
	
}