package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;


public class WeatherAlert extends RPCStruct {

	public static final String KEY_TITLE = "title";
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_EXPIRES = "expires";
	public static final String KEY_REGIONS = "regions";
	public static final String KEY_SEVERITY = "severity";
	public static final String KEY_TIME_ISSUED = "timeIssued";

	// Constructors

	public WeatherAlert() { }

	public WeatherAlert(Hashtable<String, Object> hash) {
		super(hash);
	}

	public WeatherAlert(@NonNull List<String> regions) {
		this();
		setRegions(regions);
	}

	// Setters and Getters

	/**
	 * @param title -
	 */
	public void setTitle(String title) {
		setValue(KEY_TITLE, title);
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return getString(KEY_TITLE);
	}

	/**
	 * @param summary -
	 */
	public void setSummary(String summary) {
		setValue(KEY_SUMMARY, summary);
	}

	/**
	 * @return summary
	 */
	public String getSummary() {
		return getString(KEY_SUMMARY);
	}

	/**
	 * @param severity -
	 */
	public void setSeverity(String severity) {
		setValue(KEY_SEVERITY, severity);
	}

	/**
	 * @return severity
	 */
	public String getSeverity() {
		return getString(KEY_SEVERITY);
	}

	/**
	 * Min Size: 1, Max Size: 99
	 * @param regions -
	 */
	public void setRegions(List<String> regions) {
		setValue(KEY_REGIONS, regions);
	}

	/**
	 * @return regions list
	 */
	@SuppressWarnings("unchecked")
	public List<String> getRegions(){
		return (List<String>) getObject(String.class,KEY_REGIONS);
	}

	/**
	 * @param expires -
	 */
	public void setExpires(DateTime expires) {
		setValue(KEY_EXPIRES, expires);
	}

	/**
	 * @return expires
	 */
	public DateTime getExpires() {
		return (DateTime) getObject(DateTime.class,KEY_EXPIRES);
	}

	/**
	 * @param timeIssued -
	 */
	public void setTimeIssued(DateTime timeIssued) {
		setValue(KEY_TIME_ISSUED, timeIssued);
	}

	/**
	 * @return timeIssued
	 */
	public DateTime getTimeIssued() {
		return (DateTime) getObject(DateTime.class,KEY_TIME_ISSUED);
	}

}
