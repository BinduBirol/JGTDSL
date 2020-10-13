package org.jgtdsl.actions.bank;

import java.sql.Connection;

import oracle.jdbc.OracleCallableStatement;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.MPGbankDTO;
import org.jgtdsl.dto.ResponseDTO;
import org.jgtdsl.dto.TransactionDTO;
import org.jgtdsl.utils.connection.ConnectionManager;
import org.jgtdsl.utils.connection.TransactionManager;

public class MpgBank extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<MPGbankDTO> mpg_bank;
	
	private String userid;
	private String password;
	private String bank_name;
	private String ipaddress;

	public String mpgBank(){
		
		mpg_bank= getMpgBank();		
        return SUCCESS;
	}
	
	public String createMpgUser(){		
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();		
		ResponseDTO response = new ResponseDTO();
		TransactionManager transactionManager = new TransactionManager();
		Connection conn = transactionManager.getConnection();
		String cryptPass= "";
		cryptPass= bCryptPasswordEncoder.encode(password);
		Statement stmt = null;
		
		String sql= " INSERT INTO MPG_USERINFO (USERNAME, PASSWORD, ROLE, FULLNAME) VALUES ('"+userid+"','"+cryptPass+"','ROLE_USER','"+bank_name+"' )";
						
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				transactionManager.commit();
				
				ResponseDTO procedureResponse= createMpgBank(bank_name, ipaddress);
				
				if(procedureResponse.isResponse()){					
					response.setMessasge(procedureResponse.getMessasge());
				}else{					
					response.setMessasge(procedureResponse.getMessasge());
				}
				
			} catch (Exception e) {
				response.setMessasge(e.getMessage());
				response.setResponse(false);
				transactionManager.rollback();
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

			setJsonResponse(response);
			return null;		
		
	}
	
	public ResponseDTO createMpgBank( String bankName,String ipAddress){
		ResponseDTO response = new ResponseDTO();
		TransactionManager transactionManager = new TransactionManager();
		Connection conn = transactionManager.getConnection();
		OracleCallableStatement stmt = null;
		
		String sql= "BEGIN mpg_create_bank(?,?,?); END; ";			
			
			try {
				stmt = (OracleCallableStatement) conn.prepareCall(sql);
				stmt.setString(1, bankName);
				stmt.setString(2, ipAddress);				
				stmt.registerOutParameter(3, java.sql.Types.VARCHAR);

				stmt.executeUpdate();
				response.setMessasge(stmt.getString(3));
				transactionManager.commit();
				
			} catch (Exception e) {
				response.setMessasge(e.getMessage());
				response.setResponse(false);
				transactionManager.rollback();
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
		
		return response;
	}
	
	
	public ArrayList<MPGbankDTO> getMpgBank(){
		ArrayList<MPGbankDTO> bank= new ArrayList<MPGbankDTO>();
		int sl=1;		
		Connection conn = ConnectionManager.getConnection();	 
		
		String sql= "  SELECT USERNAME, BANK_NAME, ADDRESS " +
				"    FROM MPG_USERINFO mu, MST_BANK_INFO mbi " +
				"   WHERE MU.FULLNAME = MBI.BANK_NAME " +
				"GROUP BY USERNAME, BANK_NAME, ADDRESS " +
				"order by BANK_NAME " ;		
		   
		   PreparedStatement stmt = null;
		   ResultSet r = null;
			try
			{
				stmt = conn.prepareStatement(sql);
				
				r = stmt.executeQuery();
				while (r.next())
				{
					MPGbankDTO bankdto= new MPGbankDTO();
					bankdto.setBank_name(r.getString("BANK_NAME"));	
					bankdto.setIp_address(r.getString("ADDRESS"));
					bankdto.setSl(sl);
					bankdto.setUserid(r.getString("USERNAME"));
					bank.add(bankdto);					
					sl++;
				}
			} 
			catch (Exception e){e.printStackTrace();}
	 		finally{try{stmt.close();ConnectionManager.closeConnection(conn);} catch (Exception e)
				{e.printStackTrace();}stmt = null;conn = null;}		
		return bank;
	}

	public ArrayList<MPGbankDTO> getMpg_bank() {
		return mpg_bank;
	}

	public void setMpg_bank(ArrayList<MPGbankDTO> mpg_bank) {
		this.mpg_bank = mpg_bank;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}	
	

}
