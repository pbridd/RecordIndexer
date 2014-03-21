package shared.model;

@SuppressWarnings("serial")
public class ModelException extends Exception {

	/**Default constructor
	 */
	public ModelException() {
		return;
	}

	/**
	 * Creates an exception with a customizable message
	 * @param message The message to include in this exception
	 */
	public ModelException(String message) {
		super(message);
	}

	/**
	 * Creates an exception with a throwable object
	 * @param cause The throwable that caused this exception
	 */
	public ModelException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a message with both detail message and a throwable
	 * @param message The customized mesage
	 * @param cause The throwable cause of the exception
	 */
	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}

}
