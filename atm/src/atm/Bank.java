package atm;

import java.util.Scanner;

public class Bank {

	private String name;
	private UserManager um;
	private AccountManager am;
	private int log;
	private Scanner sc;
	private boolean exitRun;
	private boolean exitAdmin;

	public Bank(String name) {
		this.name = name;
		this.um = new UserManager();
		this.am = new AccountManager();
		this.log = -1;
		this.sc = new Scanner(System.in);
		this.exitRun = true;
		this.exitAdmin = true;
	}

	private int selectMenu() {
		System.out.println("입력 : ");
		int num = this.sc.nextInt();
		return num;
	}

	private String inputString() {
		String str = this.sc.next();
		return str;
	}

	private boolean checkDuplId(String id) {
		boolean check = false;
		for (int i = 0; i < this.um.getUserList().size(); i++) {
			if (id.equals(this.um.getUserList().get(i).getId())) {
				check = true;
			}
		}
		return check;
	}

	private boolean checkPw(int idx, String pw) {
		boolean check = false;
		if (this.um.getUserList().get(idx).getPw().equals(pw)) {
			check = true;
		}
		return check;
	}

	private int checkIdx(String id) {
		int idx = -1;
		for (int i = 0; i < this.um.getUserList().size(); i++) {
			if (id.equals(this.um.getUserList().get(i).getId())) {
				idx = i;
			}
		}
		return idx;
	}

	private int AccountIndex(String id, String personalNumber) {
		int idx = -1;
		for (int i = 0; i < this.am.getAccountList().size(); i++) {
			if (this.am.getAccountList().get(i).getId().equals(id)) {
				if (this.am.getAccountList().get(i).getPersonalNum().equals(personalNumber)) {
					idx = i;
				}
			}
		}
		return idx;
	}

	private boolean checkPersonal(String id, String personalNumber) {
		boolean check = false;
		for (int i = 0; i < am.getAccountList().size(); i++) {
			if (this.am.getAccountList().get(i).getId().equals(id)) {
				if (this.am.getAccountList().get(i).getPersonalNum().equals(personalNumber)) {
					check = true;
				}
			}
		}
		return check;
	}

	private void addUser() {
		System.out.print("id : ");
		String id = inputString();
		if (checkDuplId(id)) {
			System.out.println("중복된 id가 있습니다.");
		} else {
			System.out.print("pw : ");
			String pw = inputString();
			System.out.print("이름 : ");
			String name = inputString();
			int count = 0;
			User tmp = new User(count, id, pw, name);
			this.um.createUserList(tmp);
			System.out.println("등록되었습니다.");
		}
	}

	private void deleteUser() {
		System.out.println("id : ");
		String id = inputString();
		if (checkDuplId(id)) {
			int idx = checkIdx(id);
			System.out.print("pw : ");
			String pw = inputString();
			if (checkPw(idx, pw)) {
				this.um.deleteUser(idx);
				System.out.println("탈퇴되었습니다.");
			} else {
				System.out.println("패스워드를 확인하세요. ");
			}
		} else {
			System.out.println("등록되지않은 아이디 입니다.");
		}
	}

	private void addAccount() {
		if (this.log != -1) {
			if (this.um.getUserList().get(this.log).getCount() < 3) {
				this.um.getUserList().get(this.log).setCount();
				this.am.createAccount(this.um.getUserList().get(this.log).getId());
				System.out.printf("[계좌생성 완료]\n현재 %s님의 계좌는 %d개 입니다.\n(최대 3개까지 발급가능)\n",
						this.um.getUserList().get(this.log).getId(), this.um.getUserList().get(this.log).getCount());
			} else {
				System.out.printf("[계좌생성 불가]\n현재 %s님의 계좌는 %d개 입니다.\n(최대 3개까지 발급가능)\n",
						this.um.getUserList().get(this.log).getId(), this.um.getUserList().get(this.log).getCount());
			}
		} else {
			System.out.println("로그인 후 이용가능.");
		}
	}

	private void deleteAccount() {
		if (this.log != -1) {
			System.out.print("삭제할 계좌의 고유번호를 입력하세요 : ");
			String personalNum = inputString();
			if (checkPersonal(this.um.getUserList().get(this.log).getId(), personalNum)) {
				int deletdNum = AccountIndex(this.um.getUserList().get(this.log).getId(), personalNum);
				am.deletdAccount(deletdNum);
			} else {
				System.out.println("계좌의 고유번호를 다시 확인하세요. ");
			}
		} else {
			System.out.println("로그인 후 이용가능.");
		}
	}

