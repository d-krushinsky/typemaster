package game.entity;

public class Monster extends Entity {
	private String name = "";
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Monster(int type, String name) {
		this.type = type;
		this.name = name;
	}
}
