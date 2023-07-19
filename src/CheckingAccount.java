import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends BankAccount {

	private double overdraftLimit;
	private List<Transaction> transactions;

	public CheckingAccount(String accountNumber, double balance, double overdraftLimit) {
		super(accountNumber, balance);
		setOverdraftLimit(overdraftLimit);
		setTransactions();
	}

	private void setTransactions() {
		this.transactions = new ArrayList<>();
	}

	@Override
	public void withdraw(double amount) {
		if (balance - amount < -overdraftLimit) {
			throw new IllegalArgumentException("No hay suficientes fondos disponibles para realizar esta operación");
		}
		balance -= amount;
	}

	@Override
	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0.");
		}
		balance += amount;
		transactions.add(new Transaction("Depósito", amount));
	}

	public void transfer(double amount, CheckingAccount destinationAccount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("El monto a transferir debe ser mayor a 0.");
		}
		if (destinationAccount == null) {
			throw new IllegalArgumentException("La cuenta destino es inválida o no existe.");
		}
		if (balance < amount) {
			throw new IllegalStateException("No tienes saldo suficiente para realizar esta transferencia.");
		}
		balance -= amount;
		destinationAccount.balance += amount;
		transactions.add(new Transaction("Transferencia", -amount));
	}

	public void checkBalance() {
		System.out.println("El saldo actual de tu cuenta es: $" + balance);
		if (balance < 0) {
			System.out.println("ATENCIÓN: Tu cuenta se encuentra en sobregiro.");
		}
	}

	public void setOverdraftLimit(double overdraftLimit) {
		if (overdraftLimit < 0) {
			throw new IllegalArgumentException("El límite de sobregiro debe ser mayor o igual a 0.");
		}
		if (overdraftLimit > balance) {
			throw new IllegalArgumentException("El límite de sobregiro no puede ser mayor al saldo actual de la cuenta.");
		}
		// Establecer un límite máximo para el límite de sobregiro
		double maxOverdraftLimit = balance * 0.5;
		this.overdraftLimit = Math.min(overdraftLimit, maxOverdraftLimit);
	}

	public double getOverdraftLimit() {
		return overdraftLimit;
	}

	@Override
	public String getType() {
		return "Cuenta corriente";
	}
}
