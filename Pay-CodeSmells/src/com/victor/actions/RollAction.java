package com.victor.actions;

import java.util.Map;
import java.util.UUID;

import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Hourly;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class RollAction implements Action {
	
	Map<UUID, StorageUndoData> storageMap;
	
	public RollAction(Map<UUID, StorageUndoData> storageMap) {
		this.storageMap = storageMap;
	}
	
	public void undo() {
		for(UUID uuid : storageMap.keySet()) {
			Employee employees = Main.employees.get(uuid);
			if(employees instanceof Hourly) {
				((Hourly)employees).setTimecards(storageMap.get(uuid).getListTimeCard());
				employees.setWeeksCounter(storageMap.get(uuid).getWeeksCounter());
			} else if(employees instanceof Comissioned) {
				Main.employees.put(employees.getUUID(), ((Comissioned)employees));
				((Comissioned)employees).setSellResults(storageMap.get(uuid).getListSellResult());
				employees.setWeeksCounter(storageMap.get(uuid).getWeeksCounter());
			}					
		}
		ShowDialogMessage.showMessage("Success!", "Payroll was rolled back!", false, null);
	}
	
	public void redo() {
		Main.roll();
	}

}
