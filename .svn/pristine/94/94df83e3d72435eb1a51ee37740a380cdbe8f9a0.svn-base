package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;
import org.jgtdsl.dto.ClearnessDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

public class NoticeForDueBill extends ActionSupport implements ServletContextAware {
	
	private String customer_id;	
	private String month_to;	
	private String year_to;
	private String issue_date;
	private String due_date;
	
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
	static UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
	private static final long serialVersionUID = 8854240739341830184L;
	private ServletContext servlet;

	ByteArrayOutputStream out = null;
	Document document = new Document();
	
	public HttpServletResponse response = ServletActionContext.getResponse();
	public HttpServletRequest request;
	
	public String execute() throws Exception {	
		try{		
			document.setPageSize(PageSize.A4);			
			mainReport();			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String mainReport() throws DocumentException, IOException {
		HttpServletResponse response = ServletActionContext.getResponse();

		PdfReader reader = null;
		ByteArrayOutputStream certificate = null;
		List<PdfReader> readers = null;
		String realPath = "";

		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream out = null;
		
		String fileName = "";
		readers = new ArrayList<PdfReader>();
		
		BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.WINANSI, BaseFont.EMBEDDED);
		BaseFont bfb = BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.WINANSI, BaseFont.EMBEDDED);
		Font red = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);

		try {
			realPath = servlet.getRealPath("/resources/staticPdf/due_bill_notice.pdf");

			document = new Document();
			out = new ByteArrayOutputStream();
			// left,right,top,bottom
			fileName = "DueBillNotice.pdf";

			PdfContentByte over = null;
			PdfStamper stamp = null;
			
			reader = new PdfReader(new FileInputStream(realPath));
			certificate = new ByteArrayOutputStream();
			stamp = new PdfStamper(reader, certificate);
			over = stamp.getOverContent(1);
			over.beginText();
			
			//issue no
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,String.valueOf(serialVersionUID), 110, 616, 0);
			
