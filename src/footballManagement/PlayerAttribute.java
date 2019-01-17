package footballManagement;

public class PlayerAttribute {

	private int physical,speed,mental,attack,defense,technique;

	public PlayerAttribute(int physical, int speed, int mental, int attack, int defense, int technique) {
		super();
		this.physical = physical;
		this.speed = speed;
		this.mental = mental;
		this.attack = attack;
		this.defense = defense;
		this.technique = technique;
	}

	public int getPhysical() {
		return physical;
	}

	public void setPhysical(int physical) {
		this.physical = physical;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getMental() {
		return mental;
	}

	public void setMental(int mental) {
		this.mental = mental;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getTechnique() {
		return technique;
	}

	public void setTechnique(int technique) {
		this.technique = technique;
	}

	@Override
	public String toString() {
		return "PlayerAttribute [physical=" + physical + ", speed=" + speed + ", mental=" + mental + ", attack="
				+ attack + ", defense=" + defense + ", technique=" + technique + "]";
	}

	
	
}
