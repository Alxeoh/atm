package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {

	private File userFile;
	private File accountFile;
	private String userFileName;
	private String accountFileName;
	private FileReader fr;
	private BufferedReader br;
	private FileWriter fw;
	private UserManager um;
	private AccountManager am;

	public FileManager() {
		this.userFileName = "userFile.txt";
		this.accountFileName = "accountFile.txt";
		this.userFile = new File(userFileName);
		this.accountFile = new File(accountFileName);
		this.um = new UserManager();
		this.am = new AccountManager();
	}

	public void load() {
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

	public void save() {
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
	}

}
