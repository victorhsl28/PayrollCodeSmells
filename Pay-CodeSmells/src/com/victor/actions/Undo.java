package com.victor.actions;

import java.util.UUID;

import com.victor.classes.SellResult;
import com.victor.classes.Syndicate;
import com.victor.classes.TimeCard;
import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Hourly;
import com.victor.employees.Salaried;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class Undo {
	
	public static void undo() {
		Action action = Main.lastAction;
		if(action != null) {
			Employee employee = action.getEmployee();
			switch (action.getEvent()) {
			case ADD_EMPLOYEE:
				if(!employee.getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
					Main.syndicate.remove(employee.getSyndicateUUID());
				}
				Main.employees.remove(employee.getUUID());
				ShowDialogMessage.showMessage("Success!", "Employee " + employee.getUUID().toString() + " has been removed!");
				Main.lastAction = null;
				break;
				
			case REMOVE_EMPLOYEE:
				if(employee instanceof Hourly) {
					Main.employees.put(employee.getUUID(), new Hourly(employee.getUUID(), employee.getName(), employee.getAdress(), employee.getSalary(), employee.getPaymentMethod(), employee.getPaymentSchedule(), employee.getSyndicateUUID()));
					for(TimeCard timecard : ((Hourly) employee).getTimecards()) {
						((Hourly)Main.employees.get(employee.getUUID())).getTimecards().add(timecard);
					}
				} else if(employee instanceof Salaried) {
					Main.employees.put(employee.getUUID(), new Salaried(employee.getUUID(), employee.getName(), employee.getAdress(), employee.getSalary(), employee.getPaymentMethod(), employee.getPaymentSchedule(), employee.getSyndicateUUID()));
				} else if(employee instanceof Comissioned) {
					Main.employees.put(employee.getUUID(), new Comissioned(employee.getUUID(), employee.getName(), employee.getAdress(), employee.getSalary(), employee.getPaymentMethod(), employee.getPaymentSchedule(), employee.getSyndicateUUID(), ((Comissioned)employee).getComissionedTax()));
					for(SellResult sellResult : ((Comissioned) employee).getSellResults()) {
						((Comissioned)Main.employees.get(employee.getUUID())).getSellResults().add(sellResult);
					}
				}
				if(!employee.getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
					Main.syndicate.put(employee.getSyndicateUUID(), new Syndicate(employee.getSyndicateUUID(), action.getSyndicate().getSyndicateTax()));
					for(Double tax : action.getSyndicate().getExtraTaxes()) {
						Main.syndicate.get(employee.getSyndicateUUID()).getExtraTaxes().add(tax);
					}
				}
				ShowDialogMessage.showMessage("Success!", "Employee " + employee.getName() + " has been created with the ID: " + employee.getUUID().toString() + "!");
				Main.lastAction = null;
				break;
				
			case CREATE_TIMECARD:
				if(employee instanceof Hourly) {
					Hourly hourly = ((Hourly)employee);
					hourly.getTimecards().remove(hourly.getTimecards().get(hourly.getTimecards().size() - 1));
					ShowDialogMessage.showMessage("Success!", "Last timecard removed!");
					Main.lastAction = null;
					hourly.printTimeCards();
				} else {
					ShowDialogMessage.showMessage("Fail!", "Last employee was not hourly! Could not undo the action!");
					Main.lastAction = null;
				}
				break;
				
			case CREATE_SELL_RESULT:
				if(employee instanceof Comissioned) {
					Comissioned comissioned = ((Comissioned)employee);
					comissioned.getSellResults().remove(comissioned.getSellResults().get(comissioned.getSellResults().size() - 1));
					ShowDialogMessage.showMessage("Success!", "Last sell result removed!");
					Main.lastAction = null;
					comissioned.printSellResults();
				} else {
					ShowDialogMessage.showMessage("Fail!", "Last employee was not comissioned! Could not undo the action!");
					Main.lastAction = null;
				}
				break;
			
			case CREATE_SERVICE_TAX:
				if(Main.syndicate.containsValue(action.getSyndicate())) {
					Syndicate syndicate = action.getSyndicate();
					syndicate.getExtraTaxes().remove(syndicate.getExtraTaxes().get(syndicate.getExtraTaxes().size() - 1));
					ShowDialogMessage.showMessage("Success!", "Last service tax removed!");
					Main.lastAction = null;
					syndicate.print_info();
				} else {
					ShowDialogMessage.showMessage("Fail!", "Last employee was not on syndicate! Could not undo the action!");
					Main.lastAction = null;
				}
				break;
			
			case CHANGE_EMPLOYEE_INFO:
				Employee oldEmployee = action.getOldEmployee();
				if(!employee.getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
					Main.syndicate.remove(employee.getSyndicateUUID());
				}
				Main.employees.remove(employee.getUUID());
				if(oldEmployee instanceof Hourly) {
					Main.employees.put(oldEmployee.getUUID(), new Hourly(oldEmployee.getUUID(), oldEmployee.getName(), oldEmployee.getAdress(), oldEmployee.getSalary(), oldEmployee.getPaymentMethod(), oldEmployee.getPaymentSchedule(), oldEmployee.getSyndicateUUID()));
					for(TimeCard timecard : ((Hourly) oldEmployee).getTimecards()) {
						((Hourly)Main.employees.get(employee.getUUID())).getTimecards().add(timecard);
					}
				} else if(oldEmployee instanceof Salaried) {
					Main.employees.put(oldEmployee.getUUID(), new Salaried(oldEmployee.getUUID(), oldEmployee.getName(), oldEmployee.getAdress(), oldEmployee.getSalary(), oldEmployee.getPaymentMethod(), oldEmployee.getPaymentSchedule(), oldEmployee.getSyndicateUUID()));
				} else if(oldEmployee instanceof Comissioned) {
					Main.employees.put(oldEmployee.getUUID(), new Comissioned(oldEmployee.getUUID(), oldEmployee.getName(), oldEmployee.getAdress(), oldEmployee.getSalary(), oldEmployee.getPaymentMethod(), oldEmployee.getPaymentSchedule(), oldEmployee.getSyndicateUUID(), ((Comissioned)oldEmployee).getComissionedTax()));
					for(SellResult sellResult : ((Comissioned) oldEmployee).getSellResults()) {
						((Comissioned)Main.employees.get(oldEmployee.getUUID())).getSellResults().add(sellResult);
					}
				}
				if(!oldEmployee.getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
					Main.syndicate.put(oldEmployee.getSyndicateUUID(), new Syndicate(oldEmployee.getSyndicateUUID(), action.getSyndicate().getSyndicateTax()));
					for(Double tax : action.getSyndicate().getExtraTaxes()) {
						Main.syndicate.get(oldEmployee.getSyndicateUUID()).getExtraTaxes().add(tax);
					}
				}
				ShowDialogMessage.showMessage("Success!", "Employee was rolled back!");
				Main.lastAction = null;
				break;
			
			case ROLL:
				for(UUID uuid : action.getStorageMap().keySet()) {
					Employee employees = Main.employees.get(uuid);
					if(employees instanceof Hourly) {
						((Hourly)employees).setTimecards(action.getStorageMap().get(uuid).getListTimeCard());
						employees.setWeeksCounter(action.getStorageMap().get(uuid).getWeeksCounter());
					} else if(employees instanceof Comissioned) {
						Main.employees.put(employees.getUUID(), ((Comissioned)employees));
						((Comissioned)employees).setSellResults(action.getStorageMap().get(uuid).getListSellResult());
						employees.setWeeksCounter(action.getStorageMap().get(uuid).getWeeksCounter());
					}					
				}
				ShowDialogMessage.showMessage("Success!", "Payroll was rolled back!");
				Main.lastAction = null;
				break;
				
			default:
				break;
			}
		} else {
			ShowDialogMessage.showMessage("Fail!", "There is not last action!");
		}
	}

}
