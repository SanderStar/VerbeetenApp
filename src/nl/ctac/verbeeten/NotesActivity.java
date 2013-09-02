package nl.ctac.verbeeten;

import java.util.Collections;
import java.util.List;

import nl.ctac.verbeeten.adapter.NotesAdapter;
import nl.ctac.verbeeten.domain.Model;
import nl.ctac.verbeeten.domain.Note;
import nl.ctac.verbeeten.domain.Preference;
import nl.ctac.verbeeten.service.DataTask;
import nl.ctac.verbeeten.sort.NoteSort;
import nl.ctac.verbeeten.util.ConvertUtil;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NotesActivity extends DataReceiverActivity {

	/** Tag for logging. */
	private static final String TAG = NotesActivity.class.toString();

	private ListView listView;
	private ListAdapter notesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		listView = (ListView) findViewById(R.id.listView);
		if (listView != null) {
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Object obj = parent.getAdapter().getItem(position);
					if (obj instanceof Note) {
						Note note = (Note) obj;

						Intent intent = new Intent(NotesActivity.this,
								NoteActivity.class);
						intent.putExtra(Preference.NOTE_ACTIVITY, note);
						startActivity(intent);
					} else {
						Log.e(TAG, "Wrong object found " + obj);
					}
				}
			});
		} else {
			Log.e(TAG, "Listview not found on component");
		}

		init();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// TODO refresh data; not always needed when back is done in
		// noteactivtiy
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_add:
			add();
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
		Preference preference = new Preference(Preference.NOTES_ACTIVITY,
				Model.getInstance().getBarCode());
		preference.setAction(Preference.NOTES_ACTION);
		new DataTask(this).execute(preference);
	}

	public void add() {
		Intent intent = new Intent(NotesActivity.this, NoteActivity.class);
		startActivity(intent);
	}

	@Override
	protected void processData(final String data) {
		List<Note> notes = ConvertUtil.convertNotes(data);

		Collections.sort(notes, new NoteSort());
		
		notesAdapter = new NotesAdapter(getApplicationContext(),
				R.layout.list_view_row_notes, notes);
		listView.setAdapter(notesAdapter);

		busy = Boolean.FALSE;
		invalidateOptionsMenu();
	}


	@Override
	protected IntentFilter createIntent() {
		return new IntentFilter(Preference.NOTES_ACTION);
	}

}
