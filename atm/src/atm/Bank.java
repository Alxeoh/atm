package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Bank {

	private String name;
	private UserManager um;
	private AccountManager am;
	private int log;
	private Scanner sc;
	private boolean exitRun;
	private boolean exitMenu;
	private File userFile;
	private File accountFile;
	private String userFileName;
	private String accountFileName;
	private FileReader fr;
	private BufferedReader br;
	private FileWriter fw;

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
				if (log == idx) {
					log = -1;
				}
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
		if (um.getUserList().size() < 1) {
			System.out.printf("[고객정보조회 불가]\n등록되어있는 고객정보가 없습니다.\n");
		}
		for (int i = 0; i < um.getUserList().size(); i++) {
			System.out.printf("%d) 이름: %s / Id: %s / pw: %s\n", i + 1, um.getUserList().get(i).getName(),
					um.getUserList().get(i).getId(), um.getUserList().get(i).getPw());
			int count = 1;
			for (int j = 0; j < am.getAccountList().size(); j++) {
				if (um.getUserList().get(i).getId().equals(am.getAccountList().get(j).getId())) {
					System.out.printf("   ㄴ %d. Account: %s / 고유번호: %s / 잔고 : %d\n", count++,
							am.getAccountList().get(j).getAccount(), am.getAccountList().get(j).getPersonalNum(),
							am.getAccountList().get(j).getMoney());
				}
			}
			if (count == 1) {
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
				System.out.printf("   ㄴ %d. Account: %s / 고유번호: %s / 잔고 : %d\n", count++,
						am.getAccountList().get(j).getAccount(), am.getAccountList().get(j).getPersonalNum(),
						am.getAccountList().get(j).getMoney());
			}
		}
		if (count == 1) {
			System.out.println("   ㄴ 등록된 계좌가 없습니다.");
		}
	}

	private int[] accountIdxes(int idx) {
		int count = 0;
		int arr[] = null;
		for (int j = 0; j < am.getAccountList().size(); j++) {
			if (um.getUserList().get(idx).getId().equals(am.getAccountList().get(j).getId())) {
				int temp[] = arr;
				arr = new int[count + 1];
				for (int i = 0; i < count; i++) {
					arr[i] = temp[i];
				}
				arr[count++] = j;
			}
		}
		return arr;
	}

	private void searchUser() {
		if (um.getUserList().size() < 1) {
			System.out.printf("[고객조회 불가]\n등록되어있는 고객이 없습니다.\n");
		} else {
			System.out.print("찾으실 고객의 Id 입력 : ");
			String id = inputString();
			if (checkDuplId(id)) {
				int idx = checkIdx(id);
				printUser(idx);
			} else {
				System.out.printf("[고객조회 불가]\n등록되어있는 Id가 아닙니다.\n");
			}
		}
	}

	private void exitAdmin() {
		this.exitMenu = false;
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
			while (this.exitMenu) {
				adminPrint();
				int select = selectMenu();
				adminMenuSelect(select);
			}
		} else {
			System.out.printf("[로그인 불가]\n관리자 정보를 다시 확인하세요.\n");
		}
	}

	private void exitRun() {
		String userData = "";
		String accountData = "";

		for (int i = 0; i < um.getUserList().size(); i++) {
			userData += um.getUserList().get(i).getCount() + "/" + um.getUserList().get(i).getId() + "/"
					+ um.getUserList().get(i).getPw() + "/" + um.getUserList().get(i).getName() + "\n";
		}
		for (int i = 0; i < am.getAccountList().size(); i++) {
			accountData += am.getAccountList().get(i).getId() + "/" + am.getAccountList().get(i).getAccount() + "/"
					+ am.getAccountList().get(i).getPersonalNum() + "/" + am.getAccountList().get(i).getMoney() + "\n";
		}
		try {
			fw = new FileWriter(userFileName);
			fw.write(userData);
			fw.close();
			fw = new FileWriter(accountFileName);
			fw.write(accountData);
			fw.close();
			System.out.println("[저장성공]");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("저장실패");
		}
		this.exitRun = false;
	}

	private void load() {
		this.userFileName = "userFile.txt";
		this.accountFileName = "accountFile.txt";
		this.userFile = new File(userFileName);
		this.accountFile = new File(accountFileName);
		try {
			if (this.userFile.exists()) {
				this.fr = new FileReader(this.userFileName);
				this.br = new BufferedReader(fr);
				while (this.br.ready()) {
					String temp[] = br.readLine().split("/");
					User tmpUser = new User(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3]);
					um.getUserList().add(tmpUser);
				}
				this.fr.close();
				this.br.close();
				if (this.accountFile.exists()) {
					this.fr = new FileReader(this.accountFileName);
					this.br = new BufferedReader(fr);
					while (this.br.ready()) {
						String temp[] = br.readLine().split("/");
//						String id, String account, String personalNum, int money
						Account tmpAccount = new Account(temp[0], temp[1], temp[2], Integer.parseInt(temp[3]));
						am.getAccountList().add(tmpAccount);
					}
				}
			}
			System.out.println("[로드성공]");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[로드실패]");
		}
	}

	private void printMyPage() {
		System.out.println("1) 회원정보확인");
		System.out.println("2) 회원정보 변경");
		System.out.println("0) 뒤로가기");
	}

	private void searchMyInfo() {
		printUser(log);
	}

	private void editPrint() {
		System.out.println("수정하실 부분의 메뉴를 선택하세요.");
		System.out.println("1) Id");
		System.out.println("2) Pw");
		System.out.println("3) 이름");
		System.out.println("0) 뒤로가기");
	}

	private void exitEdit() {
		this.exitMenu = false;
	}

	private void editMenu(int select) {
		if (select == 1) {
			editId();
		} else if (select == 2) {
			editPw();
		} else if (select == 3) {
			editName();
		} else if (select == 0) {
			exitEdit();
		} else {
			System.out.println("없는 메뉴 입니다.");
		}
	}

	private void editId() {
		System.out.print("수정하실 아이디를 입력하세요 : ");
		String id = inputString();
		User temp = um.getUserList().get(log);
		temp.setId(id);
		um.getUserList().remove(log);
		um.getUserList().add(log, temp);
		System.out.println("변경이 완료되었습니다.");
	}

	private void editPw() {
		System.out.print("수정하실 패스워드를 입력하세요 : ");
		String pw = inputString();
		User temp = um.getUserList().get(log);
		temp.setPw(pw);
		um.getUserList().remove(log);
		um.getUserList().add(log, temp);
		System.out.println("변경이 완료되었습니다.");
	}

	private void editName() {
		System.out.print("수정하실 이름을 입력하세요 : ");
		String name = inputString();
		User temp = um.getUserList().get(log);
		temp.setName(name);
		um.getUserList().remove(log);
		um.getUserList().add(log, temp);
		System.out.println("변경이 완료되었습니다.");
	}

	private void edit() {
		editPrint();
		int select = selectMenu();
		editMenu(select);
	}

	private void exitMyPage() {
		this.exitMenu = false;
	}

	private void myPageMenu(int select) {
		if (select == 1) {
			searchMyInfo();
		} else if (select == 2) {
			while (this.exitMenu) {
				edit();
			}
		} else if (select == 0) {
			exitMyPage();
		} else {
			System.out.println("없는 메뉴 입니다.");
		}
	}

	private void myPage() {
		while (this.exitMenu) {
			this.exitMenu = true;
			if (log != -1) {
				printMyPage();
				int select = selectMenu();
				myPageMenu(select);
			} else {
				System.out.println("로그인 후 이용가능.");
				this.exitMenu = false;
			}
		}
	}

	private int select() {
		System.out.println("번호 입력 : ");
		int select = sc.nextInt();
		return select;
	}

	private int howMuch() {
		System.out.print("금액 입력 : ");
		int money = sc.nextInt();
		return money;
	}

	private void credit(int select) {
		System.out.println("입금하실 금액을 입력하세요.");
		int money = howMuch() + am.getAccountList().get(select).getMoney();
		Account temp = am.getAccountList().get(select);
		temp.setMoney(money);
		am.getAccountList().remove(select);
		am.getAccountList().add(select, temp);
		System.out.println("[입금완료]");
	}

	private void creditMoney() {
		if (log != -1) {
			while (this.exitMenu) {
				if (um.getUserList().get(log).getCount() > 0) {
					printUser(log);
					System.out.println("입금하실 계좌를 선택하세요.");
					int[] accountIdxes = accountIdxes(log);
					int selectNumber = select();
					if (selectNumber > 0 && selectNumber <= um.getUserList().get(log).getCount()) {
						int selectAccount = accountIdxes[selectNumber - 1];
						credit(selectAccount);
						this.exitMenu = false;
					} else {
						System.out.println("잘못된 입력입니다.");
					}
				} else {
					System.out.println("[입금실패]\n계좌개설이 필요합니다.\n");
					this.exitMenu = false;
				}
			}
		} else {
			System.out.println("로그인 후 이용가능.");
		}
	}

	private void withdraw(int select) {
		if (am.getAccountList().get(select).getMoney() > 0) {
			System.out.println("출금하실 금액을 입력하세요. ");
			int money = howMuch();
			if (money <= am.getAccountList().get(select).getMoney()) {
				int total = am.getAccountList().get(select).getMoney() - money;
				Account temp = am.getAccountList().get(select);
				temp.setMoney(total);
				am.getAccountList().remove(select);
				am.getAccountList().add(select, temp);
				System.out.println("[출금완료]");
			} else {
				System.out.printf("[출금실패]\n출금요청 금액이 잔고보다 큽니다.\n");
			}

		} else {
			System.out.printf("[출금실패]\n잔고가 없습니다.\n");
			this.exitMenu = false;
		}
	}

	private void withdrawMoney() {
		if (log != -1) {
			while (this.exitMenu) {
				if (um.getUserList().get(log).getCount() > 0) {
					printUser(log);
					System.out.println("출금하실 계좌를 선택하세요.");
					int[] accountIdxes = accountIdxes(log);
					int selectNumber = select();
					if (selectNumber > 0 && selectNumber <= um.getUserList().get(log).getCount()) {
						int selectAccount = accountIdxes[selectNumber - 1];
						withdraw(selectAccount);
						this.exitMenu = false;
					} else {
						System.out.println("잘못된 입력입니다.");
					}
				} else {
					System.out.printf("[출금실패]\n계좌개설이 필요합니다.\n");
					this.exitMenu = false;
				}
			}
		} else {
			System.out.println("로그인 후 이용가능.");
		}
	}

	private int searchWithPersonalNum(String personalNumber) {
		int idx = -1;
		for (int i = 0; i < am.getAccountList().size(); i++) {
			if (personalNumber.equals(am.getAccountList().get(i).getPersonalNum())) {
				idx = i;
			}
		}
		return idx;
	}

	private void transfer(int select) {
		if (am.getAccountList().get(select).getMoney() > 0) {
			System.out.println("이체하실 금액을 입력하세요. ");
			int money = howMuch();
			if (money <= am.getAccountList().get(select).getMoney()) {
				System.out.print("이체하실 상대방 계좌의 고유번호를 입력하세요 : ");
				String personalNumber = inputString();
				int idx = searchWithPersonalNum(personalNumber);
				if (idx != -1) {
					int myMoney = am.getAccountList().get(select).getMoney() - money;
					int theOtherMoney = am.getAccountList().get(idx).getMoney() + money;

					Account temp = am.getAccountList().get(select);
					temp.setMoney(myMoney);
					am.getAccountList().remove(select);
					am.getAccountList().add(select, temp);

					Account temp2 = am.getAccountList().get(idx);
					temp.setMoney(theOtherMoney);
					am.getAccountList().remove(idx);
					am.getAccountList().add(idx, temp2);
					System.out.println("[이체완료]");
				} else {
					System.out.printf("[이체실패]\n상대방계좌의 고유번호를 다시 확인하세요.\n");
				}
			} else {
				System.out.printf("[이체실패]\n이체요청 금액이 잔고보다 큽니다.\n");
			}

		} else {
			System.out.printf("[이체실패]\n잔고가 없습니다.\n");
			this.exitMenu = false;
		}
	}

	private void transferMoney() {
		if (log != -1) {
			while (this.exitMenu) {
				if (um.getUserList().get(log).getCount() > 0) {
					printUser(log);
					System.out.println("어느계좌에서 이체하실지 선택하세요.");
					int[] accountIdxes = accountIdxes(log);
					int selectNumber = select();
					if (selectNumber > 0 && selectNumber <= um.getUserList().get(log).getCount()) {
						int selectAccount = accountIdxes[selectNumber - 1];
						transfer(selectAccount);
						this.exitMenu = false;
					} else {
						System.out.println("잘못된 입력입니다.");
					}
				} else {
					System.out.printf("[이체실패]\n계좌개설이 필요합니다.\n");
					this.exitMenu = false;
				}
			}
		} else {
			System.out.println("로그인 후 이용가능.");
		}
	}

	private void menu(int select) {
		this.exitMenu = true;
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
			creditMoney();
		} else if (select == 8) {
			withdrawMoney();
		} else if (select == 9) {
			transferMoney();
		} else if (select == 10) {
			myPage();
		} else if (select == 11) {
			admin();
		} else if (select == 12) {
			exitRun();

		} else {
			System.out.println("없는 메뉴 입니다. ");
		}
	}

	void run() {
		load();
		while (this.exitRun) {
			System.out.println("------" + this.name + "------");
			System.out.println(" 1. 회원가입");
			System.out.println(" 2. 회원탈퇴");
			System.out.println(" 3. 계좌신청");
			System.out.println(" 4. 계좌삭제");
			System.out.println(" 5. 로그인");
			System.out.println(" 6. 로그아웃");
			System.out.println(" 7. 입금");
			System.out.println(" 8. 출금");
			System.out.println(" 9. 이체");
			System.out.println("10. 마이페이지");
			System.out.println("11. 관리자모드");
			System.out.println("12. 종료");
			System.out.println("--------------------");
			int select = selectMenu();
			menu(select);
		}
	}

}