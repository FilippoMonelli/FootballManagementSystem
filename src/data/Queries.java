package data;

public class Queries {

	private final String selectAdmin = "SELECT * \r\n"+
								"FROM admin A\r\n"+ 
								"WHERE A.admin_id = ";
	
	private final String selectManager = "SELECT * \r\n"+
								"FROM manager M\r\n"+ 
								"WHERE M.manager_id = ";
	
	private final String selectClient = "SELECT * \r\n"+
									"FROM client C\r\n"+ 
									"WHERE C.client_id = ";
	
	private final String selectAllCountries = "SELECT *"+
										"FROM apps_countries;";
	
	private final String selectAllClient="SELECT * FROM client";
	private final String selectAllManager="SELECT * FROM manager";
	private final String selectAllAdmin="SELECT * FROM admin";
	
	private final String selectAllPlayers="SELECT c.country_name,p.*,t.name as 'Team name'\r\n" + 
			"FROM apps_countries c join player p\r\n" + 
			"on c.country_id=p.country\r\n" + 
			"left join team t\r\n" + 
			"on p.team=t.team_id;";
	
	private final String selectAllStadiums="SELECT * FROM stadium;";
	
	private final String selectAllMatches="SELECT m.*,t.name as 'Away Team',t1.name as 'Home Team',s.name as 'Stadium name'\r\n" + 
			"FROM footballMatch m JOIN team t\r\n" + 
			"on m.away_team=t.team_id\r\n" + 
			"join team t1\r\n" + 
			"on m.home_team=t1.team_id\r\n" + 
			"join stadium s\r\n" + 
			"on s.stadium_id=m.stadium";
	
	private final String selectAllTeams="SELECT c.country_name,t.*,s.name as 'Stadium name'\r\n" + 
			"FROM apps_countries c JOIN team t\r\n" + 
			"on c.country_id=t.country\r\n" + 
			"JOIN stadium s\r\n" + 
			"ON t.stadium=s.stadium_id;";
	
	private final String selectMatchFromStadium="SELECT m.*,t.name as 'Away Team',t1.name as 'Home Team',s.name as 'Stadium name'\r\n" + 
			"FROM footballMatch m JOIN team t\r\n" + 
			"on m.away_team=t.team_id\r\n" + 
			"join team t1\r\n" + 
			"on m.home_team=t1.team_id\r\n" + 
			"join stadium s\r\n" + 
			"on s.stadium_id=m.stadium\r\n"+
			"WHERE s.name = \'";
	
	private final String selectTicket="SELECT t.* "+
			"FROM ticket t";
	
	public Queries() {
		
	}

	public String selectAllCountries() {
		return selectAllCountries;
	}
	
	public String registerAdmin(String name,String surname,String username,String password,
			int day,int month,int year,String image) {
		
		String birthday="\'"+year+"/"+month+"/"+day+"\'";
		
		String query="INSERT INTO admin VALUES (null,+"+
		"\'"+username+"\',"+
		"\'"+name+"\',"+
		"\'"+surname+"\',"+
		"\'"+password+"\',"+
		birthday+
		",\'"+image+"\');";

		return query;
	}
	
	public String registerManager(String name,String surname,String username,String password,
			int day,int month,int year,String image) {
		
		
		String birthday="\'"+year+"/"+month+"/"+day+"\'";
		
		String query="INSERT INTO manager VALUES (null,+"+
		"\'"+username+"\',"+
		"\'"+name+"\',"+
		"\'"+surname+"\',"+
		"\'"+password+"\',"+
		birthday+
		",\'"+image+"\');";

		return query;
		
	}
	
	public String registerClient(String name,String surname,String username,String password,
			int day,int month,int year,String image) {
		
		
		String birthday="\'"+year+"/"+month+"/"+day+"\'";
		
		String query="INSERT INTO client VALUES (null,"+
		"\'"+username+"\',"+
		"\'"+name+"\',"+
		"\'"+surname+"\',"+
		birthday+
		",\'"+password+"\',"+
		"\'"+image+"\');";

		return query;
		
	}
	public String selectMatchFromStadium(String stadium) {
		return selectMatchFromStadium+stadium+"\';";
	}
	
