package utilities;

@SuppressWarnings("serial")
public class UtilitiesException extends Exception {

	/**Default constructor
	 */
	public UtilitiesException() {
		return;
	}

	/**
	 * Creates an exception with a customizable message
	 * @param message The message to include in this exception
	 */
	public UtilitiesException(String message) {
		super(message);
	}

	/**
	 * Creates an exception with a throwable object
	 * @param cause The throwable that caused this exception
	 */
	public UtilitiesException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a message with both detail message and a throwable
	 * @param message The customized mesage
	 * @param cause The throwable cause of the exception
	 */
	public UtilitiesException(String message, Throwable cause) {
		super(message, cause);
	}

}
