package game.entity.spell;

import java.util.List;

import game.entity.Monster;
import game.entity.Spell;
import game.entity.SpellType;

public class Fireball extends Spell{

	public Fireball(Monster target, int x, int y) {
		super(target, SpellType.Fireball, x, y);
	}

	@Override
	public void magic(List<Monster> list) {
	}

}