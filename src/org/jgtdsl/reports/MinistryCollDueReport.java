package org.jgtdsl.reports;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Yellow;
import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.AccountDTO;
import org.jgtdsl.dto.AddressDTO;
import org.jgtdsl.dto.BankDTO;
import org.jgtdsl.dto.BranchDTO;
import org.jgtdsl.dto.CollectionDTO;
import org.jgtdsl.dto.CustomerCategoryDTO;
import org.jgtdsl.dto.CustomerConnectionDTO;
import org.jgtdsl.dto.CustomerDTO;
import org.jgtdsl.dto.CustomerPersonalDTO;
import org.jgtdsl.dto.GasPurchaseDTO;
import org.jgtdsl.dto.MCollDueDTO;
import org.jgtdsl.dto.MeterReadingDTO;
import org.jgtdsl.dto.NonMeterReportDTO;
import org.jgtdsl.dto.TariffDTO;
import org.jgtdsl.dto.TransactionDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.reports.ReportFormat;
import org.jgtdsl.reports.ReportUtil;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




public class MinistryCollDueReport extends BaseAction {
	private static final long serialVersionUID = 1L;
	public  ServletContext servlet;
	Connection conn = ConnectionManager.getConnection();
	
		private   String area;
		private  String bill_month;
	    private  String bill_year;
	    private String report_for;
	    
	    static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
		static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
		static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
		static Font font2 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
		//static DecimalFormat  taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
		 DecimalFormat  taka_format = new DecimalFormat("###########.00");
		 
		static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
		UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");	
		
		
	public String execute() throws Exception
	{
		
		
		
		
		String fileName="Ministry_Collection_Due.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4.rotate());
		document.setMargins(20,20,40,80);
		PdfPCell pcell=null;
		
		
		try{
			
			ReportFormat eEvent = new ReportFormat(getServletContext());
			
			PdfWriter.getInstance(document, baos).setPageEvent(eEvent);			
			document.open();
			
			PdfPTable headerTable = new PdfPTable(3);
		   
				
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
            //img.setAbsolutePosition(123f, 760f);		
            	img.setAbsolutePosition(240f, 525f);		// rotate
            
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

			Chunk chunk1 = new Chunk("Gas Vaban, Mendibag, Sylhet-3100",font2);
			//Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(loggedInUser.getArea_id())-1]),font3);
			Paragraph p = new Paragraph(); 
			p.add(chunk1);			
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
			
			
			PdfPTable dataTable = new PdfPTable(3);
			//dataTable.setWidthPercentage(70);
			dataTable.setWidths(new float[]{(float) 0.15, (float) 1, (float) 0.5});
			
			
			pcell = new PdfPCell(new Paragraph("Ministry Collection-Due Report: "+String.valueOf(Area.values()[Integer.valueOf(area)-1]),font3));
			pcell.setColspan(3);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			
			pcell= new PdfPCell(new Paragraph(" "));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(3);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			
			pcell= new PdfPCell(new Paragraph("Month: "+Month.values()[Integer.valueOf(bill_month)-1]+", "+bill_year,font3));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setColspan(3);
			pcell.setPadding(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
	
			document.add(dataTable);
			generatePdfMinistryCollectionDue(document);
			generatePdfMinistryCollectionDueReal(document);
			document.close();		
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(),fileName);
			document=null;
			
		    
		}catch(Exception e){e.printStackTrace();}
		
		return null;		
	}
	
