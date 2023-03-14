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
	
	public void readUser() {
		if (UserManager.list.size() > 0) {
			for (int i = 0; i < UserManager.list.size(); i++) {
				System.out.printf("%d) 이름: %s / id: %s / pw: %s\n", i+1, UserManager.list.get(i).getName(),UserManager.list.get(i).getId(),UserManager.list.get(i).getPw());
			}
		} else {
			System.out.println("등록된 회원이 없습니다.");
		}
	}
	// User 에 대한

	// Create
	// Read
	// Update
	// Delete

}