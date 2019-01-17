package footballManagement;

public class Player {
	
	private int id;
	private String name;
	private String surname;
	private int country;
	private int team;
	private String football_role;
	private PlayerAttribute playerAttribute;
	private String teamString;
	private String countryString;
	
	public Player(int id, String name, String surname, int country, int team, String football_role,
			PlayerAttribute playerAttribute) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.country = country;
		this.team = team;
		this.football_role = football_role;
		this.playerAttribute = playerAttribute;
	}

	public Player(int id, String name, String surname, String country, String team, String football_role,
			PlayerAttribute pa) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.countryString = country;
		this.teamString = team;
		this.football_role = football_role;
		this.playerAttribute = pa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public String getFootball_role() {
		return football_role;
	}

	public void setFootball_role(String football_role) {
		this.football_role = football_role;
	}

	public PlayerAttribute getPlayerAttribute() {
		return playerAttribute;
	}

	public void setPlayerAttribute(PlayerAttribute playerAttribute) {
		this.playerAttribute = playerAttribute;
	}

	public String getTeamString() {
		return teamString;
	}

	public void setTeamString(String teamString) {
		this.teamString = teamString;
	}

	public String getCountryString() {
		return countryString;
	}

	public void setCountryString(String countryString) {
		this.countryString = countryString;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", country=" + country + ", team="
				+ team + ", football_role=" + football_role + ", playerAttribute=" + playerAttribute.toString() +  "]";
	}
	
	
	
}
