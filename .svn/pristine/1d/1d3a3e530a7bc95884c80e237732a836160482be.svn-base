package org.jgtdsl.reports;



import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.models.AreaService;

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

public class BalancingReport2 extends BaseAction{

private static final long serialVersionUID = 1L;
	
    private static String area;
    private static String customer_category;
    private static String bill_month;
    private static String bill_year;
    private String report_for; 
    private static String category_name;
    
    AreaService aa=new AreaService();
	
	public ServletContext servlet;
	




	ServletContext servletContext = null;

	static Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	static Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
	UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	Document document = new Document(PageSize.LEGAL.rotate());
	
	
	public String execute() throws Exception {
		String fileName="balancingReport2.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.LEGAL.rotate());
		document.setMargins(5,5,60,72);
		
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
			
			
								PdfPTable mTable=new PdfPTable(1);
								mTable.setWidths(new float[]{b});
								pcell=new PdfPCell(new Paragraph("JALALABAD GAS T & D SYSTEM LIMITED"));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setBorder(Rectangle.NO_BORDER);	
								mTable.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("(A company of PetroBangla)", ReportUtil.f8B));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setBorder(Rectangle.NO_BORDER);
								mTable.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph(""));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setBorder(Rectangle.NO_BORDER);
								mTable.addCell(pcell);
								
								Chunk chunk1 = new Chunk("Regional Distribution Office :",font2);
							//	Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(area)-1]),font3);
								Paragraph p = new Paragraph(); 
								p.add(chunk1);
						//		p.add(chunk2);
								pcell=new PdfPCell(p);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setBorder(Rectangle.NO_BORDER);
								mTable.addCell(pcell);
								
								
								pcell=new PdfPCell(mTable);
								pcell.setBorder(Rectangle.NO_BORDER);
								headerTable.addCell(pcell);
								
								
								
								pcell = new PdfPCell(new Paragraph(""));
								pcell.setBorder(Rectangle.NO_BORDER);
								headerTable.addCell(pcell);
								document.add(headerTable);
								
								PdfPTable headlineTable = new PdfPTable(3);
								headlineTable.setSpacingBefore(5);
								headlineTable.setSpacingAfter(10);
								headlineTable.setWidths(new float[] {
										40,70,40
									});
								
								pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setBorder(Rectangle.NO_BORDER);
								headlineTable.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Balance(summary) "+"as on ", ReportUtil.f11B));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setBorder(Rectangle.NO_BORDER);
								headlineTable.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setBorder(Rectangle.NO_BORDER);
								headlineTable.addCell(pcell);
								document.add(headlineTable);
								
								PdfPTable datatable1 = new PdfPTable(11);
								
								
								datatable1.setWidthPercentage(100);
								datatable1.setWidths(new float[] {15,15,15,15,15,15,15,15,15,15,15});
								
								
								
								
								pcell=new PdfPCell(new Paragraph("Sl",font3));
								pcell.setRowspan(3);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Category Name",font3));
								pcell.setRowspan(3);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								
							
								
								pcell=new PdfPCell(new Paragraph("Status",font3));
								pcell.setRowspan(3);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
															
								
								pcell=new PdfPCell(new Paragraph("Total Customer",font3));
								pcell.setRowspan(3);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								
								pcell=new PdfPCell(new Paragraph("Burner",font3));
								pcell.setColspan((3));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Max.Load",font3));
								pcell.setRowspan(3);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Sec.Deposit",font3));
								pcell.setColspan((2));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Balance(BDT)",font3));
								pcell.setRowspan(3);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Single",font3));
								pcell.setRowspan(2);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Double",font3));
								pcell.setRowspan(2);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("others",font3));
								pcell.setRowspan(2);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("Cash",font3));
								pcell.setRowspan(2);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								pcell=new PdfPCell(new Paragraph("BG",font3));
								pcell.setRowspan(2);
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								datatable1.addCell(pcell);
								
								
								pcell = new PdfPCell(new Paragraph(("1"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("non-metered Domestic"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("connected"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setRowspan(3);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("4"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("5"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("6"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("7"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("8"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("9"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("10"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
								pcell = new PdfPCell(new Paragraph(("11"),ReportUtil.f11));
								pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								pcell.setColspan(1);
								datatable1.addCell(pcell);
								
						
								document.add(datatable1);
								
								
								document.close();
								ReportUtil rptUtil = new ReportUtil();
								rptUtil.downloadPdf(baos, getResponse(), fileName);
								document = null;
					         
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}









	public static String getArea() {
		return area;
	}




	public static void setArea(String area) {
		BalancingReport2.area = area;
	}




	public static String getCustomer_category() {
		return customer_category;
	}




	public static void setCustomer_category(String customer_category) {
		BalancingReport2.customer_category = customer_category;
	}




	public static String getBill_month() {
		return bill_month;
	}




	public static void setBill_month(String bill_month) {
		BalancingReport2.bill_month = bill_month;
	}




	public static String getBill_year() {
		return bill_year;
	}




	public static void setBill_year(String bill_year) {
		BalancingReport2.bill_year = bill_year;
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
		BalancingReport2.category_name = category_name;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}




	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	
	
}
