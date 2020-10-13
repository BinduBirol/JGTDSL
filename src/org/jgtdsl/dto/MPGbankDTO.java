package org.jgtdsl.dto;

public class MPGbankDTO {

	private String bank_name;
	private int sl;
	private String userid;
	private String ip_address;
	
	
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	@Override
	public String toString() {
		return "MPGbankDTO [bank_name=" + bank_name + ", sl=" + sl
				+ ", userid=" + userid + ", ip_address=" + ip_address + "]";
	}
	
	
	
	
}
