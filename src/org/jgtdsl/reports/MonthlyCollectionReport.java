package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import oracle.jdbc.driver.OracleCallableStatement;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.AdayBokeyaDTO;
import org.jgtdsl.dto.CollectionReportDTO;
import org.jgtdsl.dto.NonMeterReportDTO;
import org.jgtdsl.dto.TransactionDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.DisconnType;
import org.jgtdsl.enums.Month;
import org.jgtdsl.reports.masterData.CustomerCategory;
import org.jgtdsl.reports.*;
import org.jgtdsl.utils.Utils;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;



public class MonthlyCollectionReport extends BaseAction {
	private  final long serialVersionUID = 1L;
	
    private  String area;
    private  String customer_category;
    private  String bill_month;
    private  String bill_year;
    private  String report_for; 
    private  String category_name;
	 ArrayList<CollectionReportDTO> collectionInfoList=new ArrayList<CollectionReportDTO>();
	
	UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");	

	Connection conn = ConnectionManager.getConnection();		
	String sql = "";
	ArrayList<String>customerType=new ArrayList<String>();
	
	PreparedStatement ps=null;
	ResultSet rs=null;
	//String[] areaName=new String[10];
	int a=0;
	


	public ServletContext servlet;
	ServletContext servletContext = null;

	 Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	 Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	 Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	 DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	 DecimalFormat taka_format_BD = new DecimalFormat("#,####,##,##,##0.00");
	 DecimalFormat consumption_format = new DecimalFormat("##########0.000");