			//issue date
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,this.issue_date, 500, 616, 0);
			
			//due date
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,this.due_date, 400, 368, 0);
			
			//area Name
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,loggedInUser.getArea_name(), 185, 216, 0);			
			
			String category_id= customer_id.substring(2,4);
			ClearnessDTO customer_info= getCustomerInfo(customer_id,category_id, year_to, month_to);
			
			//Customer Name
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,customer_info.getCustomerName(), 72, 560, 0);
			
			//Customer address
			over.setFontAndSize(bf, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,customer_info.getCustomerAddress(), 72, 545, 0);
			
			//Customer id
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,customer_info.getCustomerID(), 140, 524, 0);
			
			//Customer tk			
			String due_tk= customer_info.getDueAmount()+" ("+customer_info.getAmountInWords()+")";			
			over.setFontAndSize(bfb, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,due_tk, 130, 425, 0);
			
			//month year			
			String mt=  Month.values()[Integer.valueOf(month_to) - 1] + ",";
			
			over.setFontAndSize(bf, 10);
			over.showTextAligned(PdfContentByte.ALIGN_LEFT,mt+year_to, 130, 440, 0);
					

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
				document.close();
				ReportUtil rptUtil = new ReportUtil();
				rptUtil.downloadPdf(out, response, fileName);
				document = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	private ClearnessDTO getCustomerInfo(String customer_id, String area_id,
			String year, String month) {
		Connection conn = ConnectionManager.getConnection();
		ResultSet resultSet = null;
		Statement st = null;

		ClearnessDTO ctrInfo = new ClearnessDTO();
		String type = customer_id.substring(0, 4);
		String bill_table;
		if (type.equalsIgnoreCase(area_id + "01")
				|| type.equalsIgnoreCase(area_id + "09")) {
			bill_table = "BILL_NON_METERED";
		} else {
			bill_table = "BILL_METERED";
		}

		try {

			String customer_info_sql = "SELECT * "
					+ "  FROM (  SELECT bi.CUSTOMER_ID, "
					+ "                 CUSTOMER_CATEGORY, "
					+ "                 bi.AREA_ID,  getBurner(bi.customer_id) burner, "
					+ "                 LISTAGG ( "
					+ "                       TO_CHAR (TO_DATE (BILL_MONTH, 'MM'), 'MON') "
					+ "                    || ' ' "
					+ "                    || SUBSTR (BILL_YEAR, 3), "
					+ "                    ',') "
					+ "                 WITHIN GROUP (ORDER BY BILL_YEAR ASC, BILL_MONTH ASC) "
					+ "                    AS DUEMONTH, "
					+ "                 SUM ( "
					+ "                      BILLED_AMOUNT "
					+ "                    + CALCUALTESURCHARGE (BILL_ID, "
					+ "                                          TO_CHAR (SYSDATE, 'dd-mm-YYYY'))) "
					+ "                    totalamount, "
					+ " NUMBER_SPELLOUT_FUNC ( "
					+ "                    TO_NUMBER ( "
					+ "                       SUM ( "
					+ "                            BILLED_AMOUNT "
					+ "                          + CALCUALTESURCHARGE ( "
					+ "                               BILL_ID, "
					+ "                               TO_CHAR (SYSDATE, 'dd-mm-YYYY'))), "
					+ "                       '99999999999999.99')) "
					+ "                    inwords, "
					+ "                 COUNT (*) cnt " + "            FROM "
					+ bill_table
					+ " bi, CUSTOMER_CONNECTION cc "
					+ "           WHERE     BI.CUSTOMER_ID = CC.CUSTOMER_ID "
					+ "                 AND CC.STATUS = 1 "
					+ "                 AND bi.STATUS = 1 "					
					+ " AND BI.CUSTOMER_ID = '"
					+ customer_id
					+ "' "

					// "                 And bi.CUSTOMER_CATEGORY= " +
					+ "                 AND BILL_YEAR || LPAD (BILL_MONTH, 2, 0) <= '"
					+ year
					+ month
					+ "' "
					+ "        GROUP BY BI.CUSTOMER_ID, CUSTOMER_CATEGORY, bi.AREA_ID "
					+ "          HAVING COUNT (*) >= 1) tmp1, "
					+ "       (SELECT AA.CUSTOMER_ID, "
					+ "               BB.FULL_NAME, "
					+ "               BB.MOBILE, "
					+ "               AA.ADDRESS_LINE1, "
					+ "               AA.ADDRESS_LINE2 "
					+ "          FROM CUSTOMER_ADDRESS aa, CUSTOMER_PERSONAL_INFO bb "
					+ "         WHERE AA.CUSTOMER_ID = BB.CUSTOMER_ID) tmp2 "
					+ " WHERE tmp1.CUSTOMER_ID = tmp2.CUSTOMER_ID ";

			st = conn.createStatement();// Statement(customer_info_sql);

			resultSet = st.executeQuery(customer_info_sql);

			while (resultSet.next()) {

				ctrInfo.setCustomerID(resultSet.getString("CUSTOMER_ID"));
				ctrInfo.setCustomerName(resultSet.getString("FULL_NAME"));
				ctrInfo.setCustomerAddress(resultSet.getString("ADDRESS_LINE1"));
				ctrInfo.setDueMonth(resultSet.getString("DUEMONTH"));
				ctrInfo.setDueAmount(resultSet.getDouble("TOTALAMOUNT"));
				ctrInfo.setArea(resultSet.getString("AREA_ID"));
				ctrInfo.setAmountInWords(resultSet.getString("INWORDS"));

				String burner = resultSet.getString("BURNER");
				String[] brnrArray = burner.split("#");
				ctrInfo.setSingle_burner(Integer.parseInt(brnrArray[0]));
				ctrInfo.setDouble_burner(Integer.parseInt(brnrArray[1]));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				st.close();
				resultSet.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ctrInfo;
	}

	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	
	public String getMonth_to() {
		return month_to;
	}
	public void setMonth_to(String month_to) {
		this.month_to = month_to;
	}
	
	public String getYear_to() {
		return year_to;
	}
	public void setYear_to(String year_to) {
		this.year_to = year_to;
	}
	public String getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public ServletContext getServlet() {
		return servlet;
	}
	public void setServlet(ServletContext servlet) {
		this.servlet = servlet;
	}
	@Override
	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}
	
}
