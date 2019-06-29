package game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import game.Input;
import game.TypeMaster;
import game.entity.Monster;
import game.entity.MonsterType;
import game.entity.Spell;
import game.entity.SpellType;
import game.entity.Wizard;
import game.entity.WizardType;
import game.resources.Fonts;
import game.resources.Images;
import game.resources.Words;
import game.state.listener.TrainingListener;
import nightingale.state.NState;
import nightingale.ui.NActionListener;
import nightingale.ui.NButton;
import nightingale.ui.NLabel;
import nightingale.ui.NUIGroup;

public class Training implements NState{
	protected NActionListener listener = new TrainingListener();
	public NUIGroup ui = new NUIGroup();
	
	Wizard wizard = new Wizard(WizardType.Test);
	Monster doll = new Monster(MonsterType.Doll, "doll");
	List<Spell> spells = new ArrayList<Spell>();
	
	public Training() {
		ui.addElement("TO_MENU", new NButton("To Menu", 10, 10, 130, 30));
		
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
	
	@Override
	public void install() {
		TypeMaster.in.typingOn();
		
		wizard = new Wizard(WizardType.Test);
		doll = new Monster(MonsterType.Doll, "doll");
		
		wizard.setX(240); wizard.setY(385-wizard.getHeight());
		doll.setX(615); doll.setY(385-doll.getHeight());
	}
	
	@Override
	public void update() {
		ui.perform(TypeMaster.in);
		
		synchronized(spells){
			for(Spell spell : spells) {
				spell.update();
				if(spell.check()) {
					doll.setName(Words.getRandomWord());
					spell.setDeletable(true);
				}
			}
			spells.removeIf(spell -> spell.shoudDelete());
		}
		
		if(Input.ENTER_KEY.isClicked())
			if(TypeMaster.in.getTypedString().equals(doll.getName())) {
				spells.add(new Spell(doll, SpellType.Fireball, (int)wizard.getX(), (int)wizard.getY()));
			}else {
				TypeMaster.in.setCurrentString(TypeMaster.in.getTypedString());
				TypeMaster.in.restoreTypedString();
			}
	}

	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		ui.draw(g);
		g.drawImage(Images.surviveBackground, 0, (int)TypeMaster.gameCamera.getY(385),
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getWidth()),
				(int)TypeMaster.uiCamera.scale(Images.surviveBackground.getHeight()), null);
		
		g.setColor(Color.GREEN);
		wizard.draw(g2d, TypeMaster.gameCamera);
		
		doll.draw(g2d, TypeMaster.gameCamera);
		Fonts.gameFont.draw(
				doll.getName(),
				(int)(doll.getCenterX()-(Fonts.gameFont.getStringWidth(doll.getName()))/2),
				(int)(doll.getY()-Fonts.gameFont.getHeight()), 
				g2d, TypeMaster.gameCamera);
		
		synchronized(spells){
			for(Spell spell : spells) 
				spell.draw(g2d, TypeMaster.gameCamera, at);
		}
		
		Fonts.extraFont.draw(TypeMaster.in.getCurrentString(), 300, 450, g2d, TypeMaster.gameCamera);
	}
	
}
