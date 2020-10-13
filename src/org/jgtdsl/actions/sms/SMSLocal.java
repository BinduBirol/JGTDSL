package org.jgtdsl.actions.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jgtdsl.utils.connection.ConnectionManager;

public class SMSLocal {

	
	public static void main(String[] args) {
		int total=0;
		Connection conn = ConnectionManager.getConnectionStatic();
		
		String sql= "select CUSTOMER_ID,MOBILE_NO,TEXT from TEMP_SMS_LOCAL where length(MOBILE_NO)=11 and  status='N' ";
		
		PreparedStatement stmt = null;
		ResultSet r = null;
		   
		try
		{
			stmt = conn.prepareStatement(sql);
			r = stmt.executeQuery();
			while (r.next())
			{
				
				SmsSenderExtra smsSender=new SmsSenderExtra();
				smsSender.setCustomerID(r.getString("CUSTOMER_ID"));
				smsSender.setMobile(r.getString("MOBILE_NO"));
				smsSender.setText(r.getString("TEXT"));
				
				
				Thread thread = new Thread(smsSender);
				thread.start();
				total++;
				if(total==50)
				{
					total=0;
					try{Thread.sleep(5000);}catch(Exception e){e.printStackTrace();};
				}
				
				
			}
			
		}
		catch (Exception e){e.printStackTrace();}
 		finally{try{stmt.close();ConnectionManager.closeConnection(conn);} catch (Exception e)
			{e.printStackTrace();}stmt = null;conn = null;}
		
		
		
		
		

	}

}
