package atm;

public class Account {
	private String id;
	private String account;
	private String personalNum;
	private int money;

	public Account(String id, String account, String personalNum, int money) {
		this.id = id;
		this.account = account;
		this.personalNum = personalNum;
		this.money = money;
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

	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
