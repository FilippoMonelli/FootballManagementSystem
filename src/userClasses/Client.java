package userClasses;


public class Client extends Person{
	
	public Client(int id, String name, String surname, String username, String password,String birthday) {
		super(id, name, surname, username, password,birthday);
	}


	public Client(int id, String name, String surname, String username, String password, String birthday,
			String image) {
		super(id, name, surname, username, password, birthday, image);
	}



	@Override
	public String toString() {
		return "Client" + super.toString();
	}


	
	
	
}
