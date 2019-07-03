package game.logic;

import java.util.List;

import game.Layout;
import game.TypeMaster;
import game.entity.Monster;
import game.entity.MonsterType;
import game.resources.Words;
import util.Random;

public class Wave {

	protected boolean boss = false;
	protected int count = 0;
	protected MonsterType[] types;
	
	public Wave(boolean boss, int count, MonsterType[] types) {
		this.boss = boss;
		this.count = count;
		this.types = types;
	}
	
	public boolean isBoss() { return boss; }
	public boolean isOver() { return count<=0; }
	
	public void spawnMonster(List<Monster> monsters) {
		if(!isOver()) {
			count--;
			Monster monster = new Monster(types[Random.randomInt(types.length)], Words.getRandomWord());
			monster.setY(-monster.getHeight());
			monster.setX(Random.randomInt(Layout.SPAWN_LEFT_OFFSET,
					                     (int)(TypeMaster.canvas.getWidth()-monster.getWidth()-Layout.SPELL_BAR_WIDTH)));
			monsters.add(monster);
		}
	}
	
}
