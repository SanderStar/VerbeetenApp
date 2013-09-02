package nl.ctac.verbeeten;

import android.app.Activity;
import android.os.Bundle;

public class LoadingActivity extends Activity {

	/** Default request code for starting and finishing this activity. */
	public static int DEFAULT_REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
	}

}
