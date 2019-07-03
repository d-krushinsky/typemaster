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
	protected float minTime = 0, maxTime = 0;
	protected float spawnTime = 0;
	
	public Wave(boolean boss, int count, MonsterType[] types, float minTime, float maxTime) {
		this.boss = boss;
		this.count = count;
		this.types = types;
		this.minTime = minTime;
		this.maxTime = maxTime;
		genSpawnTime();
	}
	
	public boolean isBoss() { return boss; }
	public boolean isOver() { return count<=0; }
	
	private void genSpawnTime() {
		spawnTime = Random.randomFloat(minTime, maxTime);
	}
	
	public boolean checkTime(float current) {
		return current >= spawnTime;
	}
	
	public void spawnMonster(List<Monster> monsters) {
		if(!isOver()) {
			count--;
			Monster monster = new Monster(types[Random.randomInt(types.length)], Words.getRandomWord());
			monster.setY(-monster.getHeight());
			monster.setX(Random.randomInt(Layout.SPAWN_LEFT_OFFSET,
					                     (int)(TypeMaster.canvas.getWidth()-monster.getWidth()-Layout.SPELL_BAR_WIDTH)));
			monsters.add(monster);
			genSpawnTime();
		}
	}
	
}
