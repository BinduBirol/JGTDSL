package org.jgtdsl.models;

//package org.pgcl.models;
//package org.jgtdsl.models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.driver.OracleCallableStatement;

import org.jgtdsl.dto.ClearnessDTO;
import org.jgtdsl.dto.ResponseDTO;
import org.jgtdsl.dto.ipg.IpgResponse;
import org.jgtdsl.dto.ipg.PaymentMethod;
import org.jgtdsl.utils.cache.CacheUtil;
import org.jgtdsl.utils.connection.ConnectionManager;
import org.jgtdsl.utils.connection.TransactionManager;

public class IpgService {

	public static List<PaymentMethod> getPaymentMethods() {
		String cKey = "PAYMENT_METHODS";
		ArrayList<PaymentMethod> paymentMethodList = CacheUtil
				.getListFromCache(cKey, PaymentMethod.class);
		if (paymentMethodList != null)
			return paymentMethodList;
		else
			paymentMethodList = new ArrayList<PaymentMethod>();

		PaymentMethod method = null;
		Connection conn = ConnectionManager.getConnection();
		String sql = "";

		sql = " Select * from IPG_METHODS order by id ";

		PreparedStatement stmt = null;
		ResultSet r = null;
		try {
			stmt = conn.prepareStatement(sql);
			r = stmt.executeQuery();
			while (r.next()) {
				method = new PaymentMethod();
				method.setId(r.getString("ID"));
				method.setName(r.getString("NAME"));
				method.setImagUrl("/JGTDSL_WEB/resources/images/ipg/"
						+ r.getString("IMAGE_URL"));
				paymentMethodList.add(method);
			}
			CacheUtil.setListToCache(cKey, paymentMethodList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				ConnectionManager.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			conn = null;
		}

		return paymentMethodList;
	}

	public static PaymentMethod getPaymentMethod(String id) {
		String cKey = "PAYMENT_METHOD_" + id;
		PaymentMethod paymentMethod = (PaymentMethod) CacheUtil
				.getObjFromCache(cKey);
		if (paymentMethod != null)
			return paymentMethod;

		Connection conn = ConnectionManager.getConnection();
		String sql = "";

		sql = " Select * from IPG_METHODS where id =? ";

		PreparedStatement stmt = null;
		ResultSet r = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			r = stmt.executeQuery();
			while (r.next()) {
				paymentMethod = new PaymentMethod();
				paymentMethod.setId(r.getString("ID"));
				paymentMethod.setName(r.getString("NAME"));
				paymentMethod.setImagUrl(r.getString("IMAGE_URL"));
			}
			CacheUtil.setObjToCache(cKey, paymentMethod);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				ConnectionManager.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			conn = null;
		}

		return paymentMethod;
	}

	public ResponseDTO initiateTransaction(String transId, String customerId,
			String paymentMethod, Double totalAmount,
			List<ClearnessDTO> selectedBills) {
		
		
		
		ResponseDTO response = new ResponseDTO();
		TransactionManager transactionManager = new TransactionManager();
		Connection conn = transactionManager.getConnection();

		PreparedStatement mstStmt = null;
		PreparedStatement dtlStmt = null;
		// VAliation goes here....

		String sqlIpgMst = "Insert into IPG_MST(TRANSACTION_ID, CUSTOMER_ID, PAYMENT_METHOD, TOTAL_AMOUNT, STATUS) "
				+ " Values(?,?,?,?,?)";
		String sqlIpgDtl = "Insert into IPG_DTL( TRANSACTION_ID, CUSTOMER_ID, BILL_ID, BILL_AMOUNT, SURCHARGE_AMOUNT, TOTAL_AMOUNT) "
				+ " Values(?,?,?,?,?,?)";

		try {

			mstStmt = conn.prepareStatement(sqlIpgMst);
			mstStmt.setString(1, transId);
			mstStmt.setString(2, customerId);
			mstStmt.setString(3, paymentMethod);
			mstStmt.setDouble(4, totalAmount);
			mstStmt.setFloat(5, 201);

			mstStmt.execute();
			
			for (ClearnessDTO bill : selectedBills) {
				dtlStmt = conn.prepareStatement(sqlIpgDtl);
				dtlStmt.setString(1, transId);
				dtlStmt.setString(2, customerId);
				dtlStmt.setString(3, bill.getBillId());
				dtlStmt.setDouble(4, bill.getDueAmount());
				dtlStmt.setDouble(5, bill.getDueSurcharge());
				dtlStmt.setDouble(6, bill.getDueAmount()+bill.getDueSurcharge());

			
				dtlStmt.addBatch();

				dtlStmt.executeBatch(); // exception
			}

			transactionManager.commit();

		}

		catch (Exception e) {
			response.setMessasge(e.getMessage());
			response.setResponse(false);
			e.printStackTrace();
			try {
				transactionManager.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} // exception
		finally {
			try {
				mstStmt.close();
				dtlStmt.close();
				transactionManager.close();
			} catch (Exception e) // exception
			{
				e.printStackTrace();
			}
			mstStmt = null;
			dtlStmt = null;
			conn = null;
		}

		return response;

	}

	public ResponseDTO saveResponse(IpgResponse ipgResponse) {

		ResponseDTO response = new ResponseDTO();
		Connection conn = ConnectionManager.getConnection();
		OracleCallableStatement stmt = null;

		// stmt = (OracleCallableStatement)
		// conn.prepareCall("{ call Save_Sms_Defaulter(?,?,?,?,?,?)  }");
		int response_code = 0;
		String response_msg = "";

		try {

			stmt = (OracleCallableStatement) conn
					.prepareCall("{ call Save_IPG_Bill(?,?,?,?,?,?,?,?,?) }");

			stmt.setString(1, ipgResponse.getTransId());
			stmt.setString(2, ipgResponse.getIpgTrxId());
			stmt.setString(3, ipgResponse.getTxnStatus());
			stmt.setString(4, ipgResponse.getError_msg());
			stmt.setString(5, ipgResponse.getCard_no());
			stmt.setString(6, ipgResponse.getCardName());
			stmt.setInt(7, ipgResponse.getTxnStatus().equals("SUCCESS") ? 200
					: 444);

			stmt.registerOutParameter(8, java.sql.Types.INTEGER);
			stmt.registerOutParameter(9, java.sql.Types.VARCHAR);

			stmt.executeUpdate();
			response_code = stmt.getInt(8);
			response_msg = (stmt.getString(9)).trim();

			System.out.println("***Rsponse Code:" + response_code
					+ ", Response Msg: " + response_msg + "**********");
		}

		catch (Exception e) {
			e.printStackTrace();

		}

		finally {
			try {
				stmt.close();
				ConnectionManager.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			conn = null;
		}

		return response;
	}

}
