package userClasses;

public class Manager extends Person {

	public Manager(int id, String name, String surname, String username, String password, String birthday,String image) {
		super(id, name, surname, username, password, birthday, image);
	}

	public Manager(int id, String name, String surname, String username, String password, String birthday) {
		super(id, name, surname, username, password,birthday);
	}

	@Override
	public String toString() {
		return "Manager "+super.toString();
	}
}
