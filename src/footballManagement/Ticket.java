package footballManagement;

public class Ticket {
	int ticketId,ownerId,matchId;
	String code,date,owner;
	
	public Ticket(int ticketId, String code,int matchId, String date, String owner,int ownerId) {
		super();
		this.ticketId = ticketId;
		this.ownerId = ownerId;
		this.code = code;
		this.date = date;
		this.owner = owner;
		this.matchId=matchId;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", ownerId=" + ownerId + ", code=" + code + ", date=" + date
				+ ", owner=" + owner + "]";
	}
	
	
}
