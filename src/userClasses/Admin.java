package userClasses;

public class Admin extends Person {

	public Admin(int id, String name, String surname, String username, String password, String birthday) {
		super(id, name, surname, username, password,birthday);
	}

	
	
	public Admin(int id, String name, String surname, String username, String password, String birthday, String image) {
		super(id, name, surname, username, password, birthday, image);
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "Admin " +super.toString();
	}

	
}
