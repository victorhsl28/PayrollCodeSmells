package com.victor.actions;

import com.victor.classes.SellResult;
import com.victor.classes.Syndicate;
import com.victor.classes.TimeCard;
import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Hourly;
import com.victor.employees.Salaried;
import com.victor.gui.ChangeInfoGUI;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class ChangeEmployeeInfoAction implements Action{
	
	Employee employee;
	Employee oldEmployee;
	Syndicate syndicate;
	
	public ChangeEmployeeInfoAction(Employee employee, Employee oldEmployee, Syndicate syndicate) {
		this.employee = employee;
		this.oldEmployee = oldEmployee;
		this.syndicate = syndicate;
	}
	
	public void undo() {
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
			Main.syndicate.put(oldEmployee.getSyndicateUUID(), new Syndicate(oldEmployee.getSyndicateUUID(), syndicate.getSyndicateTax()));
			for(Double tax : syndicate.getExtraTaxes()) {
				Main.syndicate.get(oldEmployee.getSyndicateUUID()).getExtraTaxes().add(tax);
			}
		}
		ShowDialogMessage.showMessage("Success!", "Employee was rolled back!", false, null);
	}
	
	public void redo() {
		new ChangeInfoGUI();
	}

}
