package com.victor.actions;

import com.victor.employees.Comissioned;
import com.victor.gui.SellResultGUI;
import com.victor.utils.ShowDialogMessage;

public class CreateSellResultAction implements Action {
	
	Comissioned employee;
	
	public CreateSellResultAction(Comissioned employee) {
		this.employee = employee;
	}
	
	public void undo() {
		employee.getSellResults().remove(employee.getSellResults().get(employee.getSellResults().size() - 1));
		ShowDialogMessage.showMessage("Success!", "Last sell result removed!", false, null);
		employee.printSellResults();
	}
	
	public void redo() {
		new SellResultGUI();
	}

}
