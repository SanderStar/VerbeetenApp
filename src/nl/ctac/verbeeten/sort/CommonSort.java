package nl.ctac.verbeeten.sort;

import java.util.Date;

public class CommonSort {

	protected int compare(Date date, Date date2) {
		if (date != null) {
			return date.compareTo(date2);
		} else if (date2 == null) {
			return 0;
		} else {
			return 1;
		}
	}
}
