package nl.ctac.verbeeten.service;

/**
 * Exception for backend error handling.
 * 
 * @author sstar
 *
 */
@SuppressWarnings("serial")
public class BackendServiceException extends Exception {

	/** 
	 * Constructor
	 * 
	 * @param message error message
 	 */
	public BackendServiceException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message error message
	 */
	public BackendServiceException(Throwable message) {
		super(message);
	}
}
