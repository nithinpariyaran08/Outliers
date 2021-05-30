package com.hk.outliers.model;

import java.math.BigDecimal;

public class DiscountModel implements Comparable<DiscountModel> {

	private String date;
	private BigDecimal amount;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "DiscountModel [date=" + date + ", amount=" + amount + "]";
	}

	@Override
	public int compareTo(DiscountModel o) {
		if (this.getAmount().compareTo(o.getAmount()) > 0) {
			return 1;
		} else if (this.getAmount().compareTo(o.getAmount()) == 0)
			return 0;
		return -1;

	}

}
