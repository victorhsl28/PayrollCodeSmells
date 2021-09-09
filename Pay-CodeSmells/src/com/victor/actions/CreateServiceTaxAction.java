package com.victor.actions;

import com.victor.classes.Syndicate;
import com.victor.gui.SyndicateGUI;
import com.victor.utils.ShowDialogMessage;

public class CreateServiceTaxAction implements Action {
	
	Syndicate syndicate;
	
	public CreateServiceTaxAction(Syndicate syndicate) {
		this.syndicate = syndicate;
	}
	
	public void undo() {
		syndicate.getExtraTaxes().remove(syndicate.getExtraTaxes().get(syndicate.getExtraTaxes().size() - 1));
		ShowDialogMessage.showMessage("Success!", "Last service tax removed!", false, null);
		syndicate.print_info();
	}
	
	public void redo() {
		new SyndicateGUI();
	}

}
