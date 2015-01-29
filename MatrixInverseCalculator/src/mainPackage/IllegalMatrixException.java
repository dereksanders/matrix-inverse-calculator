package mainPackage;

/**
 * The Class IllegalMatrixException.
 * 
 * @author Derek Sanders
 * @version 1.0
 * @since December 5th, 2014
 */
public class IllegalMatrixException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new illegal matrix exception.
	 *
	 * @param message
	 *            the message
	 */
	public IllegalMatrixException(String message) {

		super(message);
	}
}