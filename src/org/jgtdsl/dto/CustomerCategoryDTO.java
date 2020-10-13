package org.jgtdsl.dto;

public class CustomerCategoryDTO {

	private String category_id;
	private String category_name;
	private String category_type;
	private String description;
	private int status;
	private String cc_receive;
	private String cc_mr;
	private String cc_sc;
	private String with_surcharge;
	private String without_surcharge;
	
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String categoryId) {
		category_id = categoryId;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String categoryName) {
		category_name = categoryName;
	}
	
	public String getCategory_type() {
		return category_type;
	}
	public void setCategory_type(String categoryType) {
		category_type = categoryType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
	public String getCc_receive() {
		return cc_receive;
	}
	public void setCc_receive(String cc_receive) {
		this.cc_receive = cc_receive;
	}
	public String getCc_mr() {
		return cc_mr;
	}
	public void setCc_mr(String cc_mr) {
		this.cc_mr = cc_mr;
	}
	public String getCc_sc() {
		return cc_sc;
	}
	public void setCc_sc(String cc_sc) {
		this.cc_sc = cc_sc;
	}
	public String getWith_surcharge() {
		return with_surcharge;
	}
	public void setWith_surcharge(String with_surcharge) {
		this.with_surcharge = with_surcharge;
	}
	public String getWithout_surcharge() {
		return without_surcharge;
	}
	public void setWithout_surcharge(String without_surcharge) {
		this.without_surcharge = without_surcharge;
	}
	public String toString() {
		return "{\"category_id\":\"" + category_id + "\", \"category_name\":\"" + category_name + "\", \"category_type\":\"" + category_type + "\", \"description\":\"" + (description==null?"":description.replaceAll("[\\r\\n]+", "<br/>")) + "\", \"status\":\"" + status + "\",\"cc_receive\":\"" + cc_receive + "\",\"cc_mr\":\"" + cc_mr + "\",\"cc_sc\":\"" + cc_sc + "\",\"with_surcharge\":\"" + with_surcharge + "\",\"without_surcharge\":\"" + without_surcharge + "\"}";
    }
	
}
