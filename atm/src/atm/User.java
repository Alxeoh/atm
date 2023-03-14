package atm;

public class User {
	private int count;
	private String id;
	private String pw;
	private String name;
	
	
	public User(int count, String id, String pw, String name) {
		this.count = count;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}

	public int getCount() {
		return this.count;
	}
	public String getId() {
		return this.id;
	}
	public String getPw() {
		return this.pw;
	}
	public String getName() {
		return this.name;
	}
	public void setCount() {
		this.count = getCount()+1;
	}
}
