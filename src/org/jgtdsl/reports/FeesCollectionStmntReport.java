package org.jgtdsl.reports;


import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import org.jgtdsl.dto.CustomerListDTO;
import org.jgtdsl.dto.CustomerPersonalDTO;
import org.jgtdsl.dto.GasPurchaseDTO;
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



/**
 * Action class to show fees collection report
 * @author Avi
 * Modified on 5 August 2019
 */
public class FeesCollectionStmntReport extends BaseAction {
	private static final long serialVersionUID = 1L;
	public  ServletContext servlet;
	ArrayList<CollectionDTO> FeesCollectionInfo=new ArrayList<CollectionDTO>();
	
	Connection conn = ConnectionManager.getConnection();
	    private  String report_for; 
		
		private   String area;
		private  String bill_month;
	    private  String bill_year;
	    
	    static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
		static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
		static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
		static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
		static DecimalFormat  taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
		static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
		UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");	
	public String execute() throws Exception
	{
				
		String fileName="fees_Collection_Statement.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		document.setMargins(20,20,50,80);
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
			
			if(report_for != null){
			generatePdfFeescollection(document);
			
			}
			else{	
				generatePdfSummary(document);

			}
			
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(),fileName);
			document=null;
		    
		}catch(Exception e){e.printStackTrace();}
		
		return null;		
	}
	
	private void generatePdfFeescollection(Document document) throws DocumentException{
		
		document.setMargins(0,0,70,72);
		PdfPTable headLinetable = null;
		PdfPCell pcell=null;
		headLinetable = new PdfPTable(3);
		headLinetable.setWidthPercentage(100);
		headLinetable.setWidths(new float[]{10,80,10});
		
		pcell = new PdfPCell(new Paragraph("",ReportUtil.f8));
		pcell.setBorder(0);
		headLinetable.addCell(pcell);
		
		String reportSign="Fees Collection Info for the month of "+Month.values()[Integer.valueOf(bill_month)-1]+"'"+bill_year;		
		
		pcell=new PdfPCell(new Paragraph(reportSign,ReportUtil.f11B));
		//pcell.setMinimumHeight(10f);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setBorder(0);
		headLinetable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(""));
		pcell.setBorder(0);
		headLinetable.addCell(pcell);
		
		document.add(headLinetable);
		
		PdfPTable datatable1 = new PdfPTable(7);
		
		datatable1.setWidthPercentage(99);
		datatable1.setWidths(new float[] {10,15,20,20,20,20,15});
		
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
		
		pcell=new PdfPCell(new Paragraph("Full Name",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Deposit Date",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Bank Branch",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Type",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Total Deposit",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		FeesCollectionInfo=getCollectionList();
		int i=1;
		int total_deposit=0;
		for(CollectionDTO x: FeesCollectionInfo){
			
			pcell= new PdfPCell(new Paragraph(String.valueOf(i), font2));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			pcell.setPadding(5);			
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

			
			pcell=new PdfPCell(new Paragraph(x.getDepoDate(),font2));		
			pcell.setPadding(3);					
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(x.getBranch_name(),font2));		
			pcell.setPadding(3);					
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			
			pcell=new PdfPCell(new Paragraph(x.getType(),font2));		
			pcell.setPadding(3);					
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(x.getTotal_deposit(),font2));		
			pcell.setPadding(3);					
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			datatable1.addCell(pcell);
			total_deposit+= Integer.parseInt(x.getTotal_deposit());
			i++;
			
		}
		pcell= new PdfPCell(new Paragraph("Total: ", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		pcell.setColspan(6);
		datatable1.addCell(pcell);
		
		pcell= new PdfPCell(new Paragraph(taka_format.format(total_deposit), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
		pcell.setPadding(6);
		datatable1.addCell(pcell);
		
		document.add(datatable1);
		
	
	}
	private void generatePdfSummary(Document document) throws DocumentException, SQLException{
		
	
		PdfPTable dataTable = new PdfPTable(3);
		dataTable.setWidthPercentage(70);
		dataTable.setWidths(new float[]{(float) 0.15, (float) 1, (float) 0.5});
		
		PdfPCell pcell=null;
		
		pcell= new PdfPCell(new Paragraph(" "));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(3);
		pcell.setBorder(Rectangle.NO_BORDER);
		dataTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("Monthly Fees Collection",font1));
		pcell.setColspan(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setBorder(Rectangle.BOX);
		pcell.setPadding(5);
		dataTable.addCell(pcell);
		
		
		pcell= new PdfPCell(new Paragraph(" "));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setColspan(3);
		pcell.setBorder(Rectangle.NO_BORDER);
		dataTable.addCell(pcell);
		
		
		pcell= new PdfPCell(new Paragraph("Collection Month: "+Month.values()[Integer.valueOf(bill_month)-1]+", "+bill_year,font1));
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setColspan(3);
		pcell.setPadding(5);
		pcell.setBorder(Rectangle.NO_BORDER);
		dataTable.addCell(pcell);
		
		ArrayList<TransactionDTO> fs= new ArrayList<TransactionDTO>();
		
		String sql="SELECT TYPE_NAME_ENG,sum(TOTAL_DEPOSIT)"
				+" FROM mst_deposit md,mst_deposit_types mdt"
				+" where MD.DEPOSIT_PURPOSE=MDT.TYPE_ID"
				+" and substr(CUSTOMER_ID,1,2)= '"+getArea()+"' "
				+" and to_char(DEPOSIT_DATE,'mm')= "+getBill_month()
				+" and to_char(DEPOSIT_DATE,'yyyy')= "+ getBill_year()
				+" and MD.DEPOSIT_PURPOSE <> '01'  "
				+" group by TYPE_NAME_ENG"
				+" order by TYPE_NAME_ENG";

				Connection conn = ConnectionManager.getConnection();
				Statement st=conn.createStatement();
								
				ResultSet rs=st.executeQuery(sql);	
				
				while(rs.next()){
					TransactionDTO fessDTO=new TransactionDTO();
					
					fessDTO.setParticulars(rs.getString("TYPE_NAME_ENG"));
					fessDTO.setFees(rs.getDouble("SUM(TOTAL_DEPOSIT)"));
					
					fs.add(fessDTO);
				}
				
				int listsize= fs.size();			
				int total= 0;
				
				for(int i=0; i<listsize; i++){
					pcell = new PdfPCell(new Paragraph(String.valueOf(i+1),font1nb));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setColspan(1);
					pcell.setPadding(5);
					dataTable.addCell(pcell);
					
					pcell = new PdfPCell(new Paragraph(fs.get(i).getParticulars(),font1nb));
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					pcell.setColspan(1);
					pcell.setPadding(5);
					dataTable.addCell(pcell);
							
					pcell = new PdfPCell(new Paragraph(taka_format.format(fs.get(i).getFees()),font1nb));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pcell.setColspan(1);
					pcell.setPadding(5);
					dataTable.addCell(pcell);
					total+=fs.get(i).getFees();
					
	}
				pcell= new PdfPCell(new Paragraph("Total: ", font3));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(6);
				pcell.setColspan(2);
				dataTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph(taka_format.format(total), font3));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(6);
				dataTable.addCell(pcell);
				
				document.add(dataTable);		
	}
	
	
	
	private ArrayList<CollectionDTO> getCollectionList(){
		
		PreparedStatement stmt = null;
		ArrayList<CollectionDTO> CollectionInfoList= new ArrayList<CollectionDTO>();
		try{
			String FeCollectionsql=" SELECT MD.CUSTOMER_ID, "
					+ "         FULL_NAME, "
					+ "         TO_CHAR (MD.DEPOSIT_DATE) DEPOSIT_DATE, "
					+ "         getbankbranch (BRANCH_ID) bank_branch, "
					+ "         TYPE_NAME_ENG, "
					+ "         TOTAL_DEPOSIT "
					+ "    FROM mst_deposit md, mst_deposit_types mdt, mview_customer_info mci "
					+ "   WHERE     MD.DEPOSIT_PURPOSE = MDT.TYPE_ID "
					+ "         AND SUBSTR (md.CUSTOMER_ID, 1, 2) =  "+ loggedInUser.getArea_id()
					+ "         AND MCI.CUSTOMER_ID = MD.CUSTOMER_ID "
					+ "         AND TO_CHAR (DEPOSIT_DATE, 'mm') =  "+ getBill_month()
					+ "         AND TO_CHAR (DEPOSIT_DATE, 'yyyy') =  "+getBill_year()
					+ "         AND MD.DEPOSIT_PURPOSE <> '01' "
					+ " ORDER BY DEPOSIT_DATE ASC ";
			stmt=conn.prepareStatement(FeCollectionsql);
			ResultSet r=stmt.executeQuery();
			while(r.next()){
				CollectionDTO collectiondto= new CollectionDTO();
				collectiondto.setCustomer_id(r.getString("CUSTOMER_ID"));
				collectiondto.setCustomer_name(r.getString("FULL_NAME"));
				collectiondto.setDepoDate(r.getString("DEPOSIT_DATE"));
				collectiondto.setBranch_name(r.getString("BANK_BRANCH"));
				collectiondto.setType(r.getString("TYPE_NAME_ENG"));
				collectiondto.setTotal_deposit(r.getString("TOTAL_DEPOSIT"));
				CollectionInfoList.add(collectiondto);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				ConnectionManager.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stmt = null;
			conn = null;
		}

		return CollectionInfoList;
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


