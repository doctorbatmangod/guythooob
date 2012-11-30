package com.stock.analyzer;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;

/**
 * Static class for displaying compiled stock data.
 * Uses the JFreeChart library, which hopefully works on everyone's eclipse.
 * If not, find Daniel. He can set it up.
 * @author Daniel Chiquito
 *
 */
public class Grapher {
	/**
	 * Function for displaying a PointSequence.
	 * @param title		The name of the stock, generally. There could potentially be more than one stock in a PointSequence, so the preffered title must be specified manually.
	 * @param data		The PointSequence
	 */
	public static void showSimplePointSequence(String title, PointSequence data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (DataPoint dp: data) {
			dataset.addValue(dp.repr, dp.tickerSymbol, dp.date);
		}
		showBarGraph(title, "Date", "Value", dataset);
	}
	public static void showSimplePointSequences(String title, Iterable<PointSequence> data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (PointSequence seq: data) {
			for (DataPoint dp: seq) {
				dataset.addValue(dp.repr, dp.tickerSymbol, dp.date);
			}
		}
		showLineGraph(title, "Date", "Value",dataset);
	}
	public static void showPointSequences(String title, PointSequence[] data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (PointSequence seq: data) {
			for (DataPoint dp: seq) {
				dataset.addValue(dp.repr, dp.tickerSymbol, dp.date);
			}
		}
		showLineGraph(title, "Date", "Value",dataset);
	}
	
	public static void showHighLowPointSequence(String title, PointSequence data) {
		OHLCDataItem[] items = new OHLCDataItem[data.size()];
		int i=0;
		String tickerSymbol = "";
		for (DataPoint dp: data) {
			items[i++] = new OHLCDataItem(dp.date, dp.open, dp.high, dp.low, dp.close, dp.volume);
			tickerSymbol = dp.tickerSymbol;
		}
		DefaultOHLCDataset dataset = new DefaultOHLCDataset(tickerSymbol, items);
		showHighLowGraph(title, "Time", "Stock", dataset);
	}
	
	/**
	 * Function for displaying a more arbitrary bar graph of stock data.
	 * @param title					The title
	 * @param categoryAxisLabel		The Y axis label
	 * @param valueAxisLabel		The X axis label
	 * @param dataset				The JFreeChart implementation of a data set.
	 */
	public static void showBarGraph(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset) {
		ChartPanel chart = new ChartPanel(ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, true));
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(chart);
		window.setSize(600,400);
		window.setVisible(true);
	}
	
	/**
	 * Function for displaying a more arbitrary line graph of stock data.
	 * @param title					The title
	 * @param categoryAxisLabel		The Y axis label
	 * @param valueAxisLabel		The X axis label
	 * @param dataset				The JFreeChart implementation of a data set.
	 */
	public static void showLineGraph(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset) {
		ChartPanel chart = new ChartPanel(ChartFactory.createLineChart(title, categoryAxisLabel, valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, true));
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(chart);
		window.setSize(600,400);
		window.setVisible(true);
	}
	
	/**
	 * Function for displaying a more arbitrary High/Low graph of stock data.
	 * @param title					The title
	 * @param categoryAxisLabel		The Y axis label
	 * @param valueAxisLabel		The X axis label
	 * @param dataset				The JFreeChart implementation of a data set.
	 */
	public static void showHighLowGraph(String title, String categoryAxisLabel, String valueAxisLabel, OHLCDataset dataset) {
		ChartPanel chart = new ChartPanel(ChartFactory.createHighLowChart(title, categoryAxisLabel, valueAxisLabel, dataset, true));
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(chart);
		window.setSize(600,400);
		window.setVisible(true);
	}
}
