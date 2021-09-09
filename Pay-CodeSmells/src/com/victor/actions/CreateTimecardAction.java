package com.victor.actions;

import com.victor.employees.Hourly;
import com.victor.gui.TimecardGUI;
import com.victor.utils.ShowDialogMessage;

public class CreateTimecardAction implements Action{
	
	Hourly employee;
	
	public CreateTimecardAction(Hourly employee) {
		this.employee = employee;
	}
	
	public void undo() {
		Hourly hourly = ((Hourly)employee);
		hourly.getTimecards().remove(hourly.getTimecards().get(hourly.getTimecards().size() - 1));
		ShowDialogMessage.showMessage("Success!", "Last timecard removed!", false, null);
		hourly.printTimeCards();
	}
	
	public void redo() {
		new TimecardGUI();
	}

}
