package com.victor.actions;

import java.util.ArrayList;
import java.util.List;

import com.victor.classes.SellResult;
import com.victor.classes.TimeCard;
import com.victor.employees.Comissioned;
import com.victor.employees.Employee;
import com.victor.employees.Hourly;

public class StorageUndoData {
	
	private int weeksCounter;
	private List<TimeCard> listTimeCard;
	private List<SellResult> listSellResult;
	
	public StorageUndoData(Employee employee) {
		this.weeksCounter = employee.getWeeksCounter();
		if(employee instanceof Hourly) {
			this.listTimeCard = new ArrayList<TimeCard>();
			for(TimeCard timecard : ((Hourly)employee).getTimecards()) {
				listTimeCard.add(timecard);
			}
		} else if(employee instanceof Comissioned) {
			this.listSellResult = new ArrayList<SellResult>();
			for(SellResult sellresult : ((Comissioned)employee).getSellResults()) {
				listSellResult.add(sellresult);
			}
		}
	}

	public int getWeeksCounter() {
		return weeksCounter;
	}

	public void setWeeksCounter(int weeksCounter) {
		this.weeksCounter = weeksCounter;
	}

	public List<TimeCard> getListTimeCard() {
		return listTimeCard;
	}

	public void setListTimeCard(List<TimeCard> listTimeCard) {
		this.listTimeCard = listTimeCard;
	}

	public List<SellResult> getListSellResult() {
		return listSellResult;
	}

	public void setListSellResult(List<SellResult> listSellResult) {
		this.listSellResult = listSellResult;
	}

}
