package atm;

import java.util.ArrayList;
import java.util.Random;

public class AccountManager {
	private static ArrayList<Account> list = new ArrayList<Account>();
	private Random rn;

	public ArrayList<Account> getAccountList() {
		return AccountManager.list;
	}

	private boolean personalNumDuplCheck(String personalNum) {
		boolean check = true;
		for (int i = 0; i < AccountManager.list.size(); i++) {
			if (AccountManager.list.get(i).getPersonalNum().equals(personalNum)) {
				check = false;
			}
		}
		return check;
	}

	public void createAccount(String id) {
		rn = new Random();
		String personalNum = "";
		while (true) {
			personalNum = String.valueOf(rn.nextInt(9000) + 1000);
			if (personalNumDuplCheck(personalNum)) {
				break;
			}
		}
		String accountNum = "5555-2222-4444-1111-" + personalNum;
		Account tmp = new Account(id, accountNum, personalNum);
		AccountManager.list.add(tmp);
	}

	public void readAccount() {
		if (AccountManager.list.size() > 0) {
			for (int i = 0; i < AccountManager.list.size(); i++) {
				System.out.printf("%d)) id: %s / Account: %s / personalNum: %s\n", i + 1,
						AccountManager.list.get(i).getId(), AccountManager.list.get(i).getAccount(),
						AccountManager.list.get(i).getPersonalNum());
			}
		} else {
			System.out.println("등록된 계좌가 없습니다.");
		}
	}

	public void deletdAccount(int idx) {
		AccountManager.list.remove(idx);
	}

}