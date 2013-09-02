package nl.ctac.verbeeten;

import java.util.Collections;
import java.util.List;

import nl.ctac.verbeeten.adapter.AgendaAdapter;
import nl.ctac.verbeeten.domain.AgendaItem;
import nl.ctac.verbeeten.domain.Model;
import nl.ctac.verbeeten.domain.Preference;
import nl.ctac.verbeeten.service.DataTask;
import nl.ctac.verbeeten.sort.AgendaItemSort;
import nl.ctac.verbeeten.util.ConvertUtil;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AgendaActivity extends DataReceiverActivity {

	/** Tag for logging. */
	private static final String TAG = AgendaActivity.class.toString();

	private ListView listView;
	private ListAdapter agendaAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);

		listView = (ListView) findViewById(R.id.listView);
		if (listView == null) {
			Log.e(TAG, "Listview not found on component");
		}

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.agenda, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_add:
			// TODO add();
			invalidateOptionsMenu();
			return true;
		case R.id.action_back:
			finish();
			invalidateOptionsMenu();
			return true;
		case R.id.action_refresh:
			init();
			invalidateOptionsMenu();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void init() {
		busy = Boolean.TRUE;
		Preference preference = new Preference(Preference.AGENDA_ACTIVITY,
				Model.getInstance().getBarCode());
		preference.setAction(Preference.AGENDA_ACTION);
		new DataTask(this).execute(preference);
	}

	@Override
	protected IntentFilter createIntent() {
		return new IntentFilter(Preference.AGENDA_ACTIVITY);
	}

	@Override
	protected void processData(String data) {

		List<AgendaItem> agenda = ConvertUtil.convertAgenda(data);

		Collections.sort(agenda, new AgendaItemSort());

		agendaAdapter = new AgendaAdapter(getApplicationContext(),
				R.layout.list_view_row_agenda, agenda);
		listView.setAdapter(agendaAdapter);

		busy = Boolean.FALSE;
		invalidateOptionsMenu();
	}

}
