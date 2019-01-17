package footballManagement;

public class TeamAttribute {
		
	private int management,stability,support;

	public TeamAttribute(int management, int stability, int support) {
		super();
		this.management = management;
		this.stability = stability;
		this.support = support;
	}

	public int getManagement() {
		return management;
	}

	public void setManagement(int management) {
		this.management = management;
	}

	public int getStability() {
		return stability;
	}

	public void setStability(int stability) {
		this.stability = stability;
	}

	public int getSupport() {
		return support;
	}

	public void setSupport(int support) {
		this.support = support;
	}

	@Override
	public String toString() {
		return "TeamAttribute [management=" + management + ", stability=" + stability + ", support=" + support + "]";
	}
	
	
}
