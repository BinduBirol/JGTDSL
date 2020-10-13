package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;
import org.jgtdsl.dto.AdayBokeyaDTO;
import org.jgtdsl.dto.AddressDTO;
import org.jgtdsl.dto.ClearnessDTO;
import org.jgtdsl.dto.CustomerApplianceDTO;
import org.jgtdsl.dto.CustomerConnectionDTO;
import org.jgtdsl.dto.CustomerDTO;
import org.jgtdsl.dto.CustomerPersonalDTO;
import org.jgtdsl.dto.ResponseDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.ConnectionStatus;
import org.jgtdsl.enums.ConnectionType;
import org.jgtdsl.enums.MeteredStatus;
import org.jgtdsl.enums.Month;
import org.jgtdsl.enums.Area;
import org.jgtdsl.models.CustomerService;
import org.jgtdsl.models.MeterService;
import org.jgtdsl.utils.cache.CacheUtil;
import org.jgtdsl.utils.connection.ConnectionManager;
import org.jgtdsl.utils.connection.TransactionManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfBorderArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

public class NoticeForSecurityAndBG extends ActionSupport implements
		ServletContextAware {
	ClearnessDTO clearnessDTO = new ClearnessDTO();
	// ArrayList<ClearnessDTO> dueMonthList=new ArrayList<ClearnessDTO>();
	ClearnessDTO cto = new ClearnessDTO();
	
	static UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");	

	Connection conn = ConnectionManager.getConnection();

	private static final long serialVersionUID = 8854240739341830184L;
	private String customer_id;
	private String issue_date;
	private String due_date;
	

	private  String report_for; 
	private String certification_id;

	

	private String area;
	private String collection_month;

	private String calender_year;
	
	private ServletContext servlet;
	
	String yearsb;
	ArrayList<ClearnessDTO> CustomerList = new ArrayList<ClearnessDTO>();
	CustomerDTO customer = new CustomerDTO();
	ClearnessDTO customerInfo;
	MeterService ms = new MeterService();
	ArrayList<CustomerApplianceDTO> applianceList = new ArrayList<CustomerApplianceDTO>();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
	Date date = new Date();
	
	
	ByteArrayOutputStream out = null;
	Document document = new Document();

	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat("##########0.000");

//	UserDTO loggedInUser = (UserDTO) ServletActionContext.getRequest()
//			.getSession().getAttribute("user");

	// Connection conn = ConnectionManager.getConnection();

	public String execute() throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//readers = new ArrayList<PdfReader>();
				
		String fileName = "notice.pdf";

		try{
			//document.open();
			document.setPageSize(PageSize.A4);
			document.setMargins(10, 10, 10, 10);
			// left,right,top,bottom
			//String fileName = "";

			
			if(report_for.equals("securityandBG")){
				 generatePdf_for_SecurityBG_Notice();
			}
			
			
				
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(out, response,fileName);
			document=null;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
}

	
	private void generatePdf_for_SecurityBG_Notice() throws FileNotFoundException, IOException {
		
		try{
			
			ArrayList<AdayBokeyaDTO> categoryList = new ArrayList<AdayBokeyaDTO>();
			ArrayList<AdayBokeyaDTO> securityAndBG = new ArrayList<AdayBokeyaDTO>();
			ArrayList<AdayBokeyaDTO> priceDate = new ArrayList<AdayBokeyaDTO>();
			
			
			try{
				String load_sql = "select cc.CUSTOMER_ID CUSTOMER_ID, MAX_LOAD, CPI.FULL_NAME CUSTOMER_NAME, CA.ADDRESS_LINE1 ADDRESS " +
									"	FROM customer_connection cc, customer_personal_info cpi, customer_address ca " +
									"	where CC.CUSTOMER_ID=CPI.CUSTOMER_ID" +
									"		and CC.CUSTOMER_ID=CA.CUSTOMER_ID" +
									"		and cc.customer_id='"+customer_id+"'";

				PreparedStatement psl = conn.prepareStatement(load_sql);
				ResultSet resultSet = psl.executeQuery();
				
				while(resultSet.next()){
					AdayBokeyaDTO categoryListDTO = new AdayBokeyaDTO();
					categoryListDTO.setCustomer_id(resultSet.getString("CUSTOMER_ID"));
					categoryListDTO.setAmount_bill(resultSet.getDouble("MAX_LOAD"));
					categoryListDTO.setCustomer_name(resultSet.getString("CUSTOMER_NAME"));
					categoryListDTO.setAddress(resultSet.getString("ADDRESS"));
					categoryList.add(categoryListDTO);
				}
				
				resultSet.close();
			    psl.close();
			    
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			String category_id=customer_id.substring(2,4);
			
			try{
				String price_sql="SELECT PRICE, to_char(EFFECTIVE_FROM) EFFECTIVE_FROM, MCC.CATEGORY_NAME CATEGORY_NAME "
									+ "		  FROM mst_tariff mt, mst_customer_category mcc"
									+ "		 WHERE     CUSTOMER_CATEGORY_ID = '"+category_id+"' "
									+ "		       AND TARIFF_ID IN (SELECT MAX (TARIFF_ID) "
									+ "		                           FROM mst_tariff "
									+ "		                          WHERE CUSTOMER_CATEGORY_ID = '"+category_id+"')"
									+ "            and MT.CUSTOMER_CATEGORY_ID=MCC.CATEGORY_ID";
				
				PreparedStatement psl = conn.prepareStatement(price_sql);
				ResultSet resultSet = psl.executeQuery();
				
				while(resultSet.next()){
					AdayBokeyaDTO priceAndDateDTO = new AdayBokeyaDTO();
					priceAndDateDTO.setAmount_bill(resultSet.getDouble("PRICE"));
					priceAndDateDTO.setDate(resultSet.getString("EFFECTIVE_FROM"));
					priceAndDateDTO.setCategory_name(resultSet.getString("CATEGORY_NAME"));
					priceDate.add(priceAndDateDTO);
				}
				
				resultSet.close();
				psl.close();
				
			}catch(SQLException e){
				e.printStackTrace();
				}
			
			
			try{
				String securityBG_sql="SELECT TOTAL_DEPOSIT "
										+ "  FROM mst_deposit "
										+ " WHERE     CUSTOMER_ID = '"+customer_id+"' "
										+ "       AND DEPOSIT_PURPOSE = 1 "
										+ "       AND DEPOSIT_TYPE = 0 "
										+ "       AND DEPOSIT_ID IN "
										+ "              (SELECT MAX (DEPOSIT_ID) "
										+ "                 FROM mst_deposit "
										+ "                WHERE     CUSTOMER_ID = '"+customer_id+"' "
										+ "                      AND DEPOSIT_PURPOSE = 1 "
										+ "                      AND DEPOSIT_TYPE = 0) "
										+ "union all "
										+ " "
										+ "SELECT TOTAL_DEPOSIT "
										+ "  FROM mst_deposit "
										+ " WHERE     CUSTOMER_ID = '"+customer_id+"' "
										+ "       AND DEPOSIT_PURPOSE = 1 "
										+ "       AND DEPOSIT_TYPE = 1 "
										+ "       AND DEPOSIT_ID IN "
										+ "              (SELECT MAX (DEPOSIT_ID) "
										+ "                 FROM mst_deposit "
										+ "                WHERE     CUSTOMER_ID = '"+customer_id+"' "
										+ "                      AND DEPOSIT_PURPOSE = 1 "
										+ "                      AND DEPOSIT_TYPE = 1)";
				
				PreparedStatement psl = conn.prepareStatement(securityBG_sql);
				
				ResultSet resultSet = psl.executeQuery();
				
				while(resultSet.next()){
					AdayBokeyaDTO securityBGDTO = new AdayBokeyaDTO();
					securityBGDTO.setAmount_bill(resultSet.getDouble("TOTAL_DEPOSIT"));
					securityAndBG.add(securityBGDTO);
				}
				
				resultSet.close();
				psl.close();
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			
//			HttpServletResponse response = ServletActionContext.getResponse();
//			
//			//readers = new ArrayList<PdfReader>();
//					
//			String fileName = "notice.pdf";
			
			PdfContentByte over = null;
			PdfStamper stamp = null;
			
			PdfReader reader = null;
			ByteArrayOutputStream certificate = null;
			List<PdfReader> readers = null;
			
			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN,
					BaseFont.WINANSI, BaseFont.EMBEDDED);
			BaseFont bfb = BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.WINANSI, BaseFont.EMBEDDED);
			Font red = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
			
			//Font red = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
			
			String picPath = servlet.getRealPath("/resources/images/logo/JG.png");
			String realPathC = servlet.getRealPath("/resources/staticPdf/SecurityBGNotice.pdf");
			
			Image img = Image.getInstance(picPath);
			
			float x = 90;
		    float y = 710;
		    float w = img.getScaledWidth();
		    float h = img.getScaledHeight();
		    img.scaleAbsolute(30f, 33f);
		    img.setAbsolutePosition(x, y);
		    
			
			
//			realPathD = servlet
//					.getRealPath("/resources/staticPdf/CertificateD.pdf");

			readers = new ArrayList<PdfReader>();
			//document = new Document();
			out = new ByteArrayOutputStream();
			// left,right,top,bottom
			
			
			reader = new PdfReader(new FileInputStream(realPathC));
			certificate = new ByteArrayOutputStream();
			stamp = new PdfStamper(reader, certificate);
			over = stamp.getOverContent(1);
			
			stamp.getOverContent(1).addImage(img);
		    Rectangle linkLocation = new Rectangle(x, y, x + w, y + h);
		    PdfDestination destination = new PdfDestination(PdfDestination.FIT);
		    PdfAnnotation link = PdfAnnotation.createLink(stamp.getWriter(),
		            linkLocation, PdfAnnotation.HIGHLIGHT_INVERT,
		            reader.getNumberOfPages(), destination);
		    link.setBorder(new PdfBorderArray(0, 0, 0));
		    stamp.addAnnotation(link, 1);
			
			over.beginText();
			
			// certification ID
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,certification_id, 100, 662, 0);
			

			// Date

			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,issue_date, 492, 662, 0);
			
//			ArrayList<AdayBokeyaDTO> categoryList = new ArrayList<AdayBokeyaDTO>();
//			ArrayList<AdayBokeyaDTO> securityAndBG = new ArrayList<AdayBokeyaDTO>();
//			ArrayList<AdayBokeyaDTO> priceDate = new ArrayList<AdayBokeyaDTO>();
			
			// name
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,categoryList.get(0).getCustomer_name(), 64, 640, 0);
			
			// address 
			
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,categoryList.get(0).getAddress(), 64, 620, 0);
			
			// customer id
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,categoryList.get(0).getCustomer_id(), 145, 600, 0);
			
			// max load
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,categoryList.get(0).getAmount_bill().toString(), 75, 489, 0);
//			
			
			// effective date
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,priceDate.get(0).getDate().toString(), 270, 489, 0);
			
			// category
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Captive Power", 380, 489, 0);
			
			// Rate
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,priceDate.get(0).getAmount_bill().toString(), 115, 473, 0);
			
			double currentTotal = categoryList.get(0).getAmount_bill() * priceDate.get(0).getAmount_bill();
			
			// update total 
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,taka_format.format(currentTotal), 75, 458, 0);
			
			// existing security amount
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,securityAndBG.get(0).getAmount_bill().toString(), 460, 458, 0);
			
			// existing BG amount
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,securityAndBG.get(1).getAmount_bill().toString(), 145, 442, 0);
			
			double securityBGTotal = securityAndBG.get(0).getAmount_bill() + securityAndBG.get(1).getAmount_bill();
			
			// existing total 
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,taka_format.format(securityBGTotal), 310, 442, 0);
			
			// update total 2
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,taka_format.format(currentTotal), 120, 426, 0);
			
			// existing total 2
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,taka_format.format(securityBGTotal), 220, 426, 0);
			
			double toPayNow = currentTotal-securityBGTotal;
			
			// to pay now 
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,taka_format.format(toPayNow), 340, 426, 0);
			
			// security to pay
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Not Given", 130, 410, 0);
			
			// bg to pay
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Not Given", 350, 410, 0);
			
			// due date
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,due_date, 167, 347, 0);
			
			// security to pay 2
			
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Not Given", 342, 347, 0);
			
			// bg to pay 2
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Not Given", 280, 331, 0);
			
			// area name
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,loggedInUser.getArea_name(), 185, 220, 0);
			
			// area name
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,loggedInUser.getArea_name(), 290, 124, 0);
			
			
			over.endText();
			stamp.close();
			readers.add(new PdfReader(certificate.toByteArray()));
			if (readers.size() > 0) {
				PdfWriter writer = PdfWriter.getInstance(document, out);

				document.open();

				PdfContentByte cb = writer.getDirectContent();
				PdfReader pdfReader = null;
				PdfImportedPage page;

				for (int k = 0; k < readers.size(); k++) {
					document.newPage();
					pdfReader = readers.get(k);
					page = writer.getImportedPage(pdfReader, 1);
					cb.addTemplate(page, 0, 0);
				}
//				document.close();
//				ReportUtil rptUtil = new ReportUtil();
//				rptUtil.downloadPdf(out, response, fileName);
//				document = null;

			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public ClearnessDTO getClearnessDTO() {
		return clearnessDTO;
	}

	

	public void setClearnessDTO(ClearnessDTO clearnessDTO) {
		this.clearnessDTO = clearnessDTO;
	}

	public ClearnessDTO getCto() {
		return cto;
	}

	public String getCertification_id() {
		return certification_id;
	}

	public void setCertification_id(String certification_id) {
		this.certification_id = certification_id;
	}

	public void setCto(ClearnessDTO cto) {
		this.cto = cto;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCollection_month() {
		return collection_month;
	}

	public void setCollection_month(String collection_month) {
		this.collection_month = collection_month;
	}

	public String getDue_date() {
		return due_date;
	}


	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	

	public String getCalender_year() {
		return calender_year;
	}

	public void setCalender_year(String calender_year) {
		this.calender_year = calender_year;
	}

	
	public ServletContext getServlet() {
		return servlet;
	}

	public void setServlet(ServletContext servlet) {
		this.servlet = servlet;
	}

	public static DecimalFormat getTaka_format() {
		return taka_format;
	}

	public static void setTaka_format(DecimalFormat taka_format) {
		DefaulterCCertificate.taka_format = taka_format;
	}

	public static DecimalFormat getConsumption_format() {
		return consumption_format;
	}

	public static void setConsumption_format(DecimalFormat consumption_format) {
		DefaulterCCertificate.consumption_format = consumption_format;
	}

	public UserDTO getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(UserDTO loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}
	
	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}
	
	

	public String getReport_for() {
		return report_for;
	}


	public void setReport_for(String report_for) {
		this.report_for = report_for;
	}


	public String[] spitSrting(String base, int size) {
		char[] separator = { ' ', '.', ',', ';', ':' };
		boolean separatorfound = false;
		String s1[] = new String[2];
		outer: for (int j = size; j >= 0; j--) {
			for (int k = 0; k < separator.length; k++) {
				if (separator[k] == base.charAt(j)) {
					s1[0] = base.substring(0, j + 1);
					s1[1] = base.substring(j + 1, base.length());
					separatorfound = true;
					break outer;
				}
			}

		}
		if (!separatorfound) {
			int x = 0;
			s1[0] = base.substring(0, size - 10);
			s1[1] = base.substring(size - 10, base.length());
		}
		return s1;
	}

	public CustomerDTO getCustomerInfo(String customer_id) {
		CustomerDTO customer = null;

		Connection conn = ConnectionManager.getConnection();

		String sql = " Select * From MVIEW_CUSTOMER_INFO Where Customer_Id=? ";

		PreparedStatement stmt = null;
		ResultSet r = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, customer_id);
			r = stmt.executeQuery();

			if (r.next()) {
				customer = new CustomerDTO();
				customer.setCustomer_id(r.getString("CUSTOMER_ID"));
				customer.setAddress(r.getString("ADDRESS"));
				customer.setCustomer_name(r.getString("FULL_NAME"));
			}
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

		return customer;

	}