	public String selectAllTeams() {
		return selectAllTeams;
	}
	
	public String selectAllMatches() {
		return selectAllMatches;
	}
	
	public String selectAllStadiums() {
		return selectAllStadiums;
	}
	
	public String selectAllPlayers() {
		return selectAllPlayers;
	}
	
	public String selectAllClient() {
		return selectAllClient;
	}
	
	public String selectAllManager() {
		return selectAllManager;
	}
	
	public String selectAllAdmin() {
		return selectAllAdmin;
	}
	
	public String selectManager(int manager) {
		return selectManager+manager;
	}
	
	public String selectAdmin(int admin) {
		return selectAdmin+admin;
	}
	
	public String selectClient(int client) {
		return selectClient+client;
	}
	
	public String updateClient(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image ) {
		
		String birthday=year+"/"+month+"/"+day;
		
		String query="UPDATE client "+
					"SET username= \'"+username+"\',name= \'"+name+"\', surname= \'"+
					surname+"\', password= \'"+password+"\', image= \'"+ image +
					"\' ,birthday = \'"+birthday+"\' "+
					"WHERE client_id ="+id+";";
		System.out.println(query);
		return query;
	}
	
	public String updateAdmin(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image ) {
		
		String birthday=year+"/"+month+"/"+day;
		
		String query="UPDATE admin "+
					"SET username= \'"+username+"\',name= \'"+name+"\', surname= \'"+
					surname+"\', password= \'"+password+"\', image= \'"+ image +
					"\' ,birthday = \'"+birthday+"\' "+
					"WHERE admin_id ="+id+";";
		System.out.println(query);
		return query;
	}
	
	public String updateManager(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image ) {
		
		String birthday=year+"/"+month+"/"+day;
		
		String query="UPDATE manager "+
					"SET username= \'"+username+"\',name= \'"+name+"\', surname= \'"+
					surname+"\', password= \'"+password+"\', image= \'"+ image +
					"\' ,birthday = \'"+birthday+"\' "+
					"WHERE manager_id ="+id+";";
		System.out.println(query);
		return query;
	}

	public String insertPlayer(String name, String surname, int country, int team, String role, int physical, int speed,
			int mental, int attack, int defense, int technique) {
//		
//		String s="INSERT INTO player (name,surname,country,team,football_role,physical,speed,mental,attack,defense,technique) "
//				+ "VALUES ("+
//				"\'"+name+"\',"+"\'"+surname+"\',"+country+","+team+","+"\'"+role+"\',"+physical+","
//				+speed+","+
//				mental+","+attack+","+defense+","+technique+");";
		
		String s1=String.format("INSERT INTO player (name,surname,country,team,football_role,physical,speed,mental,attack,defense,technique) VALUES ('%s','%s',%d,%d,'%s',%d,%d,%d,%d,%d,%d);"
			, name,surname,country,team,role,physical,speed,mental,attack,defense,technique);
		System.out.println(s1);
		
		return s1;
	}
	
	public String deleteSelectedPlayer(int id) {
		String s=String.format("DELETE FROM player" + 
				" WHERE player_ID = %d", id);
		return s;
	}
	
	public String deleteSelectedClient(int id) {
		String s=String.format("DELETE FROM client" + 
				" WHERE client_id = %d", id);
		return s;
	}
	
	public String deleteSelectedManager(int id) {
		String s=String.format("DELETE FROM manager" + 
				" WHERE manager_id = %d", id);
		return s;
	}

	public String deleteSelectedAdmin(int id) {
		String s=String.format("DELETE FROM admin" + 
				" WHERE admin_id = %d", id);
		return s;
	}

