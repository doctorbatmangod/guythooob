package com.stock.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A sequence of DataPoints.
 * PointSequence extends ArrayList<DataPoint>, so it has all the functionality and efficiency of ArrayList.
 * It also includes some rudimentary statistical methods.
 * More will come as I learn how to implement them.
 * @author Daniel Chiquito
 *
 */
@SuppressWarnings("serial")
public final class PointSequence extends ArrayList<DataPoint>{
	
	/**
	 * Sequencify a List.
	 * Designed to be used by subList.
	 * @param subList	List to be Sequencified
	 */
	public PointSequence(List<DataPoint> subList) {
		super(subList);
	}
	/**
	 * Default constructor
	 */
	public PointSequence() {
		super();
	}
	
	/**
	 * @return		the highest-valued stock in the sequence
	 */
	public double max() {
		return Collections.max(this).repr;
	}
	
	/**
	 * @return		the lowest-valued stock in the sequence
	 */
	public double min() {
		return Collections.min(this).repr;
	}
	
	/**
	 * Calculates the average of the sequence
	 * @return		the average
	 */
	public double mean() {
		double sum = 0f;
		for (Iterator<DataPoint> iter = iterator(); iter.hasNext();) {
			sum += iter.next().repr;
		}
		return sum/size();
	}
	
	public double variance() {
		double av = mean();
		double sum = 0f;
		for (Iterator<DataPoint> iter = iterator(); iter.hasNext();) {
			double r = iter.next().repr;
			sum += (r-av)*(r-av);
		}
		return sum/size();
	}
	
	/**
	 * @param start		the index of the first DataPoint
	 * @param end		the index of the last DataPoint
	 * @return			a subSequence
	 */
	@Override
	public PointSequence subList(int start, int end) {		
		return new PointSequence(super.subList(start, end));
	}
	
	// ANOVA
	// PERCENT CHANGE
	// EULERS METHOD PERCENT CHANGE
	// MEAN
	// INTERPOLATION
}
