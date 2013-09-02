package nl.ctac.verbeeten.sort;

import java.util.Comparator;

import nl.ctac.verbeeten.domain.Note;

/**
 * Sorting a list of {@link Note}.
 * 
 * @author sstar
 * 
 */
public class NoteSort extends CommonSort implements Comparator<Note> {

	/** Constructor. */
	public NoteSort() {
	}

	@Override
	public int compare(Note lhs, Note rhs) {
		return compare(rhs.getDate(), lhs.getDate());
	}

}
