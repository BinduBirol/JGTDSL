package org.jgtdsl.actions.sms;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jgtdsl.models.SMSService;

public class SmsSenderExtra  implements Runnable {

	private String mobile;
	private String otp;
	private String customerID;
	private String apptime;
	private String billMonth;
	private String billYear;
	private String name;
	private String operation;
	
	private String text;


	public void run() {	
	
		sendSMSGP();	

	}

	
	
	public void sendSMSGP() {
        String targetURL = "https://cmp.grameenphone.com/gpcmpapi/messageplatform/controller.home";
        URL url;
        HttpURLConnection connection = null;
        try {
            StringBuilder urlParameter = new StringBuilder();
            urlParameter.append("username=").append(URLEncoder.encode("JGTDAdmin", "UTF-8"));
            urlParameter.append("&password=").append(URLEncoder.encode("Buet1JgtdsL2@11ct3", "UTF-8"));
            urlParameter.append("&apicode=").append(URLEncoder.encode("1", "UTF-8"));
            urlParameter.append("&msisdn=").append(URLEncoder.encode(this.mobile, "UTF-8"));
            urlParameter.append("&countrycode=").append(URLEncoder.encode("880", "UTF-8"));
            urlParameter.append("&cli=").append(URLEncoder.encode("JalalabdGas", "UTF-8"));
            urlParameter.append("&messagetype=").append(URLEncoder.encode("1", "UTF-8"));             
            urlParameter.append("&message=").append(URLEncoder.encode(this.text, "UTF-8"));            
            urlParameter.append("&messageid=").append(URLEncoder.encode("0", "UTF-8"));
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",""
                            + Integer.toString(urlParameter.toString()
                                    .getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameter.toString());
            wr.flush();
            wr.close();
           // ApplicationDAO.sendTextMSG_TT(this.appid, this.mobile);
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            System.out.println(response.toString());
            if(response.toString().substring(0, 3).equalsIgnoreCase("200"))
            	SMSService.sentCustSMS(this.customerID, this.billMonth, this.billYear );
            
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getOtp() {
		return otp;
	}



	public void setOtp(String otp) {
		this.otp = otp;
	}



	public String getCustomerID() {
		return customerID;
	}



	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}



	public String getApptime() {
		return apptime;
	}



	public void setApptime(String apptime) {
		this.apptime = apptime;
	}



	public String getBillMonth() {
		return billMonth;
	}



	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}



	public String getBillYear() {
		return billYear;
	}



	public void setBillYear(String billYear) {
		this.billYear = billYear;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getOperation() {
		return operation;
	}



	public void setOperation(String operation) {
		this.operation = operation;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}
		
	
	
	
	
	


}
