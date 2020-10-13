package org.jgtdsl.reports;



import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.BalancingReportDTO;
import org.jgtdsl.dto.MeterReadingReportDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.models.AreaService;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
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
    
	private  String year;
    
  
    
    AreaService aa=new AreaService();
	
	public ServletContext servlet;
	




	ServletContext servletContext = null;

	static Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	static Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	Font red = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.RED);
	DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	DecimalFormat consumption_format = new DecimalFormat("##########0.000");
	static DecimalFormat number_format = new DecimalFormat("###########0.0");
	UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	Document document = new Document(PageSize.LEGAL.rotate());
	
	
	public String execute() throws Exception {
		area= new String(loggedInUser.getArea_id());
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
								
								Chunk chunk1 = new Chunk("Regional Distribution Office :"+String.valueOf(String.valueOf(Area.values()[Integer.valueOf(area)-1])),font2);
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
								datatable1.setWidths(new float[] {10,15,20,10,15,15,15,15,15,15,20});
								
								
								
								
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
								/////////////////////////////////////////////////////
			
								
								
								ArrayList<MeterReadingReportDTO> category= new ArrayList<MeterReadingReportDTO>();
								
								category= getcategory();
								int sl=1;
								
								for(MeterReadingReportDTO x: category){
									
									for(int i=0; i<=1;i++){										
										
										pcell = new PdfPCell(new Paragraph((String.valueOf(sl)),ReportUtil.f11));
										pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
										pcell.setColspan(1);
										datatable1.addCell(pcell);
										
										pcell = new PdfPCell(new Paragraph((x.getCustomer_category_name()),ReportUtil.f11));
										pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
										pcell.setColspan(1);
										datatable1.addCell(pcell);
										
										ArrayList<BalancingReportDTO> getdata= new ArrayList<BalancingReportDTO>();
										getdata= getbalancingReport2(i, x.getCategory_id(),area);
										
										for(BalancingReportDTO y:getdata){
											
											if(i==0){
											
												pcell = new PdfPCell(new Paragraph((y.getFlag()),red));
												pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
												pcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
												pcell.setRowspan(1);
												datatable1.addCell(pcell);
											}else{
												pcell = new PdfPCell(new Paragraph((y.getFlag()),ReportUtil.f11));
												pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
												pcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
												pcell.setRowspan(1);
												datatable1.addCell(pcell);
											}
											
											
											pcell = new PdfPCell(new Paragraph((y.getCustomer_count()),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
											pcell.setColspan(1);
											datatable1.addCell(pcell);
											
											pcell = new PdfPCell(new Paragraph((String.valueOf(y.getsBurner())),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
											pcell.setColspan(1);
											datatable1.addCell(pcell);
											
											pcell = new PdfPCell(new Paragraph((String.valueOf(y.getdBurner())),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
											pcell.setColspan(1);
											datatable1.addCell(pcell);
											
											pcell = new PdfPCell(new Paragraph((String.valueOf(0)),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
											pcell.setColspan(1);
											datatable1.addCell(pcell);
											
											pcell = new PdfPCell(new Paragraph((y.getMax_load()),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											pcell.setColspan(1);
											datatable1.addCell(pcell);
											
											pcell = new PdfPCell(new Paragraph(taka_format.format(y.getCash()),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											pcell.setColspan(1);
											datatable1.addCell(pcell);
											
											pcell = new PdfPCell(new Paragraph(taka_format.format(y.getBg()),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											pcell.setColspan(1);
											datatable1.addCell(pcell);
											
											pcell = new PdfPCell(new Paragraph(taka_format.format(y.getBalance()),ReportUtil.f11));
											pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											pcell.setColspan(1);
											datatable1.addCell(pcell);										
											
											sl++;
										}
										
									}
									
									
								}
								datatable1.setHeaderRows(3);
								
								
								
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


	Statement st=null;					
	ResultSet rs=null;
	Connection conn = ConnectionManager.getConnection();

	public  ArrayList<BalancingReportDTO> getbalancingReport2(int status, String cat, String area)
	{
		
		ArrayList<BalancingReportDTO> nonMeterInfo=null;			
		nonMeterInfo= new ArrayList<BalancingReportDTO>();
		String sql= "";
		
		String max_l="0";
		String sBurner="0";
		String dBurner="0";
		String stat= "";
		
		if(status==0)stat="'DISCONNECTED'";
		if(status==1)stat="'CONNECTED'";
		
		if(cat.equalsIgnoreCase("01")||cat.equalsIgnoreCase("09")){			
			sBurner= "SUM (GETBURNERBYINDX (mv.customer_id, 's'))";
			dBurner= "SUM (GETBURNERBYINDX (mv.customer_id, 'd'))";
		}else{
			max_l= " SUM (MAX_LOAD)";
		}
		
		sql= 
				"  SELECT COUNT (*) COUNT, " +
				stat+ " status, " +
				max_l+ " max_load, " +
				"         SUM (brh.balance) balance, " +
				"         SUM (getCashOrBG (mv.customer_id, 'cash')) cash, " +
				"         SUM (getCashOrBG (mv.customer_id, 'bg')) bg, " +
				sBurner+ " single, " +
				dBurner+ " double " +
				"    FROM MVIEW_CUSTOMER_INFO mv, BALANCING_REPORT_HELPER brh " +
				"   WHERE     brh.status =  " + status+
				"         AND AREA_ID =  '" + area +"' "+
				"         AND BRH.CUSTOMER_ID = MV.CUSTOMER_ID " +
				"         AND MV.CATEGORY_ID = '" +cat+"' "+
				"         AND year =  "+ year;

		//System.out.println(sql);
		
		try {
	
			 conn = ConnectionManager.getConnection();
			 st=conn.createStatement();						
			 rs=st.executeQuery(sql);
			 
			 while(rs.next()){
				// if(Integer.parseInt(rs.getString("COUNT"))>0){
				 
					 BalancingReportDTO balancingReportDTO=new BalancingReportDTO();
					 balancingReportDTO.setFlag(rs.getString("STATUS"));
					 balancingReportDTO.setCustomer_count(rs.getString("COUNT"));
					 balancingReportDTO.setsBurner(rs.getDouble("single"));
					 balancingReportDTO.setdBurner(rs.getDouble("double"));
					 balancingReportDTO.setoBurner(0.0d);
					 balancingReportDTO.setCash(rs.getDouble("cash"));
					 balancingReportDTO.setBg(rs.getDouble("bg"));
					 balancingReportDTO.setBalance(rs.getLong("BALANCE"));
					 balancingReportDTO.setMax_load(rs.getString("max_load"));
					 
					 nonMeterInfo.add(balancingReportDTO);
				 //} 
				 
			 }
			
			}catch(Exception e){
				e.printStackTrace();
			}
		finally {
			try {
				st.close();
				conn.close();
				ConnectionManager.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
			conn = null;
	}
		
	return nonMeterInfo;
	}

public ArrayList<MeterReadingReportDTO> getcategory(){
		

		ArrayList<MeterReadingReportDTO> catInfo=null;			
		catInfo= new ArrayList<MeterReadingReportDTO>();
		
		String sql= " select * from MST_CUSTOMER_CATEGORY order by CATEGORY_ID ";
		
		try {

			 conn = ConnectionManager.getConnection();
			 st=conn.createStatement();						
			 rs=st.executeQuery(sql);
			 
			 while(rs.next()){
				 
				 MeterReadingReportDTO meterReadingReportDTO=new MeterReadingReportDTO();
				 meterReadingReportDTO.setCategory_id(rs.getString("CATEGORY_ID"));
				 meterReadingReportDTO.setCustomer_category_name(rs.getString("CATEGORY_NAME"));
				 
				 catInfo.add(meterReadingReportDTO);			
				 
			 }
			
			}catch(Exception e){
				e.printStackTrace();
			}
		finally {
			try {
				st.close();
				conn.close();
				ConnectionManager.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
			conn = null;
		}
			
		return catInfo;
		
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
	
	
}
