package com.victor.actions;

import com.victor.gui.AddGUI;
import com.victor.gui.ChangeInfoGUI;
import com.victor.gui.RemoveGUI;
import com.victor.gui.SellResultGUI;
import com.victor.gui.SyndicateGUI;
import com.victor.gui.TimecardGUI;
import com.victor.main.Main;
import com.victor.utils.ShowDialogMessage;

public class Redo {
	
	public static void redo() {
		Action action = Main.lastAction;
		if(action != null) {
			switch (action.getEvent()) {
			case ADD_EMPLOYEE:
				new AddGUI();
				break;
				
			case REMOVE_EMPLOYEE:
				new RemoveGUI();
				break;
				
			case CREATE_TIMECARD:
				new TimecardGUI();
				break;
				
			case CREATE_SELL_RESULT:
				new SellResultGUI();
				break;
			
			case CREATE_SERVICE_TAX:
				new SyndicateGUI();
				break;
			
			case CHANGE_EMPLOYEE_INFO:
				new ChangeInfoGUI();
				break;
			
			case ROLL:
				Main.roll();
				break;
				
			default:
				break;
			}
		} else {
			ShowDialogMessage.showMessage("Fail!", "There is not last action!");
		}
	}
			

}