	private void login() {
		if (this.log == -1) {
			System.out.print("id : ");
			String id = inputString();
			if (checkDuplId(id)) {
				System.out.println("pw : ");
				String pw = inputString();
				int idx = checkIdx(id);
				if (checkPw(idx, pw)) {
					System.out.printf("[로그인 성공]\n%s님 환영합니다.\n", id);
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
	}

	private void logout() {
		if (this.log != -1) {
			System.out.println("로그아웃 되었습니다. ");
			this.log = -1;
		} else {
			System.out.println("이미 로그아웃 상태입니다. ");
		}
	}

	private void adminPrint() {
		System.out.println("1) 전체 고객정보 확인");
		System.out.println("2) 고객조회");
		System.out.println("3) 뒤로가기");
	}

	private boolean adminCheck(String id, String pw) {
		boolean check = false;
		if (id.equals("admin") && pw.equals("qwer")) {
			check = true;
		}
		return check;
	}

	private void printAllUser() {
		for (int i = 0; i < um.getUserList().size(); i++) {
			System.out.printf("%d) 이름: %s / Id: %s / pw: %s\n", i + 1, um.getUserList().get(i).getName(),
					um.getUserList().get(i).getId(), um.getUserList().get(i).getPw());
			int count = 1;
			for (int j = 0; j < am.getAccountList().size(); j++) {
				if (um.getUserList().get(i).getId().equals(am.getAccountList().get(j).getId())) {
					System.out.printf("   ㄴ %d. Account: %s / 고유번호: %s\n", count++, am.getAccountList().get(j).getAccount(),am.getAccountList().get(j).getPersonalNum());
				} 
			}
			if(count == 1) {
				System.out.println("   ㄴ 등록된 계좌가 없습니다.");
			}
		}
	}

	private void printUser(int idx) {
		int count = 1;
		System.out.printf("이름: %s / Id: %s / pw: %s\n", um.getUserList().get(idx).getName(),
				um.getUserList().get(idx).getId(), um.getUserList().get(idx).getPw());
		for (int j = 0; j < am.getAccountList().size(); j++) {
			if (um.getUserList().get(idx).getId().equals(am.getAccountList().get(j).getId())) {
				System.out.printf("   ㄴ %d. Account: %s / 고유번호: %s\n", count++, am.getAccountList().get(j).getAccount(),am.getAccountList().get(j).getPersonalNum());
			} 
		}
		if(count == 1) {
			System.out.println("   ㄴ 등록된 계좌가 없습니다.");
		}
	}

	private void searchUser() {
		System.out.print("찾으실 고객의 Id를 입력하세요 : ");
		String id = inputString();
		if (checkDuplId(id)) {
			int idx = checkIdx(id);
			printUser(idx);
		}
	}

	private void exitAdmin() {
		this.exitAdmin = false;
	}

	private void adminMenuSelect(int select) {
		if (select == 1) {
			printAllUser();
		} else if (select == 2) {
			searchUser();
		} else if (select == 3) {
			exitAdmin();
		}
	}

	private void admin() {
		System.out.print("관리자 id : ");
		String id = inputString();
		System.out.print("관리자 pw : ");
		String pw = inputString();
		if (adminCheck(id, pw)) {
			while (this.exitAdmin) {
				adminPrint();
				int select = selectMenu();
				adminMenuSelect(select);
			}
		} else {
			System.out.println("관리자 정보를 다시 확인하세요. ");
		}
	}

	private void exitRun() {
		this.exitRun = false;
	}

	private void menu(int select) {
		if (select == 1) {
			addUser();
		} else if (select == 2) {
			deleteUser();
		} else if (select == 3) {
			addAccount();
		} else if (select == 4) {
			deleteAccount();
		} else if (select == 5) {
			login();
		} else if (select == 6) {
			logout();
		} else if (select == 7) {
			admin();
		} else if (select == 0) {
			exitRun();
		} else {
			System.out.println("없는 메뉴 입니다. ");
		}
	}

	void run() {
		while (this.exitRun) {
			System.out.println("------" + this.name + "------");
			System.out.println("1. 회원가입");
			System.out.println("2. 회원탈퇴");
			System.out.println("3. 계좌신청");
			System.out.println("4. 계좌철회");
			System.out.println("5. 로그인");
			System.out.println("6. 로그아웃");
			System.out.println("7. 관리자모드");
			int select = selectMenu();
			menu(select);
		}
	}

}