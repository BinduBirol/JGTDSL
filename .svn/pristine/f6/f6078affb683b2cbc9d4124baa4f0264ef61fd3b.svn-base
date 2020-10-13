package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.BalancingReportDTO;
import org.jgtdsl.dto.CustomerListDTO;
import org.jgtdsl.dto.MeterReadingReportDTO;
import org.jgtdsl.dto.TransactionDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class BalancingReport extends BaseAction {

	private static String area;
	private static String year;
	

	private static final long serialVersionUID = 1L;
	CustomerListDTO customerListDTO = new CustomerListDTO();
	public ServletContext servlet;
	Connection conn = ConnectionManager.getConnection();

	BaseColor myColor = WebColors.getRGBColor("#bfbbbb");; 
	static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat number_format = new DecimalFormat("###########0.0");
	static DecimalFormat consumption_format = new DecimalFormat(
			"##########0.000");
	UserDTO loggedInUser = (UserDTO) ServletActionContext.getRequest()
			.getSession().getAttribute("user");

	public String execute() throws Exception {

		String fileName = "Balanching_Report_";
		 fileName +=String.valueOf(String.valueOf(Area.values()[Integer.valueOf(area)-1]));
	     fileName +=".pdf";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4.rotate());
		document.setMargins(5, 5, 5, 5);

		try {

			ReportFormat Event = new ReportFormat(getServletContext());
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			writer.setPageEvent(Event);
			PdfPCell pcell = null;

			document.open();
			PdfPTable headerTable = new PdfPTable(3);
			Rectangle page = document.getPageSize();
			headerTable.setTotalWidth(page.getWidth());
			float a = ((page.getWidth() * 15) / 100) / 2;
			float b = ((page.getWidth() * 40) / 100) / 2;

			headerTable.setWidths(new float[] { a, b, a });

			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);

			// for logo

			String realPath = servlet
					.getRealPath("/resources/images/logo/JG.png"); // image path
			Image img = Image.getInstance(realPath);

			// img.scaleToFit(10f, 200f);
			// img.scalePercent(200f);
			img.scaleAbsolute(32f, 35f);
			// img.setAbsolutePosition(145f, 780f);
			img.setAbsolutePosition(260f, 553f); // rotate

			document.add(img);

			PdfPTable mTable = new PdfPTable(1);
			mTable.setWidths(new float[] { b });
			pcell = new PdfPCell(new Paragraph(
					"JALALABAD GAS T & D SYSTEM LIMITED"));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph("(A company of PetroBangla)",
					ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);

			// error in chunk2

			Chunk chunk1 = new Chunk("Regional Distribution Office: ", font2);
			Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer
					.valueOf(area) - 1]), font3);
			// Chunk chunk2 = new Chunk(String.valueOf(Area.values()[1]),font3);

			Paragraph p = new Paragraph();
			p.add(chunk1);
			p.add(chunk2);
			pcell = new PdfPCell(p);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);

			pcell = new PdfPCell(mTable);
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			document.add(headerTable);
			
			
			dataTable(document);
			
			
			

			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(), fileName);
			document = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public void dataTable(Document document) throws DocumentException
	{
		 ArrayList<BalancingReportDTO> nonMeterDataConnected = new ArrayList<BalancingReportDTO>();
		 ArrayList<BalancingReportDTO> nonMeterDataDisConnected=new ArrayList<BalancingReportDTO>();
		 
		
		
		Connection conn = ConnectionManager.getConnection();
		PdfPTable headlineTable = new PdfPTable(3);
		headlineTable.setSpacingBefore(5);
		headlineTable.setSpacingAfter(10);
		headlineTable.setWidths(new float[] {
				5,100,5
			});
		PdfPCell pcell = null;
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer Balancing Report for the Ended Year: June"+"'"+year, ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		pcell.setPadding(5);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		
		///data table
		PdfPTable datatable1 = new PdfPTable(10);
		
		datatable1.setWidthPercentage(99);
		datatable1.setWidths(new float[] {50,55,20,30,30,20,40,40,40,40});
		
		
		pcell=new PdfPCell(new Paragraph("Govt./Non-Govt. Customers",font3));
		pcell.setRowspan(2);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Due Status",font3));
		pcell.setRowspan(2);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Cust. Count",font3));
		pcell.setRowspan(2);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Burners",font3));
		pcell.setRowspan(1);
		pcell.setColspan(3);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("Security Deposit",font3));
		pcell.setRowspan(1);
		pcell.setColspan(2);		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Advance Payment",font3));
		pcell.setRowspan(2);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Balance on 30Jun "+year,font3));
		pcell.setRowspan(2);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("Single",font3));
		pcell.setRowspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Double",font3));
		pcell.setRowspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Others",font3));
		pcell.setRowspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("Cash",font3));
		pcell.setRowspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("BG",font3));
		pcell.setRowspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(" ",font3));
		pcell.setRowspan(1);
		pcell.setColspan(10);
		pcell.setBorder(0);
		pcell.setPadding(0);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);	
		
		pcell=new PdfPCell(new Paragraph("Non-metered Domestic",font3));
		pcell.setRowspan(1);
		pcell.setColspan(10);
		pcell.setBackgroundColor(myColor);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Connected Customers",font3));
		pcell.setRowspan(1);
		pcell.setColspan(10);
		pcell.setBorder(0);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);		
		
		nonMeterDataConnected= getNonMeteredinfo(1);
		int total_govt_cus=0;
		double total_govt_SB=0;
		double total_govt_DB=0;
		double total_govt_OB=0;
		double total_govt_CASH=0;
		double total_govt_BG=0;
		double total_govt_ADVANCE=0;
		double total_govt_BALANCE=0;
		int total_Ngovt_cust=0;
		double total_Ngovt_SB=0;
		double total_Ngovt_DB=0;
		double total_Ngovt_OB=0;
		double total_Ngovt_CASH=0;
		double total_Ngovt_BG=0;
		double total_Ngovt_ADVANCE=0;
		double total_Ngovt_BALANCE=0;
		
		int  b_cus=0;
		Double  b_SB=0.0d;
		Double  b_DB=0.0d;
		Double  b_OB=0.0d;
		double  b_CASH=0;
		Double  b_BG=0.0d;
		double  b_ADVANCE=0;
		double  b_BALANCE=0.0;
		int  a_cust=0;
		Double  a_SB=0.0;
		Double  a_DB=0.0;
		Double  a_OB=0.0;
		Double  a_CASH=0.0;
		Double  a_BG=0.0;
		double  a_ADVANCE=0;
		double  a_BALANCE=0;
		
		pcell=new PdfPCell(new Paragraph("Non-Govt Customers",font2));
		pcell.setRowspan(3);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		for(BalancingReportDTO x: nonMeterDataConnected){
			if(x.getFlag().equalsIgnoreCase("Cb3D")){
				pcell=new PdfPCell(new Paragraph("Dues: 3 Months or Less ",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(1, "01","<=")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) b_cus= Integer.parseInt(x.getCustomer_count());
				if(!(x.getsBurner()==null)) b_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) b_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) b_OB=  x.getoBurner();
				if(!(x.getCash()==null)) b_CASH=  x.getCash();
				if(!(x.getBg()==null)) b_BG=  x.getBg();
				if(!(x.getAdvance()==null)) b_ADVANCE= Double.valueOf((x.getAdvance()));
				b_BALANCE= getBalanceForBalancing(1, "01","<=");
			}
			
			if(x.getFlag().equalsIgnoreCase("Ca3D")){
				pcell=new PdfPCell(new Paragraph("Dues: Above 3 Months",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(1, "01",">")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) a_cust= Integer.parseInt( x.getCustomer_count());
				if(!(x.getsBurner()==null)) a_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) a_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) a_OB=  x.getoBurner();
				if(!(x.getCash()==null)) a_CASH=  x.getCash();
				if(!(x.getBg()==null)) a_BG=  x.getBg();
				if(!(x.getAdvance()==null)) a_ADVANCE= Double.valueOf(x.getAdvance());
				a_BALANCE= getBalanceForBalancing(1, "01",">");
				
			}
			
			
		}
		
		total_Ngovt_cust= a_cust+b_cus;
		total_Ngovt_SB= (a_SB+b_SB);
		total_Ngovt_DB= a_DB+b_DB;
		total_Ngovt_OB= a_OB+b_OB;
		total_Ngovt_CASH= a_CASH+b_CASH;
		total_Ngovt_BG= a_BG+ b_BG;
		total_Ngovt_ADVANCE= a_ADVANCE+b_ADVANCE;
		total_Ngovt_BALANCE= a_BALANCE+b_BALANCE;
		
		pcell=new PdfPCell(new Paragraph("Total: ",font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(String.valueOf(total_Ngovt_cust),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_SB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_DB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_OB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_CASH),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BG),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_ADVANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BALANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		///end of connected  non govt
		
		  b_cus=0;
		  b_SB=0.0;
		  b_DB=0.0;
		  b_OB=0.0;
		  b_CASH=0;
		  b_BG=0.0;
		  b_ADVANCE=0;
		  b_BALANCE=0;
		  a_cust=0;
		  a_SB=0.0;
		  a_DB=0.0;
		  a_OB=0.0;
		  a_CASH=0.0;
		  a_BG=0.0;
		  a_ADVANCE=0;
		  a_BALANCE=0;
		  
		
		/// begins govt
		pcell=new PdfPCell(new Paragraph("Govt Customers",font2));
		pcell.setRowspan(3);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		for(BalancingReportDTO x: nonMeterDataConnected){
			if(x.getFlag().equalsIgnoreCase("Cb3G")){
				pcell=new PdfPCell(new Paragraph("Dues: 3 Months or Less ",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(1,"09", "<=")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) b_cus=  Integer.parseInt(x.getCustomer_count());
				if(!(x.getsBurner()==null)) b_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) b_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) b_OB=  x.getoBurner();
				if(!(x.getCash()==null)) b_CASH=  x.getCash();
				if(!(x.getBg()==null)) b_BG=  x.getBg();
				if(!(x.getAdvance()==null)) b_ADVANCE= Double.valueOf((x.getAdvance()));
				b_BALANCE= getBalanceForBalancing(1,"09", "<=");
				
			}
			
			if(x.getFlag().equalsIgnoreCase("Ca3G")){
				pcell=new PdfPCell(new Paragraph("Dues: Above 3 Months",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(1,"09", ">")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) a_cust=  Integer.parseInt(x.getCustomer_count());
				if(!(x.getsBurner()==null)) a_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) a_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) a_OB=  x.getoBurner();
				if(!(x.getCash()==null)) a_CASH=  x.getCash();
				if(!(x.getBg()==null)) a_BG=  x.getBg();
				if(!(x.getAdvance()==null)) a_ADVANCE= Double.valueOf(x.getAdvance());
				a_BALANCE= getBalanceForBalancing(1,"09", ">");
				
			}
			
			
		}
		total_govt_cus= a_cust+b_cus;
		total_govt_SB= a_SB+b_SB;
		total_govt_DB= a_DB+b_DB;
		total_govt_OB= a_OB+b_OB;
		total_govt_CASH= a_CASH+b_CASH;
		total_govt_BG= a_BG+ b_BG;
		total_govt_ADVANCE= a_ADVANCE+b_ADVANCE;
		total_govt_BALANCE=a_BALANCE+b_BALANCE;
		
		pcell=new PdfPCell(new Paragraph("Total: ",font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(String.valueOf(total_govt_cus),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_govt_SB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_govt_DB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_govt_OB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_govt_CASH),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_govt_BG),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format((total_govt_ADVANCE)),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_govt_BALANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		
		////total C///
		
		pcell=new PdfPCell(new Paragraph("Connected Total: ",font3));
		pcell.setRowspan(1);
		pcell.setColspan(2);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(String.valueOf(total_Ngovt_cust+total_govt_cus),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_SB+total_govt_SB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_DB+total_govt_DB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_OB+total_govt_OB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_CASH+total_govt_CASH),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BG+total_govt_BG),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_ADVANCE+total_govt_ADVANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BALANCE+total_govt_BALANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		
		///////////////non meter connected ends////////////
		
		  b_cus=0;
		  b_SB=0.0;
		  b_DB=0.0;
		  b_OB=0.0;
		  b_CASH=0;
		  b_BG=0.0;
		  b_ADVANCE=0;
		  b_BALANCE=0;
		  a_cust=0;
		  a_SB=0.0;
		  a_DB=0.0;
		  a_OB=0.0;
		  a_CASH=0.0;
		  a_BG=0.0;
		  a_ADVANCE=0;
		  a_BALANCE=0;
		  
		
		pcell=new PdfPCell(new Paragraph("DisConnected Customers",font3));
		pcell.setRowspan(1);
		pcell.setColspan(10);
		pcell.setBorder(0);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);	
		
		nonMeterDataDisConnected= getNonMeteredinfo(0);
		
		pcell=new PdfPCell(new Paragraph("Non-Govt Customers",font2));
		pcell.setRowspan(3);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		for(BalancingReportDTO x: nonMeterDataDisConnected){
			if(x.getFlag().equalsIgnoreCase("Cb3D")){
				pcell=new PdfPCell(new Paragraph("Dues: 3 Months or Less ",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(0,"01", "<=")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) b_cus= Integer.parseInt( x.getCustomer_count());
				if(!(x.getsBurner()==null)) b_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) b_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) b_OB=  x.getoBurner();
				if(!(x.getCash()==null)) b_CASH=  x.getCash();
				if(!(x.getBg()==null)) b_BG=  x.getBg();
				if(!(x.getAdvance()==null)) b_ADVANCE= Double.valueOf((x.getAdvance()));
				b_BALANCE= getBalanceForBalancing(0,"01", "<=");
				
			}
			
			if(x.getFlag().equalsIgnoreCase("Ca3D")){
				pcell=new PdfPCell(new Paragraph("Dues: Above 3 Months",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(0,"01", ">")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) a_cust=  Integer.parseInt(x.getCustomer_count());
				if(!(x.getsBurner()==null)) a_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) a_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) a_OB=  x.getoBurner();
				if(!(x.getCash()==null)) a_CASH=  x.getCash();
				if(!(x.getBg()==null)) a_BG=  x.getBg();
				if(!(x.getAdvance()==null)) a_ADVANCE= Double.valueOf(x.getAdvance());
				a_BALANCE= getBalanceForBalancing(0,"01", ">");
			}
			
			
		}
		total_Ngovt_cust= a_cust+b_cus;
		total_Ngovt_SB= a_SB+b_SB;
		total_Ngovt_DB= a_DB+b_DB;
		total_Ngovt_OB= a_OB+b_OB;
		total_Ngovt_CASH= a_CASH+b_CASH;
		total_Ngovt_BG= a_BG+ b_BG;
		total_Ngovt_ADVANCE= a_ADVANCE+b_ADVANCE;
		total_Ngovt_BALANCE= a_BALANCE+b_BALANCE;
		
		pcell=new PdfPCell(new Paragraph("Total: ",font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(String.valueOf(total_Ngovt_cust),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_SB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_DB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_OB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_CASH),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BG),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_ADVANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BALANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		///end of disconnected  non govt
		/// begins govt
		
		  b_cus=0;
		  b_SB=0.0;
		  b_DB=0.0;
		  b_OB=0.0;
		  b_CASH=0;
		  b_BG=0.0;
		  b_ADVANCE=0;
		  b_BALANCE=0;
		  a_cust=0;
		  a_SB=0.0;
		  a_DB=0.0;
		  a_OB=0.0;
		  a_CASH=0.0;
		  a_BG=0.0;
		  a_ADVANCE=0;
		  a_BALANCE=0;
		
		pcell=new PdfPCell(new Paragraph("Govt Customers",font2));
		pcell.setRowspan(3);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		for(BalancingReportDTO x: nonMeterDataDisConnected){
			if(x.getFlag().equalsIgnoreCase("Cb3G")){
				pcell=new PdfPCell(new Paragraph("Dues: 3 Months or Less ",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(0,"09", "<=")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) b_cus= Integer.parseInt( x.getCustomer_count());
				if(!(x.getsBurner()==null)) b_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) b_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) b_OB=  x.getoBurner();
				if(!(x.getCash()==null)) b_CASH=  x.getCash();
				if(!(x.getBg()==null)) b_BG=  x.getBg();
				if(!(x.getAdvance()==null)) b_ADVANCE= Double.valueOf((x.getAdvance()));
				b_BALANCE= getBalanceForBalancing(0,"09", "<=");
				
			}
			
			if(x.getFlag().equalsIgnoreCase("Ca3G")){
				pcell=new PdfPCell(new Paragraph("Dues: Above 3 Months",font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_count(),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getsBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getdBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(number_format.format(x.getoBurner()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCash()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getBg()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAdvance()),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(getBalanceForBalancing(0,"09", ">")),font2));
				pcell.setRowspan(1);
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
				if(!(x.getCustomer_count()==null)) a_cust= Integer.parseInt(x.getCustomer_count());
				if(!(x.getsBurner()==null)) a_SB=  x.getsBurner();
				if(!(x.getdBurner()==null)) a_DB=  x.getdBurner();
				if(!(x.getoBurner()==null)) a_OB=  x.getoBurner();
				if(!(x.getCash()==null)) a_CASH=  x.getCash();
				if(!(x.getBg()==null)) a_BG=  x.getBg();
				if(!(x.getAdvance()==null)) a_ADVANCE= Double.valueOf(x.getAdvance());
				a_BALANCE= getBalanceForBalancing(0,"09", ">");
				
			}
			
			
		}
		total_govt_cus= a_cust+b_cus;
		total_govt_SB= a_SB+b_SB;
		total_govt_DB= a_DB+b_DB;
		total_govt_OB= a_OB+b_OB;
		total_govt_CASH= a_CASH+b_CASH;
		total_govt_BG= a_BG+ b_BG;
		total_govt_ADVANCE= a_ADVANCE+b_ADVANCE;
		total_govt_BALANCE= a_BALANCE+b_BALANCE;
		
		pcell=new PdfPCell(new Paragraph("Total: ",font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(String.valueOf(total_govt_cus),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_govt_SB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_govt_DB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_govt_OB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_govt_CASH),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_govt_BG),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(String.valueOf(taka_format.format((total_govt_ADVANCE))),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_govt_BALANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		//////disconnected total
		pcell=new PdfPCell(new Paragraph("DisConnected Total: ",font3));
		pcell.setRowspan(1);
		pcell.setColspan(2);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(String.valueOf(total_Ngovt_cust+total_govt_cus),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_SB+total_govt_SB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_DB+total_govt_DB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(number_format.format(total_Ngovt_OB+total_govt_OB),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_CASH+total_govt_CASH),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BG+total_govt_BG),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_ADVANCE+total_govt_ADVANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph(taka_format.format(total_Ngovt_BALANCE+total_govt_BALANCE),font3));
		pcell.setRowspan(1);
		pcell.setColspan(1);
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		datatable1.addCell(pcell);		
		/////// grand total begins here
		
		
		
		
		
		document.add(headlineTable);
		document.add(datatable1);
		
	}
	
	Statement st=null;					
	ResultSet rs=null;	
	
	public  ArrayList<BalancingReportDTO> getNonMeteredinfo(int status)
	{
	ArrayList<BalancingReportDTO> nonMeterInfo=null;			
	nonMeterInfo= new ArrayList<BalancingReportDTO>();
	String sql= getqueryforNonmetered(status);
	
	System.out.println(getqueryforNonmetered(status));
	try {

		 conn = ConnectionManager.getConnection();
		 st=conn.createStatement();						
		 rs=st.executeQuery(sql);
		 
		 while(rs.next()){
			 
			 BalancingReportDTO balancingReportDTO=new BalancingReportDTO();
			 balancingReportDTO.setCustomer_count(rs.getString("CUSTOMER_NO"));
			 balancingReportDTO.setsBurner(rs.getDouble("SINGLE_BURNER"));
			 balancingReportDTO.setdBurner(rs.getDouble("DOUBLE_BURNER"));
			 balancingReportDTO.setoBurner(rs.getDouble("OTHERS_BURNER"));
			 balancingReportDTO.setCash(rs.getDouble("SUM(CASH)"));
			 balancingReportDTO.setBg(rs.getDouble("SUM(BG)"));
			 balancingReportDTO.setAdvance(rs.getDouble("ADVANCE_TOTAL"));
			 balancingReportDTO.setFlag(rs.getString("FLAG"));
			 
			 nonMeterInfo.add(balancingReportDTO);			
			 
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
	finally {
		try {
			st.close();
			ConnectionManager.closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		st = null;
		conn = null;
	}
		
	return nonMeterInfo;
	}
		
	private String getqueryforNonmetered(int status){
		
		String sql = " SELECT * " +
				"  FROM (SELECT * " +
				"          FROM (SELECT COUNT (customer_id) customer_no,    " +
				"                       SUM (single_burner) single_burner, " +
				"                       SUM (double_burner) double_burner, " +
				"                       SUM (others_burner) others_burner, " +
				"                       'Cb3D' flag " +
				"                  FROM bill_non_metered " +
				"                 WHERE bill_id IN " +
				"                          (SELECT "+year+"06 || AA.CUSTOMER_ID bill_id " +
				"                             FROM (  SELECT customer_id " +
				"                                       FROM bill_non_metered " +
				"                                      WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                            AND '"+year+"060101' " +
				"                                            AND status =  1" +
				"                                   GROUP BY customer_id " +
				"                                     HAVING COUNT (customer_id) <= 3) aa, " +
				"                                  customer_connection cc " +
				"                            WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                                  AND CC.STATUS = "+status+") " +
				"                UNION " +
				"                SELECT COUNT (customer_id),                 " +
				"                       SUM (single_burner), " +
				"                       SUM (double_burner), " +
				"                       SUM (others_burner), " +
				"                       'Ca3D' flag " +
				"                  FROM bill_non_metered " +
				"                 WHERE bill_id IN " +
				"                          (SELECT "+year+"06 || AA.CUSTOMER_ID bill_id " +
				"                             FROM (  SELECT customer_id " +
				"                                       FROM bill_non_metered " +
				"                                      WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                            AND '"+year+"060101' " +
				"                                            AND status = 1"+ 
				"                                   GROUP BY customer_id " +
				"                                     HAVING COUNT (customer_id) > 3) aa, " +
				"                                  customer_connection cc " +
				"                            WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                                  AND CC.STATUS = "+status+" ) " +
				"                UNION " +
				"                SELECT COUNT (customer_id),                " +
				"                       SUM (single_burner), " +
				"                       SUM (double_burner), " +
				"                       SUM (others_burner), " +
				"                       'Cb3G' flag " +
				"                  FROM bill_non_metered " +
				"                 WHERE bill_id IN " +
				"                          (SELECT "+year+"06 || AA.CUSTOMER_ID bill_id " +
				"                             FROM (  SELECT customer_id " +
				"                                       FROM bill_non_metered " +
				"                                      WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                            AND '"+year+"060109' " +
				"                                            AND status =  1"+ 
				"                                   GROUP BY customer_id " +
				"                                     HAVING COUNT (customer_id) <= 3) aa, " +
				"                                  customer_connection cc " +
				"                            WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                                  AND CC.STATUS = +"+status+") " +
				"                UNION " +
				"                SELECT COUNT (customer_id),                  " +
				"                       SUM (single_burner), " +
				"                       SUM (double_burner), " +
				"                       SUM (others_burner), " +
				"                       'Ca3G' flag " +
				"                  FROM bill_non_metered " +
				"                 WHERE bill_id IN " +
				"                          (SELECT "+year+"06 || AA.CUSTOMER_ID bill_id " +
				"                             FROM (  SELECT customer_id " +
				"                                       FROM bill_non_metered " +
				"                                      WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                            AND '"+year+"060109' " +
				"                                            AND status =  1" +
				"                                   GROUP BY customer_id " +
				"                                     HAVING COUNT (customer_id) > 3) aa, " +
				"                                  customer_connection cc " +
				"                            WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                                  AND CC.STATUS = "+status+"))) tm1, " +
				"       ((SELECT SUM (ADVANCED_AMOUNT) ADVANCE_TOTAL, 'Cb3D' flag " +
				"           FROM bill_coll_advanced " +
				"          WHERE     customer_id IN " +
				"                       (SELECT AA.CUSTOMER_ID customer_id " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                         AND '"+year+"060101' " +
				"                                         AND status =  1" +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) <= 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") " +
				"                AND status = 1 " +
				"         UNION " +
				"         SELECT SUM (ADVANCED_AMOUNT) ADVANCE_TOTAL, 'Ca3D' flag " +
				"           FROM bill_coll_advanced " +
				"          WHERE     customer_id IN " +
				"                       (SELECT AA.CUSTOMER_ID customer_id " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                         AND '"+year+"060101' " +
				"                                         AND status = 1 " +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) > 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") " +
				"                AND status = 1 " +
				"         UNION                                                           " +
				"         SELECT SUM (ADVANCED_AMOUNT) ADVANCE_TOTAL, 'Cb3G' flag " +
				"           FROM bill_coll_advanced " +
				"          WHERE     customer_id IN " +
				"                       (SELECT AA.CUSTOMER_ID customer_id " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                         AND '"+year+"060109' " +
				"                                         AND status = 1 " +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) <= 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") " +
				"                AND status = 1 " +
				"         UNION " +
				"         SELECT SUM (ADVANCED_AMOUNT) ADVANCE_TOTAL, 'Ca3G' flag " +
				"           FROM bill_coll_advanced " +
				"          WHERE     customer_id IN " +
				"                       (SELECT AA.CUSTOMER_ID customer_id " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                         AND '"+year+"060109' " +
				"                                         AND status =  1" +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) > 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") " +
				"                AND status = 1)) tm2, " +
				"       (SELECT SUM (CASH), SUM (BG), 'Cb3D' flag " +
				"          FROM (SELECT md.CUSTOMER_ID, 0 CASH, TOTAL_DEPOSIT BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                         AND '"+year+"060101' " +
				"                                         AND status =  1" +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) <= 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 1 " +
				"                       AND MD.CUSTOMER_ID LIKE '0101%' " +
				"                UNION ALL " +
				"                SELECT md.CUSTOMER_ID, TOTAL_DEPOSIT CASH, 0 BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                         AND '"+year+"060101' " +
				"                                         AND status =  1" +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) <= 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 0 " +
				"                       AND MD.CUSTOMER_ID LIKE '0101%') " +
				"        UNION " +
				"        SELECT SUM (CASH), SUM (BG), 'Ca3D' flag " +
				"          FROM (SELECT md.CUSTOMER_ID, 0 CASH, TOTAL_DEPOSIT BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                         AND '"+year+"060101' " +
				"                                         AND status =  1" +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) > 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 1 " +
				"                       AND MD.CUSTOMER_ID LIKE '0101%' " +
				"                UNION ALL " +
				"                SELECT md.CUSTOMER_ID, TOTAL_DEPOSIT CASH, 0 BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070101' " +
				"                                                                         AND '"+year+"060101' " +
				"                                         AND status = 1 " +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) > 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 0 " +
				"                       AND MD.CUSTOMER_ID LIKE '0101%') " +
				"        UNION " +
				"        SELECT SUM (CASH), SUM (BG), 'Cb3G' flag " +
				"          FROM (SELECT md.CUSTOMER_ID, 0 CASH, TOTAL_DEPOSIT BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                         AND '"+year+"060109' " +
				"                                         AND status = 1 " +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) <= 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 1 " +
				"                       AND MD.CUSTOMER_ID LIKE '0109%' " +
				"                UNION ALL " +
				"                SELECT md.CUSTOMER_ID, TOTAL_DEPOSIT CASH, 0 BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                         AND '"+year+"060109' " +
				"                                         AND status = 1 " +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) <= 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 0 " +
				"                       AND MD.CUSTOMER_ID LIKE '0109%') " +
				"        UNION " +
				"        SELECT SUM (CASH), SUM (BG), 'Ca3G' flag " +
				"          FROM (SELECT md.CUSTOMER_ID, 0 CASH, TOTAL_DEPOSIT BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                         AND '"+year+"060109' " +
				"                                         AND status = 1 " +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) > 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 1 " +
				"                       AND MD.CUSTOMER_ID LIKE '0109%' " +
				"                UNION ALL " +
				"                SELECT md.CUSTOMER_ID, TOTAL_DEPOSIT CASH, 0 BG " +
				"                  FROM mst_deposit md, " +
				"                       (SELECT AA.CUSTOMER_ID CUSTOMER_ID " +
				"                          FROM (  SELECT customer_id " +
				"                                    FROM bill_non_metered " +
				"                                   WHERE     SUBSTR (bill_id, 1, 10) BETWEEN '"+String.valueOf(Integer.parseInt(year)-1)+"070109' " +
				"                                                                         AND '"+year+"060109' " +
				"                                         AND status = 1 " +
				"                                GROUP BY customer_id " +
				"                                  HAVING COUNT (customer_id) > 3) aa, " +
				"                               customer_connection cc " +
				"                         WHERE     AA.CUSTOMER_ID = CC.CUSTOMER_ID " +
				"                               AND CC.STATUS = "+status+") bb " +
				"                 WHERE     MD.CUSTOMER_ID = BB.CUSTOMER_ID " +
				"                       AND DEPOSIT_PURPOSE = 1 " +
				"                       AND DEPOSIT_TYPE = 0 " +
				"                       AND MD.CUSTOMER_ID LIKE '0109%')) tm3 " +
				" WHERE tm1.flag = tm2.flag AND tm1.flag = tm3.flag " ;
		
		return sql;

	}

	public double getBalanceForBalancing(int cStatus, String category, String dueStatus){
		double balance=0.0;
		String sql= "SELECT SUM (ACTUAL_PAYABLE_AMOUNT) - SUM (COLLECTED_PAYABLE_AMOUNT) balance " +
				" FROM bill_non_metered bnm " +
				" WHERE customer_id IN " +
				" (SELECT AA.CUSTOMER_ID customer_id " +
				" FROM ( SELECT customer_id " +
				" FROM bill_non_metered " +
				" WHERE SUBSTR (bill_id, 1, 10) BETWEEN '"+(Integer.parseInt(year)-1)+"07"+area+category+"' " +
				" AND '"+year+"06"+area+category+"' " +
				" AND status = 1 " +
				" GROUP BY customer_id " +
				" HAVING COUNT (customer_id) "+dueStatus+" 3) aa, " +
				" customer_connection cc " +
				" WHERE AA.CUSTOMER_ID = CC.CUSTOMER_ID AND CC.STATUS = "+cStatus+") " +
				" AND BILL_MONTH || BILL_YEAR <= 6"+year; 
		 
		 try{
			conn = ConnectionManager.getConnection();
			st=conn.createStatement();						
			rs=st.executeQuery(sql);
			
			 while(rs.next()){
				 balance= rs.getDouble("BALANCE");
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		
		 finally {
				try {
					st.close();
					ConnectionManager.closeConnection(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
				st = null;
				conn = null;
			}
		 
		return balance;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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
