
import java.util.List;
import java.util.ArrayList;

public class TestBankAccount {

	public static void main(String[] args) {

		Bank bank = new Bank("BancoNico");

		User user1 = new User("Andre", "1234");
		User user2 = new User("Nico", "1234");
		User user3 = new User("Javier", "1234");
		User user4 = new User("Fran", "1234");
		User user5 = new User("Dani", "1234");

		bank.addUser(user1);
		bank.addUser(user2);
		bank.addUser(user3);
		bank.addUser(user4);
		bank.addUser(user5);

		bank.login();
		
		// ERRORES, clase bank, me pide dos veces el numero pera seleccionar la cuenta 
		//(recursividad de metodo o problema en el buffer del scanner?)
		//imprime dos veces la ultima cuenta creada!?
		//como llevo el cursor al final de lo que esta escrito en la consola?
		
	}
	}


