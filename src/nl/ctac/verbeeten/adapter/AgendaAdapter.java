package nl.ctac.verbeeten.adapter;

import java.text.DateFormat;
import java.util.List;

import nl.ctac.verbeeten.R;
import nl.ctac.verbeeten.domain.AgendaItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AgendaAdapter extends ArrayAdapter<AgendaItem> {

	private LayoutInflater inflater;
	private int resource;

	public AgendaAdapter(Context context, int resource, List<AgendaItem> objects) {
		super(context, resource, objects);
		this.inflater = LayoutInflater.from(context);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Create a new view of my layout and inflate it in the row
		convertView = (LinearLayout) inflater.inflate(resource, null);

		AgendaItem agendaItem = getItem(position);

		TextView txtId = (TextView) convertView.findViewById(R.id.id);
		if (txtId != null) {
			txtId.setText("" + agendaItem.getId());
		}

		TextView txtName = (TextView) convertView
				.findViewById(R.id.description);
		if (txtName != null) {
			txtName.setText(agendaItem.getDescription());
		}

		TextView txtLocation = (TextView) convertView
				.findViewById(R.id.location);
		if (txtLocation != null) {
			txtLocation.setText(agendaItem.getLocation());
		}

		TextView txtPerson = (TextView) convertView.findViewById(R.id.person);
		if (txtPerson != null) {
			txtPerson.setText(agendaItem.getPerson());
		}

		TextView txtDate = (TextView) convertView.findViewById(R.id.date);
		if (txtDate != null && agendaItem.getDate() != null) {
			DateFormat df = DateFormat.getDateInstance();
			txtDate.setText(df.format(agendaItem.getDate()));
		}

		return convertView;
	}

}
