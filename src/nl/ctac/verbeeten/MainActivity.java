package nl.ctac.verbeeten;

import nl.ctac.verbeeten.domain.Model;
import nl.ctac.verbeeten.integration.zxing.IntentIntegrator;
import nl.ctac.verbeeten.integration.zxing.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	/** Tag for logging. */
	private static final String TAG = MainActivity.class.toString();

	// UI instance variables
	private Button scanBtn, checkBtn;
	private TextView formatTxt, contentTxt, barCodeTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO scanning
		// instantiate UI items
		scanBtn = (Button) findViewById(R.id.scan_button);
		checkBtn = (Button) findViewById(R.id.check_button);
		formatTxt = (TextView) findViewById(R.id.scan_format);
		contentTxt = (TextView) findViewById(R.id.scan_content);
		barCodeTxt = (TextView) findViewById(R.id.barcode);

		// listen for clicks
		scanBtn.setOnClickListener(this);

		checkBtn.setOnClickListener(this);

		Log.d(TAG, "Starting main activity...");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_news:
			news(null);
			return true;
		case R.id.action_agenda:
			agenda(null);
			return true;
		case R.id.action_notes:
			notes(null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void news(View view) {
		Intent intent = new Intent(MainActivity.this, NewsActivity.class);
		startActivity(intent);
	}

	public void notes(View view) {
		Intent intent = new Intent(MainActivity.this, NotesActivity.class);
		startActivity(intent);
	}

	public void agenda(View view) {
		Intent intent = new Intent(MainActivity.this, AgendaActivity.class);
		startActivity(intent);
	}

	// TODO scanning
	public void onClick(View v) {
		// check for scan button
		if (v.getId() == R.id.scan_button) {
			// instantiate ZXing integration class
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			// start scanning
			scanIntegrator.initiateScan();
		} else if (v.getId() == R.id.check_button) {
			// TODO implement; validate barcode with REST
			Model.getInstance().setBarCode(barCodeTxt.getText().toString());

			// TODO generate barcode
			Intent intent = new Intent(MainActivity.this, BarCodeActivity.class);
			startActivity(intent);
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		// check we have a valid result
		if (scanningResult != null) {
			// get content from Intent Result
			String scanContent = scanningResult.getContents();
			// get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
			// output to UI
			formatTxt.setText("FORMAT: " + scanFormat);
			contentTxt.setText("CONTENT: " + scanContent);

			barCodeTxt.setText(scanContent);

			// TODO fix barcode
			Model.getInstance().setBarCode(scanContent);
		} else {
			// invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(),
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
