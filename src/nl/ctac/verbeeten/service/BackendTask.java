package nl.ctac.verbeeten.service;

import nl.ctac.verbeeten.domain.Preference;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Processing call to backend system in a asynchronous task.
 * 
 * @author sstar
 * 
 */
public class BackendTask extends AsyncTask<Preference, Integer, String[]> {

	/** Processing of data failed. */
	public static final String PROCESSING_FAILED = "FAILED";

	/** Processing of data succeeded. */
	public static final String PROCESSING_SUCCEEDED = "SUCCEEDED";

	/** Tag for logging. */
	private static final String TAG = BackendTask.class.toString();

	// TODO fix url http://CA001471.ctac.local:9999/service/
	/** URL to DEMO data. */
	private static final String DEMO_URL = "https://www.star4it.nl/verbeeten/rest/";

	@Override
	protected String[] doInBackground(Preference... params) {
		Log.d(TAG, "Processing request for " + params);

		publishProgress(1);

		String action = params[0].getAction();
		String method = params[0].getMethod();
		String data = params[0].getBodyData();
		String activity = params[0].getActivity();
		String barCode = params[0].getBarCode();
		
		String[] result = { PROCESSING_FAILED, activity, ""};

		assert (params != null) : "params is not filled";
		assert (params.length != 1) : "params length not 1";
		assert (!(params[0] instanceof Preference)) : "params not a preference object";

		try {
			BackendService service = new BackendService();

			service.execute(DEMO_URL + action,
					method,
					data,
					barCode,
					BackendService.JSON_FORMAT);

			String response = service.getResponse();
			result[0] = PROCESSING_SUCCEEDED;
			result[2] = response;
			
			if (response == null || "".equals(response)) {
				Log.d(TAG, "No data returned from server");
			}
			
			Log.d(TAG, "Request processed");
		} catch (BackendServiceException e) {
			// message details already logged
			String message = e.getMessage();
			Log.d(TAG, "Error from server. Details: " + message);
			result[1] = message;
		}

		return result;
	}

}