	public String execute() throws Exception {

		String fileName = "CollectionStatement.pdf";
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
			float b=((page.getWidth()*30)/100)/2;
				
			headerTable.setWidths(new float[] {
				a,b,a
			});
			
			
			pcell= new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			
			
			String realPath = servlet.getRealPath("/resources/images/logo/JG.png"); 	// image path
			Image img = Image.getInstance(realPath);
						
            	//img.scaleToFit(10f, 200f);
            	//img.scalePercent(200f);
				img.scaleAbsolute(28f, 31f);
				//img.setAbsolutePosition(145f, 780f);		
            	img.setAbsolutePosition(350f, 520f);		// rotate
            
	        document.add(img);
		
			
			PdfPTable mTable=new PdfPTable(1);
			mTable.setWidths(new float[]{b});
			pcell=new PdfPCell(new Paragraph("JALALABAD GAS T & D SYSTEM LIMITED"));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);	
			mTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("(A COMPANY OF PETROBANGLA)", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			
			if(report_for.equals("category_type_wise"))
			{
				Chunk chunk1 = new Chunk("REGIONAL OFFICE : ",font2);
				Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(area)-1]),font3);
				Paragraph p = new Paragraph(); 
				p.add(chunk1);
				p.add(chunk2);
				
				pcell=new PdfPCell(p);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				mTable.addCell(pcell);
			}
			else	if(report_for.equals("category_type_wise_f_all_area"))
			{
				Chunk chunk1 = new Chunk("Combined Report for: ",font2);
				//Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(loggedInUser.getArea_id())-1]),font3);
				Paragraph p = new Paragraph(); 
				p.add(chunk1);
				//p.add(chunk2);
				
				pcell=new PdfPCell(p);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				mTable.addCell(pcell);
			}else{
				
				Chunk chunk1 = new Chunk("REGIONAL OFFICE : ",font2);
				Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(area)-1]),font3);
				Paragraph p = new Paragraph(); 
				p.add(chunk1);
				p.add(chunk2);
				
				pcell=new PdfPCell(p);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				mTable.addCell(pcell);
			}
			
		
			
		/*	pcell=new PdfPCell(p);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);*/
			
			
						
			pcell=new PdfPCell(mTable);
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			
			
			
			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			document.add(headerTable);
			
			
			
			String bmont = "00".substring(bill_month.length()) + bill_month;

		
        	int month_year=Integer.valueOf(bill_year+bmont);
        	
        	// 	Aday bokeya report 	@hnj
        	
        	if(report_for.equals("aday_bokeya"))
			{
				
        		aday_bokeya(document);
			}
        	
        	
			if(report_for.equals("area_wise")&& month_year>=201605)
			{
				
				area_wise(document);
			}
			
			
		 	
			if(report_for.equals("category_wise")&& month_year>=201605)							// category_wise 
			{
				
				category_wise(document);
			}
			
			if(report_for.equals("category_type_wise"))
			{
				category_type_wise(document);
			}
			
			if(report_for.equals("category_type_wise_f_all_area"))
			{
				category_type_wise(document);
			}
			
		
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(), fileName);
			document = null;



		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;




	}


	


	private  PdfPCell createTableNotHeaderCell(final String text) {
		final PdfPCell cell = new PdfPCell(new Paragraph(text, font2));

		cell.setMinimumHeight(16f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		//cell.setBackgroundColor(new BaseColor(242,242,242));
		cell.setBorderColor(BaseColor.BLACK);


		return cell;
	}

	private  PdfPCell createTableHeaderCell(final String text) {
		final PdfPCell cell = new PdfPCell(new Phrase(text, font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		//cell.setBackgroundColor(new BaseColor(210,211,212));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(BaseColor.BLACK);
		cell.setFixedHeight(20f);

		return cell;
	}
	
	
	private  void area_wise(Document document) throws DocumentException
	{
		
		PdfPTable headlineTable = new PdfPTable(3);
		headlineTable.setSpacingBefore(5);
		headlineTable.setSpacingAfter(10);
		headlineTable.setWidths(new float[] {
				40,70,40
			});
		PdfPCell pcell = null;
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Monthly Collection Statement For the month Of "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year, ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		document.add(headlineTable);
		
	
	
		
		Double totalOpeningBalance=0.0;
		Double totalAdjustment=0.0;
		Double totalCurrSales=0.0;
		Double totalCurrSurcharge=0.0;
		Double totalAccountReceivale=0.0;
		Double totalGasBill=0.0;
		Double totalMeterRent=0.0;
		Double totalSurcharge=0.0;
		Double totalIncomeTax=0.0;
		Double totalVatRebate=0.0;
		Double totalHhvNhv =0.0;
		Double totalTotalCollection =0.0;
		Double totalPreviousDue =0.0;
		Double totalAvgMonthlySales =0.0;
		Double totalAvgDue =0.0;
		
		
		
		Double subSumOpeningBalance=0.0;
		Double subSumAdjustment=0.0;
		Double subSumCurrSales=0.0;
		Double subSumCurrSurcharge=0.0;
		Double subSumAccountReceivale=0.0;
		Double subSumGasBill=0.0;
		Double subSumMeterRent=0.0;
		Double subSumSurcharge=0.0;
		Double subSumIncomeTax=0.0;
		Double subSumVatRebate=0.0;
		Double subSumHhvNhv =0.0;
		Double subSumTotalCollection =0.0;
		Double subSumPreviousDue =0.0;
		Double subSumAvgMonthlySales =0.0;
		Double subSumAvgDue =0.0;
		
		
	
		
		PdfPTable datatable1 = new PdfPTable(17);
		
		datatable1.setWidthPercentage(100);
		datatable1.setWidths(new float[] {15,50,40,30,40,30,40,40,40,40,40,40,40,40,40,40,40});
		
		
		pcell=new PdfPCell(new Paragraph("Sl.No",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Category of Customer",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		String dateChunk="";
	    String dateChunk2="";
	  
	    int month_prev=Integer.valueOf(bill_month)-1;
	    
	    int numDays1 = Utils.getLastDayOfMonth(month_prev,Integer.valueOf(bill_year));
	    int numDays2= Utils.getLastDayOfMonth(Integer.valueOf(bill_month),Integer.valueOf(bill_year));
	    
	    if(bill_month.equals("01"))
	    {
	     dateChunk="Balance as on 31-12-"+(Integer.valueOf(bill_year)-1);
	     dateChunk2="Total Due as on 31-12-"+(Integer.valueOf(bill_year)-1);
	    }else
	    {
	     dateChunk="Balance as on "+numDays1+"-"+(Integer.valueOf(bill_month)-1)+"-"+bill_year;
	     dateChunk2="Total Due as on "+numDays2+"-"+(Integer.valueOf(bill_month))+"-"+bill_year;
	    }
		pcell=new PdfPCell(new Paragraph(dateChunk,font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Adjustment",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Sales For "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year,font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Surcharge",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("A/R as on DATE",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Collection For Month Of "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year,font3));
		pcell.setColspan(7);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
	
		pcell=new PdfPCell(new Paragraph(dateChunk2,font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Average monthly Sales",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Average due month",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
	
	
		pcell=new PdfPCell(new Paragraph("Gas bill",font3));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Meter Rent",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Surcharge",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Income Tax",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Vat rebate",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("NHV/HHV",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Total Collection",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
	    
		pcell=new PdfPCell(new Paragraph("01",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("02",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("03",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("04",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("05",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("06=10",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("07=(3+4+5+6)",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("08",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("09",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("10",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("11",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("12",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("13",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("14=(8+9+10+11+12+13)",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("15=(7-14)",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("16",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("17=(15/16)",font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		
		
		generateCollectionDataForReport();
		collectionInfoList=getCollectionInfoList();
		int expireListSize=collectionInfoList.size();
		

		pcell=new PdfPCell(new Paragraph("1",font3));
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph(Area.values()[Integer.valueOf(area)-1]+" AREA",font3));
		pcell.setColspan(16);
		datatable1.addCell(pcell);
		
		int cusCatCount=97;
		String previousType="GOVT";
		
		String currnertType="";
		try {
		
			   			
			for(int i=0;i<expireListSize;i++)
			
        	{
				CollectionReportDTO collectionDto=collectionInfoList.get(i);
				
    			if(cusCatCount==97)
    			{
    				pcell=new PdfPCell(new Paragraph(previousType.equals("PVT")?"A) PRIVATE":"A) GOVERNMENT",font3));
    				pcell.setColspan(2);
    				datatable1.addCell(pcell);
    				pcell=new PdfPCell(new Paragraph("",font3));
    				pcell.setColspan(15);
    				datatable1.addCell(pcell);
    			}
    			
    			currnertType=collectionDto.getCategory_type();
    			if(!currnertType.equals(previousType))
    			{
    			
	    			if(cusCatCount!=97){
	    				
	    				pcell=new PdfPCell(new Paragraph("Sub Total (A)",font3));
	    				pcell.setColspan(2);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumOpeningBalance),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAdjustment),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSales),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSurcharge),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAccountReceivale),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumGasBill),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMeterRent),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumSurcharge),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumIncomeTax),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumVatRebate),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    			
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumHhvNhv),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumTotalCollection),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAvgMonthlySales),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue/subSumAvgMonthlySales),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				 totalOpeningBalance=totalOpeningBalance+subSumOpeningBalance;
	    				 totalAdjustment=totalAdjustment+subSumAdjustment;
	    				 totalCurrSales=totalCurrSales+subSumCurrSales;
	    				 totalCurrSurcharge=totalCurrSurcharge+subSumCurrSurcharge;
	    				 totalAccountReceivale=totalAccountReceivale+subSumAccountReceivale;
	    				 totalGasBill=totalGasBill+subSumGasBill;
	    				 totalMeterRent=totalMeterRent+subSumMeterRent;
	    				 totalSurcharge=totalSurcharge+subSumSurcharge;
	    				 totalIncomeTax=totalIncomeTax+subSumIncomeTax;
	    				 totalVatRebate=totalVatRebate+subSumVatRebate;
	    				 totalHhvNhv =totalHhvNhv+subSumHhvNhv;
	    				 totalTotalCollection =totalTotalCollection+subSumTotalCollection;
	    				 totalPreviousDue =totalPreviousDue+subSumPreviousDue;
	    				 totalAvgMonthlySales =totalAvgMonthlySales+subSumAvgMonthlySales;
	    				 totalAvgDue =totalAvgDue+subSumAvgDue;
	    				
	    				
	    				 
	    				 subSumOpeningBalance=0.0;
	    				 subSumAdjustment=0.0;
	    				 subSumCurrSales=0.0;
	    				 subSumCurrSurcharge=0.0;
	    				 subSumAccountReceivale=0.0;
	    				 subSumGasBill=0.0;
	    				 subSumMeterRent=0.0;
	    				 subSumSurcharge=0.0;
	    				 subSumIncomeTax=0.0;
	    				 subSumVatRebate=0.0;
	    				 subSumHhvNhv =0.0;
	    				 subSumTotalCollection =0.0;
	    				 subSumPreviousDue =0.0;
	    				 subSumAvgMonthlySales =0.0;
	    				 subSumAvgDue =0.0;

	    				
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(currnertType.equals("PVT")?"B) PRIVATE":"B) GOVERNMENT",font3));
	    				pcell.setColspan(2);
	    				datatable1.addCell(pcell);
	    				pcell=new PdfPCell(new Paragraph("",font3));
	    				pcell.setColspan(15);
	    				datatable1.addCell(pcell);
	    				cusCatCount=97;
	    				previousType=currnertType;
	    			}
					
    			}
    			
    			pcell=new PdfPCell(new Paragraph(Character.toString ((char) cusCatCount)+")",font3));
    			datatable1.addCell(pcell);
    			pcell=new PdfPCell(new Paragraph(collectionDto.getCategory_name(),font3));
    			pcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
    			datatable1.addCell(pcell);
    				    		
    			
    			
    	        
    			
    			subSumOpeningBalance=subSumOpeningBalance+collectionDto.getOpening_balance();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getOpening_balance()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAdjustment=subSumAdjustment+collectionDto.getAdjustment();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAdjustment()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumCurrSales=subSumCurrSales+collectionDto.getCurr_sales();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getCurr_sales()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumCurrSurcharge=subSumCurrSurcharge+collectionDto.getCurr_surcharge();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getCurr_surcharge()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAccountReceivale=subSumAccountReceivale+collectionDto.getAccount_receivable();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAccount_receivable()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumGasBill=subSumGasBill+collectionDto.getGas_bill();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getGas_bill()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumMeterRent=subSumMeterRent+collectionDto.getMeter_rent();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getMeter_rent()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumSurcharge=subSumSurcharge+collectionDto.getColl_surcharge();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getColl_surcharge()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumIncomeTax=subSumIncomeTax+collectionDto.getIncome_tax();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getIncome_tax()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumVatRebate=subSumVatRebate+collectionDto.getVat_rebate();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getVat_rebate()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumHhvNhv=subSumHhvNhv+collectionDto.getHhv_nhv();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getHhv_nhv()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumTotalCollection=subSumTotalCollection+collectionDto.getTotal_collection();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getTotal_collection()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumPreviousDue=subSumPreviousDue+collectionDto.getPrevious_due();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getPrevious_due()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAvgMonthlySales=subSumAvgMonthlySales+collectionDto.getAvg_monthly_sales();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAvg_monthly_sales()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAvgDue=subSumAvgDue+collectionDto.getAvg_due();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAvg_due()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    		
    			cusCatCount++;
        	}	
    		
		
			pcell=new PdfPCell(new Paragraph("Sub Total (B)",font3));
			pcell.setColspan(2);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumOpeningBalance),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell); 
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAdjustment),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAccountReceivale),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumGasBill),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMeterRent),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumIncomeTax),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumVatRebate),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumHhvNhv),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumTotalCollection),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue/subSumAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			 totalOpeningBalance=totalOpeningBalance+subSumOpeningBalance;
			 totalAdjustment=totalAdjustment+subSumAdjustment;
			 totalCurrSales=totalCurrSales+subSumCurrSales;
			 totalCurrSurcharge=totalCurrSurcharge+subSumCurrSurcharge;
			 totalAccountReceivale=totalAccountReceivale+subSumAccountReceivale;
			 totalGasBill=totalGasBill+subSumGasBill;
			 totalMeterRent=totalMeterRent+subSumMeterRent;
			 totalSurcharge=totalSurcharge+subSumSurcharge;
			 totalIncomeTax=totalIncomeTax+subSumIncomeTax;
			 totalVatRebate=totalVatRebate+subSumVatRebate;
			 totalHhvNhv =totalHhvNhv+subSumHhvNhv;
			 totalTotalCollection =totalTotalCollection+subSumTotalCollection;
			 totalPreviousDue =totalPreviousDue+subSumPreviousDue;
			 totalAvgMonthlySales =totalAvgMonthlySales+subSumAvgMonthlySales;
			 totalAvgDue =totalAvgDue+subSumAvgDue;
			
			pcell=new PdfPCell(new Paragraph("Total Sales Of "+Area.values()[Integer.valueOf(area)-1]+"(A+B)=",font3));
			pcell.setColspan(2);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalOpeningBalance),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalAdjustment),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalCurrSales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalCurrSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalAccountReceivale),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalGasBill),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalMeterRent),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalIncomeTax),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalVatRebate),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalHhvNhv),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalTotalCollection),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalPreviousDue),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalPreviousDue/totalAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
		
			
			        
			        
			    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		
		document.add(datatable1);

		
	}
	
	
	
	// ****************************************** category wise **************************
	
	
	private  void category_wise(Document document) throws DocumentException
	{
		
		PdfPTable headlineTable = new PdfPTable(3);
		headlineTable.setSpacingBefore(5);
		headlineTable.setSpacingAfter(10);
		headlineTable.setWidths(new float[] {
				40,70,40
			});
		PdfPCell pcell = null;
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Monthly Collection Statement For the month Of "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year, ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		document.add(headlineTable);
		
	
	
		
		Double totalOpeningBalance=0.0;
		Double totalAdjustment=0.0;
		Double totalCurrSales=0.0;
		Double totalCurrSurcharge=0.0;
		Double totalAccountReceivale=0.0;
		Double totalGasBill=0.0;
		Double totalMeterRent=0.0;
		Double totalSurcharge=0.0;
		Double totalIncomeTax=0.0;
		Double totalVatRebate=0.0;
		Double totalHhvNhv =0.0;
		Double totalTotalCollection =0.0;
		Double totalPreviousDue =0.0;
		Double totalAvgMonthlySales =0.0;
		Double totalAvgDue =0.0;
		
		
		
		Double subSumOpeningBalance=0.0;
		Double subSumAdjustment=0.0;
		Double subSumCurrSales=0.0;
		Double subSumCurrSurcharge=0.0;
		Double subSumAccountReceivale=0.0;
		Double subSumGasBill=0.0;
		Double subSumMeterRent=0.0;
		Double subSumSurcharge=0.0;
		Double subSumIncomeTax=0.0;
		Double subSumVatRebate=0.0;
		Double subSumHhvNhv =0.0;
		Double subSumTotalCollection =0.0;
		Double subSumPreviousDue =0.0;
		Double subSumAvgMonthlySales =0.0;
		Double subSumAvgDue =0.0;
		
		
		
		PdfPTable collectionMonthCustomerType = new PdfPTable(3);
        collectionMonthCustomerType.setWidths(new float[] {60,90,30});
        collectionMonthCustomerType.setSpacingBefore(5);
        collectionMonthCustomerType.setSpacingAfter(5);
        
        
        pcell = new PdfPCell(new Paragraph("Collection Month: "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year,font3));
        pcell.setBorder(0);
        pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        collectionMonthCustomerType.addCell(pcell);
        
        pcell = new PdfPCell(new Paragraph("Type Of Customer",font3));
        pcell.setBorder(0);
        pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        collectionMonthCustomerType.addCell(pcell);
        
        
        pcell = new PdfPCell(new Paragraph("Captive Power",font3));
        //customerType.setBorder(0);
        pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        collectionMonthCustomerType.addCell(pcell);
        

        
        document.add(collectionMonthCustomerType);
	
        
		
		PdfPTable datatable1 = new PdfPTable(10);
		//datatable1.setWidthPercentage(100);
		datatable1.setWidths(new float[] {15,25,15,20,25,20,20,15,10,15});
		datatable1.setSpacingBefore(5);
		

		
		
		pcell=new PdfPCell(new Paragraph("Code",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer Details",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
	  
	    
		
		pcell=new PdfPCell(new Paragraph("Billing Month",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Date Of Payment",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Bank & Branch Name",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Gas Bill",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Surcharge",font3));
		pcell.setColspan(7);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
	
		pcell=new PdfPCell(new Paragraph("Fees",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("S.D",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Total Paid",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
	
	
		
	    
		
		
		
		
		generateCollectionDataForReport();
		collectionInfoList=getCollectionInfoList();
		int expireListSize=collectionInfoList.size();
		

		pcell=new PdfPCell(new Paragraph("1",font3));
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph(Area.values()[Integer.valueOf(area)-1]+" AREA",font3));
		pcell.setColspan(16);
		datatable1.addCell(pcell);
		
		int cusCatCount=97;
		String previousType="GOVT";
		
		String currnertType="";
		try {
		
			   			
			for(int i=0;i<expireListSize;i++)
			
        	{
				CollectionReportDTO collectionDto=collectionInfoList.get(i);
				
    			if(cusCatCount==97)
    			{
    				pcell=new PdfPCell(new Paragraph(previousType.equals("PVT")?"A) PRIVATE":"A) GOVERNMENT",font3));
    				pcell.setColspan(2);
    				datatable1.addCell(pcell);
    				pcell=new PdfPCell(new Paragraph("",font3));
    				pcell.setColspan(15);
    				datatable1.addCell(pcell);
    			}
    			
    			currnertType=collectionDto.getCategory_type();
    			if(!currnertType.equals(previousType))
    			{
    			
	    			if(cusCatCount!=97){
	    				
	    				pcell=new PdfPCell(new Paragraph("Sub Total (A)",font3));
	    				pcell.setColspan(2);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumOpeningBalance),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAdjustment),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSales),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSurcharge),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAccountReceivale),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumGasBill),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMeterRent),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumSurcharge),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumIncomeTax),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumVatRebate),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    			
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumHhvNhv),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumTotalCollection),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAvgMonthlySales),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue/subSumAvgMonthlySales),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				 totalOpeningBalance=totalOpeningBalance+subSumOpeningBalance;
	    				 totalAdjustment=totalAdjustment+subSumAdjustment;
	    				 totalCurrSales=totalCurrSales+subSumCurrSales;
	    				 totalCurrSurcharge=totalCurrSurcharge+subSumCurrSurcharge;
	    				 totalAccountReceivale=totalAccountReceivale+subSumAccountReceivale;
	    				 totalGasBill=totalGasBill+subSumGasBill;
	    				 totalMeterRent=totalMeterRent+subSumMeterRent;
	    				 totalSurcharge=totalSurcharge+subSumSurcharge;
	    				 totalIncomeTax=totalIncomeTax+subSumIncomeTax;
	    				 totalVatRebate=totalVatRebate+subSumVatRebate;
	    				 totalHhvNhv =totalHhvNhv+subSumHhvNhv;
	    				 totalTotalCollection =totalTotalCollection+subSumTotalCollection;
	    				 totalPreviousDue =totalPreviousDue+subSumPreviousDue;
	    				 totalAvgMonthlySales =totalAvgMonthlySales+subSumAvgMonthlySales;
	    				 totalAvgDue =totalAvgDue+subSumAvgDue;
	    				
	    				
	    				 
	    				 subSumOpeningBalance=0.0;
	    				 subSumAdjustment=0.0;
	    				 subSumCurrSales=0.0;
	    				 subSumCurrSurcharge=0.0;
	    				 subSumAccountReceivale=0.0;
	    				 subSumGasBill=0.0;
	    				 subSumMeterRent=0.0;
	    				 subSumSurcharge=0.0;
	    				 subSumIncomeTax=0.0;
	    				 subSumVatRebate=0.0;
	    				 subSumHhvNhv =0.0;
	    				 subSumTotalCollection =0.0;
	    				 subSumPreviousDue =0.0;
	    				 subSumAvgMonthlySales =0.0;
	    				 subSumAvgDue =0.0;

	    				
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(currnertType.equals("PVT")?"B) PRIVATE":"B) GOVERNMENT",font3));
	    				pcell.setColspan(2);
	    				datatable1.addCell(pcell);
	    				pcell=new PdfPCell(new Paragraph("",font3));
	    				pcell.setColspan(15);
	    				datatable1.addCell(pcell);
	    				cusCatCount=97;
	    				previousType=currnertType;
	    			}
					
    			}
    			
    			pcell=new PdfPCell(new Paragraph(Character.toString ((char) cusCatCount)+")",font3));
    			datatable1.addCell(pcell);
    			pcell=new PdfPCell(new Paragraph(collectionDto.getCategory_name(),font3));
    			pcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
    			datatable1.addCell(pcell);
    				    		
    			
    			
    	        
    			
    			subSumOpeningBalance=subSumOpeningBalance+collectionDto.getOpening_balance();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getOpening_balance()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAdjustment=subSumAdjustment+collectionDto.getAdjustment();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAdjustment()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumCurrSales=subSumCurrSales+collectionDto.getCurr_sales();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getCurr_sales()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumCurrSurcharge=subSumCurrSurcharge+collectionDto.getCurr_surcharge();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getCurr_surcharge()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAccountReceivale=subSumAccountReceivale+collectionDto.getAccount_receivable();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAccount_receivable()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumGasBill=subSumGasBill+collectionDto.getGas_bill();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getGas_bill()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumMeterRent=subSumMeterRent+collectionDto.getMeter_rent();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getMeter_rent()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumSurcharge=subSumSurcharge+collectionDto.getColl_surcharge();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getColl_surcharge()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumIncomeTax=subSumIncomeTax+collectionDto.getIncome_tax();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getIncome_tax()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumVatRebate=subSumVatRebate+collectionDto.getVat_rebate();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getVat_rebate()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumHhvNhv=subSumHhvNhv+collectionDto.getHhv_nhv();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getHhv_nhv()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumTotalCollection=subSumTotalCollection+collectionDto.getTotal_collection();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getTotal_collection()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumPreviousDue=subSumPreviousDue+collectionDto.getPrevious_due();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getPrevious_due()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAvgMonthlySales=subSumAvgMonthlySales+collectionDto.getAvg_monthly_sales();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAvg_monthly_sales()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			subSumAvgDue=subSumAvgDue+collectionDto.getAvg_due();
    			pcell=new PdfPCell(new Paragraph(taka_format.format(collectionDto.getAvg_due()),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    		
    			cusCatCount++;
        	}	
    		
		
			pcell=new PdfPCell(new Paragraph("Sub Total (B)",font3));
			pcell.setColspan(2);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumOpeningBalance),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell); 
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAdjustment),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumCurrSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAccountReceivale),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumGasBill),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMeterRent),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumIncomeTax),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumVatRebate),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumHhvNhv),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumTotalCollection),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumPreviousDue/subSumAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			 totalOpeningBalance=totalOpeningBalance+subSumOpeningBalance;
			 totalAdjustment=totalAdjustment+subSumAdjustment;
			 totalCurrSales=totalCurrSales+subSumCurrSales;
			 totalCurrSurcharge=totalCurrSurcharge+subSumCurrSurcharge;
			 totalAccountReceivale=totalAccountReceivale+subSumAccountReceivale;
			 totalGasBill=totalGasBill+subSumGasBill;
			 totalMeterRent=totalMeterRent+subSumMeterRent;
			 totalSurcharge=totalSurcharge+subSumSurcharge;
			 totalIncomeTax=totalIncomeTax+subSumIncomeTax;
			 totalVatRebate=totalVatRebate+subSumVatRebate;
			 totalHhvNhv =totalHhvNhv+subSumHhvNhv;
			 totalTotalCollection =totalTotalCollection+subSumTotalCollection;
			 totalPreviousDue =totalPreviousDue+subSumPreviousDue;
			 totalAvgMonthlySales =totalAvgMonthlySales+subSumAvgMonthlySales;
			 totalAvgDue =totalAvgDue+subSumAvgDue;
			
			pcell=new PdfPCell(new Paragraph("Total Sales Of "+Area.values()[Integer.valueOf(area)-1]+"(A+B)=",font3));
			pcell.setColspan(2);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalOpeningBalance),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalAdjustment),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalCurrSales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalCurrSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalAccountReceivale),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalGasBill),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalMeterRent),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalSurcharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalIncomeTax),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalVatRebate),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalHhvNhv),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalTotalCollection),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalPreviousDue),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalPreviousDue/totalAvgMonthlySales),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
		
			
			        
			        
			    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		
		document.add(datatable1);

		
	}
	
	
	/*	Aday bokeya report 
	 * 
	 *  @hnj
	 * */
	
	
	private  void aday_bokeya(Document document) throws DocumentException
	{
		
		document.setMargins(20,20,48,72);
		PdfPTable headLinetable = null;
		PdfPCell pcell=null;
		headLinetable = new PdfPTable(3);
		headLinetable.setWidthPercentage(100);
		headLinetable.setWidths(new float[]{60,10,30});
		
		pcell=new PdfPCell(new Paragraph("Collection Dues Report "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year, ReportUtil.f11B));
		pcell.setMinimumHeight(18f);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setBorder(0);
		pcell.setPadding(3);
		headLinetable.addCell(pcell);
		
		
		pcell = new PdfPCell(new Paragraph(""));
		pcell.setBorder(0);
		headLinetable.addCell(pcell);		
				
		
		pcell = new PdfPCell(new Paragraph("Amount in Taka"));
		pcell.setBorder(0);
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headLinetable.addCell(pcell);
		
		document.add(headLinetable);
		
		
		PdfPTable pdfPTable = new PdfPTable(10);
		pdfPTable.setWidthPercentage(100);
		pdfPTable.setWidths(new float[]{5,15,10,10,10,10,10,10,10,10});
		//pdfPTable.setHeaderRows(1);
		
		pcell = new PdfPCell(new Paragraph("SL",ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setRowspan(2);
		//pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Category",ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//pcell.setColspan(1);
		pcell.setRowspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Dues till "+Month.values()[Integer.valueOf(bill_month)-2]+"'"+bill_year,ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//pcell.setColspan(1);
		pcell.setRowspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Sales "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year,ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//pcell.setColspan(1);
		pcell.setRowspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Collection "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year,ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//pcell.setColspan(1);
		pcell.setRowspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Dues "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year,ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//pcell.setColspan(1);
		pcell.setRowspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Avg Sales",ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//pcell.setColspan(1);
		pcell.setRowspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Equivalent Avg Dues Month",ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	//	pcell.setColspan(1);
		pcell.setRowspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Disconnected",ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setRowspan(1);
		pcell.setColspan(2);
		pdfPTable.addCell(pcell);
		
		
		pcell = new PdfPCell(new Paragraph("Dues Amount",ReportUtil.f9B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setRowspan(1);		
	//	pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Equivalent Avg Dues Month",ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setRowspan(1);
	//	pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		//document.add(pdfPTable);
		
		
//		transactionList=getBankWiseDetailsTransaction();
//		int listSize=transactionList.size();
		
		
		/////////////
		
		ArrayList<AdayBokeyaDTO> categoryList = new ArrayList<AdayBokeyaDTO>();
		//ArrayList<AdayBokeyaDTO> tillPreviousMonthDues=new ArrayList<AdayBokeyaDTO>();
		//ArrayList<AdayBokeyaDTO> salesCurrentMonth=new ArrayList<AdayBokeyaDTO>();
		//ArrayList<AdayBokeyaDTO> collectionCurrentMonth = new ArrayList<AdayBokeyaDTO>();
		//ArrayList<AdayBokeyaDTO> last3MonthsAvgsales = new ArrayList<AdayBokeyaDTO>();
		
		
		Connection conn = ConnectionManager.getConnection();
		
		
		
		int currentMonth=Integer.valueOf(bill_month);
		int currentYear=Integer.valueOf(bill_year);
		
		
		//LinkedHashMap<String, Double> categoryList = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> tillPreviousMonthDues = new LinkedHashMap<String, Double>();	// query 2
		LinkedHashMap<String,Double> salesCurrentMonth = new LinkedHashMap<String,Double>();  		// query 3
		LinkedHashMap<String,Double> collectionCurrentMonth = new LinkedHashMap<String,Double>();	// query 4
		LinkedHashMap<String,Double> tillCurrentMonthDues = new LinkedHashMap<String,Double>();		// 2+3-4
		
		LinkedHashMap<String,Double> last3MonthsAvgsales = new LinkedHashMap<String,Double>();		// query 5
		
		LinkedHashMap<String,Double> disconnectedDues = new LinkedHashMap<String,Double>();			// query 6
		
		
		//### 	category list 
		
		try{
			String categoryList_sql = "SELECT CATEGORY_NAME, CATEGORY_ID "
									+ "    FROM mst_customer_category "
									+ "ORDER BY CATEGORY_ID";
			
			PreparedStatement psl = conn.prepareStatement(categoryList_sql);
			ResultSet resultSet = psl.executeQuery();
			
			while(resultSet.next()){
				AdayBokeyaDTO categoryListDTO = new AdayBokeyaDTO();
				categoryListDTO.setCategory_name(resultSet.getString("CATEGORY_NAME"));
				categoryListDTO.setCategory_id(resultSet.getString("CATEGORY_ID"));
				
				categoryList.add(categoryListDTO);
			}
		}catch(SQLException e){e.printStackTrace();
			
		}
		
		
		
		
		
		// **** 	Dues till previous month 
			
			try {
				
				String tillPreviousMonthDues_sql="SELECT CN, CC, SUM (amount) TOTAL"
												 + "    FROM (  SELECT MCC.CATEGORY_NAME CN, "
												 + "                   CUSTOMER_CATEGORY CC, "
												 + "                   sum( nvl(PAYABLE_AMOUNT,0)) amount "
												 + "              FROM bill_metered bm, mst_CUSTOMER_CATEGORY mcc "
												 + "             WHERE     BM.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
												 + "                   AND BM.AREA_ID = '"+area+"' "
												 + "                   AND bm.status = 1 "
												 + "                   AND bill_month < "+currentMonth+" "
												 + "                   AND bill_year <= "+currentYear+" "
												 + "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME "
												 + "          UNION ALL "
												 + "            SELECT MCC.CATEGORY_NAME, "
												 + "                   CUSTOMER_CATEGORY, "
												 + "                   sum( nvl(ACTUAL_PAYABLE_AMOUNT,0)) amount "
												 + "              FROM bill_non_metered bnm, mst_CUSTOMER_CATEGORY mcc "
												 + "             WHERE     bnm.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
												 + "                   AND BNM.AREA_ID = '"+area+"' "
												 + "                   AND bnm.status = 1 "
												 + "                   AND bill_month < "+currentMonth+" "
												 + "                   AND bill_year <= "+currentYear+" "
												 + "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME) "
												 + "GROUP BY CC, CN "
												 + "ORDER BY CC";
				
				
				PreparedStatement psl=conn.prepareStatement(tillPreviousMonthDues_sql);
			
	        	//System.out.println(ps1);
	        	//System.out.println(transaction_sql);
	        	ResultSet resultSet=psl.executeQuery();
	        	
	        	
	        	while(resultSet.next())
	        	{
	        		tillPreviousMonthDues.put(resultSet.getString("CC"), resultSet.getDouble("TOTAL"));
	        	}
	        	
	        	resultSet.close();
			    psl.close();
//			    conn.close();
			    
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			// **** Sales of current month
			
			try{
				
				String salesCurrentMonth_sql= "SELECT CN, CC, SUM (amount) TOTAL "
											+ "    FROM (  SELECT MCC.CATEGORY_NAME CN, "
											+ "                   CUSTOMER_CATEGORY CC, "
											+ "                   sum( nvl(PAYABLE_AMOUNT,0)) amount "
											+ "              FROM bill_metered bm, mst_CUSTOMER_CATEGORY mcc "
											+ "             WHERE     BM.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
											+ "                   AND BM.AREA_ID = '"+area+"' "
											+ "                   AND bill_month = "+currentMonth+" "
											+ "                   AND bill_year = "+currentYear+" "
											+ "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME "
											+ "          UNION ALL "
											+ "            SELECT MCC.CATEGORY_NAME, "
											+ "                   CUSTOMER_CATEGORY, "
											+ "                   sum( nvl(ACTUAL_PAYABLE_AMOUNT,0)) amount "
											+ "              FROM bill_non_metered bnm, mst_CUSTOMER_CATEGORY mcc "
											+ "             WHERE     bnm.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
											+ "                   AND BNM.AREA_ID = '"+area+"' "
											+ "                   AND bill_month = "+currentMonth+" "
											+ "                   AND bill_year = "+currentYear+" "
											+ "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME) "
											+ "GROUP BY CC, CN "
											+ "ORDER BY CC";
				
				PreparedStatement ps1=conn.prepareStatement(salesCurrentMonth_sql);
				
				ResultSet resultSet = ps1.executeQuery();

				while(resultSet.next()){
//					AdayBokeyaDTO salesCurrentMonthDTO = new AdayBokeyaDTO();
//					salesCurrentMonthDTO.setCategory_name(resultSet.getString("CN"));
//					salesCurrentMonthDTO.setCategory_id(resultSet.getString("CC"));
//					salesCurrentMonthDTO.setAmount_bill(resultSet.getDouble("TOTAL"));
					
					salesCurrentMonth.put(resultSet.getString("CC"), resultSet.getDouble("TOTAL"));
				}
				
				resultSet.close();
				ps1.close();
			   // conn.close();

			}catch(SQLException e){
				e.printStackTrace();
			}
		
			
			// ### 	current month collection
			
			try{
//				String collectionCurrentMonth_sql= "SELECT CN, CC, SUM (amount) TOTAL "
//												+ "    FROM (  SELECT MCC.CATEGORY_NAME CN, "
//												+ "                   CUSTOMER_CATEGORY CC, "
//												+ "                   sum( nvl(PAYABLE_AMOUNT,0)) amount "
//												+ "              FROM bill_metered bm, mst_CUSTOMER_CATEGORY mcc "
//												+ "             WHERE     BM.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
//												+ "                   AND BM.AREA_ID = '"+area+"' "
//												+ "                   AND bm.status = 2 "
//												+ "                   AND bill_month = "+currentMonth+" "
//												+ "                   AND bill_year = "+currentYear+" "
//												+ "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME "
//												+ "          UNION ALL "
//												+ "            SELECT MCC.CATEGORY_NAME, "
//												+ "                   CUSTOMER_CATEGORY, "
//												+ "                   sum( nvl(ACTUAL_PAYABLE_AMOUNT,0)) amount "
//												+ "              FROM bill_non_metered bnm, mst_CUSTOMER_CATEGORY mcc "
//												+ "             WHERE     bnm.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
//												+ "                   AND BNM.AREA_ID = '"+area+"' "
//												+ "                   AND bnm.status = 2 "
//												+ "                   AND bill_month = "+currentMonth+" "
//												+ "                   AND bill_year = "+currentYear+" "
//												+ "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME) "
//												+ "GROUP BY CC, CN "
//												+ "ORDER BY CC";
//				
				
			
			// 		## changed from above query similar to catetoey type wise collection report
				
			String collectionCurrentMonth_sql = "SELECT CN, CC, SUM (ACTUAL_REVENUE) TOTAL "				
												+ "    FROM (  SELECT SUBSTR (BAL.CUSTOMER_ID, 3, 2) CC, "
												+ "                   CATEGORY_NAME CN, "
												+ "                   SUM (DEBIT) - SUM (SURCHARGE) ACTUAL_REVENUE, "
												+ "                   SUM (SURCHARGE) SURCHARGE, "
												+ "                   0 FEES, "
												+ "                   0 SECURITY "
												+ "              FROM bank_account_ledger BAL, "
												+ "                   MST_BANK_INFO MBI, "
												+ "                   MST_BRANCH_INFO MBRI, "
												+ "                   MST_CUSTOMER_CATEGORY MCC "
												+ "             WHERE     BAL.BRANCH_ID = MBRI.BRANCH_ID "
												+ "                   AND MBI.BANK_ID = MBRI.BANK_ID "
												+ "                   AND MBRI.area_id = MBI.area_id "
												+ "                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID "
												+ "                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+currentMonth+", 2, 0) "
												+ "                   AND TO_CHAR (TRANS_DATE, 'YYYY') = "+currentYear+" "
												+ "                   AND TRANS_TYPE = 1 "
												+ "                   AND MBRI.AREA_ID = '"+area+"' "
												+ "          GROUP BY TRANS_TYPE, SUBSTR (BAL.CUSTOMER_ID, 3, 2), CATEGORY_NAME) "
												+ "GROUP BY CC, CN "
												+ "ORDER BY CC";

				
				PreparedStatement psl = conn.prepareStatement(collectionCurrentMonth_sql);
				
				ResultSet resultSet = psl.executeQuery();
				
				while(resultSet.next()){
//					AdayBokeyaDTO collectionCurrentMonthDTO = new AdayBokeyaDTO();
//					collectionCurrentMonthDTO.setCategory_name(resultSet.getString("CN"));
//					collectionCurrentMonthDTO.setCategory_id(resultSet.getString("CC"));
//					collectionCurrentMonthDTO.setAmount_bill(resultSet.getDouble("TOTAL"));
					
					collectionCurrentMonth.put(resultSet.getString("CC"),resultSet.getDouble("TOTAL"));
				}
				
				resultSet.close();
			    psl.close();
			   // conn.close();

			}catch(SQLException e){
				e.printStackTrace();
			}
			
			
			if(currentMonth==1){
				int previousMonth=12;
			}
			
			int previousMonth=currentMonth-1;
			
			if(previousMonth==1){
				int prvious2ndMonth=12;
			}
			
			int prvious2ndMonth=previousMonth-1;
			
			int previousYear=currentYear-1;
			
			
			// ### 	last 3 months avg sales
			
			
			try{
				String last3MonthsAvgSales ="SELECT CN, CC, round(SUM (total)/3) G_Total "
										+ "    FROM (  SELECT CN, CC, SUM (amount) total "
										+ "              FROM (  SELECT MCC.CATEGORY_NAME CN, "
										+ "                             CUSTOMER_CATEGORY CC, "
										+ "                             sum( nvl(PAYABLE_AMOUNT,0)) amount "
										+ "                        FROM bill_metered bm, mst_CUSTOMER_CATEGORY mcc "
										+ "                       WHERE     BM.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
										+ "                             AND BM.AREA_ID = '"+area+"' "
										+ "                             AND bill_month = "+currentMonth+" "
										+ "                             AND bill_year = "+currentYear+" "
										+ "                    GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME "
										+ "                    UNION ALL "
										+ "                      SELECT MCC.CATEGORY_NAME, "
										+ "                             CUSTOMER_CATEGORY, "
										+ "                             sum( nvl(ACTUAL_PAYABLE_AMOUNT,0)) amount "
										+ "                        FROM bill_non_metered bnm, mst_CUSTOMER_CATEGORY mcc "
										+ "                       WHERE     bnm.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
										+ "                             AND BNM.AREA_ID = '"+area+"' "
										+ "                             AND bill_month = "+currentMonth+" "
										+ "                             AND bill_year = "+currentYear+" "
										+ "                    GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME) "
										+ "          GROUP BY CC, CN "
										+ "          UNION ALL "
										+ "            SELECT CN, CC, SUM (amount) total "
										+ "              FROM (  SELECT MCC.CATEGORY_NAME CN, "
										+ "                             CUSTOMER_CATEGORY CC, "
										+ "                             sum( nvl(PAYABLE_AMOUNT,0)) amount "
										+ "                        FROM bill_metered bm, mst_CUSTOMER_CATEGORY mcc "
										+ "                       WHERE     BM.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
										+ "                             AND BM.AREA_ID = '"+area+"' "
										+ "                             AND bill_month = "+previousMonth+" "
										+ "                             AND bill_year = "+currentYear+" "
										+ "                    GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME "
										+ "                    UNION ALL "
										+ "                      SELECT MCC.CATEGORY_NAME, "
										+ "                             CUSTOMER_CATEGORY, "
										+ "                             sum( nvl(ACTUAL_PAYABLE_AMOUNT,0)) amount "
										+ "                        FROM bill_non_metered bnm, mst_CUSTOMER_CATEGORY mcc "
										+ "                       WHERE     bnm.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
										+ "                             AND BNM.AREA_ID = '"+area+"' "
										+ "                             AND bill_month = "+previousMonth+" "
										+ "                             AND bill_year = "+currentYear+" "
										+ "                    GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME) "
										+ "          GROUP BY CC, CN "
										+ "          UNION ALL "
										+ "            SELECT CN, CC, SUM (amount) total "
										+ "              FROM (  SELECT MCC.CATEGORY_NAME CN, "
										+ "                             CUSTOMER_CATEGORY CC, "
										+ "                             sum( nvl(PAYABLE_AMOUNT,0)) amount "
										+ "                        FROM bill_metered bm, mst_CUSTOMER_CATEGORY mcc "
										+ "                       WHERE     BM.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
										+ "                             AND BM.AREA_ID = '"+area+"' "
										+ "                             AND bill_month = "+prvious2ndMonth+" "
										+ "                             AND bill_year = "+currentYear+" "
										+ "                    GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME "
										+ "                    UNION ALL "
										+ "                      SELECT MCC.CATEGORY_NAME, "
										+ "                             CUSTOMER_CATEGORY, "
										+ "                             sum( nvl(ACTUAL_PAYABLE_AMOUNT,0)) amount "
										+ "                        FROM bill_non_metered bnm, mst_CUSTOMER_CATEGORY mcc "
										+ "                       WHERE     bnm.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
										+ "                             AND BNM.AREA_ID = '"+area+"' "
										+ "                             AND bill_month = "+prvious2ndMonth+" "
										+ "                             AND bill_year = "+currentYear+" "
										+ "                    GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME) "
										+ "          GROUP BY CC, CN) "
										+ "GROUP BY CC, CN "
										+ "ORDER BY CC";
				
				PreparedStatement psl = conn.prepareStatement(last3MonthsAvgSales);
				
				ResultSet resultSet = psl.executeQuery();
				
				while(resultSet.next()){
//					AdayBokeyaDTO last3MonthsSalesDTO = new AdayBokeyaDTO();
//					last3MonthsSalesDTO.setCategory_name(resultSet.getString("CN"));
//					last3MonthsSalesDTO.setCategory_id(resultSet.getString("CC"));
//					last3MonthsSalesDTO.setAmount_bill(resultSet.getDouble("G_Total"));
					
					last3MonthsAvgsales.put(resultSet.getString("CC"),resultSet.getDouble("G_Total"));
				}
				
				resultSet.close();
			    psl.close();
			   // conn.close();

			}catch(SQLException e){
				e.printStackTrace();
			}
			
			
			// ### 	disconnected customers dues bill
			
			try{
				String disconnectedDues_sql = "SELECT CN, CC, SUM (amount) TOTAL "
											+ "    FROM (  SELECT MCC.CATEGORY_NAME CN, "
											+ "                   CUSTOMER_CATEGORY CC, "
											+ "                   sum( nvl(PAYABLE_AMOUNT,0)) amount "
											+ "              FROM bill_metered bm, "
											+ "                   mst_CUSTOMER_CATEGORY mcc, "
											+ "                   customer_connection cc "
											+ "             WHERE     BM.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
											+ "                   AND BM.CUSTOMER_ID = CC.CUSTOMER_ID "
											+ "                   AND BM.AREA_ID = '"+area+"' "
											+ "                   AND bm.status = 1 "
											+ "                   AND CC.STATUS = 0 "
											+ "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME "
											+ "          UNION ALL "
											+ "            SELECT MCC.CATEGORY_NAME, "
											+ "                   CUSTOMER_CATEGORY, "
											+ "                   sum( nvl(ACTUAL_PAYABLE_AMOUNT,0)) amount "
											+ "              FROM bill_non_metered bnm, "
											+ "                   mst_CUSTOMER_CATEGORY mcc, "
											+ "                   customer_connection cc "
											+ "             WHERE     bnm.CUSTOMER_CATEGORY = MCC.CATEGORY_ID "
											+ "                   AND bnm.CUSTOMER_ID = CC.CUSTOMER_ID "
											+ "                   AND BNM.AREA_ID = '"+area+"' "
											+ "                   AND bnm.status = 1 "
											+ "                   AND CC.STATUS = 0 "
											+ "          GROUP BY CUSTOMER_CATEGORY, MCC.CATEGORY_NAME) "
											+ "GROUP BY CC, CN "
											+ "ORDER BY CC";
				
				PreparedStatement psl = conn.prepareStatement(disconnectedDues_sql);
				ResultSet resultSet = psl.executeQuery();
				
				while(resultSet.next()){
					disconnectedDues.put(resultSet.getString("CC"), resultSet.getDouble("TOTAL"));
				}
				
				resultSet.close();
				psl.close();
			//	conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{try{ConnectionManager.closeConnection(conn);} catch (Exception e)
			{e.printStackTrace();}conn = null;}
			
			
		double totalTillPreviousMonthDues=0.0;
		double totalSalesCurrentMonth=0.0;
		double totalCollectionCurrentMonth=0.0;
		double totalTillCurrentMonthDues=0.0;
		
		double totalLast3MonthsAvgsales=0.0;
		double totalEquiAvgDuesMonth = 0.0;
		double totalDisConDuesAmount = 0.0;
		double totalDisConEquiAvgDuesMonth = 0.0;
		
		int listSize=categoryList.size();
		
		
		for(int i=0;i<listSize;i++)
		{
//			LinkedHashMap<String, Double> tillPreviousMonthDues = new LinkedHashMap<String, Double>();	// query 2
//			LinkedHashMap<String,Double> salesCurrentMonth = new LinkedHashMap<String,Double>();  		// query 3
//			LinkedHashMap<String,Double> collectionCurrentMonth = new LinkedHashMap<String,Double>();	// query 4
//			LinkedHashMap<String,Double> tillCurrentMonthDues = new LinkedHashMap<String,Double>();		// 2+3-4
//			
//			LinkedHashMap<String,Double> last3MonthsAvgsales = new LinkedHashMap<String,Double>();		// query 5
//			
//			LinkedHashMap<String,Double> disconnectedDues = new LinkedHashMap<String,Double>();			// query 6
			
			
			pcell = new PdfPCell(new Paragraph(String.valueOf(i+1),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(categoryList.get(i).getCategory_name(),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
					
			totalTillPreviousMonthDues=totalTillPreviousMonthDues+(tillPreviousMonthDues.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : tillPreviousMonthDues.get(categoryList.get(i).getCategory_id()));
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(tillPreviousMonthDues.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : tillPreviousMonthDues.get(categoryList.get(i).getCategory_id())),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
					
			totalSalesCurrentMonth=totalSalesCurrentMonth+ (salesCurrentMonth.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : salesCurrentMonth.get(categoryList.get(i).getCategory_id()));
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(salesCurrentMonth.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : salesCurrentMonth.get(categoryList.get(i).getCategory_id())),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			totalCollectionCurrentMonth=totalCollectionCurrentMonth + (collectionCurrentMonth.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : collectionCurrentMonth.get(categoryList.get(i).getCategory_id()));
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(collectionCurrentMonth.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : collectionCurrentMonth.get(categoryList.get(i).getCategory_id())),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			
			
			double totalDuesTillCurrentMonth = (double)(tillPreviousMonthDues.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : tillPreviousMonthDues.get(categoryList.get(i).getCategory_id())) + (salesCurrentMonth.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : salesCurrentMonth.get(categoryList.get(i).getCategory_id())) - (collectionCurrentMonth.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : collectionCurrentMonth.get(categoryList.get(i).getCategory_id()));
			totalTillCurrentMonthDues = totalTillCurrentMonthDues + totalDuesTillCurrentMonth;
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(totalDuesTillCurrentMonth),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			double avgSalesMonths = (double) (last3MonthsAvgsales.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : last3MonthsAvgsales.get(categoryList.get(i).getCategory_id()));
			
			totalLast3MonthsAvgsales=totalLast3MonthsAvgsales+avgSalesMonths;
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(avgSalesMonths),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			
			
			double equivalentAvgDuesMonths = totalDuesTillCurrentMonth/avgSalesMonths;
			
			if(equivalentAvgDuesMonths > 0){
				//System.out.println("ok");
			}else {
				equivalentAvgDuesMonths = 0;
			}
			
			totalEquiAvgDuesMonth = (totalEquiAvgDuesMonth + equivalentAvgDuesMonths);
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(equivalentAvgDuesMonths > 0 ? equivalentAvgDuesMonths : 0.00),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			double disconDues = (double) (disconnectedDues.get(categoryList.get(i).getCategory_id()) == null ? 0.00 : disconnectedDues.get(categoryList.get(i).getCategory_id()));
			
			if(disconDues > 0){
				//System.out.println("ok");
			}else {
				disconDues = 0;
			}
			
			totalDisConDuesAmount = totalDisConDuesAmount + disconDues;
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(disconDues),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			double disConEquivalentAvgDuesMonths = (totalDuesTillCurrentMonth + disconDues)/avgSalesMonths;
			
			if(disConEquivalentAvgDuesMonths>0){
				//System.out.println("ok");
			}else{
				disConEquivalentAvgDuesMonths=0;
			}
			
			totalDisConEquiAvgDuesMonth= (totalDisConEquiAvgDuesMonth + disConEquivalentAvgDuesMonths);
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(disConEquivalentAvgDuesMonths > 0 ? disConEquivalentAvgDuesMonths : 0.00),ReportUtil.f10));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(2);
			pdfPTable.addCell(pcell);
			
			
//			 totalGasBill+=transactionList.get(i).getGas_bill();
//			 totalSurcharge+=transactionList.get(i).getSurcharge();
//			 totalFees+=transactionList.get(i).getFees();
//			 totalSecurityDeposit+=transactionList.get(i).getSecurity();
//			 total+=transactionList.get(i).getGas_bill()+transactionList.get(i).getSurcharge()+transactionList.get(i).getFees()+transactionList.get(i).getSecurity();
					
		}
		
		pcell = new PdfPCell(new Paragraph("Grand Total",ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(2);
		pdfPTable.addCell(pcell);
		
//		NumberFormat df = NumberFormat.getCurrencyInstance();
//		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//		//dfs.setCurrencySymbol("$");
//		//dfs.set
//		dfs.setGroupingSeparator('.');
//		dfs.setMonetaryDecimalSeparator('.');
//		((DecimalFormat) df).setDecimalFormatSymbols(dfs);
//		System.out.println(df.format(3333454));
//		System.out.println(df.format(totalTillPreviousMonthDues));
		
//		System.out.println(totalTillPreviousMonthDues);
		//System.out.println(taka_format.format(totalTillPreviousMonthDues));
		//System.out.println(taka_format_BD.format(totalTillPreviousMonthDues));
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalTillPreviousMonthDues),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalSalesCurrentMonth),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalCollectionCurrentMonth),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalTillCurrentMonthDues),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalLast3MonthsAvgsales/listSize),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalEquiAvgDuesMonth/listSize),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalDisConDuesAmount),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalDisConEquiAvgDuesMonth/listSize),ReportUtil.f10B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		document.add(pdfPTable);

		
	}
	
	
	/*	All Category type wise collection report
	 * 
	 * 	sujon
	 * */
	
	private  void category_type_wise(Document document) throws DocumentException
	{
		
		PdfPTable headlineTable = new PdfPTable(3);
		headlineTable.setSpacingBefore(5);
		headlineTable.setSpacingAfter(10);
		headlineTable.setWidths(new float[] {
				40,70,40
			});
		PdfPCell pcell = null;
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Monthly Collection Customer Type Wise", ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		document.add(headlineTable);
		
	

		
		
		PdfPTable collectionMonthCustomerType = new PdfPTable(1);
        collectionMonthCustomerType.setWidths(new float[] {180});
        collectionMonthCustomerType.setSpacingBefore(5);
        collectionMonthCustomerType.setSpacingAfter(5);
        
        
        pcell = new PdfPCell(new Paragraph("Collection Month: "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year,ReportUtil.f11B));
        pcell.setBorder(0);
        pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        collectionMonthCustomerType.addCell(pcell);

        document.add(collectionMonthCustomerType);
	
        
		
		PdfPTable pdfPTable = new PdfPTable(9);
		//datatable1.setWidthPercentage(100);
		pdfPTable.setWidths(new float[] {10,30,25,20,20,20,20,15,20});
		pdfPTable.setSpacingBefore(5);
		

		
		
		pcell=new PdfPCell(new Paragraph("Sl No",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer Category ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Monthly Gas Bill ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Tax Amount ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("Total Gas bill",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Surcharge ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Security ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);	
		
		pcell=new PdfPCell(new Paragraph("Fees ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);	
		
		pcell=new PdfPCell(new Paragraph("Total Amount ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);	
		
		
	/*	pcell=new PdfPCell(new Paragraph("Demand Charge ",ReportUtil.f11B));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);*/
		
		pcell=new PdfPCell(new Paragraph("1",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("2",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("3",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("4",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("(3+4)=5",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("6",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("7",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("8",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("(5+6+7+8)=9",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);
		
	/*	pcell=new PdfPCell(new Paragraph("10",ReportUtil.f11B));
		pcell.setRowspan(1);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfPTable.addCell(pcell);*/
		

		collectionInfoList=getCollectionInfoList();
		int listSize=collectionInfoList.size();
		

		double totalGasBill=0.0;
		double totalSurcharge=0.0;
		double totalFees=0.0;
		double totalSecurityDeposit=0.0;
		double total=0.0;
		double totaltax=0.0;
		double toatal_amount=0.0;
		double toatal_amount_whTax=0.0;
		double total_demand_charge=0;
		double total_gas_bill_coll=0;
		
		for(int i=0;i<listSize;i++)
		{
			pcell = new PdfPCell(new Paragraph(String.valueOf(i+1),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(collectionInfoList.get(i).getCategory_name(),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);
					
			pcell = new PdfPCell(new Paragraph(taka_format.format(collectionInfoList.get(i).getGas_bill()),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(collectionInfoList.get(i).getTax_amount()),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);
			
			double totalgasbillwithtax= collectionInfoList.get(i).getTax_amount()+collectionInfoList.get(i).getGas_bill();
			pcell = new PdfPCell(new Paragraph(taka_format.format(totalgasbillwithtax),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);
			
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(collectionInfoList.get(i).getColl_surcharge()),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(collectionInfoList.get(i).getSecurity()),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);				
			
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(collectionInfoList.get(i).getFees()),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);			
		
			
			pcell = new PdfPCell(new Paragraph(taka_format.format(totalgasbillwithtax+collectionInfoList.get(i).getColl_surcharge()+collectionInfoList.get(i).getFees()+collectionInfoList.get(i).getSecurity()),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);
			
		/*	pcell = new PdfPCell(new Paragraph(taka_format.format(collectionInfoList.get(i).getDemand_charge()),ReportUtil.f11));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);*/
			
			 totalGasBill+=collectionInfoList.get(i).getGas_bill();
			 totalSurcharge+=collectionInfoList.get(i).getColl_surcharge();
			 totalFees+=collectionInfoList.get(i).getFees();
			 totalSecurityDeposit+=collectionInfoList.get(i).getSecurity();
			 totaltax+=collectionInfoList.get(i).getTax_amount();
			 total_gas_bill_coll+=collectionInfoList.get(i).getTax_amount()+collectionInfoList.get(i).getGas_bill();
			 total_demand_charge+= collectionInfoList.get(i).getDemand_charge();
			 total+=collectionInfoList.get(i).getGas_bill()+collectionInfoList.get(i).getTax_amount()+collectionInfoList.get(i).getColl_surcharge()+collectionInfoList.get(i).getFees()+collectionInfoList.get(i).getSecurity();
				
			 toatal_amount = total;//+totaltax;+total_demand_charge;
			 toatal_amount_whTax=total-totaltax;
		}
		
		pcell = new PdfPCell(new Paragraph("Grand Total",ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(2);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalGasBill),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totaltax),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(total_gas_bill_coll),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalSurcharge),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalSecurityDeposit),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);		
	
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totalFees),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);		
	
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(toatal_amount),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);
		
/*		pcell = new PdfPCell(new Paragraph(taka_format.format(total_demand_charge),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setColspan(1);
		pdfPTable.addCell(pcell);*/
		
		
		
		pcell = new PdfPCell(new Paragraph("Bank Collection",ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(7);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(toatal_amount_whTax),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(2);
		pdfPTable.addCell(pcell);
		
		
		pcell = new PdfPCell(new Paragraph("Tax Collection",ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(7);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(totaltax),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(3);
		pdfPTable.addCell(pcell);	
		
		
		
		pcell = new PdfPCell(new Paragraph("Total Collection =",ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(7);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(taka_format.format(toatal_amount),ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(2);
		pdfPTable.addCell(pcell);	
		
		
		pcell = new PdfPCell(new Paragraph("  "));
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setColspan(9);
		pcell.setBorder(0);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("N.B:Demand charge is included with Monthly Gas Bill",ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setColspan(9);
		pcell.setPadding(2);
		pcell.setBorder(0);
		pdfPTable.addCell(pcell);
		
		document.add(pdfPTable);

		
	}
	
	/*	End All Category type wise collection report
	 * 
	 * 	sujon
	 * */
	
	
	private  void generateCollectionDataForReport()
	{
		OracleCallableStatement stmt=null;
		Connection conn = ConnectionManager.getConnection();
		try {
			int month=Integer.parseInt(bill_month);
			String billMonth="";
			if(month<10){
				billMonth="0"+Integer.toString(month);
			}else{
				billMonth=Integer.toString(month);
			}
			
			String monthyear=bill_year+billMonth;
			int yearmon=Integer.parseInt(monthyear);
			
			if(yearmon>201604){
			
			//System.out.println("Procedure Save_Multi_Month_Collection Begins");
			stmt = (OracleCallableStatement) conn.prepareCall("{ call Collection_Report_Helper(?,?,?) }");
			 
			
			stmt.setString(1, area);
			stmt.setInt(2,Integer.valueOf(bill_month));
			stmt.setInt(3, Integer.valueOf(bill_year));
			stmt.executeUpdate();
		
	
			}
        	
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	 
	
	private  ArrayList<CollectionReportDTO> getCollectionInfoList()
	{
	ArrayList<CollectionReportDTO> collectionInfoList=new ArrayList<CollectionReportDTO>();
	Connection conn = ConnectionManager.getConnection();	
	//String area=loggedInUser.getArea_id();
	String westAreaCode = new String("02");		// for power collection
		try {
			String wClause1="";
			String w2Clause2="";
			String w2Clause3="";
			
			if((report_for.equals("category_type_wise_f_all_area"))  ) {
				
				wClause1= " BAL.BRANCH_ID = MBRI.BRANCH_ID "
						+ "                   AND MBI.BANK_ID = MBRI.BANK_ID "
						+ "                   AND MBRI.area_id = MBI.area_id "
						+ "                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID "
						+ "                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) "
						+ "                   AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+bill_year
						+ "                   AND TRANS_TYPE = 1 "
						+ "                   AND MBRI.AREA_ID in (SELECT area  FROM USER_AREAS  WHERE USERID = '"+loggedInUser.getUserId()+"'UNION SELECT area  FROM MST_USER  WHERE USERID = '"+loggedInUser.getUserId()+"')"
						+ "                   AND BAL.CUSTOMER_ID NOT LIKE '0213%' " ;
				
				
				
				w2Clause2= " BAL.BRANCH_ID = MBRI.BRANCH_ID "
						+ "                   AND MBI.BANK_ID = MBRI.BANK_ID "
						+ "                   AND MBRI.area_id = MBI.area_id "
						+ "                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID "
						+ "                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) "
						+ "                   AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+bill_year
						+ "                   AND TRANS_TYPE = 7 "
						+ "                   AND MBRI.AREA_ID in ((SELECT area  FROM USER_AREAS  WHERE USERID = '"+loggedInUser.getUserId()+"'UNION SELECT area  FROM MST_USER  WHERE USERID = '"+loggedInUser.getUserId()+"'))"
						+ "                   AND BAL.CUSTOMER_ID NOT LIKE '0213%' " ;
				
				
				
				w2Clause3=  "  BAL.BRANCH_ID = MBRI.BRANCH_ID "
						+ "                   AND MBI.BANK_ID = MBRI.BANK_ID "
						+ "                   AND MBRI.area_id = MBI.area_id "
						+ "                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID "
						+ "                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) "
						+ "                   AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+bill_year
						+ "                   AND TRANS_TYPE = 0 "
						+ "                   AND REF_ID NOT IN (SELECT DEPOSIT_ID "
						+ "                                        FROM MST_DEPOSIT "
						+ "                                       WHERE DEPOSIT_TYPE = 1) "
						+ "                   AND MBRI.AREA_ID in ((SELECT area  FROM USER_AREAS  WHERE USERID = '"+loggedInUser.getUserId()+"'UNION SELECT area  FROM MST_USER  WHERE USERID = '"+loggedInUser.getUserId()+"'))"
						+ "                   AND BAL.CUSTOMER_ID NOT LIKE '0213%' ";
				
				
				
			}else{
				wClause1= " BAL.BRANCH_ID = MBRI.BRANCH_ID "
						+ "                   AND MBI.BANK_ID = MBRI.BANK_ID "
						+ "                   AND MBRI.area_id = MBI.area_id "
						+ "                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID "
						+ "                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) "
						+ "                   AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+bill_year
						+ "                   AND TRANS_TYPE = 1 "
						+ "                   AND MBRI.AREA_ID = '"+area+"' "
						+ "                   AND BAL.CUSTOMER_ID NOT LIKE '0213%' " ;
				
				
				
				w2Clause2= " BAL.BRANCH_ID = MBRI.BRANCH_ID "
						+ "                   AND MBI.BANK_ID = MBRI.BANK_ID "
						+ "                   AND MBRI.area_id = MBI.area_id "
						+ "                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID "
						+ "                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) "
						+ "                   AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+bill_year
						+ "                   AND TRANS_TYPE = 7 "
						+ "                   AND MBRI.AREA_ID = '"+area+"' "
						+ "                   AND BAL.CUSTOMER_ID NOT LIKE '0213%' " ;
				
				
				
				w2Clause3=  "  BAL.BRANCH_ID = MBRI.BRANCH_ID "
						+ "                   AND MBI.BANK_ID = MBRI.BANK_ID "
						+ "                   AND MBRI.area_id = MBI.area_id "
						+ "                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID "
						+ "                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) "
						+ "                   AND TO_CHAR (TRANS_DATE, 'YYYY') =  "+bill_year
						+ "                   AND TRANS_TYPE = 0 "
						+ "                   AND REF_ID NOT IN (SELECT DEPOSIT_ID "
						+ "                                        FROM MST_DEPOSIT "
						+ "                                       WHERE DEPOSIT_TYPE = 1) "
						+ "                   AND MBRI.AREA_ID = '"+area+"' "
						+ "                   AND BAL.CUSTOMER_ID NOT LIKE '0213%' ";
				
			}
			
			if (report_for.equals("category_type_wise_f_all_area") || report_for.equals("category_type_wise") ) {
				
			
				String defaulterSql= " SELECT CATEGORY, "
						+ "         CATEGORY_NAME, "
						+ "         SUM (ACTUAL_REVENUE) ACTUAL_REVENUE, "
						+ "         SUM (SURCHARGE) SURCHARGE, "
						+ "         SUM (FEES) FEESS, "
						+ "         SUM (SECURITY) SECURITY,sum(tax_amount) tax_amount,  sum(demand_charge) demand_charge "
						+ "          "
						+ "    FROM (  SELECT SUBSTR (BAL.CUSTOMER_ID, 3, 2) CATEGORY, "
						+ "                   CATEGORY_NAME, "
						+ "                   SUM (DEBIT) - SUM (SURCHARGE) ACTUAL_REVENUE, "
						+ "                   SUM (SURCHARGE) SURCHARGE, "
						+ "                   0 FEES, "
						+ "                   0 SECURITY, "
						+ "                   sum(getTaxAmount(BAL.CUSTOMER_ID,bal.TRANS_DATE)) tax_amount, sum(getDemandChargeColl (REF_ID)) demand_charge "
						+ "              FROM bank_account_ledger BAL, "
						+ "                   MST_BANK_INFO MBI, "
						+ "                   MST_BRANCH_INFO MBRI, "
						+ "                   MST_CUSTOMER_CATEGORY MCC "
						+ "             WHERE " +wClause1 
						+ "          GROUP BY TRANS_TYPE, SUBSTR (BAL.CUSTOMER_ID, 3, 2), CATEGORY_NAME "
						+ "          UNION ALL "
						+ "            SELECT SUBSTR (BAL.CUSTOMER_ID, 3, 2) CATEGORY, "
						+ "                   CATEGORY_NAME, "
						+ "                   0 ACTUAL_REVENUE, "
						+ "                   0 SURCHARGE, "
						+ "                   SUM (debit) AS FEES, "
						+ "                   0 SECURITY, "
						+ "                   0 tax_amount , 0 demand_charge  "
						+ "              FROM bank_account_ledger BAL, "
						+ "                   MST_BANK_INFO MBI, "
						+ "                   MST_BRANCH_INFO MBRI, "
						+ "                   MST_CUSTOMER_CATEGORY MCC "
						+ "             WHERE " +w2Clause2
						+ "          GROUP BY TRANS_TYPE, SUBSTR (BAL.CUSTOMER_ID, 3, 2), CATEGORY_NAME "
						+ "          UNION ALL "
						+ "            SELECT SUBSTR (BAL.CUSTOMER_ID, 3, 2) CATEGORY, "
						+ "                   CATEGORY_NAME, "
						+ "                   0 ACTUAL_REVENUE, "
						+ "                   0 SURCHARGE, "
						+ "                   0 FEES, "
						+ "                   SUM (debit) - SUM (credit) AS SECURITY, "
						+ "                   0 tax_amount, 0 demand_charge "
						+ "              FROM bank_account_ledger BAL, "
						+ "                   MST_BANK_INFO MBI, "
						+ "                   MST_BRANCH_INFO MBRI, "
						+ "                   MST_CUSTOMER_CATEGORY MCC "
						+ "             WHERE "+w2Clause3
						+ "          GROUP BY TRANS_TYPE, SUBSTR (BAL.CUSTOMER_ID, 3, 2), CATEGORY_NAME) "
						+ " GROUP BY CATEGORY, CATEGORY_NAME order by CATEGORY " ;
				
				
				PreparedStatement ps1=conn.prepareStatement(defaulterSql);
//				 ps1.setString(1, bill_month);
//				 ps1.setString(2, bill_year);
//				 ps1.setString(3, area);
//				 ps1.setString(4, bill_month);
//				 ps1.setString(5, bill_year);
//				 ps1.setString(6, area);
//				 ps1.setString(7, bill_month);
//				 ps1.setString(8, bill_year);
//				 ps1.setString(9, area);
				
	        	ResultSet resultSet=ps1.executeQuery();
	        	
	        	while(resultSet.next())
	        	{
	        		CollectionReportDTO collectionDto=new CollectionReportDTO();
	        		collectionDto.setCategory_name(resultSet.getString("CATEGORY_NAME"));
	        		collectionDto.setGas_bill(resultSet.getDouble("ACTUAL_REVENUE"));
	        		collectionDto.setColl_surcharge(resultSet.getDouble("SURCHARGE"));
	        		collectionDto.setFees(resultSet.getDouble("FEESS"));
	        		collectionDto.setSecurity(resultSet.getDouble("SECURITY"));
	        		collectionDto.setTax_amount(resultSet.getDouble("TAX_AMOUNT"));
	        		collectionDto.setDemand_charge(resultSet.getDouble("demand_charge"));	        		
	        		
	        		collectionInfoList.add(collectionDto);
	        		
	        	}
			}
			
			else
			{
				String defaulterSql="select AREA_NAME,cr.* ,CATEGORY_TYPE from COLLECTION_RRPORT cr,MST_CUSTOMER_CATEGORY mcc,MST_AREA ma " +
						"where CR.CATEGORY_ID=mcc.CATEGORY_ID " +
						"and  cr.area_id=MA.AREA_ID " +
						"order by CR.AREA_ID,CATEGORY_TYPE,CR.CATEGORY_ID " ;
				//	     bill_month and Bill_year conside korte hobe
				
				PreparedStatement ps1=conn.prepareStatement(defaulterSql);
			
	        	
	        	ResultSet resultSet=ps1.executeQuery();
	        	
	        	
	        	while(resultSet.next())
	        	{
	        		CollectionReportDTO collectionDto=new CollectionReportDTO();
	        		collectionDto.setArea_id(resultSet.getString("AREA_ID"));
	        		collectionDto.setArea_name(resultSet.getString("AREA_NAME"));
	        		collectionDto.setCategory_id(resultSet.getString("CATEGORY_ID"));
	        		//loadIncraseDTO.setName_address(resultSet.getString("NAME_ADDRESS"));
	        		collectionDto.setCategory_name(resultSet.getString("CATEGORY_NAME"));
	        		collectionDto.setCategory_type(resultSet.getString("CATEGORY_TYPE"));
	        		collectionDto.setOpening_balance(resultSet.getDouble("BALANCE_PREV_MONTH"));
	        		collectionDto.setAdjustment(resultSet.getDouble("ADJUSTMENT"));
	        		collectionDto.setCurr_sales(resultSet.getDouble("SALES_CURR_MONTH"));
	        		collectionDto.setCurr_surcharge(resultSet.getDouble("SURCHARGE"));
	        		collectionDto.setAccount_receivable(resultSet.getDouble("AR_CURR_MONTH"));
	        		collectionDto.setGas_bill(resultSet.getDouble("GAS_BILL_COLL"));
	        		collectionDto.setMeter_rent(resultSet.getDouble("METER_RENT_COLL"));
	        		collectionDto.setColl_surcharge(resultSet.getDouble("SURCHARGE_COLL"));
	        		collectionDto.setIncome_tax(resultSet.getDouble("TAX_COLL"));
	        		collectionDto.setVat_rebate(resultSet.getDouble("VAT_REBATE"));
	        		collectionDto.setHhv_nhv(resultSet.getDouble("NHV_HHV_COLL"));
	        		collectionDto.setTotal_collection(resultSet.getDouble("TOTAL_COLL"));
	        		collectionDto.setPrevious_due(resultSet.getDouble("TOTAL_DUE"));
	        		collectionDto.setAvg_monthly_sales(resultSet.getDouble("AVG_MONTHLY_SALES"));
	        		collectionDto.setAvg_due(resultSet.getDouble("AVERAGE_DUE"));
	        		
	        		
	        		collectionInfoList.add(collectionDto);
	        		
	        	}
			}
			
			
			// power customer collection
			
		if	(report_for.equals("category_type_wise")){	
			
			if(area.equals(westAreaCode)){
				
				String powerCollectionSql="SELECT CATEGORY, " +
											"         CATEGORY_NAME, " +
											"         SUM (ACTUAL_REVENUE) ACTUAL_REVENUE, " +
											"         SUM (SURCHARGE) SURCHARGE, " +
											"         SUM (FEES) FEESS, " +
											"         SUM (SECURITY) SECURITY , sum(demand_charge) demand_charge " +
											"    FROM (  SELECT SUBSTR (BAL.CUSTOMER_ID, 3, 2) CATEGORY, " +
											"                   CATEGORY_NAME, " +
											"                   SUM (DEBIT) - SUM (SURCHARGE) ACTUAL_REVENUE, " +
											"                   SUM (SURCHARGE) SURCHARGE, " +
											"                   0 FEES, " +
											"                   0 SECURITY, SUM (getDemandChargeColl (REF_ID)) demand_charge " +
											"              FROM bank_account_ledger BAL, " +
											"                   MST_BANK_INFO MBI, " +
											"                   MST_BRANCH_INFO MBRI, " +
											"                   MST_CUSTOMER_CATEGORY MCC " +
											"             WHERE     BAL.BRANCH_ID = MBRI.BRANCH_ID " +
											"                   AND MBI.BANK_ID = MBRI.BANK_ID " +
											"                   AND MBRI.area_id = MBI.area_id " +
											"                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID " +
											"                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) " +
											"                   AND TO_CHAR (TRANS_DATE, 'YYYY') = "+bill_year+" " +
											"                   AND TRANS_TYPE = 1 " +
											"                   and BAL.CUSTOMER_ID like '0213%' " +
											"          GROUP BY TRANS_TYPE, SUBSTR (BAL.CUSTOMER_ID, 3, 2), CATEGORY_NAME " +
											"          UNION ALL " +
											"            SELECT SUBSTR (BAL.CUSTOMER_ID, 3, 2) CATEGORY, " +
											"                   CATEGORY_NAME, " +
											"                   0 ACTUAL_REVENUE, " +
											"                   0 SURCHARGE, " +
											"                   SUM (debit) AS FEES, " +
											"                   0 SECURITY, 0 demand_charge " +
											"              FROM bank_account_ledger BAL, " +
											"                   MST_BANK_INFO MBI, " +
											"                   MST_BRANCH_INFO MBRI, " +
											"                   MST_CUSTOMER_CATEGORY MCC " +
											"             WHERE     BAL.BRANCH_ID = MBRI.BRANCH_ID " +
											"                   AND MBI.BANK_ID = MBRI.BANK_ID " +
											"                   AND MBRI.area_id = MBI.area_id " +
											"                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID " +
											"                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) " +
											"                   AND TO_CHAR (TRANS_DATE, 'YYYY') = "+bill_year+" " +
											"                   AND TRANS_TYPE = 7 " +
											"                   and BAL.CUSTOMER_ID like '0213%' " +
											"          GROUP BY TRANS_TYPE, SUBSTR (BAL.CUSTOMER_ID, 3, 2), CATEGORY_NAME " +
											"          UNION ALL " +
											"            SELECT SUBSTR (BAL.CUSTOMER_ID, 3, 2) CATEGORY, " +
											"                   CATEGORY_NAME, " +
											"                   0 ACTUAL_REVENUE, " +
											"                   0 SURCHARGE, " +
											"                   0 FEES, " +
											"                   SUM (debit) - SUM (credit) AS SECURITY, 0 demand_charge " +
											"              FROM bank_account_ledger BAL, " +
											"                   MST_BANK_INFO MBI, " +
											"                   MST_BRANCH_INFO MBRI, " +
											"                   MST_CUSTOMER_CATEGORY MCC " +
											"             WHERE     BAL.BRANCH_ID = MBRI.BRANCH_ID " +
											"                   AND MBI.BANK_ID = MBRI.BANK_ID " +
											"                   AND MBRI.area_id = MBI.area_id " +
											"                   AND SUBSTR (BAL.CUSTOMER_ID, 3, 2) = MCC.CATEGORY_ID " +
											"                   AND TO_CHAR (TRANS_DATE, 'MM') = LPAD ("+bill_month+", 2, 0) " +
											"                   AND TO_CHAR (TRANS_DATE, 'YYYY') = "+bill_year+" " +
											"                   AND TRANS_TYPE = 0 " +
											"                   AND REF_ID NOT IN (SELECT DEPOSIT_ID " +
											"                                        FROM MST_DEPOSIT " +
											"                                       WHERE DEPOSIT_TYPE = 1) " +
											"                   and BAL.CUSTOMER_ID like '0213%' " +
											"          GROUP BY TRANS_TYPE, SUBSTR (BAL.CUSTOMER_ID, 3, 2), CATEGORY_NAME) " +
											"GROUP BY CATEGORY, CATEGORY_NAME ";
				
				
				PreparedStatement ps1=conn.prepareStatement(powerCollectionSql);
				
	        	ResultSet resultSet=ps1.executeQuery();
	        	
	        	while(resultSet.next())
	        	{
	        		CollectionReportDTO collectionDto=new CollectionReportDTO();
	        		collectionDto.setCategory_name(resultSet.getString("CATEGORY_NAME"));
	        		collectionDto.setGas_bill(resultSet.getDouble("ACTUAL_REVENUE"));
	        		collectionDto.setColl_surcharge(resultSet.getDouble("SURCHARGE"));
	        		collectionDto.setFees(resultSet.getDouble("FEESS"));
	        		collectionDto.setSecurity(resultSet.getDouble("SECURITY"));
	        		collectionDto.setTax_amount(0.0d);
	        		collectionDto.setDemand_charge(resultSet.getDouble("demand_charge"));
	        		
	        		collectionInfoList.add(collectionDto);
	        		
	        	}
			}
		}	
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{try{ConnectionManager.closeConnection(conn);} catch (Exception e)
		{e.printStackTrace();}conn = null;}
		
		return collectionInfoList;
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


				public String getReport_for() {
					return report_for;
				}


				public void setReport_for(String report_for) {
					this.report_for = report_for;
				}


				public String getCustomer_category() {
					return customer_category;
				}


				public void setCustomer_category(String customer_category) {
					this.customer_category = customer_category;
				}


				public String getCategory_name() {
					return category_name;
				}


				public void setCategory_name(String category_name) {
					this.category_name = category_name;
				}
		
				
				public ServletContext getServlet() {
					return servlet;
				}

				public void setServletContext(ServletContext servlet) {
					this.servlet = servlet;
				}
				
				
	}
