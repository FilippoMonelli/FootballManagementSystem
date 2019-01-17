package footballManagement;

public class Stadium {
	private int stadiumId,seats,year;
	private String name,city;
	
	public Stadium(int stadiumId,String name, String city, int seats, int year ) {
		super();
		this.stadiumId = stadiumId;
		this.seats = seats;
		this.year = year;
		this.name = name;
		this.city = city;
	}

	public int getStadiumId() {
		return stadiumId;
	}

	public void setStadiumId(int stadiumId) {
		this.stadiumId = stadiumId;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Stadium [stadiumId=" + stadiumId + ", seats=" + seats + ", year=" + year + ", name=" + name + ", city="
				+ city + "]";
	}
	
	
	
	
}
