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
import org.jgtdsl.dto.CodelessDTO;
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

public class CodelessCollectionReportMonthly extends BaseAction {

	private static final long serialVersionUID = 1L;
	public ServletContext servlet;
	
	private String area;
	private String bill_month;
	private String bill_year;
	private String report_for;
	
	
	
	

	static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
	
	UserDTO loggedInUser = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");

	public String execute() throws Exception {

		String fileName = "CodelessCollection"+bill_month+bill_year+".pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.LEGAL.rotate());
		//document.setMargins(5,5,60,72);
		
		
		try {

			ReportFormat Event = new ReportFormat(getServletContext());
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			writer.setPageEvent(Event);
			PdfPCell pcell = null;
			
			document.open();
			PdfPTable headerTable = new PdfPTable(3);
		    Rectangle page = document.getPageSize();
		 
		    headerTable.setTotalWidth(page.getWidth());
			float a=((page.getWidth()*15)/100)/2;
			float b=((page.getWidth()*40)/100)/2;
				
			headerTable.setWidths(new float[] {
				a,b,a
			});
			
			
			pcell= new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			
// for logo			
	
			String realPath = servlet.getRealPath("/resources/images/logo/JG.png");  // image path
			   Image img = Image.getInstance(realPath);
			      
			             //img.scaleToFit(10f, 200f);
			             //img.scalePercent(200f);
			            img.scaleAbsolute(32f, 35f);
			            //img.setAbsolutePosition(145f, 780f);  
			            img.setAbsolutePosition(320f, 535f);  // rotate
			            
			         document.add(img);
			
			
			
			PdfPTable mTable=new PdfPTable(1);
			//mTable.setWidths(new float[]{b});
			mTable.setWidthPercentage(100);
			pcell=new PdfPCell(new Paragraph("JALALABAD GAS T & D SYSTEM LIMITED",fonth));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);	
			mTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("(A Company of PetroBangla)", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			
			//error in chunk2			
			
			Chunk chunk1 = new Chunk("Regional Office: ",font2);
			Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(area)-1]),font3);
			//Chunk chunk2 = new Chunk(String.valueOf(Area.values()[1]),font3);
			
			Paragraph p = new Paragraph(); 
			p.add(chunk1);
			p.add(chunk2);
			pcell=new PdfPCell(p);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			
			if(report_for==null){			
				pcell=new PdfPCell(new Paragraph("Codeless Collection Report For: "+Month.values()[Integer.valueOf(bill_month)]+", "+bill_year, font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				pcell.setPaddingTop(10);
				mTable.addCell(pcell);
			}else{
				
				pcell=new PdfPCell(new Paragraph("Codeless Collection Report ", font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				pcell.setPaddingTop(10);
				mTable.addCell(pcell);
			}
			
						
			pcell=new PdfPCell(mTable);
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);			
			
			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);			
			
			document.add(headerTable);
			
			
			PdfPTable dataTable = new PdfPTable(9);
			dataTable.setWidthPercentage(97);
			dataTable.setWidths(new float[]{5,10,15,8,7,15,10,15,8});
			
			pcell= new PdfPCell(new Paragraph(" ", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			pcell.setPadding(5);
			pcell.setColspan(9);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
						
			dataTable.addCell(pcell);

			pcell= new PdfPCell(new Paragraph("SL", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Collection Date", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Bank", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Collected Amount", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Collected Surcharge", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);		
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("From-To Month", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);		
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Customer ID", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Customer Name", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Inserted By", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			ArrayList<CodelessDTO> codelessCollectionInfo = getCodelessCollectionDetais();
			
			
			codelessCollectionInfo=getCodelessCollectionDetais();
			
			

			int serial=1;
			int total_amount=0;
			int total_surcharge=0;
			
			for(CodelessDTO x: codelessCollectionInfo){
				
				pcell= new PdfPCell(new Paragraph(String.valueOf(serial), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(x.getCollection_date(), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(x.getBank_branch_name(), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(x.getCollected_amount(), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(x.getCollected_surcharge(), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				
				pcell= new PdfPCell(new Paragraph(x.getMonth_year_from()+" - "+x.getMonth_year_to() ,font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(x.getCustomer_id(), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(x.getCustomer_name(), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(x.getInserted_by(), font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				serial++;
				total_amount+= Integer.parseInt(x.getCollected_amount());
				total_surcharge+= Integer.parseInt(x.getCollected_surcharge());
				
			}
			
			pcell= new PdfPCell(new Paragraph("Total: ", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);
			pcell.setColspan(3);
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph(String.valueOf(total_amount), font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph(String.valueOf(total_surcharge), font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph(" ", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);
			pcell.setColspan(4);
			dataTable.addCell(pcell);
			
			
			dataTable.setHeaderRows(2);
			document.add(dataTable);
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(),fileName);
			document=null;
		

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public ArrayList<CodelessDTO> getCodelessCollectionDetais() {
		CodelessDTO codelessDTO = null;
		ArrayList<CodelessDTO> codelessCollections = new ArrayList<CodelessDTO>();
		Connection conn = ConnectionManager.getConnection();
		String whereClause="";
		if(report_for==null) whereClause= " TO_CHAR (COLLECTION_DATE, 'mm') =  " +this.getBill_month()+
										   " AND TO_CHAR (COLLECTION_DATE, 'YYYY') = " +this.getBill_year()+" and ";
		
		String sql=  
						"  SELECT CUSTOMER_ID, " +
						"         CUSTOMER_NAME, " +
						"         ADDRESS, " +
						" get_month_name (MONTH_FROM) || ', ' || YEAR_FROM MONTH_YEAR_FROM,"+
						" get_month_name (MONTH_TO) || ', ' || YEAR_TO MONTH_YEAR_to,"+
						"         COLLECTED_AMOUNT, " +
						"         COLLECTED_SURCHARGE, " +
						"         TO_CHAR (COLLECTION_DATE) COLLECTION_DATE, " +
						"         getBankBranch (BRANCH_ID) BANK_NAME, " +
						"         INSERTED_BY, " +
						"         to_char(INSERTED_ON) INSERTED_ON " +
						"    FROM codeless_payment " +
						"   WHERE     "+whereClause+
						"          area_id = " +this.getArea()+
						" ORDER BY to_date(COLLECTION_DATE, 'dd/mm/yyyy') ASC " ;
		
		Statement stmt = null;
		ResultSet r = null;
		
		try {
			stmt = conn.createStatement();
			r = stmt.executeQuery(sql);
			
			while (r.next()) {
				
				codelessDTO= new CodelessDTO();
				
				codelessDTO.setCollection_date(r.getString("COLLECTION_DATE"));
				codelessDTO.setBank_branch_name(r.getString("BANK_NAME"));
				codelessDTO.setCollected_amount(r.getString("COLLECTED_AMOUNT"));
				codelessDTO.setCollected_surcharge(r.getString("COLLECTED_SURCHARGE"));									
				codelessDTO.setMonth_year_from(r.getString("MONTH_YEAR_FROM"));
				codelessDTO.setMonth_year_to(r.getString("MONTH_YEAR_TO"));
				codelessDTO.setCustomer_id(r.getString("CUSTOMER_ID"));
				codelessDTO.setCustomer_name(r.getString("CUSTOMER_NAME"));		
			
				
				codelessCollections.add(codelessDTO);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return codelessCollections;
	}

	
	
	

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}
	
	public String getReport_for() {
		return report_for;
	}

	public void setReport_for(String report_for) {
		this.report_for = report_for;
	}

}
