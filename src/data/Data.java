package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import footballManagement.Country;
import userClasses.Person;
import utils.DBManager;


public class Data {
	
	DBManager db;
	DataManager model;
	 
	public Data() throws SQLException{

		try {
			db = new DBManager(DBManager.JDBCDriverMySQL, DBManager.JDBCURLMySQL);
//			System.out.println(db.toString());
			db.executeQuery("SELECT * FROM admin LIMIT 1");
		}catch (SQLException e) {
			System.out.println("Error during connection database, RECREATE A DB");
			
			/*delete the tables*/
	
//			db.executeUpdate("CREATE DATABASE testFSM;");
//			
//			db.executeQuery("USE testFSM;");
			
			db.executeUpdate("DROP TABLE IF EXISTS apps_countries;");
			db.executeUpdate("DROP TABLE IF EXISTS ticket;");
			db.executeUpdate("DROP TABLE IF EXISTS team;");
			db.executeUpdate("DROP TABLE IF EXISTS stadium;");
			db.executeUpdate("DROP TABLE IF EXISTS player;");
			db.executeUpdate("DROP TABLE IF EXISTS footballMatch;");
			db.executeUpdate("DROP TABLE IF EXISTS client;");
			db.executeUpdate("DROP TABLE IF EXISTS admin;");
			db.executeUpdate("DROP TABLE IF EXISTS manager;");
			
			/*create the tables*/
			
			db.executeUpdate("CREATE TABLE apps_countries (" + 
					"country_id INTEGER PRIMARY KEY AUTO_INCREMENT ," + 
					"country_code varchar(2) NOT NULL default ''," + 
					"country_name varchar(100) NOT NULL default ''" + 
					");");
			
			db.executeUpdate("CREATE TABLE ticket( " + 
					"ticket_id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"ticket_code VARCHAR(100)," +
					"match_id INTEGER REFERENCES footballmatch,"+
					"ticket_date DATE," + 
					"ticket_owner VARCHAR(40)," + 
					"owner_id INT" + 
					");");
			
			db.executeUpdate("CREATE TABLE stadium(" + 
					"stadium_id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"name VARCHAR(40) NOT NULL DEFAULT ''," + 
					"city VARCHAR(40) NOT NULL DEFAULT '',"+
					"seats INTEGER NOT NULL," + 
					"year INTEGER NOT NULL    " + 
					");"
			);
			
			db.executeUpdate("CREATE TABLE footballMatch(" + 
					"match_id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"home_team INTEGER REFERENCES team," + 
					"away_team INTEGER REFERENCES team," + 
					"date DATE," + 
					"home_scores INTEGER DEFAULT 0," + 
					"away_scores INTEGER DEFAULT 0," + 
					"stadium INTEGER REFERENCES stadium" + 
					");" 
			);
			
			db.executeUpdate("CREATE TABLE team(" + 
					"team_id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"name VARCHAR(40)," + 
					"country INTEGER REFERENCES apps_countries," + 
					"status VARCHAR(40) DEFAULT 'professional'," + 
					"value FLOAT," + 
					"stadium INTEGER REFERENCES stadium," + 
					"year INTEGER," + 
					"management INTEGER DEFAULT 0," + 
					"stability INTEGER  DEFAULT 0," + 
					"support INTEGER  DEFAULT 0" + 
					");"
				);
			
			db.executeUpdate("CREATE TABLE player(" + 
					"player_ID INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"name VARCHAR(40)," + 
					"surname VARCHAR(40)," + 
					"country INTEGER REFERENCES apps_countries," + 
					"team INTEGER REFERENCES team," + 
					"football_role VARCHAR(40) DEFAULT 'Goalkeeper'," + 
					"physical INTEGER DEFAULT 0," + 
					"speed    INTEGER DEFAULT 0," + 
					"mental   INTEGER  DEFAULT 0," + 
					"attack   INTEGER  DEFAULT 0," + 
					"defense  INTEGER  DEFAULT 0," + 
					"technique INTEGER  DEFAULT 0" + 
					");"
				);
			
			db.executeUpdate("CREATE TABLE admin(" + 
					"admin_id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"username VARCHAR(40) UNIQUE," + 
					"name VARCHAR(40)," + 
					"surname VARCHAR(40)," + 
					"password TEXT," + 
					"birthday DATE," +
					"image TEXT"+
					");" 
			);
			
			db.executeUpdate("CREATE TABLE client(" + 
					"client_id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"username VARCHAR(40) UNIQUE," + 
					"name VARCHAR(40)," + 
					"surname VARCHAR(40)," + 
					"birthday DATE," + 
					"password VARCHAR(40)," +
					"image VARCHAR(40)"+
					");" 
			);
			
			db.executeUpdate("CREATE TABLE manager(" + 
					"manager_id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"username VARCHAR(40) UNIQUE," + 
					"name VARCHAR(40)," + 
					"surname VARCHAR(40)," + 
					"password VARCHAR(40)," + 
					"birthday DATE," +
					"image TEXT"+
					");"
			);
			
			
			/*examples for easier debug*/
			
//			db.executeUpdate("INSERT INTO admin VALUES (null,'admin','Filippo','Monelli','admin','1997/03/29','monkey');");
//			
//			db.executeUpdate("INSERT INTO client VALUES (null,'client','Claudia','Binelli', '1997/09/17','client','monkey');");
//			db.executeUpdate("INSERT INTO client VALUES (null,'client2','Marinella','Molinari', '1957/09/30','client2','monkey');");
//			
//			db.executeUpdate("INSERT INTO manager VALUES (null,'manager','Davide','Monelli','manager','1983/08/21','dog');");
			
			db.executeUpdate("INSERT INTO stadium VALUES (null,'San Siro Stadium','Milan',80018,1926);");
			db.executeUpdate("INSERT INTO stadium VALUES (null,'Olympic Stadium','Rome',70634,1953);");
			db.executeUpdate("INSERT INTO stadium VALUES (null,'San Paolo Stadium','Naples',60240,1959);");
			db.executeUpdate("INSERT INTO stadium VALUES (null,'Allianz Stadium','Turin',41507,2011);");
			db.executeUpdate("INSERT INTO stadium VALUES (null,'Luigi Ferraris Stadium','Genoa',36599,1911);");
			db.executeUpdate("INSERT INTO stadium VALUES (null,'Artemio Franchi Stadium','Florence',46389,1931);");
			db.executeUpdate("INSERT INTO stadium VALUES (null,'Mapei Stadium','Reggio Emilia',23717,1995);");
			
//			db.executeUpdate("INSERT INTO team (name,country,value,stadium,year) VALUES ('Inter',106,500,1,1908);");
//			db.executeUpdate("INSERT INTO team (name,country,value,stadium,year) VALUES ('Milan',106,500,1,1898);");
			
			
//			db.executeUpdate("INSERT INTO footballMatch (home_team,away_team,date,stadium) VALUES (1,2,'2018/10/21',1);");
//			db.executeUpdate("INSERT INTO footballMatch (home_team,away_team,date,stadium) VALUES (1,2,'2018/10/21',7);");
//			db.executeUpdate("INSERT INTO footballMatch (home_team,away_team,date,stadium) VALUES (1,2,'2018/10/21',5);");
//			db.executeUpdate("INSERT INTO footballMatch (home_team,away_team,date,stadium) VALUES (1,2,'2018/10/21',4);");
//			db.executeUpdate("INSERT INTO footballMatch (home_team,away_team,date,stadium) VALUES (1,2,'2018/10/21',1);");
//			db.executeUpdate("INSERT INTO footballMatch (home_team,away_team,date,stadium) VALUES (2,1,'2025/10/21',1);");
//			db.executeUpdate("INSERT INTO footballMatch (home_team,away_team,date,stadium) VALUES (1,2,'2025/10/25',1);");
			
			
//			db.executeUpdate("INSERT INTO player (name,surname,country,team,football_role) VALUES ('Matteo','Politano',106,1,'Forward');");
		    
			/*insert into db all countries*/
			BufferedReader b = null;
			try {
				b=new BufferedReader(new FileReader("countries.txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
		    String s = null;
			    
		    while(true) {
		    	try {
		    		s=b.readLine();
					if(s==null)
						break;
					else {
						//System.out.println(s);
						db.executeUpdate(s);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    	
		    }
			
			
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		model=new DataManager(db);
		
		/*stampa di test*/
	}
	
	public void closeConnection() throws SQLException {
		db.close();
	}
	
	public Person selectPerson(Person p) {
		Person person=null;
		try {
			person=model.selectPerson(p);
//			System.out.println(person.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	public Person loginPhase(String username,String password) {
		return model.selectLogin(username, password);
	}
	
	public DBManager getDb() {
		return db;
	}

	public void setDb(DBManager db) {
		this.db = db;
	}

	public DataManager getModel() {
		return model;
	}

	public void setModel(DataManager model) {
		this.model = model;
	}


	public ArrayList<Country> allCountries() throws SQLException{
		return model.selectAllCountry();
	}
	
	public void insertPlayer(String name,String surname,int country,int team,String role,
			int physical,int speed,int mental,int attack,int defense,int technique) throws SQLException {
		model.insertPlayer(name,surname,country,team,role,physical,speed,mental,attack,defense,technique);
	}
	
	public void registerClient(String name,String surname,String username,String password,
			int day,int month,int year,String image)throws SQLException {
		
		model.registerClient(name, surname, username, password, day, month, year, image);
		
	}
	
	public void registerManager(String name,String surname,String username,String password,
			int day,int month,int year,String image)throws SQLException {
		
		model.registerManager(name, surname, username, password, day, month, year, image);
		
	}
	
	public void registerAdmin(String name,String surname,String username,String password,
			int day,int month,int year,String image)throws SQLException {
		
		model.registerAdmin(name, surname, username, password, day, month, year, image);
		
	}
	
	public void updateClient(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
		model.updateClient(id, name, surname, username, password, day, month, year, image);
	}

	
	public void updateAdmin(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
		model.updateAdmin(id, name, surname, username, password, day, month, year, image);
	}
	
	public void updateManager(int id,String name,String surname,String username,String password,
			int day,int month,int year,String image) throws SQLException {
		model.updateManager(id, name, surname, username, password, day, month, year, image);
	}
}
