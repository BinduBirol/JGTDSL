package org.jgtdsl.actions;

import org.jgtdsl.dto.ResponseDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.models.InstallmentService;
import org.jgtdsl.models.SecurityAdjustmentService;

public class SecurityAdjustment extends BaseAction{

	private static final long serialVersionUID = -8072412222879273900L;
	private String customerId;
	private double securityAmount;
	private double OtherAmount;
	private String adjustmentMode;
	private double totalAdjustableAmount;
	private String comment;
	private String collectionDate;
	
	private String adjustmentBillStr;
	private String isMeter;
	
	
	public String getSecurityBalance()
	{
		SecurityAdjustmentService securityAdjustmentService=new SecurityAdjustmentService();
		String balance=securityAdjustmentService.getSecurityDepositBalance(customerId);
		setJsonResponse(balance);
        return null;
        
	}
	
	
	public String saveSecurityAdjustment()
	{
		UserDTO loggedInUser=(UserDTO)session.get("user");
		SecurityAdjustmentService securityAdjustmentService=new SecurityAdjustmentService();		
		ResponseDTO response=securityAdjustmentService.saveSecurityAdjustment(customerId, securityAmount,  adjustmentMode, totalAdjustableAmount,
				comment, collectionDate,isMeter, adjustmentBillStr, loggedInUser.getUserId());
		setJsonResponse(response);
        return null;        
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public double getSecurityAmount() {
		return securityAmount;
	}

	public void setSecurityAmount(double securityAmount) {
		this.securityAmount = securityAmount;
	}

	public double getOtherAmount() {
		return OtherAmount;
	}

	public void setOtherAmount(double otherAmount) {
		OtherAmount = otherAmount;
	}

	public String getAdjustmentMode() {
		return adjustmentMode;
	}

	public void setAdjustmentMode(String adjustmentMode) {
		this.adjustmentMode = adjustmentMode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	

	public String getAdjustmentBillStr() {
		return adjustmentBillStr;
	}

	public void setAdjustmentBillStr(String adjustmentBillStr) {
		this.adjustmentBillStr = adjustmentBillStr;
	}


	public double getTotalAdjustableAmount() {
		return totalAdjustableAmount;
	}


	public void setTotalAdjustableAmount(double totalAdjustableAmount) {
		this.totalAdjustableAmount = totalAdjustableAmount;
	}


	public String getIsMeter() {
		return isMeter;
	}


	public void setIsMeter(String isMeter) {
		this.isMeter = isMeter;
	}


	
	
	
}