package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import exception.DeleteException;
import exception.UserCorrectPasswordNotCorrectException;
import exception.UserNotFoundException;
import footballManagement.Country;
import footballManagement.Match;
import footballManagement.Player;
import footballManagement.PlayerAttribute;
import footballManagement.Stadium;
import footballManagement.Team;
import footballManagement.TeamAttribute;
import footballManagement.Ticket;
import utils.DBManager;
import userClasses.Admin;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;



public class DataManager {

	private DBManager db;
	private Queries queries;
	private ResultSet rs;
	
	private ArrayList<Admin> adminList;
	private ArrayList<Client> clientList;
	private ArrayList<Manager> managerList;
	private ArrayList<Team> teamList;
	private ArrayList<Player> playerList;
	private ArrayList<Match> matchList;
	private ArrayList<Stadium> stadiumList;
	private ArrayList<Country> countryList;
	private ArrayList<Match> matchNotStartedList;
	private ArrayList<Ticket> ticketList;
	private int selectedIndexClient;
	private int selectedIndexAdmin;
	private ArrayList<Ticket> ticketListUser;
	
	
	public DataManager(DBManager db) {
		this.db = db;
		adminList= new ArrayList<Admin>();
		clientList= new ArrayList<Client>();
		managerList=new ArrayList<Manager>();
		playerList=new ArrayList<Player>();
		teamList=new ArrayList<Team>();
		matchList=new ArrayList<Match>();
		stadiumList= new ArrayList<Stadium>();
		countryList=new ArrayList<Country>();
		queries=new Queries();	
		matchNotStartedList = new ArrayList<Match>();
		
		ticketListUser=new ArrayList<Ticket>();
		
		
		adminList();
		clientList();
		managerList();
		playerList();
		teamList();
		matchList();
		stadiumList();
		coutryList();
//		ticketList();
//		System.out.println("Dopo prima creazione "+ticketList.toString());
//		
		/*initialization of the index*/
		firstAdmin();
		firstClient();
		
	}
	
