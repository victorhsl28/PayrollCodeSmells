package com.victor.classes;

import java.util.Calendar;

public class SellResult {
	
	private Calendar date;
	private int value;
	
	public SellResult(int value) {
		this.date = Calendar.getInstance();
		this.value = value;
	}
	
	public void print_info() {
		int minute = date.get(Calendar.MINUTE);
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH) + 1;
		int year = date.get(Calendar.YEAR);
		String date = "Time: " + minute + ":" + hour + "\nDay: " + day + ":" + month + ":" + year + "\n";
		System.out.println("Sell Result\n" +
							"Date: " + date +
							"Value: " + value);
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	

}
