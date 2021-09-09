package com.victor.actions;

import com.victor.classes.SellResult;
import com.victor.classes.Syndicate;
import com.victor.classes.TimeCard;
import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Hourly;
import com.victor.employees.Salaried;
import com.victor.gui.RemoveGUI;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class RemoveEmployeeAction implements Action {
	
	Employee employee;
	Syndicate syndicate;
	
	public RemoveEmployeeAction(Employee employee, Syndicate syndicate) {
		this.employee = employee;
		this.syndicate = syndicate;
	}
	
	public void undo() {
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
			Main.syndicate.put(employee.getSyndicateUUID(), new Syndicate(employee.getSyndicateUUID(), syndicate.getSyndicateTax()));
			for(Double tax : syndicate.getExtraTaxes()) {
				Main.syndicate.get(employee.getSyndicateUUID()).getExtraTaxes().add(tax);
			}
		}
		ShowDialogMessage.showMessage("Success!", "Employee " + employee.getName() + " has been created with the ID: " + employee.getUUID().toString() + "!", false, null);
	}
	
	public void redo() {
		new RemoveGUI();
	}

}
