package nl.ctac.verbeeten;
import nl.ctac.verbeeten.util.ConvertUtil;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Basic activity following template pattern.
 * 
 * @author sstar
 *
 */
public abstract class DataReceiverActivity extends Activity {

	// TODO check usage
	protected Boolean busy = Boolean.FALSE;

	/** Data receiver. */
	private DataReceiver dataReceiver = new DataReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		register();
	}
	
	private class DataReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String data = intent.getStringExtra(ConvertUtil.DATA_ELEMENT);

			processData(data);
		}
	}
	
	private void register() {
		IntentFilter filter = createIntent();
		this.registerReceiver(dataReceiver, filter);
	}
	
	/** 
	 * Create intent for listening to receive correct data.
	 * 
	 * @return intent filter
	 */
	protected abstract IntentFilter createIntent();

	/** 
	 * Process data data which is received.
	 * 
	 * @param data data
	 */
	protected abstract void processData(final String data);
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.findItem(R.id.action_busy);
		if (menuItem != null) {
			menuItem.setVisible(busy);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(dataReceiver);
	}

}