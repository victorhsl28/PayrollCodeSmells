package com.victor.actions;

import com.victor.employees.Employee;
import com.victor.gui.AddGUI;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class AddEmployeeAction implements Action {
	
	Employee employee;
	
	public AddEmployeeAction(Employee employee) {
		this.employee = employee;
	}
	
	public void undo() {
		if(!employee.getSyndicateUUID().toString().equalsIgnoreCase(Main.nullUUID)) {
			Main.syndicate.remove(employee.getSyndicateUUID());
		}
		Main.employees.remove(employee.getUUID());
		ShowDialogMessage.showMessage("Success!", "Employee " + employee.getUUID().toString() + " has been removed!", false, null);
	}
	
	public void redo() {
		new AddGUI();
	}

}
