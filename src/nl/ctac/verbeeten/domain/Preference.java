package nl.ctac.verbeeten.domain;

/**
 * Preference domain object.
 * 
 * Preferences about the application. Settings for connecting to the backend
 * system.
 * 
 * @author sstar
 * 
 */
public class Preference {

	public final static String NEWS_ACTION = "news";

	public final static String NOTES_ACTION = "notes";
	
	public final static String NOTE_ADD_ACTION = "note";

	public final static String NOTE_UPDATE_ACTION = "note/%s";
	
	public static final String NOTE_DELETE_ACTION = "note/%s/%s";

	public final static String AGENDA_ACTION = "agenda";
	
	public final static String GET_METHOD = "GET";
	
	public final static String PUT_METHOD = "PUT";

	public static final String DELETE_METHOD = "DELETE";
	
	
	public static final String NEWS_ACTIVITY = NEWS_ACTION;
	
	public static final String AGENDA_ACTIVITY = AGENDA_ACTION;
	
	public static final String NOTES_ACTIVITY = NOTES_ACTION;
	
	public static final String NOTE_ACTIVITY = "note";
	
	
	/** Reference to host / backend server. */
	private String server;

	/** Reference to communication port of host / backend server. */
	private Long port;

	/** Use demo version. */
	private Boolean useDemo;
	
	/** Rest action. */
	private String action;

	// TODO redesign action vs. activity
	
	/** User activity. */
	private String activity;
	
	/** HTTP method. */
	private String method;
	
	/** HTTP body data. */
	private String bodyData;
	
	/** Barcode. */
	private String barCode;

	/** Constructor. */
	public Preference(String activity, String barCode) {
		// TODO fix
		useDemo = Boolean.TRUE;
		this.activity = activity;
		this.barCode = barCode;
	}

	/**
	 * Get server.
	 * 
	 * @return server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Set server.
	 * 
	 * @param server
	 *            server
	 */
	public void setServer(String server) {
		this.server = server;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBodyData() {
		return bodyData;
	}

	public void setBodyData(String bodyData) {
		this.bodyData = bodyData;
	}

	public Boolean getUseDemo() {
		return useDemo;
	}

	public void setUseDemo(Boolean useDemo) {
		this.useDemo = useDemo;
	}

	public String getActivity() {
		return activity;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String toString() {
		StringBuffer preference = new StringBuffer();
		preference.append(server);
		preference.append(" - ");
		preference.append(port);
		preference.append(" - ");
		preference.append(useDemo);
		preference.append(" - ");
		preference.append(action);
		preference.append(" - ");
		preference.append(method);
		preference.append(" - ");
		preference.append(bodyData);
		preference.append(" - ");
		preference.append(barCode);
		// Don't add password
		return preference.toString();
	}
}