	private void generatePdfMinistryCollectionDue(Document document) throws DocumentException{
		PdfPCell pcell=null;
		PdfPTable datatable1 = new PdfPTable(9);
		int total=0;
		
		datatable1.setWidthPercentage(99);
		datatable1.setWidths(new float[] {10,30,50,30,30,30,30,30,30});
		
		pcell=new PdfPCell(new Paragraph(" ",font3));		
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setColspan(9);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Sl",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer ID",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer Name",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		String prev_month=bill_month;
		int prev_year= Integer.parseInt(bill_year);
		
		if (bill_month.equals("1")){
			prev_month="13";
			prev_year= Integer.parseInt(bill_year)-1;
		}
		
		/////////////getting last date of month by bindu
		
		try{
			
			String date_prev = String.valueOf((Integer.parseInt(prev_month)-1))+"/1/"+prev_year;
			String date_this = String.valueOf((Integer.parseInt(bill_month)))+"/1/"+this.bill_year;
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date convertedDate_prev = dateFormat.parse(date_prev);
			Date convertedDate_this = dateFormat.parse(date_this);
			Calendar c = Calendar.getInstance();
			c.setTime(convertedDate_prev);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			//System.out.println(date+"\t"+c.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			
			
			pcell=new PdfPCell(new Paragraph("Due Amount on\n"+c.getActualMaximum(Calendar.DAY_OF_MONTH)+"-"+(Integer.valueOf(prev_month)-1)+"-"+prev_year ,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
	
			pcell=new PdfPCell(new Paragraph("Sales of\n"+Month.values()[Integer.valueOf(bill_month)-1]+", "+bill_year,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Collection of\n"+Month.values()[Integer.valueOf(bill_month)-1]+", "+bill_year,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			c= null;
			c= Calendar.getInstance();
			c.setTime(convertedDate_this);
			
			pcell=new PdfPCell(new Paragraph("Due Amount on\n"+c.getActualMaximum(Calendar.DAY_OF_MONTH)+"-"+(Integer.valueOf(bill_month))+"-"+bill_year,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Average sales",font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Average Due Months",font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Bulk Customres ",font3));		
			pcell.setPadding(3);
			//pcell.setBorder(0);
			pcell.setColspan(9);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		////header ends data starts
		ArrayList<MCollDueDTO> ministry_big_list= new ArrayList<MCollDueDTO>();	
		
		ministry_big_list= get_ministry_big();
		int sl=1;
		
		double total_prev_months_due = 0;
		double total_this_month_sales = 0;
		double total_this_collection = 0;
		double total_this_months_due = 0;
		double total_avrg_sales = 0;
		double total_avrg_due_months = 0;
		
		
		for (MCollDueDTO x:ministry_big_list){
			

				pcell=new PdfPCell(new Paragraph(String.valueOf(sl),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_id(),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_name(),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
			
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getDues_on_prev_month()/100000d) ,font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
		
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getSales_this()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCollection_this()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getDues_on_this_month()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAvrg_sales()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				double due_months= x.getDues_on_this_month()/x.getAvrg_sales();
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(due_months),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
			
				sl++;
				
				
				 total_prev_months_due = total_prev_months_due+x.getDues_on_prev_month();
				 total_this_month_sales = total_this_month_sales+x.getSales_this();
				 total_this_collection = total_this_collection+x.getCollection_this();
				 total_this_months_due = total_this_months_due+x.getDues_on_this_month();
				 total_avrg_sales = total_avrg_sales+x.getDues_on_prev_month();
				 total_avrg_due_months = total_avrg_due_months+due_months;
				
				
		}

		
		pcell= new PdfPCell(new Paragraph("Total: ", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		pcell.setColspan(3);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_prev_months_due/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_this_month_sales/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_this_collection/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_this_months_due/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		double t_avrg_sales= total_avrg_sales/ministry_big_list.size();
		double t_avrg_due_month=total_avrg_due_months/ministry_big_list.size(); 
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(t_avrg_sales/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(t_avrg_due_month), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		datatable1.setHeaderRows(3);
		
		document.add(datatable1);
		
	}
	
	
	///////////////real ministry info
	
	private void generatePdfMinistryCollectionDueReal(Document document) throws DocumentException, MalformedURLException, IOException{
		document.newPage();		
		PdfPCell pcell=null;
		
		PdfPTable headerTable = new PdfPTable(3);
		   
		
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
        //img.setAbsolutePosition(123f, 760f);		
        	img.setAbsolutePosition(240f, 560f);		// rotate
        
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

		Chunk chunk1 = new Chunk("Gas Vaban, Mendibag, Sylhet-3100",font2);
		//Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(loggedInUser.getArea_id())-1]),font3);
		Paragraph p = new Paragraph(); 
		p.add(chunk1);			
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
		
		PdfPTable datatable1 = new PdfPTable(9);
		int total=0;
		
		datatable1.setWidthPercentage(99);
		datatable1.setWidths(new float[] {10,50,30,30,30,30,30,30,30});
		
		pcell=new PdfPCell(new Paragraph(" ",font3));		
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setColspan(9);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Sl",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Ministry Name",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);		
		
		
		String prev_month=bill_month;
		int prev_year= Integer.parseInt(bill_year);
		
		if (bill_month.equals("1")){
			prev_month="13";
			prev_year= Integer.parseInt(bill_year)-1;
		}
		
		/////////////getting last date of month by bindu
		
		try{
			
			String date_prev = String.valueOf((Integer.parseInt(prev_month)-1))+"/1/"+prev_year;
			String date_this = String.valueOf((Integer.parseInt(bill_month)))+"/1/"+this.bill_year;
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date convertedDate_prev = dateFormat.parse(date_prev);
			Date convertedDate_this = dateFormat.parse(date_this);
			Calendar c = Calendar.getInstance();
			c.setTime(convertedDate_prev);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			//System.out.println(date+"\t"+c.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			
			
			pcell=new PdfPCell(new Paragraph("Due Amount on\n"+c.getActualMaximum(Calendar.DAY_OF_MONTH)+"-"+(Integer.valueOf(prev_month)-1)+"-"+prev_year ,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
	
			pcell=new PdfPCell(new Paragraph("Sales of\n"+Month.values()[Integer.valueOf(bill_month)-1]+", "+bill_year,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Collection of\n"+Month.values()[Integer.valueOf(bill_month)-1]+", "+bill_year,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			c= null;
			c= Calendar.getInstance();
			c.setTime(convertedDate_this);
			
			pcell=new PdfPCell(new Paragraph("Due Amount on\n"+c.getActualMaximum(Calendar.DAY_OF_MONTH)+"-"+(Integer.valueOf(bill_month))+"-"+bill_year,font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Average sales",font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Average Due Months",font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Remarks",font3));		
			pcell.setPadding(3);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Ministry Info ",font3));		
			pcell.setPadding(3);
			//pcell.setBorder(0);
			pcell.setColspan(9);
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		////header ends data starts
		ArrayList<MCollDueDTO> ministry_big_list= new ArrayList<MCollDueDTO>();	
		
		ministry_big_list= get_ministry_real();
		int sl=1;
		
		double total_prev_months_due = 0;
		double total_this_month_sales = 0;
		double total_this_collection = 0;
		double total_this_months_due = 0;
		double total_avrg_sales = 0;
		double total_avrg_due_months = 0;
		
		
		for (MCollDueDTO x:ministry_big_list){
			

				pcell=new PdfPCell(new Paragraph(String.valueOf(sl),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				
				
				pcell=new PdfPCell(new Paragraph(x.getCustomer_name(),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
			
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getDues_on_prev_month()/100000d) ,font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
		
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getSales_this()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getCollection_this()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getDues_on_this_month()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(x.getAvrg_sales()/100000d),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				double due_months= x.getAvg_due_months();
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(due_months),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(" ",font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
			
				sl++;
				
				
				 total_prev_months_due = total_prev_months_due+x.getDues_on_prev_month();
				 total_this_month_sales = total_this_month_sales+x.getSales_this();
				 total_this_collection = total_this_collection+x.getCollection_this();
				 total_this_months_due = total_this_months_due+x.getDues_on_this_month();
				 total_avrg_sales = total_avrg_sales+x.getDues_on_prev_month();
				 total_avrg_due_months = total_avrg_due_months+due_months;
				
				
		}

		
		pcell= new PdfPCell(new Paragraph("Total: ", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		pcell.setColspan(2);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_prev_months_due/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_this_month_sales/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_this_collection/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_this_months_due/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		double t_avrg_sales= total_avrg_sales/ministry_big_list.size();
		double t_avrg_due_month=total_avrg_due_months/ministry_big_list.size(); 
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(t_avrg_sales/100000d), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(t_avrg_due_month), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(" ", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		datatable1.setHeaderRows(3);
		
		document.add(datatable1);
		
	}
	
	
	private ArrayList<MCollDueDTO> get_ministry_big(){
		ArrayList<MCollDueDTO> ministry_big_list= new ArrayList<MCollDueDTO>();
		String prev_month= String.valueOf(Integer.parseInt(bill_month)-1);
		String prev_year= bill_year;
		
		if (bill_month.equals("1")){
			prev_month="12";
			prev_year= String.valueOf(Integer.parseInt(bill_year)-1);
		}
		
		
		if(this.bill_month.length()<2){			
			bill_month= "0"+bill_month;
		}else if(prev_month.length()<2){
			prev_month="0"+prev_month;
		}
		String wherecluse="";
		if(report_for==null) report_for="else";
		
		if(report_for.equals("combo") ){
			
			wherecluse= " BM.BILL_MONTH = "+this.bill_month
					+ "       AND BM.BILL_YEAR =  "+this.bill_year
					+ "      and CON.CUSTOMER_ID= BM.CUSTOMER_ID  and CON.IS_BULKED='Y' and BM.AREA_ID in (select area from USER_AREAS where USERID='"+loggedInUser.getUserId()+"'UNION SELECT area  FROM MST_USER  WHERE USERID='"+loggedInUser.getUserId()+"') ";
		}else{
			wherecluse=" BM.BILL_MONTH = "+this.bill_month
			+ "       AND BM.BILL_YEAR =  "+this.bill_year
			+ "      and CON.CUSTOMER_ID= BM.CUSTOMER_ID  and CON.IS_BULKED='Y' and BM.AREA_ID='"+area+"' ";
			
		}
		
		
		try{
			String big_sql=
					
					" SELECT BM.CUSTOMER_ID, "
					+ "       BM.CUSTOMER_NAME, "
					+ "       BM.PAYABLE_AMOUNT, "
					+ "       NVL (BM.COLLECTED_AMOUNT, 0) coll_amount , "
					+ "       BM.CUSTOMER_CATEGORY, "
					+ "       BM.CUSTOMER_CATEGORY_NAME, "
					+ "       GETBALANCE (BM.CUSTOMER_ID, "+prev_year+prev_month+") balance, "
					+ "         GETBALANCE (BM.CUSTOMER_ID, "+prev_year+prev_month+") "
					+ "       + BM.PAYABLE_AMOUNT "
					+ "       - NVL (BM.COLLECTED_AMOUNT, 0) "
					+ "          col4, "
					+ "       getAvrgSales (BM.CUSTOMER_ID, "+this.bill_year+bill_month+") avrgSales, "
					+ "       'PDB' ministry_name "
					+ "  FROM bill_metered bm , customer_connection con" //--, customer_connection cc, MST_MINISTRY mm "
					+ " WHERE "+wherecluse ;
			/*
					+ "       --AND cc.customer_id = bm.customer_id "
					+ "      -- AND cc.MINISTRY_ID IS NOT NULL "
					+ "      -- and CC.MINISTRY_ID= MM.MINISTRY_ID";
			*/
			
			
			PreparedStatement stmt=conn.prepareStatement(big_sql);
			ResultSet r=stmt.executeQuery();
			while(r.next()){
				MCollDueDTO mCollDueDTO= new MCollDueDTO();
				mCollDueDTO.setCustomer_id(r.getString("CUSTOMER_ID"));
				mCollDueDTO.setCustomer_name(r.getString("CUSTOMER_NAME"));
				mCollDueDTO.setDues_on_prev_month(r.getDouble("BALANCE"));
				mCollDueDTO.setSales_this(r.getDouble("PAYABLE_AMOUNT"));
				mCollDueDTO.setCollection_this(r.getDouble("coll_amount"));
				mCollDueDTO.setDues_on_this_month(r.getDouble("COL4"));
				mCollDueDTO.setAvrg_sales(r.getDouble("AVRGSALES"));
				
				ministry_big_list.add(mCollDueDTO);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return ministry_big_list;
	}
	
	private ArrayList<MCollDueDTO> get_ministry_real(){
		ArrayList<MCollDueDTO> ministry_big_list= new ArrayList<MCollDueDTO>();
		String prev_month= String.valueOf(Integer.parseInt(bill_month)-1);
		String prev_year= bill_year;
		
		if (bill_month.equals("1")){
			prev_month="12";
			prev_year= String.valueOf(Integer.parseInt(bill_year)-1);
		}
		
		
		if(this.bill_month.length()<2){			
			bill_month= "0"+bill_month;
		}else if(prev_month.length()<2){
			prev_month="0"+prev_month;
		}
		
		String wherecluse="";
		if(report_for==null) report_for="else";
	
		if(report_for.equals("combo") ){
			wherecluse=" bill_year = "+bill_year+" AND bill_month = "+bill_month+" AND area_id in  (select area from USER_AREAS where USERID='"+loggedInUser.getUserId()+"'UNION SELECT area  FROM MST_USER  WHERE USERID='"+loggedInUser.getUserId()+"') ";
			
		}else{
			
			wherecluse=" bill_year = "+bill_year+" AND bill_month = "+bill_month+" AND area_id = '"+area+"'";
		}
		
		try{
			String realsql = ""
					+ " SELECT MINISTRY_NAME, "
					+ "         SUM (ACTUAL_PAYABLE_AMOUNT) m_sales, "
					+ "         SUM (COLLECTED_PAYABLE_AMOUNT) m_collection, "
					+ "         SUM (due_till_m1), "
					+ "         SUM (due_till_m2), "
					+ "         ROUND(AVG (ACTUAL_PAYABLE_AMOUNT)) avg_sales, "
					+ "         SUM (due_month_count) t_due_month, "
					+ "         to_char(AVG (due_month_count),99.99) avg_due_month "
					+ "    FROM (SELECT bill.*, "
					+ "                 mm.MINISTRY_NAME, "
					+ "                 GETBALANCE (bill.customer_id, '"+bill_year+prev_month+"') due_till_m1, "
					+ "                 GETBALANCE (bill.customer_id, '"+bill_year+bill_month+"') due_till_m2, "
					+ "                 getDueMonthCount (bill.customer_id, '"+bill_year+bill_month+"') due_month_count "
					+ "            FROM (SELECT customer_id, "
					+ "                         ACTUAL_PAYABLE_AMOUNT, "
					+ "                         NVL (COLLECTED_PAYABLE_AMOUNT, 0) "
					+ "                            COLLECTED_PAYABLE_AMOUNT "
					+ "                    FROM bill_non_metered "
					+ "                   WHERE bill_year = "+bill_year+" AND bill_month = "+bill_month+" AND area_id = '"+area+"' "
					+ "                  UNION "
					+ "                  SELECT customer_id, PAYABLE_AMOUNT, NVL (COLLECTED_AMOUNT, 0) "
					+ "                    FROM bill_metered "
					+ "                   WHERE "+wherecluse+") bill, "
					+ "                 customer_connection cc, "
					+ "                 MST_MINISTRY mm "
					+ "           WHERE     bill.customer_id = cc.customer_id "
					+ "                 AND cc.MINISTRY_ID IS NOT NULL "
					+ "                 AND cc.MINISTRY_ID = mm.MINISTRY_ID) ddata "
					+ " GROUP BY ddata.MINISTRY_NAME ";

			
			
			PreparedStatement stmt=conn.prepareStatement(realsql);
			ResultSet r=stmt.executeQuery();
			while(r.next()){
				MCollDueDTO mCollDueDTO= new MCollDueDTO();
				//mCollDueDTO.setCustomer_id(r.getString("CUSTOMER_ID"));
				mCollDueDTO.setCustomer_name(r.getString("MINISTRY_NAME"));
				mCollDueDTO.setDues_on_prev_month(r.getDouble("SUM(DUE_TILL_M1)"));
				mCollDueDTO.setSales_this(r.getDouble("M_SALES"));
				mCollDueDTO.setCollection_this(r.getDouble("M_COLLECTION"));
				mCollDueDTO.setDues_on_this_month(r.getDouble("SUM(DUE_TILL_M2)"));
				mCollDueDTO.setAvrg_sales(r.getDouble("AVG_SALES"));
				mCollDueDTO.setAvg_due_months(r.getDouble("avg_due_month"));
				
				ministry_big_list.add(mCollDueDTO);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return ministry_big_list;
	}
	

	public String getArea() {
		return area;
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

	public void setArea(String area) {
		this.area = area;
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


