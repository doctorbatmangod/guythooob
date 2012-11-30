package com.stock.analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A static class designed solely to gather data from the Yahoo API.
 * @author Daniel Chiquito
 *
 */
public final class APIScraper {

	/**
	 * Represents an integer date as a two character string.
	 * @param day		An integer representing the day
	 * @return			The formatted string
	 */
	private static String formatDate(int day) {
		if (day < 10) {
			return "0"+day;
		}
		return day+"";
	}
	/**
	 * Query the Yahoo servers for stock data on a particular day.
	 * @param stock		The ticker symbol of the stock in question
	 * @param date		The date in question
	 * @return			A single DataPoint
	 * @throws IOException In case of disaster
	 */
	public static DataPoint query(String stock, Calendar date) throws IOException {
		try {
			return queryRange(stock, date, date).get(0);
		} catch (IOException e) {
			throw new IOException("Error downloading stock "+stock+" on date "+date.getTime());
		}
	}

	/**
	 * Query the Yahoo servers for a range of data points.
	 * @param stock		The ticker symbol of the stock in question
	 * @param from		The first date in the range
	 * @param to		The last date in the range
	 * @return			A List of DataPoints
	 * @throws IOException In case of disaster
	 */
	public static PointSequence queryRange(String stock, Calendar from, Calendar to) throws IOException {
		try {
			return query(stock, ""+from.get(Calendar.MONTH), formatDate(from.get(Calendar.DATE)), ""+from.get(Calendar.YEAR), ""+to.get(Calendar.MONTH), formatDate(to.get(Calendar.DATE)), ""+to.get(Calendar.YEAR));
		} catch (IOException e) {
			throw new IOException("Error downloading stock "+stock+" from "+from.getTime()+" to "+to.getTime());
		}
	}

	/**
	 * Query the Yahoo servers. This method is in charge of handling all the IO between client and server.
	 * @param stock			The ticker symbol of the stock in question
	 * @param fromMonth		The first month in the range
	 * @param fromDay		The first date in the range
	 * @param fromYear		The first year in the range
	 * @param toMonth		The last month in the range
	 * @param toDay			The last date in the range
	 * @param toYear		The last year in the range
	 * @return				A List of dataPoints
	 * @throws IOException  In case of disaster
	 */
	private static PointSequence query(String stock, String fromMonth, String fromDay, String fromYear, String toMonth, String toDay, String toYear) throws IOException  {
		/*
		s = TICKER
		a = fromMonth-1
		b = fromDay (two digits)
		c = fromYear
		d = toMonth-1
		e = toDay (two digits)
		f = toYear
		g = d for day, m for month, y for yearly
		 */
		URL url12  = new URL("http://ichart.finance.yahoo.com/table.csv?s="+stock+"&a="+fromMonth+"&b="+fromDay+"&c="+fromYear+"&d="+toMonth+"&e="+toDay+"&f="+toYear+"&g=d&&ignore=.csv");
		URLConnection urlConn = url12.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		BufferedReader buff= new BufferedReader(inStream);
		// The first line is just the names of the columns.
		//String head = buff.readLine();
		buff.readLine();
		PointSequence data = new PointSequence();
		String content = buff.readLine();
		while (content != null && !content.equals("")){
			data.add(0, new DataPoint(stock, content));
			content = buff.readLine();
		}
		return data;
	}


	/**
	 * Test dat code
	 * @param args	Does nothing
	 * @throws IOException In case of emergency
	 */
	public static void main(String[] args) throws IOException {
		Calendar date1 = Calendar.getInstance();
		date1.set(Calendar.DATE, 1);
		date1.set(Calendar.MONTH, 10);
		date1.set(Calendar.YEAR, 2012);
		Calendar date2 = Calendar.getInstance();
		date2.set(Calendar.DATE, 19);
		date2.set(Calendar.MONTH, 10);
		date2.set(Calendar.YEAR, 2012);
		//System.out.println(query("MSFT", date2));
		List<PointSequence> data = new ArrayList<PointSequence>();
		data.add(queryRange("MSFT", date1, date2));
		data.add(queryRange("YHOO", date1, date2));
		data.add(queryRange("APL", date1, date2));
		Grapher.showSimplePointSequences("Stock", data);
		Grapher.showHighLowPointSequence("APL",data.get(2));
		Grapher.showSimplePointSequence("MSFT", data.get(0));
	}
}
