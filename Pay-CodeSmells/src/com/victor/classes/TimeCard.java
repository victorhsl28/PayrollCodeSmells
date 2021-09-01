package com.victor.classes;

import java.util.Calendar;
import java.util.Random;

public class TimeCard {
	
	private boolean completed;
	private Calendar arriveDate;
	private Calendar exitDate;
	private int workedHours;
	
	public TimeCard() {
		this.arriveDate = Calendar.getInstance();
		this.exitDate = null;
		this.workedHours = -1;
		this.completed = false;
	}
	
	public void closeTimecard() {
		this.exitDate = Calendar.getInstance();
		//long seconds = (exitDate.getTimeInMillis() - arriveDate.getTimeInMillis()) / 1000;
		//this.workedHours = (int) (seconds / 3600);
		this.workedHours = (new Random()).nextInt(10);
		this.completed = true;
	}
	
	public void print_info() {
		int day_arrive = this.arriveDate.get(Calendar.DAY_OF_MONTH);
		int month_arrive = this.arriveDate.get(Calendar.MONTH);
		int year_arrive = this.arriveDate.get(Calendar.YEAR);
		int hour_arrive = this.arriveDate.get(Calendar.HOUR_OF_DAY);
		int minute_arrive = this.arriveDate.get(Calendar.MINUTE);
		
		int day_exit = this.exitDate.get(Calendar.DAY_OF_MONTH);
		int month_exit = this.exitDate.get(Calendar.MONTH);
		int year_exit = this.exitDate.get(Calendar.YEAR);
		int hour_exit = this.exitDate.get(Calendar.HOUR_OF_DAY);
		int minute_exit = this.exitDate.get(Calendar.MINUTE);
		
		System.out.println("=======Timecard=======\n" +
							"Arrive: \n>Date: " + day_arrive + "/" + month_arrive + "/" + year_arrive + "\n" + 
							">Hour: " + hour_arrive + ":" + minute_arrive);
		
		System.out.println("Exit: \n>Date: " + day_exit + "/" + month_exit + "/" + year_exit + "\n" + 
				">Hour: " + hour_exit + ":" + minute_exit + "\n"
				+ "Total worked hour: " + this.workedHours + "\n======================");
	}
	
	public Calendar getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Calendar arriveDate) {
		this.arriveDate = arriveDate;
	}
	public Calendar getExitDate() {
		return exitDate;
	}
	public void setExitDate(Calendar exitDate) {
		this.exitDate = exitDate;
	}

	public int getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
