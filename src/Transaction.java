import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

	private double amount;
	private String description;
	String formattedDateTime;

	public Transaction(String description, double amount) {
		getDateTime();
		this.amount = amount;
		this.description = description;
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