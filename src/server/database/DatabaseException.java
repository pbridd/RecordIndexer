package server.database;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {

	/**Default constructor
	 */
	public DatabaseException() {
		return;
	}

	/**
	 * Creates an exception with a customizable message
	 * @param message The message to include in this exception
	 */
	public DatabaseException(String message) {
		super(message);
	}

	/**
	 * Creates an exception with a throwable object
	 * @param cause The throwable that caused this exception
	 */
	public DatabaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a message with both detail message and a throwable
	 * @param message The customized mesage
	 * @param cause The throwable cause of the exception
	 */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
