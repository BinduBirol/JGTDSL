package org.jgtdsl.reports;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.ClearnessDTO;
import org.jgtdsl.dto.CustomerApplianceDTO;
import org.jgtdsl.dto.CustomerDTO;
import org.jgtdsl.dto.LoadExceedReportDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Month;
import org.jgtdsl.models.MeterService;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfBorderArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class MaxLoadExceedNotice extends BaseAction {	
	static UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
	private String customer_id;
	private String bill_month;
	private String bill_year;
	private String issue_date;
	private ServletContext servlet;
	ByteArrayOutputStream out = null;
	Document document = new Document();
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
	
	public String execute() throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = "MaxLoadExceednotice-"+customer_id+".pdf";

		try{
			document.setPageSize(PageSize.A4);
			document.setMargins(10, 10, 10, 10);
			// left,right,top,bottom
			max_load_exceed_notice();				
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(out, response,fileName);
			document=null;			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;		
}

	private void max_load_exceed_notice() throws DocumentException, IOException {
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
		String realPathC = servlet.getRealPath("/resources/staticPdf/max_load_exceeded_request.pdf");
		String picPath = servlet.getRealPath("/resources/images/logo/JG.png");			
		Image img = Image.getInstance(picPath);		
		float x = 60;
	    float y = 730;
	    float w = img.getScaledWidth();
	    float h = img.getScaledHeight();
	    img.scaleAbsolute(30f, 33f);
	    img.setAbsolutePosition(x, y);
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
		ArrayList<LoadExceedReportDTO> loadExceedCustomerList=getInfoindividual();
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,this.getBill_year()+this.getBill_month()+this.getCustomer_id(), 115, 665, 0);			
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,this.getIssue_date(), 470, 665, 0);			
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,loadExceedCustomerList.get(0).getFull_name(), 140, 627, 0);
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,loadExceedCustomerList.get(0).getAddress(), 70, 610, 0);
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,this.getCustomer_id(), 165, 588, 0);	
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,consumption_format.format(loadExceedCustomerList.get(0).getMax_load()), 71, 487, 0);
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,Month.values()[Integer.valueOf(bill_month)-1]+", "+this.getBill_year().toString(), 380, 470, 0);		

		
		over.setFontAndSize(bfb, 10);
		over.setColorFill(BaseColor.RED);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,consumption_format.format(loadExceedCustomerList.get(0).getActual_consumption()), 71, 455, 0);
		
		over.setColorFill(BaseColor.BLACK);
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,consumption_format.format(loadExceedCustomerList.get(0).getMax_load()), 330, 430, 0);
		
		over.setFontAndSize(bfb, 10);
		over.showTextAligned(PdfContentByte.ALIGN_LEFT,loadExceedCustomerList.get(0).getArea_name(), 71, 270, 0);	
		
		
				
		
		
		
		
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
			
		}
		
	}

	private ArrayList<LoadExceedReportDTO> getInfoindividual(){
		Connection conn =null;
		ArrayList<LoadExceedReportDTO> customerInfoList= new ArrayList<LoadExceedReportDTO>();
		
		String varname1 = ""
				+ "SELECT tbl.CUSTOMER_ID, "
				+ "         mci.FULL_NAME, "
				+ "         mci.CATEGORY_ID, MCI.ADDRESS, "
				+ "         mci.CATEGORY_NAME, "
				+ "         mci.AREA_ID, "
				+ "         MCI.AREA_NAME, "
				+ "         BILLING_MONTH, "
				+ "         BILLING_YEAR, "
				+ "         BILL_DATE, "
				+ "         MAX_LOAD, "
				+ "         PMAX_LOAD, "
				+ "         ACTUAL_CONSUMPTION, "
				+ "         ACTUAL_CONSUMPTION - PMAX_LOAD DIFFERENCE, "
				+ "           ( (ACTUAL_CONSUMPTION - PMAX_LOAD) * 100) "
				+ "         / DECODE (PMAX_LOAD, 0, 1, PMAX_LOAD) "
				+ "            PERCENT_USAGE "
				+ "    FROM (  SELECT CUSTOMER_ID, "
				+ "                   BILLING_MONTH, "
				+ "                   BILLING_YEAR, "
				+ "                   TO_DATE ( "
				+ "                      TO_CHAR ('01-' || BILLING_MONTH || '-' || BILLING_YEAR), "
				+ "                      'dd-MM-YYYY') "
				+ "                      BILL_DATE, "
				+ "                   SUM (PMAX_LOAD) PMAX_LOAD, "
				+ "                   SUM (ACTUAL_CONSUMPTION) ACTUAL_CONSUMPTION "
				+ "              FROM meter_reading "
				+ "          GROUP BY CUSTOMER_ID, BILLING_MONTH, BILLING_YEAR) tbl, "
				+ "         MVIEW_CUSTOMER_INFO mci "
				+ "   WHERE     tbl.customer_id = mci.customer_id "
				+ "         AND BILLING_YEAR = 2019 "
				+ "         AND mci.customer_id = '"+this.getCustomer_id()+"' "
				+ "         AND billing_month = "+getBill_month()
				+ "         AND billing_year = "+getBill_year();
				

		try{
			conn = ConnectionManager.getConnection();
			Statement st= conn.createStatement();
			ResultSet rs= st.executeQuery(varname1);
			while(rs.next()){
				LoadExceedReportDTO len= new LoadExceedReportDTO();
				len.setMax_load(rs.getFloat("max_load"));
				len.setFull_name(rs.getString("full_name"));
				len.setAddress(rs.getString("ADDRESS"));
				len.setActual_consumption(rs.getFloat("ACTUAL_CONSUMPTION"));
				len.setArea_name(rs.getString("area_name"));
				customerInfoList.add(len);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{try {
			conn.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}}
		return customerInfoList;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}

	public String getBill_month() {
		return bill_month;
	}

	public void setBill_month(String bill_month) {
		this.bill_month = bill_month;
	}

	public String getBill_year() {
		return bill_year;
	}

	public void setBill_year(String bill_year) {
		this.bill_year = bill_year;
	}	
	
	public ServletContext getServlet() {
		return servlet;
	}

	public void setServlet(ServletContext servlet) {
		this.servlet = servlet;
	}

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}
	
}