//	// /get multiple customer list
//	private ArrayList<ClearnessDTO> getCustomerList(String from_cus_id,
//			String to_cus_id, String cust_cat_id, String area) {
//
//		ArrayList<ClearnessDTO> custList = new ArrayList<ClearnessDTO>();
//		PreparedStatement ps1 = null;
//		ResultSet resultSet = null;
//		Connection conn = null;
//		if (collection_month.length() < 2) {
//			collection_month = "0" + collection_month;
//		}
//		String type = null;
//		if (from_cus_id.isEmpty()) {
//			type = area + this.customer_category;
//		} else {
//			type = from_cus_id.substring(0, 4);
//		}
//		String bill_table;
//		if (type.equalsIgnoreCase(area + "01")
//				|| type.equalsIgnoreCase(area + "09")) {
//			bill_table = "BILL_NON_METERED";
//		} else {
//			bill_table = "BILL_METERED";
//		}
//		String whereClause = null;
//		if (from_cus_id.isEmpty() && to_cus_id.isEmpty()) {
//			whereClause = "      AND BI.CUSTOMER_CATEGORY='"
//					+ this.customer_category + "'  ";
//		} else {
//			whereClause = "         AND BI.CUSTOMER_ID BETWEEN '" + from_cus_id
//					+ "' AND '" + to_cus_id + "' ";
//		}
//		try {
//			String transaction_sql = "  SELECT bi.CUSTOMER_ID, COUNT (*) cnt "
//					+ "    FROM "
//					+ bill_table
//					+ " bi, CUSTOMER_CONNECTION cc "
//					+ "   WHERE     BI.CUSTOMER_ID = CC.CUSTOMER_ID "
//					+ "         AND CC.STATUS = 1 "
//					+
//					// "         AND bi.STATUS = 1 " +
//					"         AND bi.area_id = '"
//					+ area
//					+ "' "
//					+ whereClause
//					+ "                 AND BILL_YEAR || LPAD (BILL_MONTH, 2, 0) <= '"
//					+ calender_year
//					+ collection_month
//					+ "'  GROUP BY BI.CUSTOMER_ID, CUSTOMER_CATEGORY, bi.AREA_ID "
//					+ "  HAVING COUNT (*) >= 1 ";
//
//			conn = ConnectionManager.getConnection();
//			ps1 = conn.prepareStatement(transaction_sql);
//			resultSet = ps1.executeQuery();
//			while (resultSet.next()) {
//				ClearnessDTO ClearnessDTO = new ClearnessDTO();
//				ClearnessDTO.setCustomerID(resultSet.getString("CUSTOMER_ID"));
//				custList.add(ClearnessDTO);
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			try {
//				ps1.close();
//				resultSet.close();
//				conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		return custList;
//
//	}

	// insert into database
	public void insertClarificationHistory(String cust_id, String issue_date,
			String insert_by, Double dues_status) {
		ResponseDTO response = new ResponseDTO();

		if (collection_month.length() < 2) {
			collection_month = "0" + collection_month;
		}
		TransactionManager transactionManager = new TransactionManager();
		Connection conn = transactionManager.getConnection();

		// response=validateReconnInfo(reconn,disconn);
		// if(response.isResponse()==false)
		// return response;

		String sqlInsert = "INSERT INTO CLARIFICATION_HISTORY ( "
				+ "   CUSTOMER_ID, CALENDER_YEAR, ISSUE_DATE,  "
				+ "   STATUS, DUES_STATUS, INSERTED_ON,  "
				+ "   INSERTED_BY, CALENDER_MONTH, CERTIFICATION_ID )  "
				+ "   VALUES ( ?,?,sysdate,?,?,sysdate,?,?,?)";

		String checkIsAvailable = "Select count(customer_id) CUS_COUNT from CLARIFICATION_HISTORY where CALENDER_MONTH=? and CALENDER_YEAR=? and customer_id=?";

		PreparedStatement stmt = null;
		ResultSet r = null;
		int count = 0;

		try {
			stmt = conn.prepareStatement(checkIsAvailable);
			stmt.setString(1, collection_month);
			stmt.setString(2, calender_year);
			stmt.setString(3, cust_id);
			r = stmt.executeQuery();
			if (r.next())
				count = r.getInt("CUS_COUNT");

			if (count == 0) {
				stmt = conn.prepareStatement(sqlInsert);
				stmt.setString(1, cust_id);
				stmt.setString(2, calender_year);
				stmt.setInt(3, 1); // / 1 means all generated(approved)
				stmt.setDouble(4, dues_status);
				stmt.setString(5, insert_by);
				stmt.setString(6, collection_month);
				stmt.setString(7, certification_id);
				stmt.execute();
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

		return;
	}
}
