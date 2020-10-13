package org.jgtdsl.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import oracle.jdbc.driver.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.jgtdsl.dto.InstallmentAgreementDTO;
import org.jgtdsl.dto.ResponseDTO;
import org.jgtdsl.utils.connection.ConnectionManager;

public class SecurityAdjustmentService {


	public String getSecurityDepositBalance(String customerId)
	{
		/*
		String query="Select total_security_amount, total_debit-total_credit-total_security_amount Other_Deposit From  " +
		"( " +
		"Select * From " +
		"(Select sum(debit) total_debit From CUSTOMER_SECURITY_LEDGER Where Customer_Id=?)tmp1, " +
		"(Select nvl(sum(credit),0) total_credit From CUSTOMER_SECURITY_LEDGER Where Customer_Id=?)tmp2, " +
		"(Select sum(security_amount) total_security_amount From CUSTOMER_SECURITY_LEDGER Where Customer_Id=?)tmp3 " +
		") " ;
		*/
		
		String query = "  SELECT NVL (SUM (total_deposit), 0)"
				+ " total_security_amount "
				+ " FROM mst_deposit "
				+ " WHERE customer_id = ? AND DEPOSIT_TYPE IN (0) AND DEPOSIT_PURPOSE IN (1, 2) ";
		String balanceJson="";
		Connection conn = ConnectionManager.getConnection();
		   PreparedStatement stmt = null;
		   ResultSet r = null;
			try
			{
				stmt = conn.prepareStatement(query);
				stmt.setString(1, customerId);
				//stmt.setString(2, customerId);
				//stmt.setString(3, customerId);
				r = stmt.executeQuery();
				if (r.next())
				{
					balanceJson="{\"security\":"+r.getString("total_security_amount")+",\"others\":"+r.getString("total_security_amount")+"}";
				}
			} 
			catch (Exception e){e.printStackTrace();}
	 		finally{try{stmt.close();ConnectionManager.closeConnection(conn);} catch (Exception e)
				{e.printStackTrace();}stmt = null;conn = null;}
		
		
		return balanceJson;
	}
	
	public ResponseDTO saveSecurityAdjustment(String customerId,
			double securityAmount, String adjustmentMode,
			double totalAdjustableAmount, String comment,
			String collectionDate, String isMeter, String adjustmentBillStr,
			String loggedInUser)
	{
		
		Connection conn = ConnectionManager.getConnection();
	 	OracleCallableStatement stmt=null;
	 	int response_code=0;
	 	String response_msg="";
	 	ResponseDTO response=new ResponseDTO();
		
		String[] billArr;
		
		if(adjustmentBillStr.equalsIgnoreCase(""))
			billArr=new String[0];
		else 
			billArr=adjustmentBillStr.split("@");
		
		String[] billIdArr =new String[billArr.length];
		double[] adjustmentAmountArr = new double[billArr.length];
		double[] surchargeAmountArr =new double[billArr.length];
		int[] billTypeArr =new int[billArr.length];
		
		for(int i=0; i<billArr.length; i++)
		{
			String[] parts=billArr[i].split("#");
			billTypeArr[i] = Integer.parseInt(parts[0]);
			billIdArr[i] = parts[1];
			adjustmentAmountArr[i] =Double.parseDouble(parts[2]);
			
			try{
			surchargeAmountArr[i] = Double.parseDouble(parts[3]);
			}
			catch(Exception ex){
				surchargeAmountArr[i] = 0;
			}
			
		}		
		
		 try	
		  {
	    	ArrayDescriptor arrString = new ArrayDescriptor("VARCHARARRAY", conn);
	    	ArrayDescriptor arrNumber = new ArrayDescriptor("NUMBERARRAY", conn);
			
		
			ARRAY inputBillId=new ARRAY(arrString,conn,billIdArr);	
	    	ARRAY inputAdjustAmount=new ARRAY(arrNumber,conn,adjustmentAmountArr);
	    	ARRAY inputSurchargeAmount=new ARRAY(arrNumber,conn,surchargeAmountArr); 			
			
			stmt = (OracleCallableStatement) conn.prepareCall("BEGIN Save_Security_Adjustment(?,?,?,?,?,?,?,?,?,?,?,?,?); END; ");
			   
			stmt.setString(1, customerId);
			stmt.setString(2, isMeter);			
			stmt.setDouble(3, securityAmount);
			stmt.setString(4, adjustmentMode);
			stmt.setDouble(5, totalAdjustableAmount);
			stmt.setString(6, comment);
			stmt.setString(7, collectionDate);
			stmt.setArray(8, inputBillId);			
			stmt.setArray(9, inputAdjustAmount);
			stmt.setArray(10, inputSurchargeAmount);								
			stmt.setString(11, loggedInUser);
			
			stmt.registerOutParameter(12, java.sql.Types.INTEGER);
			stmt.registerOutParameter(13, java.sql.Types.VARCHAR);			
			
    		stmt.executeUpdate();
 			response_code = stmt.getInt(12);
			response_msg = (stmt.getString(13)).trim();

			response.setMessasge(response_msg);
			response.setResponse(response_code==1?true:false);
		  }
	    catch (Exception e){e.printStackTrace();response.setResponse(false);response.setMessasge(e.getMessage());return response;}
 		finally{try{stmt.close();ConnectionManager.closeConnection(conn);} catch (Exception e)
			{e.printStackTrace();}stmt = null;conn = null;}
	 		
		 return response;	
	
	}
	
	
}
