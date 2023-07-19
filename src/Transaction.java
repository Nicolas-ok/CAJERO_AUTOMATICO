import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

	private double amount;
	private String description;
	private String formattedDateTime;

	public Transaction(String description, double amount) {
		setDateTime();
		setDescription(description);
		setAmount(amount);
	}

	private void setDescription(String description) {
		this.description = description;
	}

	private void setAmount(double amount) {
		this.amount = amount;
	}

	private void setDateTime() {
		LocalDateTime now = LocalDateTime.now();
		this.formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
// ESTO ES UN TEST!!!!!!!!!!!


	public double getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public String getFormattedDateTime() {
		return formattedDateTime;
	}
}
