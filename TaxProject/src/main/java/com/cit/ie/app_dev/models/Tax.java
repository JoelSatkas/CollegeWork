package com.cit.ie.app_dev.models;

public class Tax {

	private double exemptBase;
	private double afterBase;
	private double afterBasePer;
	private double remainderPer;
	private double taxCharge;
	
	public double getExemptBase() {
		return exemptBase;
	}
	public void setExemptBase(double exemptBase) {
		this.exemptBase = exemptBase;
	}
	public double getAfterBase() {
		return afterBase;
	}
	public void setAfterBase(double afterBase) {
		this.afterBase = afterBase;
	}
	public double getAfterBasePer() {
		return afterBasePer;
	}
	public void setAfterBasePer(double afterBasePer) {
		this.afterBasePer = afterBasePer;
	}
	public double getRemainderPer() {
		return remainderPer;
	}
	public void setRemainderPer(double remainderPer) {
		this.remainderPer = remainderPer;
	}
	public double getTaxCharge() {
		return taxCharge;
	}
	public void setTaxCharge(double taxCharge) {
		this.taxCharge = taxCharge;
	}

}
