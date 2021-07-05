package marketing.exceptions;

public class UpdateIsBannedException extends Exception {
	private static final long serialVersionUID = 1L;

	public UpdateIsBannedException(String message) {
		super(message);
	}
}
