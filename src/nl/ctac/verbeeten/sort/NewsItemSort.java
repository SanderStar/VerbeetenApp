package nl.ctac.verbeeten.sort;

import java.util.Comparator;

import nl.ctac.verbeeten.domain.NewsItem;

/**
 * Sorting a list of {@link NewsItem}.
 * 
 * @author sstar
 * 
 */
public class NewsItemSort extends CommonSort implements Comparator<NewsItem> {

	/** Constructor. */
	public NewsItemSort() {
	}

	@Override
	public int compare(NewsItem lhs, NewsItem rhs) {
		// First sort on type
		int type = lhs.getType().compareTo(rhs.getType());
		if (type == 0) {
			// Type is equal sort on date
			return compare(rhs.getDate(), lhs.getDate());
		}
		return type;
	}

}
