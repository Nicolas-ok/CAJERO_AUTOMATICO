import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

	private double amount;
	private String description;
	String formattedDateTime;

	public Transaction(String description, double amount) {
		getDateTime();
		setDescription(description);
		setAmount(amount);

	}

	private void setDescription(String description) {
		this.description = description;

	}

	private void setAmount(double amount) {
		this.amount = amount;

	}

	public String getDateTime() {
		LocalDateTime now = LocalDateTime.now();
		String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		return formattedDateTime;

	}

	public double getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}
}