	private void coutryList() {
		try {
			rs=db.executeQuery(queries.selectAllCountries());
			while(rs.next()){
				Country c= countryResultSet(rs);
				this.countryList.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void adminList(){
		try {
			rs=db.executeQuery(queries.selectAllAdmin());
			while(rs.next()) {
				
				Admin a = adminResultSet(rs);
				this.adminList.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void clientList() {
		try {
			rs=db.executeQuery(queries.selectAllClient());
		
			while(rs.next()) {
				Client c= clientResultSet(rs);
				this.clientList.add(c);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void managerList() {
		try {
			rs=db.executeQuery(queries.selectAllManager());
			while(rs.next()) {
				Manager m = managerResultSet(rs);
				this.managerList.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playerList() {
		try {
			rs=db.executeQuery(queries.selectAllPlayers());
			while(rs.next()) {
				
				Player p = playerResultSet(rs);

				this.playerList.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void matchList() {
		try {
			rs=db.executeQuery(queries.selectAllMatches());
			while(rs.next()) {
				Match m=matchResultSet(rs);
				this.matchList.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void teamList() {
		try {
			rs=db.executeQuery(queries.selectAllTeams());
			while(rs.next()) {
				
				
				Team t= teamResultSet(rs);
				this.teamList.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void stadiumList() {
		try {
			rs=db.executeQuery(queries.selectAllStadiums());

			while(rs.next()) {
				Stadium s=stadiumResultSet(rs);
				this.stadiumList.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Person selectLogin(String username,String password){
//		System.out.println("dati inseriti:"+ username + " "+ password);
		try {
			boolean found = false;
			boolean isPresent = false;
			
			for (Client user : clientList) {
				if(user.getUsername().equalsIgnoreCase(username)) {
					isPresent=true;
				}
				if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
					found = true;
					return (Client) user;
				}
			}

			for (Admin user : adminList) {
				if(user.getUsername().equalsIgnoreCase(username)) {
//					System.out.println("UTENTE TROVATO");
					isPresent=true;
				}
				if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
//					System.out.println("UTENTE E PASSWORD CORRETTI");
					found = true;
					return (Admin) user;
				}
			}

			for (Manager user : managerList) {
				if(user.getUsername().equalsIgnoreCase(username)) {
					isPresent=true;
				}
				if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
					found = true;
					return (Manager) user;
				}
			}

			if(!isPresent)
				throw new UserNotFoundException();
			else {
				if (!found)
					throw new UserCorrectPasswordNotCorrectException();
			}
		} catch (UserNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "User is not present in Database.\nRegister with the approriate button"
					,"Not register error", JOptionPane.WARNING_MESSAGE);
			//ex.printStackTrace();
		}catch (UserCorrectPasswordNotCorrectException e) {
			JOptionPane.showMessageDialog(null, "User or password isn't correct."
					,"Login error", JOptionPane.WARNING_MESSAGE);
		}
		return null;	}
	

	public ArrayList<Match> matchesForStadium(String stadium){
		ArrayList<Match> matches = new ArrayList<Match>();
		
		try {
			rs=db.executeQuery(queries.selectMatchFromStadium(stadium)); 
		
			while(rs.next()) {
				Match m=matchResultSet(rs);
				matches.add(m);
			}
			
//			System.out.println("dentro metodo"+matches.toString());
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return matches;
		
	}
	
	public ArrayList<Country> selectAllCountry() throws SQLException{
		
		rs=db.executeQuery(queries.selectAllCountries());
		
		while(rs.next()) {
			Country c=countryResultSet(rs);
			countryList.add(c);
		}
		
		return countryList;
	}
	
	public Person selectPerson(Person p) throws SQLException {
		Person person=null;
		if (p instanceof Client) {
			rs=db.executeQuery(queries.selectClient(p.getId()));
			while(rs.next())	
				person=clientResultSet(rs);
		}else if(p instanceof Manager){
			rs=db.executeQuery(queries.selectManager(p.getId()));
			while(rs.next())
				person=managerResultSet(rs);
		}else {
			rs=db.executeQuery(queries.selectAdmin(p.getId()));
			while(rs.next())
				person=adminResultSet(rs);
		}
		
		return person;
	}
	
	public Ticket ticketResultSet(ResultSet rs) {
		Ticket t=null;
		
		try {
			t= new Ticket(rs.getInt("ticket_id"),
					rs.getString("ticket_code"),
					rs.getInt("match_id"),
					rs.getString("ticket_date"),
					rs.getString("ticket_owner"),
					rs.getInt("owner_id"));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return t;
	}
	
	public Team teamResultSet(ResultSet rs) {
		Team t=null;
		try {
			TeamAttribute ta=new TeamAttribute(
					rs.getInt("management"),
					rs.getInt("stability"),
					rs.getInt("support")
					);
			t= new Team(
					rs.getInt("team_id"),
					rs.getString("name"),
					rs.getString("country_name"),
					rs.getString("status"),
					rs.getFloat("value"),
					rs.getString("Stadium name"),
					rs.getInt("year"),
					ta);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	
	public Match matchResultSet(ResultSet rs) {
		Match m=null;
		try {
			m=new Match(
					rs.getInt("match_id"),
					rs.getString("home team"),
					rs.getString("away team"),
					rs.getString("stadium name"),
					rs.getString("date")
					);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public Country countryResultSet(ResultSet rs) {
		Country c = null;
		try {
			c=new Country(
					rs.getInt("country_id"),
					rs.getString("country_code"),
					rs.getString("country_name")
					);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	
	
	public Stadium stadiumResultSet(ResultSet rs) {
		Stadium s=null;
		try {
			s= new Stadium(
					rs.getInt("stadium_id"),
					rs.getString("name"),
					rs.getString("city"),
					rs.getInt("seats"),
					rs.getInt("year")
					);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public Admin adminResultSet(ResultSet rs) {
		Admin a=null;
		try {
	
			a = new Admin(
				rs.getInt("admin_id"),
				rs.getString("name"),
				rs.getString("surname"),
				rs.getString("username"),
				rs.getString("password"),
				rs.getString("birthday"),
				rs.getString("image"));
		return a;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public Client clientResultSet(ResultSet rs) {
		Client c=null;
		try {
			c= new Client(
				rs.getInt("client_id"),
				rs.getString("name"),
				rs.getString("surname"),
				rs.getString("username"),
				rs.getString("password"),
				rs.getString("birthday"),
				rs.getString("image"));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public Manager managerResultSet(ResultSet rs) {
		Manager m=null;
		try {
			m = new Manager(
					rs.getInt("manager_id"),
					rs.getString("name"),
					rs.getString("surname"),
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("birthday"),
					rs.getString("image"));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public Player playerResultSet(ResultSet rs) {
		Player p=null;
		try {
			PlayerAttribute pa=new PlayerAttribute(
					rs.getInt("physical"),
					rs.getInt("speed"),
					rs.getInt("mental"),
					rs.getInt("attack"),
					rs.getInt("defense"),
					rs.getInt("technique")
					);
			 p= new Player(
			rs.getInt("Player_ID"),
			rs.getString("name"),
			rs.getString("surname"),
			rs.getString("country_name"),
			rs.getString("Team name"),
			rs.getString("football_role"),
			pa);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public void registerClient(String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
			
		db.executeUpdate(queries.registerClient(name, surname, username, password, day, month, year, image));
		

	}
	
	public void registerManager(String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
		
		db.executeUpdate(queries.registerManager(name, surname, username, password, day, month, year, image));
		
	}
	
	public void registerAdmin(String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {

		db.executeUpdate(queries.registerAdmin(name, surname, username, password, day, month, year, image));
	}
	
	public void updateClient(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
		
		db.executeUpdate(queries.updateClient(id, name, surname, username, password, day, month, year, image));
	}
	
	public void updateManager(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
		
		db.executeUpdate(queries.updateManager(id, name, surname, username, password, day, month, year, image));
	}
	
	public void updateAdmin(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
		
		db.executeUpdate(queries.updateAdmin(id, name, surname, username, password, day, month, year, image));
	}
	
	public void updatePlayer(int id, String name, String surname, int country, int team, String football_role,
			int physical, int speed, int mental, int attack, int defense, int technique) throws SQLException {
		db.executeUpdate(queries.updatePlayer(id,name,surname,country,team,football_role,physical,speed,mental,attack,defense,technique));
	}
	
	public Person getSelectedItemAdmin() {
		return adminList.get(selectedIndexAdmin);
	}
	
	public int getSelectedIndexAdmin() {
		return selectedIndexAdmin;
	}
	
	public void setSelectedIndexAdmin(int selectedIndex) {
		this.selectedIndexAdmin = selectedIndex;
	}
	
	public void firstAdmin() {
		selectedIndexAdmin = 0;
	}
	
	public void lastAdmin() {
		selectedIndexAdmin = adminList.size() - 1;
	}

	public void nextAdmin() {
		selectedIndexAdmin = Math.min(selectedIndexAdmin + 1, adminList.size() - 1);
	}
	
	public void previousAdmin() {
		selectedIndexAdmin = Math.max(selectedIndexAdmin - 1, 0);
	}

	/*methods for manage ClientList*/
	
	public Person getSelectedItemClient() {
		return clientList.get(selectedIndexClient);
	}
	
	public int getSelectedIndexClient() {
		return selectedIndexClient;
	}
	
	public void setSelectedIndexClient(int selectedIndex) {
		this.selectedIndexClient = selectedIndex;
	}
	
	public void firstClient() {
		selectedIndexAdmin = 0;
	}
	
	public void lastClient() {
		selectedIndexClient = clientList.size() - 1;
	}

	public void nextClient() {
		selectedIndexClient = Math.min(selectedIndexClient + 1, clientList.size() - 1);
	}
	
	public void previousClient() {
		selectedIndexClient = Math.max(selectedIndexClient - 1, 0);
	}

	
	
	public ArrayList<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(ArrayList<Admin> adminList) {
		this.adminList = adminList;
	}

	public ArrayList<Client> getClientList() {
		return clientList;
	}

	public void setClientList(ArrayList<Client> clientList) {
		this.clientList = clientList;
	}

	public ArrayList<Manager> getManagerList() {
		return managerList;
	}

	public void setManagerList(ArrayList<Manager> managerList) {
		this.managerList = managerList;
	}
	
	public DBManager getDb() {
		return db;
	}

	public void setDb(DBManager db) {
		this.db = db;
	}
	
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public ArrayList<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(ArrayList<Team> teamList) {
		this.teamList = teamList;
	}

	public ArrayList<Match> getMatchList() {
		return matchList;
	}

	public void setMatchList(ArrayList<Match> matchList) {
		this.matchList = matchList;
	}

	public ArrayList<Stadium> getStadiumList() {
		return stadiumList;
	}

	public void setStadiumList(ArrayList<Stadium> stadiumList) {
		this.stadiumList = stadiumList;
	}

	public ArrayList<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}

	public ArrayList<Match> getMatchNotStartedList() {
		return matchNotStartedList;
	}

	public void setMatchNotStartedList(ArrayList<Match> matchNotStartedList) {
		this.matchNotStartedList = matchNotStartedList;
	}

	public void insertPlayer(String name, String surname, int country, int team, String role, int physical, int speed,
			int mental, int attack, int defense, int technique) throws SQLException {
		db.executeUpdate(queries.insertPlayer(name,surname,country,team,role,physical,speed,mental,attack,defense,technique));
	}

	public void removePlayer(int id) throws SQLException, DeleteException  {
		int i=0;
		i=db.executeUpdate(queries.deleteSelectedPlayer(id));
		if(i!=1) {
			throw new DeleteException();
		}
	}

	public void removeClient(int id) throws SQLException, DeleteException {
		int i=0;
		i=db.executeUpdate(queries.deleteSelectedClient(id));
		if(i!=1) {
			throw new DeleteException();
		}
	}

	public void removeManager(int id) throws SQLException, DeleteException {
		int i=0;
		i=db.executeUpdate(queries.deleteSelectedManager(id));
		if(i!=1) {
			throw new DeleteException();
		}
	}

	public void removeAdmin(int id) throws SQLException, DeleteException {
		int i=0;
		i=db.executeUpdate(queries.deleteSelectedAdmin(id));
		if(i!=1) {
			throw new DeleteException();
		}
	}

	public void refreshAdminList() {
		ArrayList<Admin> newAdmins = new ArrayList<Admin>();
		
		try {
			rs=db.executeQuery(queries.selectAllAdmin());
			while(rs.next()) {
				
				Admin p = adminResultSet(rs);

				newAdmins.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.adminList=newAdmins;
	}

	public void refreshManagerList() {
		ArrayList<Manager> newManagers = new ArrayList<Manager>();
		
		try {
			rs=db.executeQuery(queries.selectAllManager());
			while(rs.next()) {
				
				Manager p = managerResultSet(rs);

				newManagers.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.managerList=newManagers;
	}

	public void refreshClientList() {
		ArrayList<Client> newClients = new ArrayList<Client>();
		
		try {
			rs=db.executeQuery(queries.selectAllClient());
			while(rs.next()) {
				
				Client p = clientResultSet(rs);

				newClients.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.clientList=newClients;
	}
	
	public void refreshPlayerList() {
		ArrayList<Player> newPlayers = new ArrayList<Player>();
		
		try {
			rs=db.executeQuery(queries.selectAllPlayers());
			while(rs.next()) {
				
				Player p = playerResultSet(rs);

				newPlayers.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.playerList=newPlayers;
	}
	
	public void refreshMatch() {
		ArrayList<Match> newMatch = new ArrayList<Match>();
		
		try {
			rs=db.executeQuery(queries.selectAllMatches());
			while(rs.next()) {
				
				Match m = matchResultSet(rs);

				newMatch.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.matchList=newMatch;
	}
	
	public void selectMatchesNotStarted() throws SQLException {
		ArrayList<Match> matches=new ArrayList<Match>();
		rs=db.executeQuery(queries.selectMatchesNotYetStarted());
		while(rs.next()) {
			Match m=matchResultSet(rs);
			matches.add(m);
		}
		this.matchNotStartedList=matches;
	}

	public void updateMatch(int match_id, int homeTeam, int awayTeam, int stadium, String update) throws SQLException {
		db.executeUpdate(queries.updateMatch(match_id,homeTeam,awayTeam,stadium,update));
	}

	public void insertMatch(int home_team, int away_team, int stadium, String data) throws SQLException {
		db.executeUpdate(queries.insertMatch(home_team,away_team,stadium,data));
	}

	public void removeMatch(int match_id) throws SQLException {
		db.executeUpdate(queries.deleteSelectedMatch(match_id));
	}

	public void insertTeam(String name, int country_id, String status, String value, int stadiumId, int year,
			int management, int stability, int support) throws SQLException {
		db.executeUpdate(queries.insertTeam(name,country_id,status,value,stadiumId,year,management,stability,support));
	}

	public void updateTeam(int team_id,String name, int country_id, String status, String valueString, int stadiumId, int year,
			int management, int stability, int support) throws SQLException {
		db.executeUpdate(queries.updateTeam(team_id,name,country_id,status,valueString,stadiumId,year,management,stability,support));
		
	}

	public void refreshTeam() {
		ArrayList<Team> newTeam = new ArrayList<Team>();
		
		try {
			rs=db.executeQuery(queries.selectAllTeams());
			while(rs.next()) {
				
				Team m = teamResultSet(rs);

				newTeam.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.teamList=newTeam;
	}

	public void removeTeam(int team_id) throws SQLException {
		db.executeUpdate(queries.deleteSelectedTeam(team_id));
	}

	public void refreshStadium() {
		ArrayList<Stadium> newStadiums = new ArrayList<Stadium>();
		
		try {
			rs=db.executeQuery(queries.selectAllStadiums());
			while(rs.next()) {
				
				Stadium m = stadiumResultSet(rs);

				newStadiums.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stadiumList=newStadiums;
	}

	public void insertStadium(String name, String city, int seats, int year) throws SQLException {
		db.executeUpdate(queries.insertStadium(name,city,seats,year));
	}

	public void updateStadium(int stadiumId, String name, String city, int seats, int year) throws SQLException {
		db.executeUpdate(queries.updateStadium(stadiumId,name,city,seats,year));
	}

	
	public ArrayList<Ticket> getAllTicketList() {
//		System.out.println(ticketList.toString());
		return this.ticketList;
	}

	public ArrayList<Ticket> getUserTicketList(Person user) {
//		ticketListUser(user);
		return this.ticketListUser;
	}

	public void insertTicket(String code, int match_id, String currentDate, String username, int id) throws SQLException {
		db.executeUpdate(queries.insertTicket(code,match_id,currentDate,username,id));
	}

	public void refreshTicket() {
		ArrayList<Ticket> newTickets = new ArrayList<Ticket>();
		
		try {
			rs=db.executeQuery(queries.selectTicket());
			while(rs.next()) {
				
				Ticket m = ticketResultSet(rs);

				newTickets.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("REFRESH: "+ newTickets.toString());
		this.ticketList=newTickets;
//		System.out.println("TicketList in refresh: "+ ticketList.toString());
		
	}

	public void refreshUserTicket(Person person) {
		ArrayList<Ticket> newTickets = new ArrayList<Ticket>();
		
		try {
			rs=db.executeQuery(queries.selectUserTicket(person.getId(), person.getUsername()));
			while(rs.next()) {
				
				Ticket m = ticketResultSet(rs);

				newTickets.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("REFRESH: "+ newTickets.toString());
		this.ticketListUser=newTickets;
//		System.out.println("TicketList in refresh: "+ ticketListUser.toString());
	}

	public void removeStadium(int stadiumId) throws SQLException {
		db.executeUpdate(queries.deleteSelectedStadium(stadiumId));
	}

	

	
}