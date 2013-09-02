package nl.ctac.verbeeten.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nl.ctac.verbeeten.domain.AgendaItem;
import nl.ctac.verbeeten.domain.NewsItem;
import nl.ctac.verbeeten.domain.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ConvertUtil {

	public static final String DATA_ELEMENT = "data";

	/** Tag for logging. */
	private static final String TAG = ConvertUtil.class.toString();

	public static List<NewsItem> convertNews(String data) {

		List<NewsItem> news = new ArrayList<NewsItem>();

		try {
			JSONArray objects = new JSONArray(data);
			int length = objects.length();

			for (int i = 0; i < length; i++) {
				JSONObject object = objects.getJSONObject(i);
				Long id = object.getLong("id");
				Long version = object.getLong("version");
				String type = object.getString("type");
				String description = object.getString("description");
				String d = object.getString("date");

				NewsItem newsItem = new NewsItem();
				newsItem.setId(id);
				newsItem.setVersion(version);
				newsItem.setType(type);
				newsItem.setDescription(description);
				try {
					if (d != null && !"".equals(d)) {
						DateFormat df = DateUtil.getDateTimeFormat();
						newsItem.setDate(df.parse(d));
					}
				} catch (ParseException e) {
					// TODO exception handling toast error
					Log.e(TAG, e.getMessage(), e);
					// FIXME toasting
					/*
					 * Toast.makeText(getApplicationContext(), "Error " +
					 * e.getMessage(), Toast.LENGTH_LONG) .show();
					 */
				}

				news.add(newsItem);
			}

			return news;
			
		} catch (JSONException e) {
			// TODO exception handling toast error
			Log.e(TAG, e.getMessage(), e);
			// FIXME toasting
			/*
			 * Toast.makeText(getApplicationContext(), "Error " +
			 * e.getMessage(), Toast.LENGTH_LONG) .show();
			 */
		}
		
		return new ArrayList<NewsItem>();
	}

	public static List<AgendaItem> convertAgenda(String data) {
		List<AgendaItem> agenda = new ArrayList<AgendaItem>();

		try {
			JSONArray objects = new JSONArray(data);
			int length = objects.length();

			for (int i = 0; i < length; i++) {
				JSONObject object = objects.getJSONObject(i);
				Long id = object.getLong("id");
				Long version = object.getLong("version");
				String description = object.getString("description");
				String location = object.getString("location");
				String person = object.getString("person");
				String d = object.getString("date");

				AgendaItem agendaItem = new AgendaItem();
				agendaItem.setId(id);
				agendaItem.setVersion(version);
				agendaItem.setDescription(description);
				agendaItem.setLocation(location);
				agendaItem.setPerson(person);
				try {
					if (d != null && !"".equals(d)) {
						DateFormat df = DateUtil.getDateTimeFormat();
						agendaItem.setDate(df.parse(d));
					}
				} catch (ParseException e) {
					// TODO exception handling toast error
					Log.e(TAG, e.getMessage(), e);
					// FIXME toasting
					/*
					 * Toast.makeText(getApplicationContext(), "Error " +
					 * e.getMessage(), Toast.LENGTH_LONG) .show();
					 */
				}

				agenda.add(agendaItem);
			}

			return agenda;
			
		} catch (JSONException e) {
			// TODO exception handling toast error
			Log.e(TAG, e.getMessage(), e);
			// FIXME toasting
			/*
			 * Toast.makeText(getApplicationContext(), "Error " +
			 * e.getMessage(), Toast.LENGTH_LONG) .show();
			 */
		}

		return new ArrayList<AgendaItem>();
	}

	public static List<Note> convertNotes(String data) {

		List<Note> notes = new ArrayList<Note>();

		try {
			JSONArray objects = new JSONArray(data);
			int length = objects.length();

			for (int i = 0; i < length; i++) {
				JSONObject object = objects.getJSONObject(i);
				Long id = object.getLong("id");
				Long version = object.getLong("version");
				String description = object.getString("description");
				String d = object.getString("date");

				Note note = new Note();
				note.setId(id);
				note.setVersion(version);
				note.setDescription(description);
				try {
					// value can be null or "" -> parse exception occurs
					if (d != null && !"".equals(d)) {
						DateFormat df = DateUtil.getDateTimeFormat();
						note.setDate(df.parse(d));
					}
				} catch (ParseException e) {
					// TODO exception handling toast error
					Log.e(TAG, e.getMessage(), e);
					// FIXME toasting
					/*
					 * Toast.makeText(getApplicationContext(), "Error " +
					 * e.getMessage(), Toast.LENGTH_LONG) .show();
					 */
				}

				notes.add(note);
			}

			return notes;

		} catch (JSONException e) {
			// TODO exception handling toast error
			Log.e(TAG, e.getMessage(), e);
			// FIXME toasting
			/*
			 * Toast.makeText(getApplicationContext(), "Error " +
			 * e.getMessage(), Toast.LENGTH_LONG) .show();
			 */
		}

		return new ArrayList<Note>();
	}

	/**
	 * Create a new {@link Note}.
	 * 
	 * @param id
	 *            unique identification of note
	 * @param version
	 *            version of note
	 * @param description
	 *            description of note
	 * @param date
	 *            date of note
	 * @return {@link Note}
	 */
	private static Note createNote(String id, String version,
			String description, String date) {
		Note note = new Note();
		if (id != null && !"".equals(id)) {
			// update
			// TODO conversion
			note.setId(Long.valueOf(id));
			// TODO conversion
			note.setVersion(Long.valueOf(version));
		}
		note.setDescription(description);
		try {
			if (date != null && !"".equals(date)) {
				DateFormat df = DateUtil.getDateFormat();
				note.setDate(df.parse(date));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}

	/**
	 * Convert note to JSON string. The id of the note should NOT be included in
	 * the JSON string.
	 * 
	 * @param id
	 *            unique identification of note
	 * @param version
	 *            version of note
	 * @param description
	 *            description of note
	 * @param date
	 *            date of note
	 * @return JSON string of note
	 */
	public static String convertNote(String id, String version,
			String description, String date) {

		Note note = createNote(id, version, description, date);

		// TODO validate note?

		JSONObject object = new JSONObject();
		try {
			object.put("id", note.getId());
			object.put("description", note.getDescription());
			if (note.getDate() != null) {
				DateFormat dfs = DateUtil.getDateTimeFormat();
				object.put("date", dfs.format(note.getDate()));
			}
			object.put("version", note.getVersion());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}

}
