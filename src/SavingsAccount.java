import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SavingsAccount extends BankAccount {

	private final int TIME_PERIOD = 3;
	private final double MIN_ACTUAL_BALANCE = 0.5;
	private final double INTEREST_RATE = 0.3;
	private List<Transaction> transactions;
	private double minimumBalance;
	Scanner scInt = new Scanner(System.in);

	public SavingsAccount(String accountNumber, double balance) {
		super(accountNumber, balance);
		this.transactions = new ArrayList<>();
	}

	public double getINTEREST_RATE() {
		return INTEREST_RATE;
	}

	@Override
	public void withdraw(double amount) {
		if (amount > balance) {
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
		applyInterest();
		transactions.add(new Transaction("Depósito", amount));
	}

	public void applyInterest() {
		double interest = balance * INTEREST_RATE;
		System.out.println("Interés generado: $" + interest);
	}

	public double accumulatedInterest() {
		double accumulatedInterest = balance * INTEREST_RATE * TIME_PERIOD;
		return accumulatedInterest;
	}

	public void checkBalance() {
		System.out.println("Saldo actual: $" + balance);
		System.out.println("Saldo mínimo: $" + minimumBalance);
		System.out.println("Saldo disponible: $" + (balance - minimumBalance));
		System.out.println("Interés acumulado: $" + accumulatedInterest());
		System.out.println("\nHistorial de transacciones: ");
		for (Transaction transaction : transactions) {
			System.out.println(transaction.getDescription() + " - $" + transaction.getAmount() + " - "
					+ transaction.getDateTime());
		}
	}

	protected void setMinimumBalance(double minBalance) {
		  boolean inputError = false;
		  do {
		    try {
		      System.out.println("Ingresa el saldo mínimo: ");
		      double minimumBalance = scInt.nextDouble();
		      if (minimumBalance < 0) {
		          throw new IllegalArgumentException("El saldo mínimo debe ser mayor o igual a 0.");
		      }
		      if (minimumBalance > balance * MIN_ACTUAL_BALANCE) {
		          throw new IllegalArgumentException("El saldo mínimo no puede ser mayor al 50% del saldo actual de la cuenta.");
		      }
		      if (minimumBalance % 1 != 0) {
		          throw new IllegalArgumentException("El saldo mínimo debe ser un valor entero.");
		      }
		      this.minimumBalance = minimumBalance;
		      inputError = false;
		    } catch (IllegalArgumentException ex) {
		      System.out.println(ex.getMessage());
		      inputError = true;
		    }
		  } while (inputError);
		}

	public void transfer(double amount, BankAccount destination) {
		if (amount <= 0) {
			throw new IllegalArgumentException("El monto a transferir debe ser mayor a 0.");
		}
		if (destination == null || destination.equals(this)) {
			throw new IllegalArgumentException("La cuenta de destino no es válida.");
		}
		if (balance - amount < minimumBalance) {
			throw new IllegalStateException("No tienes saldo suficiente para realizar la transferencia.");
		}
		balance -= amount;
		destination.deposit(amount);
		transactions.add(new Transaction("Transferencia", amount));
	}

	@Override
	public String getType() {
		return "Cuenta de ahorros";
	}

	public double getMinimumBalance() {
		// TODO Auto-generated method stub
		return minimumBalance;
	}

}