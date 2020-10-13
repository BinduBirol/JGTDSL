package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.CashAndBGReportsDTO;
import org.jgtdsl.dto.FreedomFighterDTO;
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

public class CashAndBGReports extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	public ServletContext servlet;
	Connection conn = ConnectionManager.getConnection();
	
    private static String area;
    private static String customer_category;
    private String report_for; 
    private static String category_name;
    private String category_id;
    
    
    static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	static Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat(
			"##########0.000");
	UserDTO loggedInUser = (UserDTO) ServletActionContext.getRequest()
			.getSession().getAttribute("user");
	
	
	public String execute() throws Exception {
		String fileName = "CashAndBGReports.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		document.setMargins(20, 20, 50, 80);
		PdfPCell pcell = null;

		ReportFormat eEvent = new ReportFormat(getServletContext());

		PdfWriter.getInstance(document, baos).setPageEvent(eEvent);

		document.open();
		
		try {
		
		/////////////////header starts/////////////
		
		PdfPTable headerTable = new PdfPTable(3);
		   
		
		headerTable.setWidths(new float[] {
			5,190,5
		});
		
		headerTable.setWidths(new float[] {
				5,190,5
			});

			
			pcell= new PdfPCell(new Paragraph(""));
			pcell.setBorder(0);
			headerTable.addCell(pcell);
			
			
			String realPath = servlet.getRealPath("/resources/images/logo/JG.png"); 	// image path
			Image img = Image.getInstance(realPath);
						
            	//img.scaleToFit(10f, 200f);
            	//img.scalePercent(200f);
            img.scaleAbsolute(28f, 31f);
            img.setAbsolutePosition(123f, 760f);		
            	//img.setAbsolutePosition(290f, 540f);		// rotate
            
	        document.add(img);
			
			
			PdfPTable mTable=new PdfPTable(1);
			mTable.setWidthPercentage(90);
			mTable.setWidths(new float[]{100});
			pcell=new PdfPCell(new Paragraph("JALALABAD GAS T & D SYSTEM LIMITED",fonth));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);	
			mTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("(A COMPANY OF PETROBANGLA)", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			mTable.addCell(pcell);

			Chunk chunk1 = new Chunk("REGIONAL OFFICE : ",font2);
			Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(loggedInUser.getArea_id())-1]),font3);
			Paragraph p = new Paragraph(); 
			p.add(chunk1);
			p.add(chunk2);
			pcell=new PdfPCell(p);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			mTable.addCell(pcell);
					
			pcell=new PdfPCell(mTable);
			pcell.setBorder(0);
			headerTable.addCell(pcell);
					
			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(0);
			headerTable.addCell(pcell);
			document.add(headerTable);
			///////////header ends/////////////
			
			PdfPTable dataTable = new PdfPTable(6);
			dataTable.setWidthPercentage(100);
			dataTable.setWidths(new float[]{(float) .07 , (float) .14, (float) .34,(float) .10, (float) .10,(float) .15});
			
			pcell= new PdfPCell(new Paragraph(" "));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(6);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Cash & BG Reports",font3));
			pcell.setColspan(6);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			//loop starts
			
			String sql0=" SELECT CATEGORY_ID,CATEGORY_NAME FROM MST_CUSTOMER_CATEGORY  ";
			Statement stmt0 = null;
			ResultSet r0 = null;
			
			
			
				stmt0 = conn.createStatement();
				r0 = stmt0.executeQuery(sql0);
				
				
			while(r0.next())
				{
				
								
				setCategory_id(r0.getString("CATEGORY_ID"));
			
			
			
			
			
			///loop starts
			pcell= new PdfPCell(new Paragraph("-"));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(6);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			///datatable header
			pcell= new PdfPCell(new Paragraph(   r0.getString("CATEGORY_NAME")  ) );
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setColspan(6);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Sl", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Customer ID", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Customer Name", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			
			
			pcell= new PdfPCell(new Paragraph("Cash", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("BG", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("TAKA", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			ArrayList<CashAndBGReportsDTO> cashAndBGReports = getCashAndBGReportsList();
			double total=0;
			
			for(CashAndBGReportsDTO x:cashAndBGReports ){
				//for(int x=0; x<freeFighters.size(); x++)
					
					pcell= new PdfPCell(new Paragraph(String.valueOf(x.getSerial()), font4));
					pcell.setPadding(5);
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);					
					dataTable.addCell(pcell);
					
					pcell= new PdfPCell(new Paragraph(x.getCustomer_id(), font4));
					pcell.setPadding(5);
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);					
					dataTable.addCell(pcell);
					
					pcell= new PdfPCell(new Paragraph(x.getCustomer_name(), font4));
					pcell.setPadding(5);
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);					
					dataTable.addCell(pcell);
					
					
					
					pcell= new PdfPCell(new Paragraph(x.getCash(), font4));
					pcell.setPadding(5);
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);					
					dataTable.addCell(pcell);
					pcell= new PdfPCell(new Paragraph(x.getBg(), font4));
					pcell.setPadding(5);
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);					
					dataTable.addCell(pcell);
					double t=0.0;
					double y=0.0;
					double z=0.0;
					if(!(x.getCash()==null)){
					y= Double.parseDouble(x.getCash());
					}
					if(!(x.getBg()==null)){
						 z= Double.parseDouble(x.getBg());
					}
					t=y+z;
					total=total+t;
					pcell= new PdfPCell(new Paragraph(taka_format.format(t),font4) );
					pcell.setPadding(5);
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);					
					dataTable.addCell(pcell);
					
					
					}
			pcell = new PdfPCell(new Paragraph("Total: ",font2));
			pcell.setColspan(5);
			pcell.setPadding(5);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setBorder(Rectangle.BOX);
			dataTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(total),font4));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(1);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			}
			///loop ends
			
			
			
			document.add(dataTable);
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(),fileName);
			document=null;
		
	}catch (Exception e) {
		e.printStackTrace();
    
	}
		return null;
	}
	
	public ArrayList<CashAndBGReportsDTO> getCashAndBGReportsList() {
		CashAndBGReportsDTO cashAndBGReportsDTO = null;
		ArrayList<CashAndBGReportsDTO> cashAndBGReports = new ArrayList<CashAndBGReportsDTO>();
		Connection conn = ConnectionManager.getConnection();
		
		
			
		
		String sql="SELECT temp.FULL_NAME, " +
				"       temp.CUSTOMER_ID, " +
				"       SUM (CASH), " +
				"       SUM (BG) " +
				"  FROM (SELECT CPI.FULL_NAME, " +
				"               md.CUSTOMER_ID, " +
				"               0 CASH, " +
				"               TOTAL_DEPOSIT BG " +
				"          FROM mst_deposit md, customer_personal_info cpi " +
				"         WHERE     DEPOSIT_PURPOSE = 1 " +
				"               AND DEPOSIT_TYPE = 1 " +
				"               AND SUBSTR (MD.CUSTOMER_ID, 1, 2) = '"+loggedInUser.getArea_id()+"' " +
				"               AND MD.CUSTOMER_ID = CPI.CUSTOMER_ID " +
				"               AND SUBSTR (md.customer_id, 3, 2) ='"+String.valueOf(getCategory_id())+ "'" +
				"        UNION ALL " +
				"        SELECT CPI.FULL_NAME, " +
				"               md.CUSTOMER_ID, " +
				"               TOTAL_DEPOSIT CASH, " +
				"               0 BG " +
				"          FROM mst_deposit md, customer_personal_info cpi " +
				"         WHERE     DEPOSIT_PURPOSE = 1 " +
				"               AND DEPOSIT_TYPE = 0 " +
				"               AND SUBSTR (MD.CUSTOMER_ID, 1, 2) = '"+loggedInUser.getArea_id()+"' " +
				"               AND MD.CUSTOMER_ID = CPI.CUSTOMER_ID " +
				"               AND SUBSTR (md.customer_id, 3, 2) = '"+String.valueOf(getCategory_id())+ "' ) temp " +
				"               group by temp.CUSTOMER_ID,temp.FULL_NAME " +
				"               order by TEMP.CUSTOMER_ID " ;




		
		Statement stmt = null;
		ResultSet r = null;
		
		try {
			stmt = conn.createStatement();
			r = stmt.executeQuery(sql);
			int i= 1;
			
			while (r.next()) {
				
				cashAndBGReportsDTO= new CashAndBGReportsDTO();
				cashAndBGReportsDTO.setCustomer_id(r.getString("CUSTOMER_ID"));
				
				cashAndBGReportsDTO.setCustomer_name(r.getString("FULL_NAME"));
				cashAndBGReportsDTO.setCash(r.getString("SUM(CASH)"));
				cashAndBGReportsDTO.setBg(r.getString("SUM(BG)"));
				cashAndBGReportsDTO.setTaka(r.getString("SUM(CASH)"));
				
				cashAndBGReportsDTO.setSerial(i++);
				
				
				cashAndBGReports.add(cashAndBGReportsDTO);				
			}
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cashAndBGReports;
	}
	
	
	
	
	public ServletContext getServlet() {
		return servlet;
	}

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}
	public static String getArea() {
		return area;
	}
	public static void setArea(String area) {
		CashAndBGReports.area = area;
	}
	public static String getCustomer_category() {
		return customer_category;
	}
	public static void setCustomer_category(String customer_category) {
		CashAndBGReports.customer_category = customer_category;
	}
	public String getReport_for() {
		return report_for;
	}
	public void setReport_for(String report_for) {
		this.report_for = report_for;
	}
	public static String getCategory_name() {
		return category_name;
	}
	public static void setCategory_name(String category_name) {
		CashAndBGReports.category_name = category_name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

}
