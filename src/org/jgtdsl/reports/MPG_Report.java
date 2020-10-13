package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.actions.bank.BankTransaction;
import org.jgtdsl.dto.CollectionDTO;
import org.jgtdsl.dto.TransactionDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.models.BankTransactionService;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MPG_Report extends BaseAction{
	private static final long serialVersionUID = 1L;
	public  ServletContext servlet;
	
	public String from_date;
	public String to_date;
	public String bank_name;
	
	Connection conn = ConnectionManager.getConnection();
	   
	    
	    static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
		static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
		static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
		static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
		static DecimalFormat  taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
		static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
		UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
		
		public String mpgCollDtl() throws Exception
		{
					
			String fileName="MPG_Collection_DTL.pdf";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document(PageSize.A4.rotate());
			document.setMargins(5,5,5,60);
			
			
			
			try{
				
				ReportFormat eEvent = new ReportFormat(getServletContext());
				
				PdfWriter.getInstance(document, baos).setPageEvent(eEvent);
				PdfPCell pcell=null;
				document.open();
				PdfPTable header= header(document);
				
				PdfPTable dtlTable= new PdfPTable(9);
				dtlTable.setWidthPercentage(98);
				dtlTable.setWidths(new float[] {
						10,25,20,30,20,20,20,30,30
					});
				
				
				BankTransactionService bt= new BankTransactionService();
				ArrayList<TransactionDTO> mpg_coll_dtl= new ArrayList<TransactionDTO>();
				mpg_coll_dtl= bt.getMPGTransactionList(from_date, to_date,bank_name);
				
				pcell=new PdfPCell(new Paragraph(" ", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setColspan(9);
				pcell.setBorder(0);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Serial", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Collection Date", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);				
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Customer ID", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Bills", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Gas bill", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Surcharge", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Total", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Bank", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Trans ID", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				double t_gas_bill=0;
				double t_surcharge=0;
				double t_grand_total=0;
				
				
				for(TransactionDTO x: mpg_coll_dtl){
					
					pcell=new PdfPCell(new Paragraph(x.getSl(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getTrans_date(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getCustomer_id(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getParticulars(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);	
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getGas_bill()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getSurcharge()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getDebit()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getBank_name(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getTrans_id(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);	
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					pcell.setPadding(2);
					dtlTable.addCell(pcell);
					
					t_gas_bill+=x.getGas_bill();
					t_surcharge+= x.getSurcharge();
					t_grand_total+=x.getDebit();
					
				}
				
				pcell=new PdfPCell(new Paragraph("Total", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(2);
				pcell.setColspan(4);
				dtlTable.addCell(pcell);
				
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_gas_bill), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_surcharge), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);
				dtlTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Grand Total: "+taka_format.format(t_grand_total), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setPadding(2);
				pcell.setColspan(3);
				dtlTable.addCell(pcell);
				
				
				
				
				
				
				dtlTable.setHeaderRows(2);
				document.add(header);
				document.add(dtlTable);
				document.close();
				ReportUtil rptUtil = new ReportUtil();
				rptUtil.downloadPdf(baos, getResponse(),fileName);
				document=null;
			    
			}catch(Exception e){e.printStackTrace();}
			
			return null;		
		}
		
		public String mpgCollsum() throws Exception
		{
					
			String fileName="MPG_Collection_Summary.pdf";			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document(PageSize.A4.rotate());
			document.setMargins(5,5,5,60);
			
			
			try{
				PdfPCell pcell= null;
				
				ReportFormat eEvent = new ReportFormat(getServletContext());
				
				PdfWriter.getInstance(document, baos).setPageEvent(eEvent);
				
				document.open();
				PdfPTable sumTable= new PdfPTable(6);
				sumTable.setWidthPercentage(98);
				
				BankTransactionService bt= new BankTransactionService();
				ArrayList<TransactionDTO> mpg_coll_sum= new ArrayList<TransactionDTO>();
				mpg_coll_sum= bt.getMPGTransactionDateWise(from_date, to_date,bank_name);
				
				pcell=new PdfPCell(new Paragraph(" ", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setColspan(6);
				pcell.setBorder(0);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Serial", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Collection Date", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Gas bill", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Surcharge", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Total", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Count", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				double t_gas_bill=0;
				double t_surcharge=0;
				double t_grand_total=0;
				int t_count=0;
				
				for(TransactionDTO x:mpg_coll_sum){
					pcell=new PdfPCell(new Paragraph(x.getSl(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getTrans_date(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getGas_bill()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getSurcharge()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getDebit()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(String.valueOf(x.getCount()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					t_gas_bill+=x.getGas_bill();
					t_surcharge+= x.getSurcharge();
					t_grand_total+=x.getDebit();
					t_count+= x.getCount();
				}
				
				pcell=new PdfPCell(new Paragraph("Total", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(2);
				pcell.setColspan(2);
				sumTable.addCell(pcell);
				
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_gas_bill), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_surcharge), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_grand_total), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);				
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(String.valueOf(t_count), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);				
				sumTable.addCell(pcell);
				
				sumTable.setHeaderRows(2);
				PdfPTable header= header(document);
				
				
				document.add(header);
				document.add(sumTable);
				document.close();
				ReportUtil rptUtil = new ReportUtil();
				rptUtil.downloadPdf(baos, getResponse(),fileName);
				document=null;
			    
			}catch(Exception e){e.printStackTrace();}
			
			return null;		
		}
		
		
		public String areawisempgColl() throws Exception{
			String fileName="Area_Wise_MPG_Collection.pdf";			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document(PageSize.A4.rotate());
			document.setMargins(5,5,5,60);
			
			try{
				PdfPCell pcell= null;
				
				ReportFormat eEvent = new ReportFormat(getServletContext());
				
				PdfWriter.getInstance(document, baos).setPageEvent(eEvent);
				
				document.open();
				PdfPTable sumTable= new PdfPTable(6);
				sumTable.setWidthPercentage(98);
				
				BankTransactionService bt= new BankTransactionService();
				ArrayList<TransactionDTO> areawise_mpg_coll_sum= new ArrayList<TransactionDTO>();
				areawise_mpg_coll_sum= bt.getMPGTransactionAreaWise(from_date, to_date,bank_name);
				
				pcell=new PdfPCell(new Paragraph(" ", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setColspan(6);
				pcell.setBorder(0);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Serial", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Area", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Gas bill", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Surcharge", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Total", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Count", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(3);
				sumTable.addCell(pcell);
				
				double t_gas_bill=0;
				double t_surcharge=0;
				double t_grand_total=0;
				int t_count=0;
				
				for(TransactionDTO x:areawise_mpg_coll_sum){
					pcell=new PdfPCell(new Paragraph(x.getSl(), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getArea_name()+" ("+x.getArea_id()+")", ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getGas_bill()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getSurcharge()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(taka_format.format(x.getDebit()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(String.valueOf(x.getCount()), ReportUtil.f8));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
					pcell.setPadding(3);
					sumTable.addCell(pcell);
					
					t_gas_bill+=x.getGas_bill();
					t_surcharge+= x.getSurcharge();
					t_grand_total+=x.getDebit();
					t_count+= x.getCount();
				}
				
				pcell=new PdfPCell(new Paragraph("Total", ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);	
				pcell.setPadding(2);
				pcell.setColspan(2);
				sumTable.addCell(pcell);
				
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_gas_bill), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_surcharge), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(taka_format.format(t_grand_total), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				pcell.setPadding(2);				
				sumTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(String.valueOf(t_count), ReportUtil.f8B));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setPadding(2);				
				sumTable.addCell(pcell);
				
				sumTable.setHeaderRows(2);
				PdfPTable header= header(document);
				
				
				document.add(header);
				document.add(sumTable);
				document.close();
				ReportUtil rptUtil = new ReportUtil();
				rptUtil.downloadPdf(baos, getResponse(),fileName);
				document=null;
			    
			}catch(Exception e){e.printStackTrace();}
			
			
			return null;
		}
		
		public PdfPTable header(Document document){
			PdfPCell pcell=null;
			PdfPTable headerTable = null;
			try{
				headerTable = new PdfPTable(3);
				   
				
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
				
				pcell=new PdfPCell(new Paragraph("MPG Collection Report from "+from_date+" to "+to_date, font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(0);
				mTable.addCell(pcell);
						
				pcell=new PdfPCell(mTable);
				pcell.setBorder(0);
				headerTable.addCell(pcell);
						
				pcell = new PdfPCell(new Paragraph(""));
				pcell.setBorder(0);
				headerTable.addCell(pcell);
				
				String realPath = servlet.getRealPath("/resources/images/logo/JG.png");  // image path
				   Image img = Image.getInstance(realPath);
				      
				             //img.scaleToFit(10f, 200f);
				             //img.scalePercent(200f);
				            img.scaleAbsolute(32f, 35f);
				            //img.setAbsolutePosition(145f, 780f);  
				             img.setAbsolutePosition(200f, 550f);  // rotate
				            
				         document.add(img);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			return headerTable;			
			
		}
		
		public ServletContext getServlet() {
			return servlet;
		}

		public void setServletContext(ServletContext servlet) {
			this.servlet = servlet;
		}

		public String getFrom_date() {
			return from_date;
		}

		public void setFrom_date(String from_date) {
			this.from_date = from_date;
		}

		public String getTo_date() {
			return to_date;
		}

		public void setTo_date(String to_date) {
			this.to_date = to_date;
		}

		public String getBank_name() {
			return bank_name;
		}

		public void setBank_name(String bank_name) {
			this.bank_name = bank_name;
		}
		
		

}
