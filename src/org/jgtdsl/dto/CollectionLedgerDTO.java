package org.jgtdsl.dto;

import org.jgtdsl.enums.BankAccountTransactionType;

public class CollectionLedgerDTO {

	private String trans_date;
	private int debit_amount;
	private String trans_type;
	private String bank_branch;
	private String remarks;
	private String insert_date;
	private int surcharge;
	
	
	
	public int getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(int surcharge) {
		this.surcharge = surcharge;
	}
	public String getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}
	
	public int getDebit_amount() {
		return debit_amount;
	}
	public void setDebit_amount(int debit_amount) {
		this.debit_amount = debit_amount;
	}
	
	
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public String getBank_branch() {
		return bank_branch;
	}
	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}	
	
}
