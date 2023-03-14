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
	}

	private int selectMenu() {
		System.out.println("메뉴 입력 : ");
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
		System.out.print("회원가입 하실 Id 입력 : ");
		String id = inputString();
		if (checkDuplId(id)) {
			System.out.printf("[회원등록 실패]\n중복된 id가 있습니다.\n");
		} else {
			System.out.print("회원가입 하실 Pw 입력 : ");
			String pw = inputString();
			System.out.print("회원가입 하실 분의 이름 입력 : ");
			String name = inputString();
			int count = 0;
			User tmp = new User(count, id, pw, name);
			this.um.createUserList(tmp);
			System.out.println("[회원등록 성공]");
		}
	}

	private void deleteUser() {
		System.out.println("탈퇴하실 Id 입력 : ");
		String id = inputString();
		if (checkDuplId(id)) {
			int idx = checkIdx(id);
			System.out.print("탈퇴하실 Pw 입력 : ");
			String pw = inputString();
			if (checkPw(idx, pw)) {
				this.um.deleteUser(idx);
				System.out.println("[회원탈퇴 성공]");
			} else {
				System.out.printf("[회원탈퇴 실패]\n패스워드를 확인하세요. \n");
			}
		} else {
			System.out.printf("[회원탈퇴 실패]\n등록되지않은 아이디 입니다.\n");
		}
	}

	private void addAccount() {
		if (this.log != -1) {
			if (this.um.getUserList().get(this.log).getCount() < 3) {
				this.um.getUserList().get(this.log).setCount();
				this.am.createAccount(this.um.getUserList().get(this.log).getId());
				System.out.printf("[계좌생성 완료]\n현재 %s님의 계좌는 %d개 입니다.\n(최대 3개까지 발급가능)\n",
						this.um.getUserList().get(this.log).getName(), this.um.getUserList().get(this.log).getCount());
			} else {
				System.out.printf("[계좌생성 불가]\n현재 %s님의 계좌는 %d개 입니다.\n(최대 3개까지 발급가능)\n",
						this.um.getUserList().get(this.log).getName(), this.um.getUserList().get(this.log).getCount());
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
				System.out.println("[계좌삭제 완료]");
			} else {
				System.out.printf("[계좌삭제 실패]\n계좌의 고유번호를 다시 확인하세요.\n");
			}
		} else {
			System.out.println("로그인 후 이용가능.");
		}
	}

	private void login() {
		if (this.log == -1) {
			System.out.print("로그인 하실 Id 입력 : ");
			String id = inputString();
			if (checkDuplId(id)) {
				System.out.println("로그인 하실 Pw 입력 : ");
				String pw = inputString();
				int idx = checkIdx(id);
				if (checkPw(idx, pw)) {
					System.out.printf("[로그인 성공]\n%s님 환영합니다.\n", um.getUserList().get(idx).getName());
					this.log = idx;
				} else {
					System.out.printf("[로그인 실패]\n패스워드를 확인하세요.\n");
				}
			} else {
				System.out.printf("[로그인 실패]\n등록되지않은 아이디 입니다.\n");
			}
		} else {
			System.out.println("이미 로그인 상태입니다.");
		}
	}

	private void logout() {
		if (this.log != -1) {
			System.out.println("[로그아웃 완료] ");
			this.log = -1;
		} else {
			System.out.printf("[로그아웃 불가]\n이미 로그아웃 상태입니다.\n");
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
		if(um.getUserList().size()<1) {
			System.out.printf("[고객정보조회 불가]\n등록되어있는 고객정보가 없습니다.\n");
		}
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
		if(um.getUserList().size()<1) {
			System.out.printf("[고객조회 불가]\n등록되어있는 고객이 없습니다.\n");
		}else {
			System.out.print("찾으실 고객의 Id 입력 : ");
			String id = inputString();
			if (checkDuplId(id)) {
				int idx = checkIdx(id);
				printUser(idx);
			}else {
				System.out.printf("[고객조회 불가]\n등록되어있는 Id가 아닙니다.\n");
			}
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
		System.out.print("관리자 Id 입력 : ");
		String id = inputString();
		System.out.print("관리자 Pw 입력 : ");
		String pw = inputString();
		if (adminCheck(id, pw)) {
			while (this.exitAdmin) {
				adminPrint();
				int select = selectMenu();
				adminMenuSelect(select);
			}
		} else {
			System.out.printf("[로그인 불가]\n관리자 정보를 다시 확인하세요.\n");
		}
	}

	private void exitRun() {
		this.exitRun = false;
	}

	private void menu(int select) {
		this.exitAdmin = true;
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
			System.out.println("4. 계좌삭제");
			System.out.println("5. 로그인");
			System.out.println("6. 로그아웃");
			System.out.println("7. 관리자모드");
			System.out.println("--------------------");
			int select = selectMenu();
			menu(select);
		}
	}

}