package footballManagement;

public class Match {
	private int match_id,home_team,away_team,stadium;
	
	private String date;

	private String homeString_team;

	private String awayString_team;

	private String stadiumString;

	public Match(int match_id, int home_team, int away_team, int stadium,String date) {
		super();
		this.match_id = match_id;
		this.home_team = home_team;
		this.away_team = away_team;
		this.stadium = stadium;
		this.date = date;
	}

	public Match(int match_id, String home_team, String away_team, String stadium, String date) {
		super();
		this.match_id = match_id;
		this.homeString_team = home_team;
		this.awayString_team = away_team;
		this.stadiumString = stadium;
		this.date = date;
	}

	public int getMatch_id() {
		return match_id;
	}

	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}

	public int getHome_team() {
		return home_team;
	}

	public void setHome_team(int home_team) {
		this.home_team = home_team;
	}

	public int getAway_team() {
		return away_team;
	}

	public void setAway_team(int away_team) {
		this.away_team = away_team;
	}

	public int getStadium() {
		return stadium;
	}

	public void setStadium(int stadium) {
		this.stadium = stadium;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHomeString_team() {
		return homeString_team;
	}

	public void setHomeString_team(String homeString_team) {
		this.homeString_team = homeString_team;
	}

	public String getAwayString_team() {
		return awayString_team;
	}

	public void setAwayString_team(String awayString_team) {
		this.awayString_team = awayString_team;
	}

	public String getStadiumString() {
		return stadiumString;
	}

	public void setStadiumString(String stadiumString) {
		this.stadiumString = stadiumString;
	}

	@Override
	public String toString() {
		return "Match [match_id=" + match_id + ", home_team=" + home_team + ", away_team=" + away_team + ", stadium="
				+ stadium + ", date=" + date + ", homeString_team=" + homeString_team + ", awayString_team="
				+ awayString_team + ", stadiumString=" + stadiumString + "]";
	}

	
	
}
