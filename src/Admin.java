
public class Admin implements Authenticable {
	private String username;
	private String password;
	private String email;

	public Admin(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Override
	public boolean login(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}

	public boolean verifyEmail(String email) {
		return this.email.equals(email);
	}

}
