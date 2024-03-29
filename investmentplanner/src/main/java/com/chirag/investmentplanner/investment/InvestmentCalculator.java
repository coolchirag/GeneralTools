package com.chirag.investmentplanner.investment;

import com.chirag.investmentplanner.InterestUtility;

public class InvestmentCalculator {

	/**
	 * This method will calculate investmentReturn as per cumulative interest.
	 * @param amount amount which you want to invest
	 * @param annualInterestRate Annual rate of interest 
	 * @param maturityPeriodInMonths Time duration for which you want to invest.
	 * @param interestPeriodInMonths Time duration, after which bank give you an interest. (i.e quarterly, 6 months, yearly)
	 * @return
	 */
	public static InvestmentReturnInfoBean cumulativeInterestInvestment(double amount, float annualInterestRate, int maturityPeriodInMonths, int interestPeriodInMonths)
	{
		int currentTimePeriodInMonths=interestPeriodInMonths;
		double totalAmount = amount;
		while(currentTimePeriodInMonths<=maturityPeriodInMonths)
		{
			totalAmount = totalAmount + InterestUtility.calculateInterestAmount(InterestUtility.calculateInterestRate(annualInterestRate, interestPeriodInMonths), totalAmount);
			currentTimePeriodInMonths = currentTimePeriodInMonths + interestPeriodInMonths;
		}
		currentTimePeriodInMonths = currentTimePeriodInMonths-interestPeriodInMonths;
		totalAmount = totalAmount + InterestUtility.calculateInterestAmount(InterestUtility.calculateInterestRate(annualInterestRate, maturityPeriodInMonths-currentTimePeriodInMonths), totalAmount);
		
		return new InvestmentReturnInfoBean(amount, totalAmount - amount, totalAmount);
	}
	
	/**
	 * This method will calculate investmentReturn for Recurring investment (i.e SIP, LIC policy)
	 * @param initialAmount Amount which you pay at a time of join policy.
	 * @param periodicAmount Amount which you pay periodicaly (i.e in LIC you pay 23k)
	 * @param investmentFrequencyInMonths How frequently you invest (i.e in LIC you pay 23k yearly)
	 * @param paymentPeriodInMonths for how many months you pay periodic amount. (i.e you pay for 5 years but your policy got matured at 7th year)
	 * @param maturityPeriodInMonths Policy duration
	 * @param annualInterestRate Annual interest rate
	 * @param interestPeriodInMonths Time duration, after which bank give you an interest. (i.e quarterly, 6 months, yearly)
	 * @return
	 */
	
	/**
	 * This method will calculate investmentReturn for Recurring investment (i.e SIP, LIC policy)
	 * This algo calculate interest on every recurring payment for remaining maturity period.
	 * @param initialAmount Amount which you pay at a time of join policy.
	 * @param recurringAmount Amount which you pay periodically (i.e in LIC you pay 23k)
	 * @param recurringFrequencyInMonths How frequently you invest (i.e in LIC you pay 23k yearly)
	 * @param paymentPeriodInMonths  for how many months you pay periodic amount. (i.e you pay for 5 years but your policy got matured at 7th year)
	 * @param maturityPeriodInMonths Policy duration
	 * @param annualInterestRate Annual interest rate
	 * @param interestPeriodInMonths Time duration, after which bank give you an interest. (i.e quarterly, 6 months, yearly)
	 * 
	 * For verification : https://www.creditfinanceplus.com/calculators/calculate-future-value-investments-compound-interest.php#help-calculators
	 * @return
	 */
	public static InvestmentReturnInfoBean cumulativeInterestRecurringInvestment(double initialAmount, double recurringAmount, int recurringFrequencyInMonths, int paymentPeriodInMonths, int maturityPeriodInMonths, float annualInterestRate, int interestPeriodInMonths)
	{
		double totalInvestedAmount = initialAmount;
		final InvestmentReturnInfoBean initialAmountInvestmentInfoBean = cumulativeInterestInvestment(initialAmount, annualInterestRate, maturityPeriodInMonths, interestPeriodInMonths);
		double totalAmount = initialAmountInvestmentInfoBean.getMaturityAmount();
		double totalEarnedInterestAmount = initialAmountInvestmentInfoBean.getEarnedInterestAmount();
		System.out.println("Initial amount investment info : "+initialAmountInvestmentInfoBean);
		int numOfRecuringPayment = (paymentPeriodInMonths/recurringFrequencyInMonths-1);
		System.out.println("Number of Recuring Payment : "+numOfRecuringPayment);
		
		
		for(int recurringIndex = 1; recurringIndex <= numOfRecuringPayment; recurringIndex++)
		{
			//totalAmount += recurringAmount;
			final InvestmentReturnInfoBean recurringAmountInvestmentInfoBean = cumulativeInterestInvestment(recurringAmount, annualInterestRate, maturityPeriodInMonths-(recurringIndex*recurringFrequencyInMonths), interestPeriodInMonths);
			System.out.println(recurringIndex + " Recurring investment info : "+recurringAmountInvestmentInfoBean);
			totalAmount = totalAmount + recurringAmountInvestmentInfoBean.getMaturityAmount();
			totalEarnedInterestAmount = totalEarnedInterestAmount + recurringAmountInvestmentInfoBean.getEarnedInterestAmount();
			totalInvestedAmount = totalInvestedAmount + recurringAmount;
		}
		return new InvestmentReturnInfoBean(totalInvestedAmount, totalEarnedInterestAmount, totalAmount);
	}
	
