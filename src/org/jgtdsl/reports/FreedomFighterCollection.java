


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

public class FreedomFighterCollection extends BaseAction {
	private static final long serialVersionUID = 1L;
	public ServletContext servlet;
	Connection conn = ConnectionManager.getConnection();

	private String area;
	private  String bill_month;
    private  String bill_year;

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

		String fileName = "FreedomFightersCollection.pdf";
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
		
			PdfPTable dataTable = new PdfPTable(5);
			dataTable.setWidthPercentage(100);
			dataTable.setWidths(new float[]{(float) .10 , (float) .15, (float) .55, (float) .10,(float) .10});
			
			pcell= new PdfPCell(new Paragraph(" "));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Freedom Fighters Collection Report",font3));
			pcell.setColspan(5);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph("Collection Month: "+Month.values()[Integer.valueOf(bill_month)-1]+", "+bill_year,font3));
			pcell.setColspan(5);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(6);
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph(" "));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			///datatable header
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
			
			
			
			pcell= new PdfPCell(new Paragraph("Debit", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			pcell= new PdfPCell(new Paragraph("Credit", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setRowspan(2);			
			dataTable.addCell(pcell);
			
			///////////datatable header ends			
			
			ArrayList<FreedomFighterDTO> freeFighters = getFreedomFighterList();
			int totalbill=0;
			int totalcoll=0;
			
			for(FreedomFighterDTO x:freeFighters ){
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
				
				
				
				pcell= new PdfPCell(new Paragraph(x.getBilled_amount(), font4));
				pcell.setPadding(5);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);					
				dataTable.addCell(pcell);
				pcell= new PdfPCell(new Paragraph(x.getCollected_payable_amount(), font4));
				pcell.setPadding(5);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);					
				dataTable.addCell(pcell);
				
				int y= Integer.parseInt(x.getBilled_amount());
				int z=0;				
				if(!(x.getCollected_payable_amount()==null)){
					 z= Integer.parseInt(x.getCollected_payable_amount());
				}
				
				
				
				totalbill= totalbill+y;
				totalcoll= totalcoll+z;
				
				
				
			}
			pcell = new PdfPCell(new Paragraph("Total: ",font2));
			pcell.setColspan(3);
			pcell.setPadding(5);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setBorder(Rectangle.BOX);
			dataTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(totalbill),font4));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(totalcoll),font4));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
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
	
	public ArrayList<FreedomFighterDTO> getFreedomFighterList() {
		FreedomFighterDTO freedomFighterDTO = null;
		ArrayList<FreedomFighterDTO> freedomFighters = new ArrayList<FreedomFighterDTO>();
		Connection conn = ConnectionManager.getConnection();
		
		String sql= "SELECT cpi.CUSTOMER_ID, " +
				"       cpi.FULL_NAME, " +
				"       SUBSTR (cpi.CUSTOMER_ID, 3, 2) AS customer_category, " +
				"       bnm.BILLED_AMOUNT, " +
				"       BNM.COLLECTED_PAYABLE_AMOUNT " +
				"  FROM customer_personal_info cpi, bill_non_metered bnm " +
				" WHERE     FREEDOM_FIGHTER = 'Y' " +
				"       AND CPI.CUSTOMER_ID = BNM.CUSTOMER_ID " +
				"       AND BNM.AREA_ID = '"+loggedInUser.getArea_id()+"' " +
				"       AND BILL_MONTH =" +getBill_month()  +
				"       AND bill_year = " +getBill_year()  +
				"       order by CPI.CUSTOMER_ID " ;


		
		Statement stmt = null;
		ResultSet r = null;
		
		try {
			stmt = conn.createStatement();
			r = stmt.executeQuery(sql);
			int i= 1;
			
			while (r.next()) {
				
				freedomFighterDTO= new FreedomFighterDTO();
				freedomFighterDTO.setCustomer_id(r.getString("CUSTOMER_ID"));
				freedomFighterDTO.setCustomer_category(r.getString("CUSTOMER_CATEGORY"));
				freedomFighterDTO.setCustomer_name(r.getString("FULL_NAME"));
				freedomFighterDTO.setBilled_amount(r.getString("BILLED_AMOUNT"));
				freedomFighterDTO.setCollected_payable_amount(r.getString("COLLECTED_PAYABLE_AMOUNT"));
				
				freedomFighterDTO.setSerial(i++);
				
				
				freedomFighters.add(freedomFighterDTO);				
			}
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return freedomFighters;
	}

	public ServletContext getServlet() {
		return servlet;
	}

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
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

}
