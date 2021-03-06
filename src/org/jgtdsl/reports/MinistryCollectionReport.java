package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.MinistryCollectionDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MinistryCollectionReport extends BaseAction {
	private String coll_month;
	private String coll_year;
	DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");

	public ServletContext servlet;
	public HttpServletResponse response = ServletActionContext.getResponse();
	public HttpServletRequest request;

	static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
	static Font font1nb = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

	UserDTO loggedInUser = (UserDTO) ServletActionContext.getRequest()
			.getSession().getAttribute("user");

	public String execute() throws Exception {

		String fileName = "Ministry_Collection.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		 document.setMargins(0, 0, 20, 60);
		PdfPCell pcell = null;

		try {

			ReportFormat eEvent = new ReportFormat(getServletContext());

			PdfWriter.getInstance(document, baos).setPageEvent(eEvent);

			document.open();

			PdfPTable headerTable = new PdfPTable(3);

			headerTable.setWidths(new float[] { 5, 190, 5 });

			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(0);
			headerTable.addCell(pcell);

			String realPath = servlet
					.getRealPath("/resources/images/logo/JG.png"); // image path
			Image img = Image.getInstance(realPath);

			// img.scaleToFit(10f, 200f);
			// img.scalePercent(200f);
			img.scaleAbsolute(28f, 31f);
			img.setAbsolutePosition(125f, 773f);
			// img.setAbsolutePosition(290f, 540f); // rotate

			document.add(img);

			PdfPTable mTable = new PdfPTable(1);
			mTable.setWidthPercentage(90);
			mTable.setWidths(new float[] { 90 });
			pcell = new PdfPCell(new Paragraph(
					"JALALABAD GAS T & D SYSTEM LIMITED", fonth));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			mTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph("(A COMPANY OF PETROBANGLA)",
					font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			mTable.addCell(pcell);

			Chunk chunk1 = new Chunk("REGIONAL OFFICE : ", font2);
			Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer
					.valueOf(loggedInUser.getArea_id()) - 1]), font3);
			Paragraph p = new Paragraph();
			p.add(chunk1);
			p.add(chunk2);
			pcell = new PdfPCell(p);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			mTable.addCell(pcell);

			pcell = new PdfPCell(mTable);
			pcell.setBorder(0);
			headerTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(0);
			headerTable.addCell(pcell);
			document.add(headerTable);

			PdfPTable dataTable = new PdfPTable(3);
			dataTable.setWidthPercentage(90);
			// dataTable.setWidths(new float[] { (float) 0.15, (float) 1,(float)
			// 0.5 });

			pcell = new PdfPCell(new Paragraph(" "));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(3);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(
					"Ministry Customers Collection Details for "
							+ Month.values()[Integer.valueOf(coll_month) - 1]
							+ ", " + coll_year, font1));
			pcell.setColspan(3);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.BOX);
			pcell.setPadding(5);
			dataTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(" "));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(3);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			document.add(dataTable);		
			

			PdfPTable mainTable = new PdfPTable(7);
			mainTable.setWidthPercentage(97);
			mainTable.setWidths(new float[] {5, 15, 15, 30,30, 30, 12 });

			pcell = new PdfPCell(new Paragraph(" ", ReportUtil.f8B));
			pcell.setColspan(7);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mainTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Sl", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			mainTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph("Trans Date", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			mainTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph("Customer ID", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			mainTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph("Customer Category",
					ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			mainTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Ministry Name",
					ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			mainTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph("Bank", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			mainTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph("Collected amount",
					ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			mainTable.addCell(pcell);
			
			ArrayList<MinistryCollectionDTO> ministryCollectionList = getMinistryCollection();
			int sl=1;
			int total_coll_amount=0;

			for (MinistryCollectionDTO x : ministryCollectionList) {
				
				pcell = new PdfPCell(new Paragraph(String.valueOf(sl),
						ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPadding(5);
				mainTable.addCell(pcell);

				pcell = new PdfPCell(new Paragraph(x.getTrans_date(),
						ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPadding(5);
				mainTable.addCell(pcell);

				pcell = new PdfPCell(new Paragraph(x.getCustomer_id(),
						ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPadding(5);
				mainTable.addCell(pcell);

				pcell = new PdfPCell(new Paragraph(
						x.getCustomer_category_name(), ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPadding(5);
				mainTable.addCell(pcell);
				
				pcell = new PdfPCell(new Paragraph(
						x.getMinistry_name(), ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPadding(5);
				mainTable.addCell(pcell);

				pcell = new PdfPCell(new Paragraph(x.getBank(),
						ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPadding(5);
				mainTable.addCell(pcell);

				pcell = new PdfPCell(new Paragraph(x.getCollected_amount(),
						ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(5);
				mainTable.addCell(pcell);
				
				sl++;				

			}	
			
			
			ArrayList<MinistryCollectionDTO> ministryCollectionListTotal= getMinistryCollectionTotal();
			
			PdfPTable totalTable= new PdfPTable(3); 
			totalTable.setWidthPercentage(90);
			totalTable.setWidths(new float[]{8,30,15});
			
			pcell = new PdfPCell(new Paragraph(" ", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			pcell.setBorder(0);
			pcell.setColspan(4);
			totalTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Summary/Total", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPaddingBottom(3);
			pcell.setBorder(0);
			pcell.setColspan(4);
			totalTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Sl", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			totalTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Minitry Category (Customer Count)", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			totalTable.addCell(pcell);			
			
			
			pcell = new PdfPCell(new Paragraph("Total Collected amount", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPaddingBottom(3);
			totalTable.addCell(pcell);
			
			int sr=1;		
			
			
			for(MinistryCollectionDTO x: ministryCollectionListTotal){
				
				pcell = new PdfPCell(new Paragraph(String.valueOf(sr), ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPaddingBottom(3);
				totalTable.addCell(pcell);
				
				pcell = new PdfPCell(new Paragraph(x.getMinistry_name()+" ("+x.getCount()+")", ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPaddingBottom(3);
				totalTable.addCell(pcell);		
				
				
				pcell = new PdfPCell(new Paragraph(taka_format.format(Integer.parseInt(x.getCollected_amount())), ReportUtil.f8));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPaddingBottom(3);
				totalTable.addCell(pcell);
				sr++;		
				total_coll_amount+= Integer.parseInt(x.getCollected_amount());
				
			}
			pcell = new PdfPCell(new Paragraph("Total: "+taka_format.format(total_coll_amount)+" Tk.",
					ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setBorder(0);
			pcell.setColspan(3);
			pcell.setPaddingBottom(3);
			totalTable.addCell(pcell);
			
			document.add(totalTable);
			document.add(mainTable);			
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(), fileName);
			document = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<MinistryCollectionDTO> getMinistryCollection() {
		MinistryCollectionDTO ministry = null;
		ArrayList<MinistryCollectionDTO> ministryList = new ArrayList<MinistryCollectionDTO>();
		Connection conn = ConnectionManager.getConnection();

		String sql = 
				  "  SELECT BAL.CUSTOMER_ID, "
				+ "         CC.MINISTRY_ID, "
				+ "         MM.MINISTRY_NAME, "
				+ "         TO_CHAR (BAL.TRANS_DATE) tran_date, "
				+ "         getbankbranch (bal.BRANCH_ID) bank, "
				+ "         BAL.Debit, "
				+ "         MCC.CATEGORY_NAME "
				+ "    FROM BANK_ACCOUNT_LEDGER bal, "
				+ "         CUSTOMER_CONNECTION cc, "
				+ "         MST_MINISTRY mm, "
				+ "         MST_CUSTOMER_CATEGORY mcc, "
				+ "         mst_branch_info mbi "
				+ "   WHERE     TO_CHAR (TRANS_DATE, 'MM') =  "+this.coll_month
				+ "         AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+this.coll_year
				+ "         AND BAL.CUSTOMER_ID = CC.CUSTOMER_ID "
				+ "         AND MCC.CATEGORY_ID = SUBSTR (CC.CUSTOMER_ID, 3, 2) "
				+ "         AND CC.MINISTRY_ID = MM.MINISTRY_ID "
				+ "         AND CC.MINISTRY_ID IS NOT NULL "
				+ "         and MBI.BANK_ID=BAL.BANK_ID and MBI.BRANCH_ID=BAL.BRANCH_ID "
				+ "         and MBI.AREA_ID= '"+loggedInUser.getArea_id()+"' "				
				+ " ORDER BY tran_date ASC";

		Statement stmt = null;
		ResultSet r = null;

		try {
			stmt = conn.createStatement();
			r = stmt.executeQuery(sql);

			while (r.next()) {
				ministry = new MinistryCollectionDTO();

				ministry.setCustomer_id(r.getString("CUSTOMER_ID"));
				ministry.setMinistry_id(r.getString("MINISTRY_ID"));
				ministry.setMinistry_name(r.getString("MINISTRY_NAME"));
				ministry.setTrans_date(r.getString("tran_date"));
				ministry.setCollected_amount(r.getString("Debit"));
				ministry.setCustomer_category_name(r.getString("CATEGORY_NAME"));
				ministry.setBank(r.getString("bank"));

				ministryList.add(ministry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ministryList;
	}

	public ArrayList<MinistryCollectionDTO> getMinistryCollectionTotal() {
		MinistryCollectionDTO ministry = null;
		ArrayList<MinistryCollectionDTO> ministryList = new ArrayList<MinistryCollectionDTO>();
		Connection conn = ConnectionManager.getConnection();

		String sql =  "SELECT CC.MINISTRY_ID, "
				+ "         MM.MINISTRY_NAME, "
				+ "         SUM (BAL.Debit) t_debit, "
				+ "         COUNT (*) COUNT "
				+ "    FROM BANK_ACCOUNT_LEDGER bal, "
				+ "         CUSTOMER_CONNECTION cc, "
				+ "         MST_MINISTRY mm, "
				+ "         mst_branch_info bmi "
				+ "   WHERE     TO_CHAR (TRANS_DATE, 'MM') =  "+this.coll_month
				+ "         AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+this.coll_year
				+ "         AND BAL.CUSTOMER_ID = CC.CUSTOMER_ID "
				+ "         AND CC.MINISTRY_ID = MM.MINISTRY_ID "
				+ "         AND CC.MINISTRY_ID IS NOT NULL "
				+ "         and bmi.BANK_ID=BAL.BANK_ID and bmi.BRANCH_ID=BAL.BRANCH_ID "
				+ "         AND bmi.AREA_ID =  '"+loggedInUser.getArea_id()+"' "
				+ " GROUP BY CC.MINISTRY_ID, MM.MINISTRY_NAME";

		Statement stmt = null;
		ResultSet r = null;

		try {
			stmt = conn.createStatement();
			r = stmt.executeQuery(sql);

			while (r.next()) {
				ministry = new MinistryCollectionDTO();
				
				ministry.setMinistry_name(r.getString("MINISTRY_NAME"));				
				ministry.setCollected_amount(r.getString("t_debit"));
				ministry.setCount(r.getString("count"));
				ministryList.add(ministry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ministryList;
	}

	public String getColl_month() {
		return coll_month;
	}

	public void setColl_month(String coll_month) {
		this.coll_month = coll_month;
	}

	public String getColl_year() {
		return coll_year;
	}

	public void setColl_year(String coll_year) {
		this.coll_year = coll_year;
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

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
