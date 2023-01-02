
import java.util.List;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		Bank bank = new Bank("BancoNico");

		User user1 = new User("Nico", "1234");
		User user2 = new User("Dani", "1234");
		User user3 = new User("Mica", "1234");

		bank.addUser(user1);
		bank.addUser(user2);
		bank.addUser(user3);

		bank.login();

	}
}
