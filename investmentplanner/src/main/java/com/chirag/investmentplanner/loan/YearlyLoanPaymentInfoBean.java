package com.chirag.investmentplanner.loan;

public class YearlyLoanPaymentInfoBean {

	private double totalPayedAmount;
	private double totalInterestPayedAmount;
	private double totalPayedPrincipalAmount;
	private double remainigLoanAmount;
	
	
	
	
	public double getTotalPayedAmount() {
		return totalPayedAmount;
	}
	public void addTotalPayedAmount(double totalPayedAmount) {
		this.totalPayedAmount = this.totalPayedAmount + totalPayedAmount;
	}
	public double getTotalInterestPayedAmount() {
		return totalInterestPayedAmount;
	}
	public void addTotalInterestPayedAmount(double totalInterestPayedAmount) {
		this.totalInterestPayedAmount = this.totalInterestPayedAmount + totalInterestPayedAmount;
	}
	public double getRemainigLoanAmount() {
		return remainigLoanAmount;
	}
	public void setRemainigLoanAmount(double remainigLoanAmount) {
		this.remainigLoanAmount = remainigLoanAmount;
	}
	public double getTotalPayedPrincipalAmount() {
		return totalPayedPrincipalAmount;
	}
	public void addTotalPayedPrincipalAmount(double totalPayedPrincipalAmount) {
		this.totalPayedPrincipalAmount = this.totalPayedPrincipalAmount + totalPayedPrincipalAmount;
	}
	
	
	
	@Override
	public String toString() {
		return "YearlyLoanPaymentInfoBean [totalPayedAmount=" + totalPayedAmount + ", totalInterestPayedAmount="
				+ totalInterestPayedAmount + ", totalPayedPrincipalAmount=" + totalPayedPrincipalAmount
				+ ", remainigLoanAmount=" + remainigLoanAmount +"]";
	}
	
	
}
