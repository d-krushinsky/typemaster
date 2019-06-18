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
import nightingale.ui.NUIElement;
import nightingale.ui.NUIGroup;
import util.Random;

public class Survive implements NState{

	protected float speed = 0;
	protected int kills = 0;
	
	protected SurviveListener listener = new SurviveListener();
	
	public NUIGroup ui = new NUIGroup();
	public NUIGroup endUI = new NUIGroup();
	
	private boolean end = false;
	
	public Survive() {
		ui.addElement("TO_MENU", new NButton("To Menu", 10, 10, 130, 30));
		endUI.addElement("END", new NButton("END", 0, 380, 90, 40));
		endUI.addElement("end_label", new NLabel(
				"Castle destroyed.",
				0, 365-Fonts.inputFont.getHeight(), 
				Fonts.inputFont.getStringWidth("Castle destoryed."), Fonts.uiFont.getHeight()));
		
		ui.setCamera(TypeMaster.uiCamera);
		ui.setActionListener(listener);
		endUI.setCamera(TypeMaster.uiCamera);
		endUI.setActionListener(listener);
		
		ui.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
				((NButton)element).setImages(Images.pressedButton, Images.focusedButton, Images.calmButton);
			}else if(element instanceof NLabel) {
				((NLabel)element).setFont(Fonts.uiFont);
			}
		} );
		
		endUI.getElements().forEach( (element) -> { 
			if(element instanceof NButton) { 
				((NButton)element).setFont(Fonts.uiFont);
				((NButton)element).setImages(Images.pressedButton, Images.focusedButton, Images.calmButton);
			}else if(element instanceof NLabel) {
				((NLabel)element).setFont(Fonts.inputFont);
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
		speed = 1+ModeSelection.speed/10;
		monsters.clear();
		whizzbangs.clear();
		startTime = 0;
		kills = 0;
		end = false;
		
		castle.HP(10);
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
		
		for(NUIElement element : endUI.getElements()) {
			element.setX(TypeMaster.canvas.getWidth()/2-element.getWidth()/2);
		}
	}

	private long startTime = 0;
	private float time = 2.6f;
	
	private boolean speedUp;
	
	@Override
	public void update() {
		if(end) {
			endUI.perform(TypeMaster.in);
		}else {
			if(speedUp) {
				speedUp = false;
				speed += 0.1;
			}
			killed.clear();
			hitted.clear();
			synchronized(monsters) {
				if(castle.HP() <= 0) end = true;
				ui.perform(TypeMaster.in);
				if(startTime == 0 || System.currentTimeMillis()-startTime >= time*1000) {
					time = (float)Random.randomDouble(1, 3);
					startTime = System.currentTimeMillis();
					monsters.add(new Monster(MonsterType.Goblin, Words.getRandomWord()));
					monsters.get(monsters.size()-1).setX(Random.randomInt(40, 740));
					monsters.get(monsters.size()-1).setY(-100);
				}
				
				for(Monster monster : monsters) {
					monster.update();
					monster.move(speed);
					if(monster.getY() > castle.getY()) {
						castle.HP(castle.HP()-1);
						killed.add(monster);
						kills--;
					}
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
					if(killed.size() != 0 && kills % 12 == 0) speedUp = true;  
					kills += killed.size(); //FIX!!!!!
				}
				
				if(Input.ENTER_KEY.isClicked()) {
					boolean yep = false;
					for(Monster monster : monsters)
						if(monster.getName().equals(TypeMaster.in.getTypedString())) {
							//monsters.remove(monster);
							whizzbangs.add(new Whizzbang(
									monster, WhizzbangType.Fireball,
									(int)wizard.getX(), (int)wizard.getY()));
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
		Fonts.inputFont.draw(
				TypeMaster.in.getCurrentString(),
				(int)(castle.getCenterX() - Fonts.inputFont.getStringWidth(TypeMaster.in.getCurrentString())/2),
				(int)(castle.getCenterY() - Fonts.inputFont.getHeight()/2),
				g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("Kills: "+kills, 40, (int)(castle.getY()+(Fonts.gameFont.getHeight()*4.5)), g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("Failed "+(10-castle.HP())+"/10", 600, (int)(castle.getY()+(Fonts.gameFont.getHeight()*4.5)), g2d, TypeMaster.gameCamera);
		ui.draw(g);
		if(end) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, TypeMaster.canvas.getWidth(), TypeMaster.canvas.getHeight());
			endUI.draw(g);
		}
	}
	
}