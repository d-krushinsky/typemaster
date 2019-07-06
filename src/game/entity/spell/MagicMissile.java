package game.entity.spell;

import java.util.List;

import game.entity.Monster;
import game.entity.Spell;
import game.entity.SpellType;

public class MagicMissile extends Spell{

	public boolean re = false;
	
	public MagicMissile(Monster target, int x, int y) {
		super(target, SpellType.MagicMissile, x, y);
	}

	@Override
	public void magic(List<Monster> list) {		
		if(list == null) return;
		if(shouldDelete() && !re) {
			setDeletable(false);
			re = true;
			Monster min = null;
			for(Monster monster : list) {
				if(monster != target && (min==null || min.getDistanceTo(this) > monster.getDistanceTo(this)))
					min = monster;
			}
			if(min==null) setDeletable(true);
			else target = min;
		}
	}
	
}
