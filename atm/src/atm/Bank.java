package atm;

import java.util.Scanner;

public class Bank {

	private String name;
	private UserManager um;
	private AccountManager am;
	private int log;
	private Scanner sc;

	// Banking 관련 메소드

	public Bank(String name) {
		this.name = name;
		this.um = new UserManager();
		this.am = new AccountManager();
		this.log = -1;
		this.sc = new Scanner(System.in);
	}

	private int selectMenu() {
		System.out.println("입력 : ");
		int num = sc.nextInt();
		return num;
	}

	private String inputString() {
		String str = sc.next();
		return str;
	}

	private boolean checkDuplId(String id) {
		boolean check = false;
		for (int i = 0; i < um.getUserList().size(); i++) {
			if (id.equals(um.getUserList().get(i).getId())) {
				check = true;
			}
		}
		return check;
	}

	private boolean checkPw(int idx, String pw) {
		boolean check = false;
		if (um.getUserList().get(idx).getPw().equals(pw)) {
			check = true;
		}
		return check;
	}

	private int checkIdx(String id) {
		int idx = -1;
		for (int i = 0; i < um.getUserList().size(); i++) {
			if (id.equals(um.getUserList().get(i).getId())) {
				idx = i;
			}
		}
		return idx;
	}

	private int AccountIndex(String id, String personalNumber) {
		int idx = -1;
		for (int i = 0; i < am.getAccountList().size(); i++) {
			if (am.getAccountList().get(i).getId().equals(id)) {
				if (am.getAccountList().get(i).getPersonalNum().equals(personalNumber)) {
					idx = i;
				}
			}
		}
		return idx;
	}

	private boolean checkPersonal(String id, String personalNumber) {
		boolean check = false;
		for (int i = 0; i < am.getAccountList().size(); i++) {
			if (am.getAccountList().get(i).getId().equals(id)) {
				if (am.getAccountList().get(i).getPersonalNum().equals(personalNumber)) {
					check = true;
				}
			}
		}
		return check;
	}

	private void menu(int select) {
		if (select == 1) {
			System.out.print("id : ");
			String id = inputString();
			if (checkDuplId(id)) {
				System.out.println("중복된 id가 있습니다.");
			} else {
				System.out.print("이름 : ");
				String name = inputString();
				System.out.print("pw : ");
				String pw = inputString();
				int count = 1;
				User tmp = new User(count, id, pw, name);
				um.createUserList(tmp);
				System.out.println("등록되었습니다.");
			}
		} else if (select == 2) {
			System.out.println("id : ");
			String id = inputString();
			if (checkDuplId(id)) {
				int idx = checkIdx(id);
				System.out.print("pw : ");
				String pw = inputString();
				if (checkPw(idx, pw)) {
					um.deleteUser(idx);
					System.out.println("탈퇴되었습니다.");
				} else {
					System.out.println("패스워드를 확인하세요. ");
				}
			} else {
				System.out.println("등록되지않은 아이디 입니다.");
			}
		} else if (select == 3) {
			if (log != -1) {
				if (um.getUserList().get(log).getCount() < 3) {
					am.createAccount(um.getUserList().get(log).getId());
					System.out.println("계좌생성완료");
					System.out.printf("현재 %s님의 계좌는 %d개 입니다.\n(최대 3개까지 발급가능)", um.getUserList().get(log).getId(),
							um.getUserList().get(log).getCount());
				} else {
					System.out.printf("현재 %s님의 계좌는 %d개 입니다.\n(최대 3개까지 발급가능)", um.getUserList().get(log).getId(),
							um.getUserList().get(log).getCount());
				}
			} else {
				System.out.println("로그인 후 이용가능.");
			}
		} else if (select == 4) {
			if (log != -1) {
				System.out.print("삭제할 계좌의 고유번호를 입력하세요 : ");
				String personalNum = inputString();
				if (checkPersonal(um.getUserList().get(log).getId(), personalNum)) {
					int deletdNum = AccountIndex(um.getUserList().get(log).getId(), personalNum);
					am.deletdAccount(deletdNum);
				} else {
					System.out.println("계좌의 고유번호를 다시 확인하세요. ");
				}
			} else {
				System.out.println("로그인 후 이용가능.");
			}
		} else if (select == 5) {
			if (log == -1) {
				System.out.print("id : ");
				String id = inputString();
				if (checkDuplId(id)) {
					System.out.println("pw : ");
					String pw = inputString();
					int idx = checkIdx(id);
					if (checkPw(idx, pw)) {
						System.out.printf("[로그인 성공]\n%s님 환영합니다.", id);
						this.log = idx;
					} else {
						System.out.println("패스워드를 확인하세요. ");
					}
				} else {
					System.out.println("등록되지않은 아이디 입니다.");
				}
			} else {
				System.out.println("이미 로그인 상태입니다.");
			}
		} else if (select == 6) {
			if (log != -1) {
				System.out.println("로그아웃 되었습니다. ");
			} else {
				System.out.println("이미 로그아웃 상태입니다. ");
			}
		} else {
			System.out.println("없는 메뉴 입니다. ");
		}
	}

	void run() {
		System.out.println("------" + this.name + "------");
		System.out.println("1. 회원가입");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 계좌신청");
		System.out.println("4. 계좌철회");
		System.out.println("5. 로그인");
		System.out.println("6. 로그아웃");
		int select = selectMenu();
		menu(select);
	}

}