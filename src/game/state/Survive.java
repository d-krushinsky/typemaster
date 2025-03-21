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
import game.entity.spell.Fireball;
import game.entity.spell.MagicMissile;
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
		// All waves
		waves.add(new Wave(false, Random.randomInt(14, 32), new MonsterType[]{ MonsterType.Goblin }, 1f, 3.2f)); //Goblins
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
	Ring<SpellType> spellQueue = new Ring<SpellType>(); // Queue of spells
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
		
		//Fill spells queue
		for(int i=0;i<15;i++) {
			if(Random.randomInt(4)==0) spellQueue.add(SpellType.MagicMissile);
			else spellQueue.add(SpellType.Fireball);
		}
		
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
			ui.perform(TypeMaster.in);
			speed = (0.5f+ModeSelection.speed/10.0f) + (kills/12)/10.0f;
			synchronized(monsters) {
				if(castle.HP() <= 0) end = true;
				
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
				deleteDeletable();
				checkInputString();
				if(Input.ESC_KEY.isClicked()) {
					TypeMaster.in.typingOff();
					paused = !paused;
				}
			}
		}else {
			ui.perform(TypeMaster.in);
			if(Input.ESC_KEY.isClicked()) {
				TypeMaster.in.typingOn();
				paused = !paused;
			}
		}
	}
	
	private void updateMonsters() {
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
				if(spell.getTarget().shouldDelete()) spell.setDeletable(true);
				spell.magic(monsters);
			}
		}
	}
	
	public void deleteDeletable() {
		synchronized (spells) {
			spells.removeIf(spell -> spell.shouldDelete() );
		}
		monsters.removeIf(monster -> monster.shouldDelete());
	}
	
	private void castSpell(Monster monster) {
		if(spellQueue.current() == SpellType.Fireball)
			spells.add(new Fireball(monster, (int)wizard.getX(), (int)wizard.getY()));
		else if(spellQueue.current() == SpellType.MagicMissile)
			spells.add(new MagicMissile(monster, (int)wizard.getX(), (int)wizard.getY()));
		spellQueue.next();
	}
	
	// If it's boss, find nearest monster and check, is current spell typed
	// If it's monsters, find monster with typed name
	private void checkInputString() {
		if(Input.ENTER_KEY.isClicked()) {
			synchronized(spells) {
				if(!wave.isBoss()) {
					for(Monster monster : monsters)
						if(ModeSelection.diff == Difficulty.HARD) {
							if(monster.getName().equals(TypeMaster.in.getTypedString())) {
								castSpell(monster);
								return;
							}
						}else if(ModeSelection.diff == Difficulty.EASY) {
							if(monster.getName().toLowerCase().equals(TypeMaster.in.getTypedString().toLowerCase())) {
								castSpell(monster);
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
							castSpell(nearest);
							return;
						}
					}else if(ModeSelection.diff == Difficulty.EASY) {
						if(bossWords.current().toLowerCase().equals(TypeMaster.in.getTypedString().toLowerCase())) {
							bossWords.next();
							castSpell(nearest);
							return;
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
		// Background
		g.drawImage(Images.surviveBackground, 0, 0,
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getWidth()),
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getHeight()), null);
		
		// Draw monsters
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
		
		// Draw spells
		synchronized(spells){
			for(Spell spell : spells)
				spell.draw(g2d, TypeMaster.gameCamera, at);
		}
		
		// Draw text
		synchronized(monsters) {
			for(Monster monster : monsters) {
				if(!wave.isBoss()) {
					int x = (int)(monster.getCenterX()-(Fonts.gameFont.getStringWidth(monster.getName()))/2);
					if(x<0) x = 0;
					else if(x + Fonts.gameFont.getStringWidth(monster.getName()) > Settings.DEFAULT_WIDTH-Layout.SPELL_BAR_WIDTH) 
						x = Settings.DEFAULT_WIDTH-Layout.SPELL_BAR_WIDTH - Fonts.gameFont.getStringWidth(monster.getName());
					Fonts.gameFont.draw(
							monster.getName(),
							x,
							(int)(monster.getY()-Fonts.gameFont.getHeight()), 
							g2d, TypeMaster.gameCamera);
				}else {
					for(int i=0;i<8;i++) {
						Fonts.spellFont.draw(
								bossWords.getRight(i), 
								(int)(Settings.DEFAULT_WIDTH-Layout.SPELL_BAR_WIDTH-Fonts.spellFont.getStringWidth(bossWords.getRight(i))), 
								(int)(Layout.BOSS_WORDS_Y-(i*Fonts.spellFont.getHeight())), 
								g2d, TypeMaster.gameCamera);
					}
				}
			}
		}
		
		// Draw UI
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(
				(int)TypeMaster.gameCamera.getX(Settings.DEFAULT_WIDTH-Layout.SPELL_BAR_WIDTH), 
				(int)0, 
				(int)TypeMaster.gameCamera.scale(Layout.SPELL_BAR_WIDTH), 
				(int)TypeMaster.gameCamera.scale(Settings.DEFAULT_HEIGHT-castle.getHeight()));
		g.setColor(Color.BLACK);
		g.drawRect(
				(int)TypeMaster.gameCamera.getX(Settings.DEFAULT_WIDTH-Layout.SPELL_BAR_WIDTH), 
				(int)0, 
				(int)TypeMaster.gameCamera.scale(Layout.SPELL_BAR_WIDTH), 
				(int)TypeMaster.gameCamera.scale(Settings.DEFAULT_HEIGHT-castle.getHeight()));
		for(int i=0;i<15;i++) {
			switch(spellQueue.getRight(i)){
			case Fireball:
				g.drawImage(Images.fireballIcon,
						(int)TypeMaster.gameCamera.getX(Settings.DEFAULT_WIDTH-Layout.SPELL_BAR_WIDTH/2-Images.fireballIcon.getWidth()/2),
						(int)TypeMaster.gameCamera.getY(Settings.DEFAULT_HEIGHT-Images.fireballIcon.getHeight()-castle.getHeight()-Images.fireballIcon.getHeight()*i-2*i),
						(int)TypeMaster.gameCamera.scale(Images.fireballIcon.getWidth()),
						(int)TypeMaster.gameCamera.scale(Images.fireballIcon.getHeight()), null);
				break;
			case MagicMissile:
				g.drawImage(Images.magicmissileIcon,
						(int)TypeMaster.gameCamera.getX(Settings.DEFAULT_WIDTH-Layout.SPELL_BAR_WIDTH/2-Images.magicmissileIcon.getWidth()/2),
						(int)TypeMaster.gameCamera.getY(Settings.DEFAULT_HEIGHT-Images.magicmissileIcon.getHeight()-castle.getHeight()-Images.magicmissileIcon.getHeight()*i-2*i),
						(int)TypeMaster.gameCamera.scale(Images.magicmissileIcon.getWidth()),
						(int)TypeMaster.gameCamera.scale(Images.magicmissileIcon.getHeight()), null);
				break;
			}
		}
		
		Fonts.inputFont.draw(
				TypeMaster.in.getCurrentString(),
				(int)(castle.getCenterX() - Fonts.inputFont.getStringWidth(TypeMaster.in.getCurrentString())/2),
				(int)(castle.getCenterY() - Fonts.inputFont.getHeight()/2 + Fonts.inputFont.getHeight()/3),
				g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("Kills: "+kills, 40, (int)(castle.getY()+(Fonts.gameFont.getHeight()*5)), g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw("Failed "+(10-castle.HP())+"/10", 600, (int)(castle.getY()+(Fonts.gameFont.getHeight()*5)), g2d, TypeMaster.gameCamera);
		if(end) {
			ui.draw(g2d);
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, TypeMaster.canvas.getWidth(), TypeMaster.canvas.getHeight());
			endUI.draw(g);
		}else if(paused) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, TypeMaster.canvas.getWidth(), TypeMaster.canvas.getHeight());
			Fonts.extraFont.draw(
					"Paused",
					Settings.DEFAULT_WIDTH/2 - Fonts.extraFont.getStringWidth("Paused")/2,
					Settings.DEFAULT_HEIGHT/2 - Fonts.extraFont.getHeight()/2,
					g2d, TypeMaster.gameCamera);
			Fonts.gameFont.draw(
					"(Press ESCAPE for unpause)",
					Settings.DEFAULT_WIDTH/2 - Fonts.gameFont.getStringWidth("(Press ESCAPE for unpause)")/2,
					Settings.DEFAULT_HEIGHT/2 + Fonts.extraFont.getHeight()/2 ,
					g2d, TypeMaster.gameCamera);
			ui.draw(g2d);
		}else ui.draw(g2d);
	}
}