package synapticloop.templar.exception;

public class ParseCommandException extends Exception {
	private static final long serialVersionUID = 8526257822543110375L;

	public ParseCommandException(String message) {
		super(message);
	}

	public ParseCommandException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
