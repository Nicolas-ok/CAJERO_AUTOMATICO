
public class InsufficientFundsException extends Exception {
	
	public InsufficientFundsException() {
		super("No hay suficientes fondos disponibles para realizar esta operación");
	}
}
