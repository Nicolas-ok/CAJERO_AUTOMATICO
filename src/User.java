
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class User {

	private String username;
	private String password;
	private BankAccount account;

	private List<BankAccount> accounts;

	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
		setAccounts();

	}

	private void setUsername(String username) {
		this.username = username;

	}

	private void setAccounts() {
		this.accounts = new ArrayList<>();

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}

	protected void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

}