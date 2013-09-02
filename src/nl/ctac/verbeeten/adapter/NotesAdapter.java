package nl.ctac.verbeeten.adapter;

import java.text.DateFormat;
import java.util.List;

import nl.ctac.verbeeten.R;
import nl.ctac.verbeeten.domain.Note;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesAdapter extends ArrayAdapter<Note> {

	private LayoutInflater inflater;
	private int resource;

	public NotesAdapter(Context context, int resource, List<Note> objects) {
		super(context, resource, objects);
		this.inflater = LayoutInflater.from(context);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Create a new view of my layout and inflate it in the row
		convertView = (LinearLayout) inflater.inflate(resource, null);

		Note note = getItem(position);

		TextView txtId = (TextView) convertView.findViewById(R.id.id);
		if (txtId != null) {
			txtId.setText("" + note.getId());
		}

		TextView txtName = (TextView) convertView
				.findViewById(R.id.description);
		if (txtName != null) {
			txtName.setText(note.getDescription());
		}

		TextView txtDate = (TextView) convertView.findViewById(R.id.date);
		if (txtDate != null && note.getDate() != null) {
			DateFormat df = DateFormat.getDateInstance();
			txtDate.setText(df.format(note.getDate()));
		}

		return convertView;
	}

}
