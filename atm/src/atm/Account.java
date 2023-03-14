package atm;

public class Account {
	private String id;
	private String account;
	private String personalNum;
	

	public Account(String id, String account, String personalNum) {
		this.id = id;
		this.account = account;
		this.personalNum = personalNum;
	}
	
	public String getId() {
		return this.id;
	}
	public String getAccount() {
		return this.account;
	}
	public String getPersonalNum() {
		return this.personalNum;
	}
}
