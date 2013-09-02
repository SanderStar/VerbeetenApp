package nl.ctac.verbeeten.service;

import nl.ctac.verbeeten.LoadingActivity;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class DataTask extends BackendTask {

	/** Current activity for task. */
	private final Activity activity;

	/**
	 * Constructor.
	 * 
	 * @param activity
	 *            current activity
	 */
	public DataTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPostExecute(String[] result) {

		// TODO fix; error handling, etc, etc

		// Result should be an array of 3 elements
		if (result != null && result.length == 3) {
			if (BackendTask.PROCESSING_SUCCEEDED.equals(result[0])) {

				Intent intent = new Intent(result[1]);
				intent.putExtra("data", result[2]);

				activity.sendBroadcast(intent);
			} else {
				Toast.makeText(this.activity.getApplicationContext(),
						"error " + result[1], Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this.activity.getApplicationContext(),
					"unknown error", Toast.LENGTH_LONG).show();
		}

		this.activity.finishActivity(LoadingActivity.DEFAULT_REQUEST_CODE);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		// TODO don't show progress this way
		/*
		Intent intent = new Intent(this.activity, LoadingActivity.class);
		this.activity.startActivityForResult(intent,
				LoadingActivity.DEFAULT_REQUEST_CODE);
		*/
	}

}