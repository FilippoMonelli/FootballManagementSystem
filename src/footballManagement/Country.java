package footballManagement;

public class Country {

	private int country_id;
	private String country_code,country_name;
	
	
	
	public Country(int country_id, String country_code, String country_name) {
		super();
		this.country_id = country_id;
		this.country_code = country_code;
		this.country_name = country_name;
	}
	
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	@Override
	public String toString() {
		return "Country [country_id=" + country_id + ", country_code=" + country_code + ", country_name=" + country_name
				+ "]";
	}
	
	
}
