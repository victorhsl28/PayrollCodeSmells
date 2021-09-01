package com.victor.employees;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.victor.classes.Address;
import com.victor.classes.SellResult;

public class Comissioned extends Employee {
	
	private double comissionedTax;
	private List<SellResult> sellResults;

	public Comissioned(UUID uuid, String name, Address adress, Double salary, PaymentMethod paymentMethod, String paymentSchedule, UUID syndicateUUID, Double comissionedTax) {
		super(uuid, name, adress, salary, paymentMethod, paymentSchedule, syndicateUUID);
		this.comissionedTax = comissionedTax;
		this.sellResults = new ArrayList<SellResult>();
	}
	
	public void printSellResults() {
		for(SellResult sellResult : sellResults) {
			sellResult.print_info();
		}
	}

	public double getComissionedTax() {
		return comissionedTax;
	}

	public void setComissionedTax(double comissionedTax) {
		this.comissionedTax = comissionedTax;
	}

	public List<SellResult> getSellResults() {
		return sellResults;
	}

	public void setSellResults(List<SellResult> sellResults) {
		this.sellResults = sellResults;
	}
}
