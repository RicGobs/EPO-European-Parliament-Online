package com.euparliament.rest.referendum;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

class ReferendumDateStartComparator implements Comparator<Referendum> {

	@Override
	public int compare(Referendum r1, Referendum r2) {
		String pattern = "dd/MM/yyyy HH:mm:ss";
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(pattern);
		int result = 0;
		try {
			cal1.setTime(df.parse(r1.getId().getDateStartConsensusProposal()));
			cal2.setTime(df.parse(r2.getId().getDateStartConsensusProposal()));
			result = cal1.compareTo(cal2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}