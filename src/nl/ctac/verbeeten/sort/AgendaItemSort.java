package nl.ctac.verbeeten.sort;

import java.util.Comparator;

import nl.ctac.verbeeten.domain.AgendaItem;

/**
 * Sorting a list of {@linkplain AgendaItem}.
 * 
 * @author sstar
 * 
 */
public class AgendaItemSort extends CommonSort implements
		Comparator<AgendaItem> {

	/** Constructor. */
	public AgendaItemSort() {
	}

	@Override
	public int compare(AgendaItem lhs, AgendaItem rhs) {
		return compare(rhs.getDate(), lhs.getDate());
	}

}
