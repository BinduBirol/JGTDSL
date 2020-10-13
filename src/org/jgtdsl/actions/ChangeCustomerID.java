package org.jgtdsl.actions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.jgtdsl.dto.ResponseDTO;
import org.jgtdsl.models.CustomerService;
import org.jgtdsl.utils.cache.CacheUtil;
import org.jgtdsl.utils.connection.TransactionManager;

public class ChangeCustomerID extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String oldCustomerID;
	private String newCustomer_id;

	public String changeCustomerIDmethod() {
		ResponseDTO response = new ResponseDTO();
		String validate= validateNewCustIdentity(newCustomer_id);
		if(validate.equals("Y")){
			response.setMessasge("<b>The new Customer ID already exist!! Try again with Different serial!!</b>");
			response.setResponse(true);
			setJsonResponse(response);
		}else{
			response = changeCustIdentity(this.oldCustomerID, newCustomer_id);
			keepHistory(this.newCustomer_id,this.oldCustomerID);
			setJsonResponse(response);
		}
		
		
		CacheUtil.clear("CUSTOMER_INFO_" + getOldCustomerID());
		return null;
	}

	public static ResponseDTO changeCustIdentity(String oldID, String newID) {
		ResponseDTO response = new ResponseDTO();
		TransactionManager transactionManager = new TransactionManager();
		Connection conn = transactionManager.getConnection();

		CallableStatement stmt = null;

		try {
			stmt = conn.prepareCall(" { call  Change_Customer_ID(?,?) }");

			stmt.setString(1, oldID);
			stmt.setString(2, newID);

			stmt.execute();

			transactionManager.commit();

			response.setMessasge("Successfully Changed Customer ID to <b style='color:green;'>"+newID+"</b> ");
			response.setResponse(true);

		} catch (Exception e) {
			response.setMessasge(e.getMessage());
			response.setResponse(false);
			e.printStackTrace();
			try {
				transactionManager.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				stmt.close();				
				transactionManager.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			conn = null;
		}

		return response;

	}
	
	
	public static String validateNewCustIdentity(String newID) {
		ResponseDTO response = new ResponseDTO();
		TransactionManager transactionManager = new TransactionManager();
		Connection conn = transactionManager.getConnection();

		PreparedStatement stmt = null;
		ResultSet rs= null;
		String sql="select * from customer_connection where customer_id= ?";

		try {
			stmt = conn.prepareStatement(sql);			
			stmt.setString(1, newID);

			rs= stmt.executeQuery();
			if(rs.next()){
				return "Y";
			}

			
		} catch (Exception e) {
			response.setMessasge(e.getMessage());
			response.setResponse(false);
			e.printStackTrace();
			
		} finally {
			try {
				stmt.close();				
				transactionManager.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			conn = null;
		}

		return "N";

	}
	

	public String getOldCustomerID() {
		return oldCustomerID;
	}

	public void setOldCustomerID(String oldCustomerID) {
		this.oldCustomerID = oldCustomerID;
	}

	public String getNewCustomer_id() {
		return newCustomer_id;
	}

	public void setNewCustomer_id(String newCustomer_id) {
		this.newCustomer_id = newCustomer_id;
	}

	
	public  String keepHistory(String newID, String oldID){
		
		ResponseDTO response = new ResponseDTO();
		TransactionManager transactionManager = new TransactionManager();
		Connection conn = transactionManager.getConnection();

		Statement stmt = null;
		
		String sql=	"INSERT INTO CUSTOMER_ID_CHANGE_HISTORY (OLD_ID, NEW_ID, CHANGE_DATE)"+
					"VALUES ('"+oldID+"', '"+newID+"', SYSDATE)";

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			transactionManager.commit();

			
		} catch (Exception e) {
			response.setMessasge(e.getMessage());
			response.setResponse(false);
			e.printStackTrace();
			
		} finally {
			try {
				stmt.close();				
				transactionManager.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			conn = null;
		}

		
		return "successfully Inserted";
	}
	

}
