package com.cit.ie.app_dev.models;

public class Citizen {
	
	private String name;
	private String surName;
	private double salary;
	private double taxPayed;
	private double takeHome;
	
	public double getTaxPayed() {
		return taxPayed;
	}
	public void setTaxPayed(double taxPayed) {
		this.taxPayed = taxPayed;
	}
	public double getTakeHome() {
		return takeHome;
	}
	public void setTakeHome(double takeHome) {
		this.takeHome = takeHome;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}

}
