package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.DisconnType;
import org.jgtdsl.enums.Month;
import org.jgtdsl.models.AreaService;
import org.jgtdsl.reports.masterData.CustomerCategory;
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



public class MonthlySalesStatement extends BaseAction {
	private static final long serialVersionUID = 1L;
	
    private static String area;
    private static String customer_category;
    private static String bill_month;
    private static String bill_year;
    private String report_for; 
    private static String category_name;
    AreaService aa=new AreaService();
	
	String sql = "";
	ArrayList<String>customerType=new ArrayList<String>();
	
	PreparedStatement ps=null;
	ResultSet rs=null;
	//String[] areaName=new String[10];
	int a=0;
	


	public ServletContext servlet;
	ServletContext servletContext = null;

	static Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	static Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
	UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");


	public String execute() throws Exception {




		
		String fileName=String.valueOf(Month.values()[Integer.valueOf(bill_month)-1].getLabel());
		       fileName +="/"+bill_year+"SalesStatement";
		    //   fileName +=String.valueOf(String.valueOf(Area.values()[Integer.valueOf(loggedInUser.getArea_id())-1]));
		       fileName +=".pdf";
		 
		 
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
	
			String realPath = servlet.getRealPath("/resources/images/logo/JG.png");  // image path
			   Image img = Image.getInstance(realPath);
			      
			             //img.scaleToFit(10f, 200f);
			             //img.scalePercent(200f);
			            img.scaleAbsolute(32f, 35f);
			            //img.setAbsolutePosition(145f, 780f);  
			             img.setAbsolutePosition(348f, 519f);  // rotate
			            
			         document.add(img);
			
			
			
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
			
	
//error in chunk2			
			
			if(report_for.equals("combo")){
				area=aa.getAreaName();
				Chunk chunk1 = new Chunk("Combined Report for: ",font3);
				Chunk chunk2 = new Chunk(area,font3);
				Paragraph p = new Paragraph(); 
				p.add(chunk1);
				p.add(chunk2);
				pcell=new PdfPCell(p);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				mTable.addCell(pcell);
				
			}else{
				Chunk chunk1 = new Chunk("Regional Distribution Office :",font2);
				Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(area)-1]),font3);
				Paragraph p = new Paragraph(); 
				p.add(chunk1);
				p.add(chunk2);
				pcell=new PdfPCell(p);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				mTable.addCell(pcell);
				
			}
			
			
			//Chunk chunk2 = new Chunk(String.valueOf(Area.values()[1]),font3);
			
			
			

			
			
						
			pcell=new PdfPCell(mTable);
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			
			
			
			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			document.add(headerTable);
			

			if(report_for.equals("area_wise"))
			{
				area_wise(document);
				burner_table(document);
			}else if(report_for.equals("category_wise"))			
			{
				category_wise(document);
			}else if(report_for.equals("combo")){
				
				area_wise(document);
				burner_table(document);
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


	private static PdfPCell createTableNotHeaderCell(final String text) {
		final PdfPCell cell = new PdfPCell(new Paragraph(text, font2));

		cell.setMinimumHeight(16f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		//cell.setBackgroundColor(new BaseColor(242,242,242));
		cell.setBorderColor(BaseColor.BLACK);


		return cell;
	}

	private static PdfPCell createTableHeaderCell(final String text) {
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
		Connection conn = ConnectionManager.getConnection();
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
		
		pcell=new PdfPCell(new Paragraph("Statement of Gas Sales For The Month Of "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year, ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		document.add(headlineTable);
		
		BigDecimal domGovActExpMin=BigDecimal.ZERO;
		BigDecimal domGovActWithMin=BigDecimal.ZERO;
		BigDecimal domGovBillunit=BigDecimal.ZERO;
		BigDecimal domGovDiff=BigDecimal.ZERO;
		BigDecimal domGovTotalCon=BigDecimal.ZERO;
		BigDecimal domGovValueOfActCon=BigDecimal.ZERO;
		BigDecimal domGovMinCharge=BigDecimal.ZERO;
		BigDecimal domGovHHV=BigDecimal.ZERO;
		BigDecimal domGovMeterRent=BigDecimal.ZERO;
		BigDecimal domGovTotalBill=BigDecimal.ZERO;
		BigDecimal rate=BigDecimal.ZERO;
		
		BigDecimal value_of_actual_consum = BigDecimal.ZERO;
		BigDecimal total_bill_amount=BigDecimal.ZERO;
		BigDecimal total_bill_amount_withoutother=BigDecimal.ZERO;
		BigDecimal nhv_hhv_amount=BigDecimal.ZERO;
		BigDecimal demand_charge_amount=BigDecimal.ZERO;
		BigDecimal meter_rent_amount=BigDecimal.ZERO;
	
		
		BigDecimal totalActExpMin=BigDecimal.ZERO;
		BigDecimal totalActWithMin=BigDecimal.ZERO;
		BigDecimal totalBillunit=BigDecimal.ZERO;
		BigDecimal totalDiff=BigDecimal.ZERO;
		BigDecimal totalTotalCon=BigDecimal.ZERO;
		BigDecimal totalValueOfActCon=BigDecimal.ZERO;
		BigDecimal totalMinCharge=BigDecimal.ZERO;
		BigDecimal totalHHV=BigDecimal.ZERO;
		BigDecimal totalMeterRent=BigDecimal.ZERO;
		BigDecimal totalTotalBill=BigDecimal.ZERO;
		BigDecimal total_customer_count=BigDecimal.ZERO;
		
		
		BigDecimal subSumActExpMin=BigDecimal.ZERO;
		BigDecimal subSumActWithMin=BigDecimal.ZERO;
		BigDecimal subSumBillunit=BigDecimal.ZERO;
		BigDecimal subSumDiff=BigDecimal.ZERO;
		BigDecimal subSumTotalCon=BigDecimal.ZERO;
		BigDecimal subSumValueOfActCon=BigDecimal.ZERO;
		BigDecimal subSumMinCharge=BigDecimal.ZERO;
		BigDecimal subSumHHV=BigDecimal.ZERO;
		BigDecimal subSumMeterRent=BigDecimal.ZERO;
		BigDecimal subSumTotalBill=BigDecimal.ZERO;
		BigDecimal sub_customer_count=BigDecimal.ZERO;
		
		
	
		
		PdfPTable datatable1 = new PdfPTable(12);
		
		datatable1.setWidthPercentage(100);
		datatable1.setWidths(new float[] {15,40,18,40,30,25,30,30,30,40,40,40
		});
		
		
		pcell=new PdfPCell(new Paragraph("Sl.No",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Category of Customer",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer Count",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("Approved Load",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("Total Actual Consumption",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);	
		
		pcell=new PdfPCell(new Paragraph("Rate",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		
		pcell=new PdfPCell(new Paragraph("Value of Total Actual Consumption",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);	
		
		pcell=new PdfPCell(new Paragraph("Billing Amount",font3));
		pcell.setColspan(5);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);		
		
		pcell=new PdfPCell(new Paragraph("Demand charge",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Meter Rent",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("NHV/HHV",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
	
		pcell=new PdfPCell(new Paragraph("Total Billed Amount",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		
		
		pcell=new PdfPCell(new Paragraph("Remarks",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);		
		
		int cusCatCount=97;
		String previousType="GOVT";
		
		String currnertType="";
		String whereClause="";
		
		
		
		if( report_for.equals("combo") ){
		
		whereClause= 	" 		  SR.customer_id = conn.customer_id " +
						"         AND BILLING_MONTH = " + this.getBill_month()  +
						"         AND BILLING_YEAR = "+ this.getBill_year()  +
						"         AND SUBSTR (SR.customer_id, 3, 2) = MCC.CATEGORY_ID " +
						"		  and  SUBSTR (SR.customer_id, 1, 2) in (select area from USER_AREAS where USERID='"+loggedInUser.getUserId()+"'UNION SELECT area  FROM MST_USER  WHERE USERID='"+loggedInUser.getUserId()+"') " ;
					
		}else{
		
		whereClause= " SR.customer_id = conn.customer_id " +
				"         AND BILLING_MONTH = " + this.getBill_month()  +
				"         AND BILLING_YEAR = "+ this.getBill_year()  +
				"         AND SUBSTR (SR.customer_id, 3, 2) = MCC.CATEGORY_ID " +
				"         AND SUBSTR (SR.customer_id, 1, 2) = " + this.getArea()  ;
		}
		
		try {
		
			String sql1=" SELECT MCC.CATEGORY_ID, " +
						"         CATEGORY_NAME ,COUNT (SR.CUSTOMER_ID) customer_count, " +
						"         MCC.CATEGORY_TYPE, " +
						"          SUM (nvl(conn.max_load,0)) ACTUAL_EXCEPT_MINIMUM, /*max load change in 28/8/2019 by bindu for urgency*/" +
						"         SUM (ACTUAL_WITH_MINIMUM) ACTUAL_WITH_MINIMUM, " +
						"         SUM (BILLING_UNIT) BILLING_UNIT, " +
						"         SUM (DIFFERENCE) DIFFERENCE, " +
						"         SUM (TOTAL_ACTUAL_CONSUMPTION) TOTAL_ACTUAL_CONSUMPTION, " +
						"         RATE, " +
						"         SUM (TOTAL_ACTUAL_CONSUMPTION*RATE) VALUE_OF_ACTUAL_CONSUMPTION, " +
						"         SUM (getDemandCharge(bill_id)) MINIMUM_CHARGE, /*demand change in 28/8/2019 by bindu for urgency*/" +
						"         SUM (METER_RENT) METER_RENT, " +
						"         SUM (SURCHARGE_AMOUNT) SURCHARGE_AMOUNT, " +

						"         SUM (HHV_NHV_AMOUNT) HHV_NHV_AMOUNT, SUM(TOTAL_AMOUNT)+SUM (nvl(HHV_NHV_AMOUNT,0))  TOTAL_AMOUNT, " +
						//"         round(SUM ((TOTAL_ACTUAL_CONSUMPTION*RATE)+nvl(MINIMUM_CHARGE,0)+nvl(METER_RENT,0)+nvl(HHV_NHV_AMOUNT,0))) TOTAL_AMOUNT " +
						

						"         SUM (HHV_NHV_AMOUNT) HHV_NHV_AMOUNT, " +
						//"         round(SUM ((TOTAL_ACTUAL_CONSUMPTION*RATE)+nvl(MINIMUM_CHARGE,0)+nvl(METER_RENT,0)+nvl(HHV_NHV_AMOUNT,0))) TOTAL_AMOUNT " +

						"         SUM (HHV_NHV_AMOUNT) HHV_NHV_AMOUNT " +
						//"         round(SUM ((TOTAL_ACTUAL_CONSUMPTION*round(RATE,2))+nvl(MINIMUM_CHARGE,0)+nvl(METER_RENT,0)+nvl(HHV_NHV_AMOUNT,0))) TOTAL_AMOUNT " +

						"    FROM SALES_REPORT SR, CUSTOMER_CONNECTION conn, MST_CUSTOMER_CATEGORY mcc " +
						"   WHERE    " + whereClause +
						" GROUP BY MCC.CATEGORY_ID, " +
						"         MCC.CATEGORY_NAME, " +
						"         MCC.CATEGORY_TYPE, " +
						"         RATE " +
						" ORDER BY MCC.CATEGORY_TYPE ASC, MCC.CATEGORY_ID ASC ";
				
    		PreparedStatement ps1=conn.prepareStatement(sql1);
    		
    		ResultSet rs1=ps1.executeQuery();    			
    		while(rs1.next())
        	{
    			currnertType=rs1.getString("CATEGORY_TYPE");
    			
    			if(cusCatCount==97)
    			{
    				
    				pcell=new PdfPCell(new Paragraph(previousType.equals(currnertType)?"A) GOVERNMENT":"",font3));
    				pcell.setColspan(2);
    				datatable1.addCell(pcell);
    				
    				pcell=new PdfPCell(new Paragraph("",font3));
    				pcell.setColspan(11);
    				datatable1.addCell(pcell);
    			}
    			
    			
    			if(!currnertType.equals(previousType))
    			{
    			
	    			if(cusCatCount!=97){
	    				
	    				 domGovActExpMin=BigDecimal.ZERO;
	    				 domGovActWithMin=BigDecimal.ZERO;
	    				 domGovBillunit=BigDecimal.ZERO;
	    				 domGovDiff=BigDecimal.ZERO;
	    				 domGovTotalCon=BigDecimal.ZERO;
	    				 domGovValueOfActCon=BigDecimal.ZERO;
	    				 domGovMinCharge=BigDecimal.ZERO;
	    				 domGovHHV=BigDecimal.ZERO;
	    				 domGovMeterRent=BigDecimal.ZERO;
	    				 domGovTotalBill=BigDecimal.ZERO;
		    			
	    				pcell=new PdfPCell(new Paragraph("Sub Total (A)",font3));
	    				pcell.setColspan(2);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);	 
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(sub_customer_count),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(consumption_format.format(subSumActExpMin),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(consumption_format.format(subSumTotalCon),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(" "));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumValueOfActCon),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMinCharge),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMeterRent),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumHHV),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    			
	    				
	    				pcell=new PdfPCell(new Paragraph(taka_format.format(subSumTotalBill),font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph("",font3));
	    				pcell.setColspan(1);
	    				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				datatable1.addCell(pcell);
	    				
	    				
	    				
	    				
	    				
	    				totalActExpMin=totalActExpMin.add(subSumActExpMin);
	    				totalActWithMin=totalActWithMin.add(subSumActWithMin);
	    				totalBillunit=totalBillunit.add(subSumBillunit);
	    				totalDiff=totalDiff.add(subSumDiff);
	    				totalTotalCon=totalTotalCon.add(subSumTotalCon);
	    				totalValueOfActCon=totalValueOfActCon.add(subSumValueOfActCon);
	    				totalMinCharge=totalMinCharge.add(subSumMinCharge);
	    				totalHHV=totalHHV.add(subSumHHV);
	    				totalMeterRent=totalMeterRent.add(subSumMeterRent);
	    				totalTotalBill=totalTotalBill.add(subSumTotalBill);
	    				
	    				 total_customer_count= total_customer_count.add(sub_customer_count);
	    				
	    				 
	    				     subSumActExpMin=BigDecimal.ZERO;
	    					 subSumActWithMin=BigDecimal.ZERO;
	    					 subSumBillunit=BigDecimal.ZERO;
	    					 subSumDiff=BigDecimal.ZERO;
	    					 subSumTotalCon=BigDecimal.ZERO;
	    					 subSumValueOfActCon=BigDecimal.ZERO;
	    					 subSumMinCharge=BigDecimal.ZERO;
	    					 subSumHHV=BigDecimal.ZERO;
	    					 subSumMeterRent=BigDecimal.ZERO;
	    					 subSumTotalBill=BigDecimal.ZERO;
	    					 sub_customer_count=BigDecimal.ZERO;
	    				
	    				
	    				
	    				
	    				pcell=new PdfPCell(new Paragraph(currnertType.equals("PVT")?"B) PRIVATE":"A) GOVERNMENT",font3));
	    				pcell.setColspan(2);
	    				datatable1.addCell(pcell);
	    				pcell=new PdfPCell(new Paragraph("",font3));
	    				pcell.setColspan(11);
	    				datatable1.addCell(pcell);
	    				cusCatCount=97;
	    				previousType=currnertType;
	    			}
	    			
	    		
					
    			}
    			
    			pcell=new PdfPCell(new Paragraph(Character.toString ((char) cusCatCount)+")",font3));
    			datatable1.addCell(pcell);
    			pcell=new PdfPCell(new Paragraph(rs1.getString("CATEGORY_NAME"),font3));
    			pcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
    			datatable1.addCell(pcell);
    				    		
    			sub_customer_count=sub_customer_count.add(rs1.getBigDecimal("customer_count"));
    			pcell=new PdfPCell(new Paragraph(rs1.getString("customer_count"),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    	        
    			
    			subSumActExpMin=subSumActExpMin.add(rs1.getBigDecimal("ACTUAL_EXCEPT_MINIMUM")); // new solution	    			
    			
    			String category=rs1.getString("CATEGORY_ID");
    			/*
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovActExpMin=domGovActExpMin.add(rs1.getFloat("ACTUAL_EXCEPT_MINIMUM")==0?BigDecimal.ZERO:rs1.getBigDecimal("ACTUAL_EXCEPT_MINIMUM"));
    			}
    			pcell=new PdfPCell(new Paragraph("1"));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			
    			subSumActWithMin=subSumActWithMin.add(rs1.getFloat("ACTUAL_WITH_MINIMUM")==0?BigDecimal.ZERO:rs1.getBigDecimal("ACTUAL_WITH_MINIMUM"));
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovActWithMin=domGovActWithMin.add(rs1.getFloat("ACTUAL_WITH_MINIMUM")==0?BigDecimal.ZERO:rs1.getBigDecimal("ACTUAL_WITH_MINIMUM"));
    			}
    			pcell=new PdfPCell(new Paragraph("2"));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			subSumBillunit=subSumBillunit.add(rs1.getFloat("BILLING_UNIT")==0?BigDecimal.ZERO:rs1.getBigDecimal("BILLING_UNIT"));
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovBillunit=domGovBillunit.add(rs1.getFloat("BILLING_UNIT")==0?BigDecimal.ZERO:rs1.getBigDecimal("BILLING_UNIT"));
    			}
    		pcell=new PdfPCell(new Paragraph("3"));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			*/
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovActExpMin=domGovActExpMin.add(rs1.getFloat("ACTUAL_EXCEPT_MINIMUM")==0?BigDecimal.ZERO:rs1.getBigDecimal("ACTUAL_EXCEPT_MINIMUM"));
    			}
    			pcell=new PdfPCell(new Paragraph(consumption_format.format(rs1.getBigDecimal("ACTUAL_EXCEPT_MINIMUM")),font2));//new solution
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);    	
    			
    			
    			
    			 total_bill_amount	 	= rs1.getFloat("TOTAL_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_AMOUNT");
    			 demand_charge_amount   = rs1.getFloat("MINIMUM_CHARGE")==0?BigDecimal.ZERO:rs1.getBigDecimal("MINIMUM_CHARGE");
    			 meter_rent_amount		= rs1.getFloat("METER_RENT")==0?BigDecimal.ZERO:rs1.getBigDecimal("METER_RENT");
    			 nhv_hhv_amount			= rs1.getFloat("HHV_NHV_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("HHV_NHV_AMOUNT");
    			 rate					= rs1.getFloat("RATE")==0?BigDecimal.ZERO:rs1.getBigDecimal("RATE");
    			 BigDecimal resultSum =(demand_charge_amount).add(meter_rent_amount).add(nhv_hhv_amount);
    			 
    			 total_bill_amount_withoutother = total_bill_amount.subtract(resultSum) ;
    			 
    			 
    			 BigDecimal division_result= total_bill_amount_withoutother.divide(rate,2, BigDecimal.ROUND_HALF_UP);
    			 
    			 
    			 
    			subSumTotalCon=subSumTotalCon.add(division_result);
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovTotalCon=domGovTotalCon.add(division_result);
    			}
    			pcell=new PdfPCell(new Paragraph(consumption_format.format(division_result),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			
    			subSumDiff=subSumDiff.add(rs1.getFloat("DIFFERENCE")==0?BigDecimal.ZERO:rs1.getBigDecimal("DIFFERENCE"));
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovDiff=domGovDiff.add(rs1.getFloat("DIFFERENCE")==0?BigDecimal.ZERO:rs1.getBigDecimal("DIFFERENCE"));
    			}
    		/*	pcell=new PdfPCell(new Paragraph(rs1.getString("RATE")));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);*/
    			
    			
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				rate=rate.add(rs1.getFloat("RATE")==0?BigDecimal.ZERO:rs1.getBigDecimal("RATE"));
    			}
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("RATE")==0?BigDecimal.ZERO:rs1.getBigDecimal("RATE")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);    			
    			
    			
    			
    			subSumValueOfActCon=subSumValueOfActCon.add(total_bill_amount_withoutother);
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovValueOfActCon=domGovValueOfActCon.add(total_bill_amount_withoutother);
    			}
    			pcell=new PdfPCell(new Paragraph(taka_format.format(total_bill_amount_withoutother),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			subSumMinCharge=subSumMinCharge.add(rs1.getFloat("MINIMUM_CHARGE")==0?BigDecimal.ZERO:rs1.getBigDecimal("MINIMUM_CHARGE"));
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovMinCharge=domGovMinCharge.add(rs1.getFloat("MINIMUM_CHARGE")==0?BigDecimal.ZERO:rs1.getBigDecimal("MINIMUM_CHARGE"));
    			}
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("MINIMUM_CHARGE")==0?BigDecimal.ZERO:rs1.getBigDecimal("MINIMUM_CHARGE")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			
    			subSumMeterRent=subSumMeterRent.add(rs1.getFloat("METER_RENT")==0?BigDecimal.ZERO:rs1.getBigDecimal("METER_RENT"));
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovMeterRent=domGovMeterRent.add(rs1.getFloat("METER_RENT")==0?BigDecimal.ZERO:rs1.getBigDecimal("METER_RENT"));
    			}
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("METER_RENT")==0?BigDecimal.ZERO:rs1.getBigDecimal("METER_RENT")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			

    			
    			subSumHHV=subSumHHV.add(rs1.getFloat("HHV_NHV_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("HHV_NHV_AMOUNT"));
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovHHV=domGovHHV.add(rs1.getFloat("HHV_NHV_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("HHV_NHV_AMOUNT"));
    			}
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("HHV_NHV_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("HHV_NHV_AMOUNT")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			
    			
    			subSumTotalBill=subSumTotalBill.add(rs1.getFloat("TOTAL_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_AMOUNT"));
    			
    			if(category.equals("01")||category.equals("02"))
    			{
    				domGovTotalBill=domGovTotalBill.add(rs1.getFloat("TOTAL_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_AMOUNT"));
    			}
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("TOTAL_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_AMOUNT")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			

    			
    			pcell=new PdfPCell(new Paragraph(" ",font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			
    			
    			
    			
    			
    			cusCatCount++;
        	}	
    		totalActExpMin=totalActExpMin.add(subSumActExpMin);
			totalActWithMin=totalActWithMin.add(subSumActWithMin);
			totalBillunit=totalBillunit.add(subSumBillunit);
			totalDiff=totalDiff.add(subSumDiff);
			totalTotalCon=totalTotalCon.add(subSumTotalCon);
			totalValueOfActCon=totalValueOfActCon.add(subSumValueOfActCon);
			totalMinCharge=totalMinCharge.add(subSumMinCharge);
			totalHHV=totalHHV.add(subSumHHV);
			totalMeterRent=totalMeterRent.add(subSumMeterRent);
			totalTotalBill=totalTotalBill.add(subSumTotalBill);
			total_customer_count=total_customer_count.add(sub_customer_count);
			
//    		pcell=new PdfPCell(new Paragraph("Total Dom.(PVT)",font3));
//			pcell.setColspan(2);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(domGovActExpMin),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(domGovActWithMin),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(domGovBillunit),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(domGovDiff),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(domGovTotalCon),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			
//			
//			
//			pcell=new PdfPCell(new Paragraph(taka_format.format(domGovValueOfActCon),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(taka_format.format(domGovMinCharge),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			
//			
//			pcell=new PdfPCell(new Paragraph(taka_format.format(domGovMeterRent),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(taka_format.format(domGovHHV),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			
//			
//			pcell=new PdfPCell(new Paragraph(taka_format.format(domGovTotalBill),font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			
//			
//			pcell=new PdfPCell(new Paragraph("",font3));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph("Sub Total (B)",font3));
			pcell.setColspan(2);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);			
//			
			
				
			pcell=new PdfPCell(new Paragraph(taka_format.format(sub_customer_count),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(consumption_format.format(subSumActExpMin),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(consumption_format.format(subSumTotalCon),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(" "));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumValueOfActCon),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMinCharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumMeterRent),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumHHV),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(subSumTotalBill),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
//			
			pcell=new PdfPCell(new Paragraph(""));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			if(loggedInUser.getUserId().equals("central_revenue") ) {
				
				pcell=new PdfPCell(new Paragraph("Total Sales Of All Areas (A+B)=",font3));
				pcell.setColspan(2);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
			
				
			}else{
				
				pcell=new PdfPCell(new Paragraph("Total Sales Of (A+B)=",font3));
				pcell.setColspan(2);
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				datatable1.addCell(pcell);
				
			
			}
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(total_customer_count),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			

			pcell=new PdfPCell(new Paragraph(consumption_format.format(totalActExpMin),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(consumption_format.format(totalTotalCon),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);		
			
			pcell=new PdfPCell(new Paragraph(""));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalValueOfActCon),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalMinCharge),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalMeterRent),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalHHV),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalTotalBill),font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		
		
			pcell=new PdfPCell(new Paragraph("",font3));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			        
			        
			    
		} catch (Exception e){e.printStackTrace();}
 		finally{try{ConnectionManager.closeConnection(conn);} catch (Exception e)
			{e.printStackTrace();}conn = null;}
	        
		
	        
		
		document.add(datatable1);
		
	}
	
	
	private void burner_table(Document document)   throws DocumentException{
		Connection conn = ConnectionManager.getConnection();
		Statement st1=null;
		Statement st2=null;
		Statement st3=null;
		Statement st4=null;
		
		ResultSet rs1= null;
		ResultSet rs2= null;
		ResultSet rs3= null;
		ResultSet rs4= null;
		
		
		
		
		PdfPTable burnerTable = new PdfPTable(3);
		//headlineTable.setSpacingBefore(5);
		//headlineTable.setSpacingAfter(10);
		burnerTable.setWidths(new float[] {
				7,5,5
			});
		
		PdfPCell pcell = null;
		
		pcell=new PdfPCell(new Paragraph("   ", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(3);
		pcell.setPadding(10);
		pcell.setBorder(Rectangle.NO_BORDER);
		burnerTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Category", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setPadding(3);
		pcell.setBorder(Rectangle.BOX);
		burnerTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Single", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setPadding(3);
		pcell.setBorder(Rectangle.BOX);
		burnerTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Double", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setPadding(3);
		pcell.setBorder(Rectangle.BOX);
		burnerTable.addCell(pcell);
		
		String SingleDom="";
		String DoubleDom="";
		String SingleGov="";
		String DoubleGov="";
		
		String	whereclus1="";
		String	whereclus2="";
		String	whereclus3="";
		String	whereclus4="";
		
		if(report_for.equals("combo") ){
			//bindu
		whereclus1=	" BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
					" AND BILLING_MONTH = " +bill_month+
					" AND BILLING_YEAR = " +bill_year+
					" AND SR.IS_METER = 0 " +
					" AND SUBSTR (bb.customer_id, 3, 2) = '01' " +
					" AND BB.APPLIANCE_TYPE_CODE = '01' " +
					"  and  SUBSTR (SR.customer_id, 1, 2) in (select area from USER_AREAS where USERID='"+loggedInUser.getUserId()+"') ";
	
		whereclus2= " BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
			" AND BILLING_MONTH = " +bill_month+
			" AND BILLING_YEAR = " +bill_year+
			" AND SR.IS_METER = 0 " +
			" AND SUBSTR (bb.customer_id, 3, 2) = '01' " +
			" AND BB.APPLIANCE_TYPE_CODE = '02' " +
			"  and  SUBSTR (SR.customer_id, 1, 2) in (select area from USER_AREAS where USERID='"+loggedInUser.getUserId()+"') ";
	
	
		whereclus3= " BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
			" AND BILLING_MONTH = " +bill_month+
			" AND BILLING_YEAR = " +bill_year+
			" AND SR.IS_METER = 0 " +
			" AND SUBSTR (bb.customer_id, 3, 2) = '09' " +
			" AND BB.APPLIANCE_TYPE_CODE = '01' " +
			"  and  SUBSTR (SR.customer_id, 1, 2) in (select area from USER_AREAS where USERID='"+loggedInUser.getUserId()+"') ";
	
	
		whereclus4= "  BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
			" AND BILLING_MONTH = " +bill_month+
			" AND BILLING_YEAR = " +bill_year+
			" AND SR.IS_METER = 0 " +
			" AND SUBSTR (bb.customer_id, 3, 2) = '09' " +
			" AND BB.APPLIANCE_TYPE_CODE = '02' " +
			"  and  SUBSTR (SR.customer_id, 1, 2) in (select area from USER_AREAS where USERID='"+loggedInUser.getUserId()+"') ";
			
		}else{
			
			whereclus1= " BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
					" AND BILLING_MONTH = " +bill_month+
					" AND BILLING_YEAR = " +bill_year+
					" AND SR.IS_METER = 0 " +
					" AND SUBSTR (bb.customer_id, 1, 4) = '"+area+"01' " +
					" AND BB.APPLIANCE_TYPE_CODE = '01' " ;
			
			whereclus2= " BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
					" AND BILLING_MONTH = " +bill_month+
					" AND BILLING_YEAR = " +bill_year+
					" AND SR.IS_METER = 0 " +
					" AND SUBSTR (bb.customer_id, 1, 4) = '"+area+"01' " +
					" AND BB.APPLIANCE_TYPE_CODE = '02' " ;
			
			whereclus3= " BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
					" AND BILLING_MONTH = " +bill_month+
					" AND BILLING_YEAR = " +bill_year+
					" AND SR.IS_METER = 0 " +
					" AND SUBSTR (bb.customer_id, 1, 4) = '"+area+"09' " +
					" AND BB.APPLIANCE_TYPE_CODE = '01' " ;
			
			whereclus4= " BB.CUSTOMER_ID = SR.CUSTOMER_ID " +
					" AND BILLING_MONTH = " +bill_month+
					" AND BILLING_YEAR = " +bill_year+
					" AND SR.IS_METER = 0 " +
					" AND SUBSTR (bb.customer_id, 1, 4) = '"+area+"09' " +
					" AND BB.APPLIANCE_TYPE_CODE = '02' " ;
			
			
		}
		
		
		
		String sqlSingleDomestic = "SELECT  SUM (NEW_APPLIANCE_QNT) as burnerSingle " +
				" FROM BURNER_QNT_CHANGE " +
				" WHERE PID IN " +
				" ( SELECT MAX (PID) " +
				" FROM BURNER_QNT_CHANGE bb, sales_report sr " +
				" WHERE "+ whereclus1 +
				" GROUP BY BB.CUSTOMER_ID) " ;
				
		
		String sqlDoubleDomestic = "SELECT  SUM (NEW_APPLIANCE_QNT) as burnerDouble " +
				" FROM BURNER_QNT_CHANGE " +
				" WHERE PID IN " +
				" ( SELECT MAX (PID) " +
				" FROM BURNER_QNT_CHANGE bb, sales_report sr " +
				" WHERE "+whereclus2 +
				" GROUP BY BB.CUSTOMER_ID) " ;
		
		
		String sqlSinglegovt = "SELECT  SUM (NEW_APPLIANCE_QNT) as burnerSingle " +
				" FROM BURNER_QNT_CHANGE " +
				" WHERE PID IN " +
				" ( SELECT MAX (PID) " +
				" FROM BURNER_QNT_CHANGE bb, sales_report sr " +
				" WHERE " +whereclus3 +
				" GROUP BY BB.CUSTOMER_ID) " ;
				
		
		String sqlDoubleGovt = "SELECT  SUM (NEW_APPLIANCE_QNT) as burnerDouble " +
				" FROM BURNER_QNT_CHANGE " +
				" WHERE PID IN " +
				" ( SELECT MAX (PID) " +
				" FROM BURNER_QNT_CHANGE bb, sales_report sr " +
				" WHERE " +whereclus4 +
				" GROUP BY BB.CUSTOMER_ID) " ;
		
		try{
			 st1=conn.createStatement();
			 st2=conn.createStatement();
			 st3=conn.createStatement();
			 st4=conn.createStatement();
			
			rs1=st1.executeQuery(sqlSingleDomestic);
			rs2=st2.executeQuery(sqlDoubleDomestic);
			rs3=st3.executeQuery(sqlSinglegovt);
			rs4=st4.executeQuery(sqlDoubleGovt);
			
			
			while(rs1.next()){
				SingleDom= rs1.getString("burnerSingle");								
			}
			
			while(rs2.next()){
				DoubleDom= rs2.getString("burnerDouble");
				
			}
			
			while(rs3.next()){
				SingleGov= rs3.getString("burnerSingle");								
			}
			
			while(rs4.next()){
				DoubleGov= rs4.getString("burnerDouble");
				
			}
			
			if(SingleDom==null)SingleDom="0";
			if(DoubleDom==null)DoubleDom="0";
			if(SingleGov==null)SingleGov="0";
			if(DoubleGov==null)DoubleGov="0";
			
			pcell=new PdfPCell(new Paragraph("Non-Metered Domestic", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(3);
			pcell.setBorder(1);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(SingleDom, ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(DoubleDom, ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);			
			
			pcell=new PdfPCell(new Paragraph("Non-Metered Domestic Govt", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(SingleGov, ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(DoubleGov, ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);			
			
			int total_single=0;
			int total_double=0;
			
		
			total_single= Integer.parseInt(SingleDom)+Integer.parseInt(SingleGov);
			
			
			total_double= Integer.parseInt(DoubleDom)+Integer.parseInt(DoubleGov);
			
			
			
			pcell=new PdfPCell(new Paragraph("Total", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(String.valueOf(total_single), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(String.valueOf(total_double), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setPadding(3);
			pcell.setBorder(Rectangle.BOX);
			burnerTable.addCell(pcell);	
			
			
			document.add(burnerTable);
		}catch (Exception e){e.printStackTrace();}
 		finally{try{
 			ConnectionManager.closeConnection(conn);
 			st1.close();st2.close();st3.close();st4.close();
 			rs1.close();rs2.close();rs3.close();rs4.close();
 		} catch (Exception e)
			{e.printStackTrace();}conn = null;st1= null;st2= null;st3= null;st4= null;
			rs1=null;rs2=null;rs3=null;rs4=null;		
 		}		
		
	}
	
	
	private static void category_wise(Document document) throws DocumentException
	{
		Connection conn = ConnectionManager.getConnection();
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
		
		pcell=new PdfPCell(new Paragraph("Monthly Sales "+category_name+" For the month Of "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year, ReportUtil.f11B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("", ReportUtil.f8B));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.NO_BORDER);
		headlineTable.addCell(pcell);
		document.add(headlineTable);
		
	
		
		BigDecimal totalActExpMin=BigDecimal.ZERO;
		BigDecimal totalActWithMin=BigDecimal.ZERO;
		BigDecimal totalBillunit=BigDecimal.ZERO;
		BigDecimal totalDiff=BigDecimal.ZERO;
		BigDecimal totalTotalCon=BigDecimal.ZERO;
		int total_SB=0;
		int total_DB=0;
		BigDecimal totalValueOfActCon=BigDecimal.ZERO;
		BigDecimal totalMinCharge=BigDecimal.ZERO;
		BigDecimal totalHHV=BigDecimal.ZERO;
		BigDecimal totalMeterRent=BigDecimal.ZERO;
		BigDecimal totalTotalBill=BigDecimal.ZERO;
		
		
		
	
		PdfPTable datatable1 =null;
	
		if(customer_category.equals("01")|| customer_category.equals("09")){
			
			datatable1 = new PdfPTable(10);
			datatable1.setWidthPercentage(100);
			datatable1.setWidths(new float[] {15,15,50,15,15,30,40,30,30,40});	
			
		}else{
			
			datatable1 = new PdfPTable(8);
			datatable1.setWidthPercentage(100);
			datatable1.setWidths(new float[] {15,25,70,30,40,30,30,40});
			
		}
		
		datatable1.setHeaderRows(3);
		
		
		pcell=new PdfPCell(new Paragraph("Sl.No",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer Code",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Customer Name",font3));
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		if(customer_category.equals("01")|| customer_category.equals("09")){
			
			pcell=new PdfPCell(new Paragraph("SB",font3));
			pcell.setRowspan(3);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("DB",font3));
			pcell.setRowspan(3);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable1.addCell(pcell);
		}
		
		pcell=new PdfPCell(new Paragraph("Monthly Gas Consumption m3",font3));
		//pcell.setColspan(5);
		pcell.setRowspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Amount in Taka",font3));
		pcell.setColspan(4);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
//
//		pcell=new PdfPCell(new Paragraph("Actual Consumption Except Minimum ChargeBills",font3));
//		pcell.setRowspan(2);
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("Minimum Consumption",font3));
//		pcell.setColspan(3);
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("Total Actual Consumption",font3));
//		pcell.setRowspan(2);
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("Rate",font3));
//		pcell.setRowspan(2);
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Actual Gas Bill",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Minimum Gas Bill",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("Meter Rent",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("NHV/HHV",font3));
//		pcell.setRowspan(2);
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
	
		pcell=new PdfPCell(new Paragraph("Total",font3));
		pcell.setRowspan(2);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("Actual Cons of Minimum Bills",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("Billing Unit",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("Difference",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
		
//		pcell=new PdfPCell(new Paragraph("01",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("02",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		
//		pcell=new PdfPCell(new Paragraph("03",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		
//		pcell=new PdfPCell(new Paragraph("04",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("05",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("06",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("07=(06-05)",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("08=(04+05)",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		
//		pcell=new PdfPCell(new Paragraph("rate",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		
//		pcell=new PdfPCell(new Paragraph("09",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("10)",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("11",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
//		pcell=new PdfPCell(new Paragraph("12",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);		
//		pcell=new PdfPCell(new Paragraph("13=(08+09+10+11+12)",font3));
//		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph("1",font3));
		datatable1.addCell(pcell);
		pcell=new PdfPCell(new Paragraph(Area.values()[Integer.valueOf(area)-1]+" AREA",font3));
		pcell.setColspan(13);
		datatable1.addCell(pcell);
		
		int cusCatCount=97;
		String previousType="GOVT";
		
		String currnertType="";
		try {
		
			String sql1="select MCC.CATEGORY_ID,decode(SR.IS_METER,1,substr(CATEGORY_NAME,1,length(CATEGORY_NAME)-7),0,substr(CATEGORY_NAME,1,length(CATEGORY_NAME)-7)||' (Non-Meter)') CATEGORY_NAME" +
						",MCC.CATEGORY_TYPE,SR.IS_METER,FULL_NAME, SR.customer_id CODE,";
			if(customer_category.equals("01")|| customer_category.equals("09")){
				   sql1+="getBurner(SR.customer_id) BURNER,";	
			}
			       sql1+=" ACTUAL_EXCEPT_MINIMUM, ACTUAL_WITH_MINIMUM, "+
						" BILLING_UNIT,  DIFFERENCE,TOTAL_ACTUAL_CONSUMPTION,RATE ,"+
						" VALUE_OF_ACTUAL_CONSUMPTION,MINIMUM_CHARGE, METER_RENT, "+
						" SURCHARGE_AMOUNT, HHV_NHV_AMOUNT, TOTAL_AMOUNT "+
						"from SALES_REPORT SR,CUSTOMER_CONNECTION conn,MST_CUSTOMER_CATEGORY mcc,CUSTOMER_PERSONAL_INFO cpi "+
						"where SR.customer_id=conn.customer_id and  SR.customer_id=cpi.customer_id and BILLING_MONTH=? and BILLING_YEAR=?"+
						"AND substr(SR.customer_id,3,2)=MCC.CATEGORY_ID and substr(SR.customer_id,1,2)=?"+ 
						"AND SUBSTR(SR.customer_id, 3, 2)=?"+
						"ORDER BY SR.customer_id";
    		PreparedStatement ps1=conn.prepareStatement(sql1);
    		ps1.setString(1, bill_month);
    		ps1.setString(2, bill_year);
    		ps1.setString(3, area);
    		ps1.setString(4, customer_category);
    		ResultSet rs1=ps1.executeQuery();    	
    		int count=1;
    		while(rs1.next())
        	{   
    			pcell=new PdfPCell(new Paragraph(String.valueOf(count++),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			pcell=new PdfPCell(new Paragraph(rs1.getString("CODE"),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
    			datatable1.addCell(pcell);
    			
    			pcell=new PdfPCell(new Paragraph(rs1.getString("FULL_NAME"),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
    			datatable1.addCell(pcell);
    			
    			if(customer_category.equals("01")|| customer_category.equals("09")){
    				String burner = rs1.getString("BURNER");
    				String[] brnrArray = burner.split("#");
    				total_SB+=Integer.parseInt(brnrArray[0]);
    				total_DB+=Integer.parseInt(brnrArray[1]);
    				
    				pcell=new PdfPCell(new Paragraph(brnrArray[0],font2));
        			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        			datatable1.addCell(pcell);
        			
        			pcell=new PdfPCell(new Paragraph(brnrArray[1],font2));
        			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        			datatable1.addCell(pcell);
    			}
    			
//    			totalActExpMin=totalActExpMin.add(rs1.getBigDecimal("ACTUAL_EXCEPT_MINIMUM")); // new solution	    			  		
//    			pcell=new PdfPCell(new Paragraph(consumption_format.format(rs1.getBigDecimal("ACTUAL_EXCEPT_MINIMUM")),font2));//new solution
//    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			datatable1.addCell(pcell);
//    			
//   			
//   			
//    			totalActWithMin=totalActWithMin.add(rs1.getFloat("ACTUAL_WITH_MINIMUM")==0?BigDecimal.ZERO:rs1.getBigDecimal("ACTUAL_WITH_MINIMUM"));
//    			pcell=new PdfPCell(new Paragraph(consumption_format.format(rs1.getFloat("ACTUAL_WITH_MINIMUM")==0?BigDecimal.ZERO:rs1.getBigDecimal("ACTUAL_WITH_MINIMUM")),font2));
//    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			datatable1.addCell(pcell);
    			
//    			
//    			totalBillunit=totalBillunit.add(rs1.getFloat("BILLING_UNIT")==0?BigDecimal.ZERO:rs1.getBigDecimal("BILLING_UNIT"));
//    			pcell=new PdfPCell(new Paragraph(consumption_format.format(rs1.getFloat("BILLING_UNIT")==0?BigDecimal.ZERO:rs1.getBigDecimal("BILLING_UNIT")),font2));
//    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			datatable1.addCell(pcell);
//    			
//    			
//    			totalDiff=totalDiff.add(rs1.getFloat("DIFFERENCE")==0?BigDecimal.ZERO:rs1.getBigDecimal("DIFFERENCE"));
//    			pcell=new PdfPCell(new Paragraph(consumption_format.format(rs1.getFloat("DIFFERENCE")==0?BigDecimal.ZERO:rs1.getBigDecimal("DIFFERENCE")),font2));
//    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			datatable1.addCell(pcell);
//    			
    			
    			totalTotalCon=totalTotalCon.add(rs1.getFloat("TOTAL_ACTUAL_CONSUMPTION")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_ACTUAL_CONSUMPTION"));
    			pcell=new PdfPCell(new Paragraph(consumption_format.format(rs1.getFloat("TOTAL_ACTUAL_CONSUMPTION")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_ACTUAL_CONSUMPTION")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
//    			pcell=new PdfPCell(new Paragraph(rs1.getString("RATE"),font2));
//    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			datatable1.addCell(pcell);
    			
    			
    			
    			totalValueOfActCon=totalValueOfActCon.add(rs1.getFloat("VALUE_OF_ACTUAL_CONSUMPTION")==0?BigDecimal.ZERO:rs1.getBigDecimal("VALUE_OF_ACTUAL_CONSUMPTION"));
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("VALUE_OF_ACTUAL_CONSUMPTION")==0?BigDecimal.ZERO:rs1.getBigDecimal("VALUE_OF_ACTUAL_CONSUMPTION")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			totalMinCharge=totalMinCharge.add(rs1.getFloat("MINIMUM_CHARGE")==0?BigDecimal.ZERO:rs1.getBigDecimal("MINIMUM_CHARGE"));
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("MINIMUM_CHARGE")==0?BigDecimal.ZERO:rs1.getBigDecimal("MINIMUM_CHARGE")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			
    			
    			
    			totalMeterRent=totalMeterRent.add(rs1.getFloat("METER_RENT")==0?BigDecimal.ZERO:rs1.getBigDecimal("METER_RENT"));
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("METER_RENT")==0?BigDecimal.ZERO:rs1.getBigDecimal("METER_RENT")),font2));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    			

    			
//    			totalHHV=totalHHV.add(rs1.getFloat("HHV_NHV_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("HHV_NHV_AMOUNT"));
//    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("HHV_NHV_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("HHV_NHV_AMOUNT")),font2));
//    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    			datatable1.addCell(pcell);
//    			
    			
    			
    			
    			totalTotalBill=totalTotalBill.add(rs1.getFloat("TOTAL_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_AMOUNT"));
    			pcell=new PdfPCell(new Paragraph(taka_format.format(rs1.getFloat("TOTAL_AMOUNT")==0?BigDecimal.ZERO:rs1.getBigDecimal("TOTAL_AMOUNT")),ReportUtil.f8B));
    			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    			datatable1.addCell(pcell);
    		
    		
    			
    			
        	}	
    		
		} catch (Exception e){e.printStackTrace();}
 		finally{try{ConnectionManager.closeConnection(conn);} catch (Exception e)
			{e.printStackTrace();}conn = null;}
	        
		
		if(customer_category.equals("01")|| customer_category.equals("09")){
			pcell=new PdfPCell(new Paragraph("Total:",font3));
			pcell.setColspan(3);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(String.valueOf(total_SB),ReportUtil.f9B));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(String.valueOf(total_DB),ReportUtil.f9B));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
		}else{
			pcell=new PdfPCell(new Paragraph("Total:",font3));
			pcell.setColspan(3);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
		}
    		
			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(totalActExpMin),ReportUtil.f9B));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(totalActWithMin),ReportUtil.f9B));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
//			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(totalBillunit),ReportUtil.f9B));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
			
//			pcell=new PdfPCell(new Paragraph(consumption_format.format(totalDiff),ReportUtil.f9B));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(consumption_format.format(totalTotalCon),ReportUtil.f9B));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
//			pcell=new PdfPCell(new Paragraph("",ReportUtil.f9B));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalValueOfActCon),ReportUtil.f9B));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalMinCharge),ReportUtil.f9B));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalMeterRent),ReportUtil.f9B));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
			
//			pcell=new PdfPCell(new Paragraph(taka_format.format(totalHHV),ReportUtil.f9B));
//			pcell.setColspan(1);
//			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			datatable1.addCell(pcell);
			
			
			
			pcell=new PdfPCell(new Paragraph(taka_format.format(totalTotalBill),ReportUtil.f9B));
			pcell.setColspan(1);
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			datatable1.addCell(pcell);
		
		
			document.add(datatable1);
			        
				
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