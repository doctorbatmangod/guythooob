package com.stock.analyzer;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Daniel Chiquito
 * 
 */
public class DataPoint implements Comparable<DataPoint> {
	public final String tickerSymbol;
	public final Date date;
	public final double open;
	public final double high;
	public final double low;
	public final double close;
	public final long volume;
	/** I dunno what this is. Yahoo provides it though.*/
	public final double adjClose;
	
	/**
	 * The value that the DataPoint will default to for statistical purposes.
	 * For example, when calculating a mean, repr is used.
	 */
	public final double repr;
	
	/**
	 * Initialize using a raw string passed from the Yahoo CSV.
	 * @param tickerSymbol		the name of the stock
	 * @param chronData			the raw data
	 */
	public DataPoint(String tickerSymbol, String chronData) {
		this.tickerSymbol = tickerSymbol;
		String[] data = chronData.substring(0,chronData.length()-1).split(",");
		String[] datearray = data[0].split("-");
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.clear();
		dateCalendar.set(Calendar.YEAR, Integer.parseInt(datearray[0]));
		dateCalendar.set(Calendar.MONTH, Integer.parseInt(datearray[1]));
		dateCalendar.set(Calendar.DATE, Integer.parseInt(datearray[2]));
		date = dateCalendar.getTime();
		open = Double.parseDouble(data[1]);
		high = Double.parseDouble(data[2]);
		low = Double.parseDouble(data[3]);
		close = Double.parseDouble(data[4]);
		volume = Long.parseLong(data[5]);
		adjClose = Double.parseDouble(data[6]);
		
		repr = (double)(high+low)/2f;
	}
	
	/**
	 * It's a toString. Deal with it.
	 */
	public String toString() {
		return tickerSymbol+"("+date+"):"+repr;
	}
	
	/**
	 * For comparing two DataPoints
	 */
	@Override
	public int compareTo(DataPoint dp) {
		if (repr-dp.repr < 0) {
			return -1;
		} else if (repr-dp.repr > 0) {
			return 1;
		}
		return 0;
	}
}