	public String updatePlayer(int id, String name, String surname, int country, int team, String football_role,
			int physical, int speed, int mental, int attack, int defense, int technique) {
		
		String s=String.format("UPDATE player "+
					"SET name ='%s', surname='%s', country = %d,team= %d,football_role='%s',physical=%d,"
					+ "speed=%d,mental=%d,attack=%d,defense=%d,technique=%d "+
					"WHERE player_ID = %d;",name,surname,country,team,football_role,physical,speed,mental,attack,defense,technique,id);
		System.out.println(s);
		return s;
	}
	
	public String selectMatchesNotYetStarted() {
		String s=selectAllMatches+
				" WHERE m.date > NOW()";
		return s;
	}

	public String updateMatch(int match_id, int homeTeam, int awayTeam, int stadium, String update) {
		String s=String.format("UPDATE footballmatch "+
				"SET home_team = %d,away_team = %d, stadium = %d"+
				", date='%s' "+
				"WHERE match_id = %d", homeTeam,awayTeam,stadium,update,match_id);
		return s;
	}

	public String insertMatch(int home_team, int away_team, int stadium, String data) {
		String s= String.format("INSERT INTO footballmatch (home_team,away_team,date,stadium) "+
				"VALUES (%d,%d,'%s',%d)", home_team,away_team,data,stadium);
		System.out.println(s);
		return s;
	}

	public String deleteSelectedMatch(int match_id) {
		String s=String.format("DELETE FROM footballmatch" + 
				" WHERE match_id = %d", match_id);
		return s;
	}

	public String insertTeam(String name, int country_id, String status, String value, int stadiumId, int year,
			int management, int stability, int support) {
		
		String s= String.format("INSERT INTO team (name,country,status,value,stadium,year,management,stability,support) "+
				"VALUES ('%s',%d,'%s',%s,%d,%d,%d,%d,%d);",
				name,country_id,status,value,stadiumId,year,management,stability,support);
		System.out.println(s);
		return s;
	}

	public String updateTeam(int team_id, String name, int country_id, String status, String valueString, int stadiumId,
			int year, int management, int stability, int support) {
		System.out.println(name+country_id+status+valueString+stadiumId+year+management+stability+support);
		String s=String.format("UPDATE team "+
				"SET name = '%s',country = %d , status= '%s', value= %s, stadium = %d, year = %d, management= %d, stability= %d, support= %d "+
				"WHERE team_id = %d;", name,country_id,status,valueString,stadiumId,year,management,stability,support,team_id);
		System.out.println(s);
		return s;
	}

	public String deleteSelectedTeam(int team_id) {
		String s=String.format("DELETE FROM team" + 
				" WHERE team_id = %d", team_id);
		return s;
	}

	public String insertStadium(String name, String city, int seats, int year) {
		String s= String.format("INSERT INTO stadium (name,city,seats,year) VALUES"+
					"('%s','%s',%d,%d)", name,city,seats,year);
		return s;
	}

	public String updateStadium(int stadiumId, String name, String city, int seats, int year) {
		String s=String.format("UPDATE stadium "+
						"SET name = '%s',city='%s',seats=%d,year=%d "+
						"WHERE stadium_id=%d", name,city,seats,year,stadiumId);
		return s;
	}
	
	public String selectTicket() {
		String s= selectTicket;
		
		return s+";";
	}

	public String selectUserTicket(int ownerId,String owner) {
		String s=String.format(selectTicket+" WHERE t.ticket_owner = '%s' AND t.owner_id = %d ;",owner,ownerId);
		System.out.println(s);
		return s;
	}

	public String insertTicket(String code, int match_id, String currentDate, String username, int id) {
		String s= String.format("INSERT INTO ticket (ticket_code,match_id,ticket_date,ticket_owner,owner_id) VALUES"+
				"('%s',%d,'%s','%s',%d)", code,match_id,currentDate,username,id);
	return s;
	}

	public String deleteSelectedStadium(int stadiumId) {
		String s=String.format("DELETE FROM stadium" + 
				" WHERE stadium_id = %d", stadiumId);
		return s;
	}
}
