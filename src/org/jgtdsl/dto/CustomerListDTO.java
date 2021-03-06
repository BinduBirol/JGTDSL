package org.jgtdsl.dto;

import com.google.gson.Gson;

public class CustomerListDTO {

	private String customerName;
	private String customerId;
	private String customerAddress;
	private String fatherName;
	private String status;
	private double ledgerBalance;
	private String category;
	private String categoryName;
	private double maxLoad;
	private double min_load;
	private float burnerQty;
	private float billBurner;
	private double numberOfCustomer;
	private String customerType;
	private String areaName;
	private int count;
	private String meter_info;
	
	private String sub_ids;
	private String sub_burners;
	private String sub_names;
	private String sub_address;
	
	private int single_burners;
	private int double_burners;
	
	
	
	
	public int getSingle_burners() {
		return single_burners;
	}
	public void setSingle_burners(int single_burners) {
		this.single_burners = single_burners;
	}
	
	public int getDouble_burners() {
		return double_burners;
	}
	public void setDouble_burners(int double_burners) {
		this.double_burners = double_burners;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getM_inst_date() {
		return m_inst_date;
	}
	public void setM_inst_date(String m_inst_date) {
		this.m_inst_date = m_inst_date;
	}
	public String getM_no() {
		return m_no;
	}
	public void setM_no(String m_no) {
		this.m_no = m_no;
	}
	
	private double pressure;
	private String mobile;
	private String m_inst_date;
	private String m_no;
	

	
	//for mixed customer report 
	
	
	
	

	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getMin_load() {
		return min_load;
	}
	public String getSub_ids() {
		return sub_ids;
	}
	public void setSub_ids(String sub_ids) {
		this.sub_ids = sub_ids;
	}
	public String getSub_burners() {
		return sub_burners;
	}
	public void setSub_burners(String sub_burners) {
		this.sub_burners = sub_burners;
	}
	public String getSub_names() {
		return sub_names;
	}
	public void setSub_names(String sub_names) {
		this.sub_names = sub_names;
	}
	public String getSub_address() {
		return sub_address;
	}
	public void setSub_address(String sub_address) {
		this.sub_address = sub_address;
	}
	public String getMeter_info() {
		return meter_info;
	}
	public void setMeter_info(String meter_info) {
		this.meter_info = meter_info;
	}
	public void setMin_load(double min_load) {
		this.min_load = min_load;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public double getNumberOfCustomer() {
		return numberOfCustomer;
	}
	public void setNumberOfCustomer(double numberOfCustomer) {
		this.numberOfCustomer = numberOfCustomer;
	}
	public float getBurnerQty() {
		return burnerQty;
	}
	public void setBurnerQty(float burnerQty) {
		this.burnerQty = burnerQty;
	}
	public float getBillBurner() {
		return billBurner;
	}
	public void setBillBurner(float billBurner) {
		this.billBurner = billBurner;
	}
	public double getMaxLoad() {
		return maxLoad;
	}
	public void setMaxLoad(double maxLoad) {
		this.maxLoad = maxLoad;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getLedgerBalance() {
		return ledgerBalance;
	}
	public void setLedgerBalance(double ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}
	public String toString() {         
        Gson gson = new Gson();
		return gson.toJson(this);
    }		
	
	
}
