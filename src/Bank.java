import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Bank {
	private List<User> users;
	private List<BankAccount> accounts;
	private int counterCheckingAccounts = 100;
	private int counterSavingsAccounts = 1000;

	Scanner scanner = new Scanner(System.in, "UTF-8");

	public Bank(String code) {
		setUsers();
		setAccounts();
	}

	private void setAccounts() {
		accounts = new ArrayList<>();
	}

	private void setUsers() {
		users = new ArrayList<>();
	}

	public List<BankAccount> getAccounts() {
		List<BankAccount> accountsCopy = new ArrayList<>(accounts);
		return accountsCopy;
	}

	public List<User> getUsers() {
		List<User> usersCopy = new ArrayList<>(users);
		return usersCopy;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public User login() {
		User loggedInUser = null;
		boolean isLoggedIn = false;
		while (!isLoggedIn) {
			System.out.printf("Ingresa tu nombre de usuario: %n");
			String username = scanner.nextLine();
			System.out.printf("Ingresa tu contraseña: %n");
			String password = scanner.nextLine();
			for (User user : users) {
				if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
					loggedInUser = user;
					isLoggedIn = true;
					break;
				}
			}
			if (!isLoggedIn) {
				System.out.println("El nombre de usuario o la contraseña son incorrectos. Inténtelo de nuevo.");
			}
		}
		System.out.print("Bienvenido: ");
		selectAccount(loggedInUser);
		try {
			displayMenu(loggedInUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loggedInUser;
	}

	/**
	 * Este método permite crear una cuenta para un usuario determinado. El usuario
	 * puede elegir entre crear una cuenta corriente o de ahorros. Si elige crear
	 * una cuenta corriente, se solicitará un límite de sobregiro. Si elige crear
	 * una cuenta de ahorros, no se solicitará ningún dato adicional. Una vez creada
	 * la cuenta, se le asignará un número único y se agregará a la lista de cuentas
	 * del usuario y a la lista de cuentas de la clase Bank.
	 *
	 * @param user El usuario al que se le va a crear una cuenta.
	 */
	public void createAccountForUser(User user) {
		boolean exit = false;
		while (!exit) {
			int optionAccount = requestAccountType();
			if (optionAccount == 3) {
				exit = true;
			} else {
				String accountNumber;
				double balance = 0;
				boolean exceptionThrown = false;
				while (!exceptionThrown) {
					try {
						System.out.print("Ingrese el balance inicial: ");
						balance = scanner.nextDouble();
						exceptionThrown = true;
					} catch (InputMismatchException e) {
						System.out.println("Por favor ingrese un balance válido");
						scanner.nextLine();
						exceptionThrown = false;
					}
				}
				BankAccount newAccount = null;
				if (optionAccount == 1) {
					// Crea cuenta corriente
					double overdraftLimit = 0;
					accountNumber = String.valueOf(counterCheckingAccounts);
					newAccount = createCheckingAccount(accountNumber, balance, overdraftLimit);
					counterCheckingAccounts++;
					System.out.println("Cuenta creada con éxito. Número de cuenta: " + newAccount.getAccountNumber());
				}
				if (optionAccount == 2) {
					// Crea cuenta de ahorros
					accountNumber = String.valueOf(counterSavingsAccounts);
					newAccount = createSavingsAccount(accountNumber, balance);
					counterSavingsAccounts++;
					System.out.println("Cuenta creada con éxito. Número de cuenta: " + newAccount.getAccountNumber());
				}
				// Agrega cuenta al usuario y a la lista de cuentas
				user.getAccounts().add(newAccount);
				accounts.add(newAccount);
			}
		}
	}

	public BankAccount selectAccount(User user) {
		List<BankAccount> accounts = user.getAccounts();
		boolean accountSelected = false; // Variable para almacenar si el usuario ha seleccionado una cuenta o no

		while (!accountSelected && !accounts.isEmpty()) {
			// Mostrar al usuario las cuentas disponibles para elegir
			showAccounts();
			// Pedir al usuario que ingrese el número de la cuenta que desea seleccionar
			System.out.print("Selecciona una cuenta: ");

			if (scanner.hasNextInt()) {
				int option = scanner.nextInt();
				scanner.nextLine(); // Limpiar el buffer de entrada

				// Validar que la opción ingresada sea válida (que sea un número entre 1 y el
				// número de cuentas del usuario)
				if (option >= 1 && option <= accounts.size()) {
					accountSelected = true; // Marcar que el usuario ha seleccionado una cuenta
					// Retornar la cuenta seleccionada y terminar la ejecución del método
					return accounts.get(option - 1);
				} else {
					System.out.println("Opción inválida. Por favor, ingrese un número de cuenta válido.");
				}
			} else {
				System.out.println("Opción inválida. Por favor, ingrese un número de cuenta válido.");
				scanner.nextLine(); // Limpiar el buffer de entrada
			}
		}
		System.out.print("No tienes cuentas disponibles. ¿Deseas crear una? (S/N) ");
		String response = scanner.nextLine();
		if (response.equalsIgnoreCase("S")) {
			createAccountForUser(user);
			// Verificar si el usuario ha creado al menos una cuenta
			if (!user.getAccounts().isEmpty()) {
				return selectAccount(user); // volver a ejecutar el método para seleccionar la nueva cuenta
			}
		}
		return null;
	}

	public enum Option {
	    // Opciones en inglés
	    BALANCE("View balance"),
	    DEPOSIT("Make a deposit"),
	    TRANSFER("Make a transfer"),
	    WITHDRAWAL("Withdrawal"),
	    VIEW_TRANSACTIONS("View transactions"),
	    PASSWORD("Change password"),
	    SELECT_ANOTHER_ACCOUNT("Select another account"),
	    EXIT("Exit");

	    // Atributo que almacena la descripción de la opción
	    private String description;

	    // Constructor que recibe la descripción de la opción
	    Option(String description) {
	        this.description = description;
	    }

	    // Método que devuelve la descripción de la opción
	    @Override
	    public String toString() {
	        // Reemplazar la descripción de la opción por su equivalente en español
	        switch (this) {
	            case BALANCE:
	                return "Ver saldo";
	            case DEPOSIT:
	                return "Realizar un depósito";
	            case TRANSFER:
	                return "Realizar una transferencia";
	            case WITHDRAWAL:
	                return "Retiro";
	            case VIEW_TRANSACTIONS:
	                return "Ver transacciones";
	            case PASSWORD:
	                return "Cambiar contraseña";
	            case SELECT_ANOTHER_ACCOUNT:
	                return "Seleccionar otra cuenta";
	            case EXIT:
	                return "Salir";
	            default:
	                return "";
	        }
	    }
	}

	public void displayMenu(User user) {
		BankAccount selectedAccount = selectAccount(user);
		Option option = null;
		while (option != Option.EXIT) {
			try {
				System.out.println("Seleccione una opción:");
				for (int i = 0; i < Option.values().length; i++) {
					System.out.println(i + 1 + " - " + Option.values()[i]);
				}
				int index = scanner.nextInt() - 1;
				if (index < 0 || index >= Option.values().length) {
					System.out.println("Por favor ingrese una opción válida");
					continue;
				}
				option = Option.values()[index];
				switch (option) {
				case BALANCE:
					viewBalance(selectedAccount);
					break;
				case DEPOSIT:
					makeDeposit(selectedAccount);
					break;
				case TRANSFER:
					makeTransfer(selectedAccount);
					break;
				case WITHDRAWAL:
					withdrawal(selectedAccount);
					break;
				case VIEW_TRANSACTIONS:
					viewTransactions(selectedAccount);
					break;
				case PASSWORD:
					changePassword(user);
					break;
				case SELECT_ANOTHER_ACCOUNT:
					selectedAccount = selectAccount(user);
					break;
				case EXIT:
					System.out.println("Gracias por usar nuestro servicio. ¡Hasta pronto!");
					break;
				default:
				}
				if (option != Option.EXIT) {
					System.out.print("¿Desea realizar otra operación? (s/n) ");
					String response = scanner.nextLine();
					if (response.equalsIgnoreCase("n")) {
						System.out.println("¿Desea iniciar sesión nuevamente? (s/n) ");
						response = scanner.nextLine();
						if (response.equalsIgnoreCase("s")) {
							login();
						} else {
							System.out.print("Muchas gracias por utilizar nuestros servicios, que tenga un buen día! ");
							break;
						}
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Por favor ingrese una opción válida");
				scanner.nextLine();
				continue;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int requestAccountType() {
		int optionAccount = 0;
		while (optionAccount != 1 && optionAccount != 2 && optionAccount != 3) {
			try {
				System.out.print("Ingrese el tipo de cuenta que desea crear (1 para cuenta corriente, "
						+ "2 para cuenta de ahorros, 3 para salir): ");
				optionAccount = scanner.nextInt();
				scanner.nextLine();
				if (optionAccount != 1 && optionAccount != 2 && optionAccount != 3) {
					System.out.println("Opción inválida, por favor ingrese 1, 2 o 3");
				}
			} catch (InputMismatchException e) {
				System.out.println("Por favor ingrese una opción válida (1, 2 o 3)");
				scanner.nextLine();
			}
		}
		return optionAccount;
	}

	private CheckingAccount createCheckingAccount(String accountNumber, double balance, double overdraftLimit) {
		boolean exceptionThrown = false;
		while (!exceptionThrown) {
			try {
				System.out.print("Ingrese el límite de sobregiro: ");
				overdraftLimit = scanner.nextDouble();
				CheckingAccount newAccount = new CheckingAccount(accountNumber, balance, overdraftLimit);
				newAccount.setOverdraftLimit(overdraftLimit);
				exceptionThrown = true;
				counterCheckingAccounts++;
				return newAccount;
			} catch (IllegalArgumentException e) {
				System.out.println("Por favor ingrese un límite de sobregiro válido");
				scanner.nextLine();
				exceptionThrown = false;
			}
		}
		return null;
	}

	private SavingsAccount createSavingsAccount(String accountNumber, double balance) {
		SavingsAccount newAccount = new SavingsAccount(accountNumber, balance);
		boolean inputError = false;
		do {
			System.out.print("Ingrese el saldo mínimo: ");
			double minimumBalance = scanner.nextDouble();
			try {
				newAccount.setMinimumBalance(minimumBalance);
				inputError = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
				inputError = true;
			}
		} while (inputError);
		counterSavingsAccounts++;
		return newAccount;
	}

	private void viewBalance(BankAccount selectedAccount) {
		System.out.println("Tu saldo es: " + selectedAccount.getBalance());
		scanner.nextLine();
	}

	public void showAccounts() {
		for (BankAccount account : accounts) {
			if (account instanceof CheckingAccount) {
				CheckingAccount checkingAccount = (CheckingAccount) account;
				// Muestra información de la cuenta corriente
				System.out.println("Cuenta corriente: " + checkingAccount.getAccountNumber() + " | Saldo: $"
						+ checkingAccount.getBalance() + " | Límite de sobregiro: $"
						+ checkingAccount.getOverdraftLimit());
			} else if (account instanceof SavingsAccount) {
				SavingsAccount savingsAccount = (SavingsAccount) account;
				// Muestra información de la cuenta de ahorros
				System.out.println("Cuenta de ahorros: " + savingsAccount.getAccountNumber() + " | Saldo: $"
						+ savingsAccount.getBalance() + " | Tasa de interés: " + savingsAccount.getINTEREST_RATE()
						+ "%");
			}
		}
	}

	private void changePassword(User user) {
		System.out.println("Ingresa tu contraseña actual: ");
		scanner.nextLine();
		String currentPassword = scanner.nextLine();

		if (!user.getPassword().equals(currentPassword)) {
			System.out.println("Contraseña incorrecta.");
			return;
		}
		System.out.println("Ingresa tu nueva contraseña: ");
		String newPassword = scanner.nextLine();
		System.out.println("Ingresa nuevamente la nueva contraseña para confirmarla: ");
		String confirmedPassword = scanner.nextLine();
		if (!newPassword.equals(confirmedPassword)) {
			System.out.println("Las contraseñas no coinciden. No se ha realizado el cambio.");
			return;
		}
		user.setPassword(newPassword);
		System.out.println("Contraseña cambiada con éxito.");
	}

	private void viewTransactions(BankAccount selectedAccount) {
		BankAccount bankAccount = (BankAccount) selectedAccount;
		List<Transaction> transactions = bankAccount.getTransactions();
		if (transactions.isEmpty()) {
			System.out.println("No hay transacciones registradas para esta cuenta.");
			scanner.nextLine();
		} else {
			System.out.println("Lista de transacciones:");
			for (Transaction transaction : transactions) {
				System.out.println(transaction.getDescription() + " - " + transaction.getDateTime() + " - $"
						+ transaction.getAmount());
				scanner.nextLine();
			}
		}
	}

	private void withdrawal(BankAccount selectedAccount) {
		BankAccount bankAccount = (BankAccount) selectedAccount;
		System.out.println("Ingresa la cantidad a retirar: ");
		double amount = scanner.nextDouble();
		if (amount > bankAccount.getBalance()) {
			System.out.println("No tienes suficiente saldo para realizar este retiro.");
			scanner.nextLine();
		} else {
			bankAccount.withdraw(amount);
			System.out.println("Retiro realizado con éxito. Tu saldo actual es: " + bankAccount.getBalance());
			scanner.nextLine();
		}
	}

	/**
	 *
	 * Realiza una transferencia de una cuenta seleccionada a otra cuenta de
	 * destino. Primero pregunta al usuario por el número de cuenta de destino y
	 * luego por la cantidad a transferir. Luego verifica si la cuenta de destino
	 * existe y si la cuenta seleccionada tiene saldo suficiente para realizar la
	 * transferencia. Si la cuenta seleccionada es una cuenta de ahorros, también se
	 * verifica que la transferencia no cause que el saldo mínimo sea superado. Si
	 * todas las condiciones se cumplen, se realiza la transferencia y se muestra un
	 * mensaje de éxito. Si la cuenta de destino no existe o la cuenta seleccionada
	 * no tiene saldo suficiente, se muestra un mensaje de error. Si la cuenta
	 * seleccionada es un tipo de cuenta que no permite realizar transferencias, se
	 * muestra un mensaje de error.
	 *
	 * @param selectedAccount la cuenta desde donde se realizará la transferencia
	 * @throws Exception si ocurre algún error al realizar la transferencia
	 */
	private void makeTransfer(BankAccount selectedAccount) throws Exception {
		System.out.println("Ingresa el número de cuenta destino: ");
		String accountNumber = scanner.next();
		BankAccount destinationAccount = findAccountByNumber(accountNumber);
		if (destinationAccount == null) {
			System.out.println("Cuenta destino no encontrada.");
		} else {
			System.out.println("Ingresa la cantidad a transferir: ");
			double amount = scanner.nextDouble();
			if (selectedAccount instanceof SavingsAccount) {
				SavingsAccount savingsAccount = (SavingsAccount) selectedAccount;
				if (amount > savingsAccount.getBalance()) {
					System.out.println("No tienes suficiente saldo para realizar esta transferencia.");
					scanner.nextLine();
				} else if (savingsAccount.getBalance() - amount < savingsAccount.getMinimumBalance()) {
					System.out.println(
							"No puedes realizar esta transferencia ya que el saldo mínimo de tu cuenta de ahorros sería superado.");
					scanner.nextLine();
				} else {
					savingsAccount.transfer(destinationAccount, amount);
					System.out.println("Transferencia realizada con éxito.");
					scanner.nextLine();
				}
			} else if (selectedAccount instanceof CheckingAccount) {
				CheckingAccount checkingAccount = (CheckingAccount) selectedAccount;
				if (amount > checkingAccount.getBalance()) {
					System.out.println("No tienes suficiente saldo para realizar esta transferencia.");
					scanner.nextLine();
				} else {
					checkingAccount.transfer(destinationAccount, amount);
					System.out.println("Transferencia realizada con éxito.");
					scanner.nextLine();
				}
			} else {
				System.out.println("Esta operación no está disponible para este tipo de cuenta.");
				scanner.nextLine();
			}
		}
	}

	private void makeDeposit(BankAccount selectedAccount) {
		System.out.println("Ingresa la cantidad a depositar: ");
		boolean validInput = false;
		while (!validInput) {
			String input = scanner.nextLine();
			try {
				double amount = Integer.parseInt(input);
				selectedAccount.deposit(amount);
				Transaction depositTransaction = new Transaction("Depósito", amount);
				try {
					selectedAccount.addTransaction(depositTransaction);
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
				}
				System.out.println("Depósito realizado con éxito. Tu saldo actual es: " + selectedAccount.getBalance());
				validInput = true;
			} catch (NumberFormatException e) {
				System.out.println("Por favor ingrese una cantidad válida (un número entero)");
			}
		}
	}

	public BankAccount findAccountByNumber(String accountNumber) {
		List<BankAccount> accounts = getAccounts();
		for (BankAccount account : accounts) {
			if (account.getAccountNumber().equals(accountNumber)) {
				return account;
			}
		}
		System.out.println("La cuenta especificada no ha sido encontrada.");
		return null;
	}

	public void selectAnotherAccount(User user) {
		System.out.println("Selecciona una cuenta: ");
		int accountNumber = scanner.nextInt();
		List<BankAccount> accounts = user.getAccounts();
		if (accountNumber < 0 || accountNumber >= accounts.size()) {
			System.out.println("Cuenta inválida. Inténtalo de nuevo.");
			selectAnotherAccount(user);
			return;
		}
		user.setAccount(accounts.get(accountNumber));
	}

}