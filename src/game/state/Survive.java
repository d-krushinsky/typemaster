package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import game.Input;
import game.Layout;
import game.TypeMaster;
import game.entity.Castle;
import game.entity.Monster;
import game.entity.MonsterType;
import game.entity.Spell;
import game.entity.SpellType;
import game.entity.Wizard;
import game.entity.WizardType;
import game.logic.Wave;
import game.resources.Fonts;
import game.resources.Images;
import game.resources.Words;
import game.state.ModeSelection.Difficulty;
import game.state.listener.SurviveListener;
import nightingale.state.NState;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIElement;
import nightingale.ui.NUIGroup;
import util.Random;
import util.Ring;

public class Survive implements NState{

	protected float speed = 0;       // Monsters movement speed
	protected int kills = 0;         // Count of kills
	private boolean end = false;     // Is game ended
	private long startTime = 0;      // Last spawn time in millis
	private boolean paused = false;  // Is game paused
	
	protected SurviveListener listener = new SurviveListener();
	
	public NUIGroup ui = new NUIGroup();
	public NUIGroup endUI = new NUIGroup();
	
	protected Ring<Wave> waves = new Ring<Wave>();
	
	public Survive() {
		//All waves
		waves.add(new Wave(false, Random.randomInt(3, 5), new MonsterType[]{ MonsterType.Goblin }, 1.5f, 3.2f)); //Goblins
		waves.add(new Wave(true, Random.randomInt(1, 2), new MonsterType[]{ MonsterType.GoblinBoss }, 0, 1)); //Goblins boss
		
		ui.addElement("TO_MENU", new NButton("To Menu", 10, 546, 130, 44));
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
	Wizard wizard = new Wizard(WizardType.Test);
	List<Monster> monsters = new ArrayList<Monster>();
	List<Spell> spells = new ArrayList<Spell>();
	
	Wave wave; // Current wave of monsters
	Ring<String> bossWords = new Ring<String>(); // Words that use to defeat boss
	
	@Override
	public void install() {
		TypeMaster.in.setCurrentString("");
		TypeMaster.in.typingOn();
		speed = 1+(ModeSelection.speed*2)/10;
		monsters.clear();
		spells.clear();
		wave = new Wave(waves.current());
		startTime = 0;
		kills = 0;
		end = false;
		
		castle.HP(10);
		castle.setWidth(800);
		castle.setHeight((600*25)/100);
		castle.setX(0);
		castle.setY(600-((600*25)/100));
		
		wizard.setX(castle.getWidth()/2-wizard.getWidth()/2);
		wizard.setY(castle.getY() - wizard.getHeight()/2);
		
		for(NUIElement element : endUI.getElements()) {
			element.setX(TypeMaster.canvas.getWidth()/2-element.getWidth()/2);
		}
	}
	
	private void genBossWords() {
		bossWords.clear();
		for(int i=0;i<30;i++) {
			bossWords.add(Words.getRandomWord());
		}
	}
	
	@Override
	public void update() {
		if(end) {
			endUI.perform(TypeMaster.in);
		}else if(!paused){
			speed = (1+ModeSelection.speed/10.0f) + (kills/12)/10.0f;
			synchronized(monsters) {
				if(castle.HP() <= 0) end = true;
				ui.perform(TypeMaster.in);
				
				if(!wave.isOver()) {
					if(wave.checkTime((System.currentTimeMillis()-startTime) / 1000.0f)) {
						wave.spawnMonster(monsters);
						if(wave.isBoss()) {
							genBossWords();
						}
						startTime = System.currentTimeMillis();
					}
				}else if(monsters.isEmpty()) wave = new Wave(waves.next());
				
				updateMonsters();
				updateSpells();
				checkInputString();
				if(Input.ESC_KEY.isClicked()) {
					TypeMaster.in.typingOff();
					paused = !paused;
				}
			}
		}else {
			if(Input.ESC_KEY.isClicked()) {
				TypeMaster.in.typingOn();
				paused = !paused;
			}
		}
	}
	
	private void updateMonsters() {
		synchronized(monsters) {
			for(Monster monster : monsters) {
				monster.update();
				monster.move(speed);
				if(monster.getY() > castle.getY()) {
					castle.HP(castle.HP()-1);
					monster.setDeletable(true);
				}
				if(monster.HP()<=0) monster.setDeletable(true);
			}
		}
	}
	
	private void updateSpells() {
		synchronized(spells){
			for(Spell spell : spells) {
				spell.update();
				if(spell.check()) {
					if(!wave.isBoss()) {
						spell.getTarget().setDeletable(true);
						kills++;
					}else spell.getTarget().HP(spell.getTarget().HP()-1);
					spell.setDeletable(true);
				}
				if(spell.getTarget().shoudDelete()) spell.setDeletable(true);
			}
			spells.removeIf(spell -> spell.shoudDelete() );
			monsters.removeIf(monster -> monster.shoudDelete());
		}
	}
	
	private void checkInputString() {
		if(Input.ENTER_KEY.isClicked()) {
			synchronized(monsters) {
				synchronized(spells) {
					if(!wave.isBoss()) {
						for(Monster monster : monsters)
							if(ModeSelection.diff == Difficulty.HARD) {
								if(monster.getName().equals(TypeMaster.in.getTypedString())) {
									spells.add(new Spell(
											monster, SpellType.Fireball,
											(int)wizard.getX(), (int)wizard.getY()));
									return;
								}
							}else if(ModeSelection.diff == Difficulty.EASY) {
								if(monster.getName().toLowerCase().equals(TypeMaster.in.getTypedString().toLowerCase())) {
									spells.add(new Spell(
											monster, SpellType.Fireball,
											(int)wizard.getX(), (int)wizard.getY()));
									return;
								}
							}
					}else {
						Monster nearest = null;
						for(Monster monster : monsters) {
							if(nearest == null || monster.getY()+monster.getHeight() < nearest.getY()+nearest.getHeight()) {
								nearest = monster;
							}
						}
						if(ModeSelection.diff == Difficulty.HARD) {
							if(bossWords.current().equals(TypeMaster.in.getTypedString())) {
								bossWords.next();
								spells.add(new Spell(
										nearest, SpellType.Fireball,
										(int)wizard.getX(), (int)wizard.getY()));
								return;
							}
						}else if(ModeSelection.diff == Difficulty.EASY) {
							if(bossWords.current().toLowerCase().equals(TypeMaster.in.getTypedString().toLowerCase())) {
								bossWords.next();
								spells.add(new Spell(
										nearest, SpellType.Fireball,
										(int)wizard.getX(), (int)wizard.getY()));
								return;
							}
						}
					}
				}
			}
			TypeMaster.in.setCurrentString(TypeMaster.in.getTypedString());
			TypeMaster.in.restoreTypedString();
		}
	}
	
	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		//background
		g.drawImage(Images.surviveBackground, 0, 0,
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getWidth()),
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getHeight()), null);
		
		synchronized(monsters) {
			for(Monster monster : monsters) {
				g.setColor(new Color(0, 0, 0, 50));
				g.fillOval(
						(int)monster.getX(TypeMaster.gameCamera),
						(int)(monster.getY(TypeMaster.gameCamera)+monster.getHeight(TypeMaster.gameCamera)-monster.getHeight(TypeMaster.gameCamera)/7),
						(int)monster.getWidth(TypeMaster.gameCamera),
						(int)monster.getHeight(TypeMaster.gameCamera)/5);
				monster.draw(g2d, TypeMaster.gameCamera);
			}
		}
		
		castle.draw(g2d, TypeMaster.gameCamera);
		g.setColor(new Color(0, 0, 0, 100));
		g.fillOval(
				(int)wizard.getX(TypeMaster.gameCamera),
				(int)(wizard.getY(TypeMaster.gameCamera)+wizard.getHeight(TypeMaster.gameCamera)-wizard.getHeight(TypeMaster.gameCamera)/7),
				(int)wizard.getWidth(TypeMaster.gameCamera),
				(int)wizard.getHeight(TypeMaster.gameCamera)/5);
		wizard.draw(g2d, TypeMaster.gameCamera);
		
		//draw spells
		synchronized(spells){
			for(Spell spell : spells)
				spell.draw(g2d, TypeMaster.gameCamera, at);
		}
		
		synchronized(monsters) {
			for(Monster monster : monsters) {
				if(!wave.isBoss()) {
					Fonts.gameFont.draw(
							monster.getName(),
							(int)(monster.getCenterX()-(Fonts.gameFont.getStringWidth(monster.getName()))/2),
							(int)(monster.getY()-Fonts.gameFont.getHeight()), 
							g2d, TypeMaster.gameCamera);
				}else {
					for(int i=0;i<5;i++) {
						Fonts.gameFont.draw(
								bossWords.getRight(i), 
								(int)(TypeMaster.canvas.getWidth()-Layout.SPELL_BAR_WIDTH-Fonts.gameFont.getStringWidth(bossWords.getRight(i))), 
								(int)(Layout.BOSS_WORDS_Y-(i*Fonts.gameFont.getHeight())), 
								g2d, TypeMaster.gameCamera);
					}
				}
			}
		}
		
		Fonts.inputFont.draw(
				TypeMaster.in.getCurrentString(),
				(int)(castle.getCenterX() - Fonts.inputFont.getStringWidth(TypeMaster.in.getCurrentString())/2),
				(int)(castle.getCenterY() - Fonts.inputFont.getHeight()/2 + Fonts.inputFont.getHeight()/3),
				g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("Kills: "+kills, 40, (int)(castle.getY()+(Fonts.gameFont.getHeight()*5)), g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("Failed "+(10-castle.HP())+"/10", 600, (int)(castle.getY()+(Fonts.gameFont.getHeight()*5)), g2d, TypeMaster.gameCamera);
		ui.draw(g);
		if(end) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, TypeMaster.canvas.getWidth(), TypeMaster.canvas.getHeight());
			endUI.draw(g);
		}else if(paused) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, TypeMaster.canvas.getWidth(), TypeMaster.canvas.getHeight());
			Fonts.extraFont.draw(
					"Paused",
					TypeMaster.canvas.getWidth()/2 - Fonts.extraFont.getStringWidth("Paused")/2,
					TypeMaster.canvas.getHeight()/2 - Fonts.extraFont.getHeight()/2,
					g2d, TypeMaster.gameCamera);
			Fonts.gameFont.draw(
					"(Press ESCAPE for unpause)",
					TypeMaster.canvas.getWidth()/2 - Fonts.gameFont.getStringWidth("(Press ESCAPE for unpause)")/2,
					TypeMaster.canvas.getHeight()/2 + Fonts.extraFont.getHeight()/2 ,
					g2d, TypeMaster.gameCamera);
		}
	}
	
}