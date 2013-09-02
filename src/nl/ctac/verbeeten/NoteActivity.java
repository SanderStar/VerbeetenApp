package nl.ctac.verbeeten;

import java.text.DateFormat;

import nl.ctac.verbeeten.domain.Model;
import nl.ctac.verbeeten.domain.Note;
import nl.ctac.verbeeten.domain.Preference;
import nl.ctac.verbeeten.service.DataTask;
import nl.ctac.verbeeten.util.ConvertUtil;
import nl.ctac.verbeeten.util.DateUtil;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteActivity extends DataReceiverActivity {

	/** Tag for logging. */
	private static final String TAG = NoteActivity.class.toString();

	private EditText editTextId;
	private EditText editTextVersion;
	private EditText editTextDescription;
	private EditText editTextDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);

		// TODO check if edittexts exists
		editTextId = (EditText) findViewById(R.id.editTextId);
		editTextVersion = (EditText) findViewById(R.id.editTextVersion);
		editTextDescription = (EditText) findViewById(R.id.editTextDescription);
		editTextDate = (EditText) findViewById(R.id.editTextDate);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Object obj = bundle.get(Preference.NOTE_ACTIVITY);
			if (obj instanceof Note) {
				Note note = (Note) obj;

				editTextId.setText("" + note.getId());
				editTextVersion.setText("" + note.getVersion());
				editTextDescription.setText(note.getDescription());
				DateFormat df = DateUtil.getDateFormat();
				if (note.getDate() != null) {
					editTextDate.setText(df.format(note.getDate()));
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_save:
			save();
			invalidateOptionsMenu();
			return true;
		case R.id.action_delete:
			delete();
			invalidateOptionsMenu();
			return true;
		case R.id.action_back:
			cancel();
			invalidateOptionsMenu();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void cancel() {
		busy = Boolean.TRUE;

		finish();
	}

	private void delete() {
		busy = Boolean.TRUE;

		Preference preference = new Preference(Preference.NOTE_ACTIVITY, Model
				.getInstance().getBarCode());
		String action = String
				.format(Preference.NOTE_DELETE_ACTION, new Object[] {
						editTextId.getText(), editTextVersion.getText() });
		preference.setAction(action);
		preference.setMethod(Preference.DELETE_METHOD);
		new DataTask(this).execute(preference);
	}

	private void save() {
		busy = Boolean.TRUE;

		String id = editTextId.getText().toString();
		String version = editTextVersion.getText().toString();
		String description = editTextDescription.getText().toString();
		String date = editTextDate.getText().toString();

		// TODO validate note

		String data = ConvertUtil.convertNote(id, version, description, date);

		Preference preference = new Preference(Preference.NOTE_ACTIVITY, Model
				.getInstance().getBarCode());
		if (id != null && !"".equals(id)) {
			String action = String.format(Preference.NOTE_UPDATE_ACTION, id);
			preference.setAction(action);
		} else {
			preference.setAction(Preference.NOTE_ADD_ACTION);
		}
		preference.setMethod(Preference.PUT_METHOD);
		preference.setBodyData(data);
		new DataTask(this).execute(preference);
	}

	@Override
	protected IntentFilter createIntent() {
		return new IntentFilter(Preference.NOTE_ACTIVITY);
	}

	@Override
	protected void processData(String data) {
		busy = Boolean.FALSE;

		invalidateOptionsMenu();

		finish();
	}

}
