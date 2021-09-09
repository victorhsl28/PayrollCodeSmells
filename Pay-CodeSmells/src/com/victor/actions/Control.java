package com.victor.actions;

import com.victor.main.Main;

public class Control implements Action {
	
	Action action;
	
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public void undo() {
		action.undo();
		Main.control.setAction(null);
	}

	@Override
	public void redo() {
		action.redo();
		Main.control.setAction(null);
	}
	
	public Action getAction() {
		return action;
	}

}
