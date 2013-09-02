package nl.ctac.verbeeten.domain;


public class Model {

	/** Reference to instance self (singleton). */
	private static Model instance;
	
	/** User identification. */
	private String barCode;
	
	/**
	 * Create instance of self.
	 * 
	 * @return instance self
	 */
	public static synchronized Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	

}
