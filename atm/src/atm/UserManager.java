package atm;

import java.util.ArrayList;

public class UserManager {
	private static ArrayList<User> list = new ArrayList<User>();

	public ArrayList<User> getUserList() {
		return UserManager.list;
	}

	public void createUserList(User user) {
		UserManager.list.add(user);
	}

	public void deleteUser(int idx) {
		UserManager.list.remove(idx);
	}
}