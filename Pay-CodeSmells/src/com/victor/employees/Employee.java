package com.victor.employees;

import java.util.UUID;

import com.victor.classes.Address;

public abstract class Employee {
	
	private UUID uuid;
	private String name;
	private Address adress;
	private double salary;
	private PaymentMethod paymentMethod;
	private String paymentSchedule;
	private int weeksCounter;
	private UUID syndicateUUID;
	private boolean payed;
	
	public Employee(UUID uuid, String name, Address adress, Double salary, PaymentMethod paymentMethod, String paymentSchedule, UUID syndicateUUID) {
		this.uuid = uuid;
		this.name = name;
		this.adress = adress;
		this.salary = salary;
		this.paymentMethod = paymentMethod;
		this.paymentSchedule = paymentSchedule;
		this.weeksCounter = 1;
		this.syndicateUUID = syndicateUUID;
		this.payed = false;
	}
	
	public static enum PaymentMethod {
	    MAIL_CHECK, HAND_CHECK, DEPOSIT_BANK_ACCOUNT;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public Address getAdress() {
		return adress;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getSyndicateUUID() {
		return syndicateUUID;
	}

	public void setSyndicateUUID(UUID syndicateUUID) {
		this.syndicateUUID = syndicateUUID;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getPaymentSchedule() {
		return paymentSchedule;
	}

	public void setPaymentSchedule(String paymentSchedule) {
		this.paymentSchedule = paymentSchedule;
	}

	public int getWeeksCounter() {
		return weeksCounter;
	}

	public void setWeeksCounter(int weeksCounter) {
		this.weeksCounter = weeksCounter;
	}

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}
}
