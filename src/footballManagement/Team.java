package footballManagement;

public class Team {
	private int team_id;
	private String name;
	private int country;
	private String status;
	private float value;
	private int stadium;
	private int year;
	
	private TeamAttribute teamAttribute;
	private String countryString;
	private String stadiumString;

	public Team(int team_id, String name, int country, String status, float value, int stadium, int year,
			TeamAttribute teamAttribute) {
		super();
		this.team_id = team_id;
		this.name = name;
		this.country = country;
		this.status = status;
		this.value = value;
		this.stadium = stadium;
		this.year = year;
		this.teamAttribute = teamAttribute;
	}

	public Team(int team_id, String name, String country, String status, float value, String stadium, int year,
			TeamAttribute ta) {
		this.team_id = team_id;
		this.name = name;
		this.countryString = country;
		this.status = status;
		this.value = value;
		this.stadiumString = stadium;
		this.year = year;
		this.teamAttribute = ta;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getStadium() {
		return stadium;
	}

	public void setStadium(int stadium) {
		this.stadium = stadium;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public TeamAttribute getTeamAttribute() {
		return teamAttribute;
	}

	public void setTeamAttribute(TeamAttribute teamAttribute) {
		this.teamAttribute = teamAttribute;
	}

	public String getCountryString() {
		return countryString;
	}

	public void setCountryString(String countryString) {
		this.countryString = countryString;
	}

	public String getStadiumString() {
		return stadiumString;
	}

	public void setStadiumString(String stadiumString) {
		this.stadiumString = stadiumString;
	}

	@Override
	public String toString() {
		return "Team [team_id=" + team_id + ", name=" + name + ", country=" + country + ", status=" + status
				+ ", value=" + value + ", stadium=" + stadium + ", year=" + year + ", teamAttribute=" + teamAttribute.toString()
				+ "]";
	}
	
	
}
