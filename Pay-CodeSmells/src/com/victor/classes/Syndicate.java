package com.victor.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Syndicate {
	
	private UUID syndicateUUID;
	private double syndicateTax;
	private ArrayList<Double> extraTaxes;
	
	public Syndicate(UUID syndicateUUID, double syndicateTax) {
		this.syndicateUUID = syndicateUUID;
		this.syndicateTax = syndicateTax;
		this.extraTaxes = new ArrayList<>();
	}
	
	public void print_info() {
		System.out.println("ID: " + this.getSyndicateUUID().toString() + "\n" +
							"Tax: " + this.getSyndicateTax() + "\n");
		System.out.println("=Extras Taxes=");
		for(Double extrataxes : extraTaxes) {
			System.out.println(String.valueOf(extrataxes));
		}
		System.out.println("==============");
	}

	public UUID getSyndicateUUID() {
		return syndicateUUID;
	}
	
	public void setSyndicateUUID(UUID syndicateUUID) {
		this.syndicateUUID = syndicateUUID;
	}
	
	public double getSyndicateTax() {
		return syndicateTax;
	}
	
	public void setSyndicateTax(double syndicateTax) {
		this.syndicateTax = syndicateTax;
	}
	
	public List<Double> getExtraTaxes() {
		return extraTaxes;
	}
	
	public void setExtraTaxes(ArrayList<Double> extraTaxes) {
		this.extraTaxes = extraTaxes;
	}

}
