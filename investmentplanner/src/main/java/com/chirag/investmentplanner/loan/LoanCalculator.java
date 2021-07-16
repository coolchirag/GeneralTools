package com.chirag.investmentplanner.loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chirag.investmentplanner.InterestUtility;
import com.chirag.investmentplanner.investment.InvestmentCalculator;

public class LoanCalculator {

	/**
	 * 
	 * @param emiAmount
	 * @param annualInterestRate
	 * @param loanAmount
	 * @param incomeTaxSlab
	 * @param annualInterestRateForInvetment
	 * @param partPaymentInfo 
	 * @return
	 */
	public static FinalLoanPaymentInfoBean getLoanPaymentInfo(double emiAmount, float annualInterestRate, double loanAmount, float incomeTaxSlab, float annualInterestRateForInvetment)
	{
		double remainingLoanAmount = loanAmount;
		double totalPayedAmount = 0;
		double totalPayedInterestAmount = 0;
		double totalEarnedInterestOnInvestment = 0;
		int numOfEmi = 0;
		List<Double> interestPayedPerEmi = new ArrayList<Double>();
		List<Double> principalPayedPerEmi = new ArrayList<Double>();
		List<YearlyLoanPaymentInfoBean> yearlyLoanPaymentInfoBeans = new ArrayList<YearlyLoanPaymentInfoBean>();
		YearlyLoanPaymentInfoBean yearlyLoanPaymentInfoBean = new YearlyLoanPaymentInfoBean();
		while(remainingLoanAmount>0)
		{
			//double paymentAmount = emiAmount;
			totalPayedAmount = totalPayedAmount + emiAmount;
			numOfEmi++;
			
			yearlyLoanPaymentInfoBean.addTotalPayedAmount(emiAmount);
			double payedInterestAmount = InterestUtility.calculateInterestAmount(InterestUtility.calculateInterestRate(annualInterestRate, 1), remainingLoanAmount);
			double payedPrincipalAmount = emiAmount - payedInterestAmount;
			//double earnedInterestOnInvestment = InterestUtility.calculateInterestAmount(InterestUtility.calculateInterestRate(annualInterestRateForInvetment, 1), remainingLoanAmount);
			//totalEarnedInterestOnInvestment = totalEarnedInterestOnInvestment+earnedInterestOnInvestment;
			remainingLoanAmount = remainingLoanAmount - (payedPrincipalAmount);
			totalPayedInterestAmount = totalPayedInterestAmount + payedInterestAmount;
			interestPayedPerEmi.add(payedInterestAmount);
			principalPayedPerEmi.add(payedPrincipalAmount);
			yearlyLoanPaymentInfoBean.addTotalInterestPayedAmount(payedInterestAmount);
			yearlyLoanPaymentInfoBean.addTotalPayedPrincipalAmount(payedPrincipalAmount);
			System.out.println("remianing loan amount : "+remainingLoanAmount+", payedInterestAmount : "+payedInterestAmount);
			if(remainingLoanAmount > loanAmount)
			{
				System.out.println("Wrong remaining amount : "+remainingLoanAmount);
				break;
			}
			
			if(numOfEmi%12 == 0)
			{
				yearlyLoanPaymentInfoBean.setRemainigLoanAmount(remainingLoanAmount);
				yearlyLoanPaymentInfoBeans.add(yearlyLoanPaymentInfoBean);
				yearlyLoanPaymentInfoBean = new YearlyLoanPaymentInfoBean();
			}
		}
		//Calculate investment interest as invest loan amount as FD from first day of loan.
		totalEarnedInterestOnInvestment = InvestmentCalculator.cumulativeInterestInvestment(loanAmount, annualInterestRateForInvetment, numOfEmi, 3).getEarnedInterestAmount();
		double totalSavedIncomeTax = (totalPayedAmount-totalEarnedInterestOnInvestment) * incomeTaxSlab/100;
		return new FinalLoanPaymentInfoBean(totalPayedAmount, totalPayedInterestAmount, interestPayedPerEmi, principalPayedPerEmi, yearlyLoanPaymentInfoBeans, numOfEmi, totalEarnedInterestOnInvestment, totalSavedIncomeTax);
	}
	
	/*
	 * public double getSavedIncomeTax() { return
	 * (totalPayedAmount-totalEarnedInterestOnInvestment) * incomeTaxSlab/100; }
	 */
}
