package com.victor.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.victor.actions.Action;
import com.victor.actions.StorageUndoData;
import com.victor.actions.Action.Event;
import com.victor.classes.SellResult;
import com.victor.classes.Syndicate;
import com.victor.classes.TimeCard;
import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Hourly;
import com.victor.employees.Salaried;
import com.victor.gui.MainGUI;

public class Main {
	
	public static Map<UUID, Employee> employees;
	public static Map<UUID, Syndicate> syndicate;
	public static List<String> paymentSchedules;
	public static Action lastAction;
	public static String nullUUID;

	public static void main(String[] args) {
		employees = new HashMap<UUID, Employee>();
		syndicate = new HashMap<UUID, Syndicate>();
		paymentSchedules = new ArrayList<String>();
		loadDefaultPaymentSchedule();
		nullUUID = new UUID(0, 0).toString();
		new MainGUI();
	}
	
	public static void roll() {
		Map<UUID, StorageUndoData> employeeMap = new HashMap<UUID, StorageUndoData>();
		//int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK); //1 sunday to 7 saturday
		//int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int dayOfMonth = 31;
		int dayOfWeek = 6;
		for(Employee employee : employees.values()) {
			try {
				String[] payment = employee.getPaymentSchedule().split(" ");
				int day = 0;
				if(payment[0].equalsIgnoreCase("mensal")) {
					if(payment[1].equalsIgnoreCase("$")) {
						day = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
					} else {
						day = Integer.valueOf(payment[1]);
					}
					
					if(day == dayOfMonth) {
						pay(employee, employeeMap);
					}
					
				} else if(payment[0].equalsIgnoreCase("semanal")){
					day = dayFromString(payment[2]);
					int totalWeeks = Integer.valueOf(payment[1]);
					if(day == dayOfWeek) {
						if(totalWeeks == employee.getWeeksCounter()) {
							pay(employee, employeeMap);
							employee.setWeeksCounter(1);
						} else {
							updateWeeksCounter(employee, employeeMap);
							employee.setWeeksCounter(employee.getWeeksCounter() + 1);
						}
					}
				} else {
					System.out.println("Error while paying employee " + employee.getUUID().toString() + "! Payment schedule is incorrect!");
				}
			} catch (Exception e) {
				System.out.println("Error while paying employee " + employee.getUUID().toString() + "!");
				e.printStackTrace();
			}
		}
		
		lastAction = new Action(null, null, null, employeeMap, Event.ROLL);
	}
	
	public static void pay(Employee employee, Map<UUID, StorageUndoData> employeeMap) {
		if(employee instanceof Hourly) {
			Hourly hourly = ((Hourly)employee);
			double salary = 0;
			for(TimeCard timecard : hourly.getTimecards()) {
				if(timecard.isCompleted()) {
					if(timecard.getWorkedHours() > 8) {
						int extra_hours = timecard.getWorkedHours() - 8;
						salary += 8 * hourly.getSalary();
						salary += extra_hours * hourly.getSalary() + (extra_hours * hourly.getSalary() * 0.5);
					} else {
						if(timecard.getWorkedHours() > 0) {
							salary += timecard.getWorkedHours() * hourly.getSalary();
						}
					}
				}
			}
			
			if(!hourly.getSyndicateUUID().toString().equalsIgnoreCase(nullUUID)) {
				Syndicate syndicateHourly = syndicate.get(hourly.getSyndicateUUID());
				salary -= syndicateHourly.getSyndicateTax();
				for(Double tax : syndicateHourly.getExtraTaxes()) {
					salary -= tax;
				}
			}
			
			employeeMap.put(employee.getUUID(), new StorageUndoData(((Hourly)employee)));
			hourly.getTimecards().clear();
			System.out.println("Payed " + salary + " via " + hourly.getPaymentMethod().toString() + " to employee " + hourly.getUUID().toString());
			
		} else if(employee instanceof Comissioned) {
			Comissioned comissioned = ((Comissioned)employee);
			double salary = comissioned.getSalary();
			for(SellResult sellResult : comissioned.getSellResults()) {
				salary += sellResult.getValue() * comissioned.getComissionedTax();
			}
			if(!comissioned.getSyndicateUUID().toString().equalsIgnoreCase(nullUUID)) {
				Syndicate syndicateComissioned = syndicate.get(comissioned.getSyndicateUUID());
				salary -= syndicateComissioned.getSyndicateTax();
				for(Double tax : syndicateComissioned.getExtraTaxes()) {
					salary -= tax;
				}
			}
			employeeMap.put(employee.getUUID(), new StorageUndoData(((Comissioned)employee)));
			comissioned.getSellResults().clear();
			System.out.println("Payed " + salary + " via " + comissioned.getPaymentMethod().toString() + " to employee " + comissioned.getUUID().toString());
			
		} else if(employee instanceof Salaried) {
			double salary = employee.getSalary();
			if(!employee.getSyndicateUUID().toString().equalsIgnoreCase(nullUUID)) {
				Syndicate syndicateSalaried = syndicate.get(employee.getSyndicateUUID());
				salary -= syndicateSalaried.getSyndicateTax();
				for(Double tax : syndicateSalaried.getExtraTaxes()) {
					salary -= tax;
				}
			}
			System.out.println("Payed " + salary + " via " + employee.getPaymentMethod().toString() + " to employee " + employee.getUUID().toString());
		}
	}
	
	public static void updateWeeksCounter(Employee employee, Map<UUID, StorageUndoData> employeeMap) {
		if(employee instanceof Hourly) {
			employeeMap.put(employee.getUUID(), new StorageUndoData(((Hourly)employee)));
		} else if(employee instanceof Comissioned) {
			employeeMap.put(employee.getUUID(), new StorageUndoData(((Comissioned)employee)));
		}
	}
	
	private static void loadDefaultPaymentSchedule() {
		paymentSchedules.add("semanal 1 sexta");
		paymentSchedules.add("semanal 2 sexta");
		paymentSchedules.add("mensal $");
	}
	
	private static int dayFromString(String string) {
		int day = 1;
		switch (string) {
		case "domingo":
			day = 1;
			break;
		case "segunda":
			day = 2;
			break;
		case "terca":
			day = 3;
			break;
		case "quarta":
			day = 4;
			break;
		case "quinta":
			day = 5;
			break;
		case "sexta":
			day = 6;
			break;
		case "sabado":
			day = 7;
			break;
		default:
			break;
		}
		
		return day;
	}

}
