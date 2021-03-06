package org.jgtdsl.dto;

public class BalancingReportDTO {

	private Double dBurner;
	private Double sBurner;
	private Double oBurner;
	private String customer_count;
	private Double cash;
	private Double bg;
	private Double advance;
	private long balance;
	private String flag;
	private String min_load;
	private String max_load;
	
	
	///for detailed
	
	private String customer_id;
	private String customer_name;
	private String security_deposit;
	private String due_months;
	
	

	public String getMin_load() {
		return min_load;
	}

	public void setMin_load(String min_load) {
		this.min_load = min_load;
	}

	public String getMax_load() {
		return max_load;
	}

	public void setMax_load(String max_load) {
		this.max_load = max_load;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getSecurity_deposit() {
		return security_deposit;
	}

	public void setSecurity_deposit(String security_deposit) {
		this.security_deposit = security_deposit;
	}

	public String getDue_months() {
		return due_months;
	}

	public void setDue_months(String due_months) {
		this.due_months = due_months;
	}

	public Double getdBurner() {
		return dBurner;
	}

	public void setdBurner(Double dBurner) {
		this.dBurner = dBurner;
	}

	public Double getsBurner() {
		return sBurner;
	}

	public void setsBurner(Double sBurner) {
		this.sBurner = sBurner;
	}

	public Double getoBurner() {
		return oBurner;
	}

	public void setoBurner(Double oBurner) {
		this.oBurner = oBurner;
	}

	public String getCustomer_count() {
		return customer_count;
	}

	public void setCustomer_count(String customer_count) {
		this.customer_count = customer_count;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getBg() {
		return bg;
	}

	public void setBg(Double bg) {
		this.bg = bg;
	}

	public Double getAdvance() {
		return advance;
	}

	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long l) {
		this.balance = l;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