	/**
	 * This method will calculate investmentReturn for Recurring investment (i.e SIP, LIC policy)
	 * This algo calculate interest on recurring payment for next recurring payment period only.
	 * @param initialAmount Amount which you pay at a time of join policy.
	 * @param recurringAmount Amount which you pay periodically (i.e in LIC you pay 23k)
	 * @param recurringFrequencyInMonths How frequently you invest (i.e in LIC you pay 23k yearly)
	 * @param paymentPeriodInMonths  for how many months you pay periodic amount. (i.e you pay for 5 years but your policy got matured at 7th year)
	 * @param maturityPeriodInMonths Policy duration
	 * @param annualInterestRate Annual interest rate
	 * @param interestPeriodInMonths Time duration, after which bank give you an interest. (i.e quarterly, 6 months, yearly)
	 * 
	 * For verification : https://www.creditfinanceplus.com/calculators/calculate-future-value-investments-compound-interest.php#help-calculators
	 * @return
	 */
	public static InvestmentReturnInfoBean cumulativeInterestRecurringInvestmentAlgo2(double initialAmount, double recurringAmount, int recurringFrequencyInMonths, int paymentPeriodInMonths, int maturityPeriodInMonths, float annualInterestRate, int interestPeriodInMonths)
	{
		double totalInvestedAmount = initialAmount;
		double totalAmount = initialAmount;
		double totalEarnedInterestAmount = 0;
		if(recurringFrequencyInMonths%interestPeriodInMonths != 0) {
			final String errorMsg = "recurringFrequencyInMonths is not in mpltiply of interestPeriodInMonths";
			System.out.println(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		int numOfRecuringPayment = (paymentPeriodInMonths/recurringFrequencyInMonths)-1;
		for(int recurringIndex =0;  recurringIndex <= numOfRecuringPayment; recurringIndex++) {
			if(recurringIndex > 0) {
				totalAmount += recurringAmount; 
				totalInvestedAmount += recurringAmount;
			}
			final InvestmentReturnInfoBean recurringAmountInvestmentInfoBean = cumulativeInterestInvestment(totalAmount, annualInterestRate, recurringFrequencyInMonths, interestPeriodInMonths);
			System.out.println(recurringIndex + " Recurring investment info : "+recurringAmountInvestmentInfoBean+ " totalInverstedAmmount : "+totalInvestedAmount);
			totalAmount = recurringAmountInvestmentInfoBean.getMaturityAmount();
			totalEarnedInterestAmount = totalEarnedInterestAmount + recurringAmountInvestmentInfoBean.getEarnedInterestAmount();
		}
		if(maturityPeriodInMonths>paymentPeriodInMonths) {
			final InvestmentReturnInfoBean recurringAmountInvestmentInfoBean = cumulativeInterestInvestment(totalAmount, annualInterestRate, maturityPeriodInMonths-paymentPeriodInMonths, interestPeriodInMonths);
			System.out.println("Interest enrnerd after payment preiod investment info : "+recurringAmountInvestmentInfoBean);
			totalAmount = recurringAmountInvestmentInfoBean.getMaturityAmount();
			totalEarnedInterestAmount = totalEarnedInterestAmount + recurringAmountInvestmentInfoBean.getEarnedInterestAmount();
		}
		return new InvestmentReturnInfoBean(totalInvestedAmount, totalEarnedInterestAmount, totalAmount);
	}
	
	
}
