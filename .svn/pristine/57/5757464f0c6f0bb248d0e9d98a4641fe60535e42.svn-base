package org.jgtdsl.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.jgtdsl.dto.BurnerQntChangeDTO;
import org.jgtdsl.dto.CustomerApplianceDTO;
import org.jgtdsl.dto.CustomerDTO;
import org.jgtdsl.dto.CustomerLedgerDTO;
import org.jgtdsl.dto.CustomerMeterDTO;
import org.jgtdsl.dto.DepositLedgerDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.ConnectionStatus;
import org.jgtdsl.models.BurnerQntChangeService;
import org.jgtdsl.models.CustomerService;
import org.jgtdsl.models.LedgerService;
import org.jgtdsl.models.MeterService;
import org.jgtdsl.reports.DefaulterCCertificate;
import org.jgtdsl.reports.ReportFormat;
import org.jgtdsl.reports.ReportUtil;

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

public class SecurityLedgerInfoReportIndividual extends BaseAction {
	
	private static final long serialVersionUID = 8854240739341830184L;
	private ServletContext servlet;
	
	
	private String area_id;
	private String customer_id;	
	public static int textDiff = 597;
	ArrayList<DepositLedgerDTO> securityLedger= new ArrayList<DepositLedgerDTO>();
	ConnectionStatus connenum;
	MeterService ms=new MeterService();
	
	LedgerService ls= new LedgerService();
	CustomerDTO customerInfo = new CustomerDTO();
	CustomerService cs= new CustomerService();

	public String execute() throws Exception {
		Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
		Font font2 = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
		Font font4 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
		UserDTO loggedInUser = (UserDTO) session.get("user");
		if ((area_id == null || area_id.equalsIgnoreCase(""))
				&& loggedInUser != null)
			area_id = loggedInUser.getArea_id();

		String fileName = "SECURITY_LEDGER-" + customer_id + ".pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4.rotate());
		try {
			ReportFormat Event = new ReportFormat(getServletContext());
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			writer.setPageEvent(Event);
			PdfPCell pcell = null;

			document.open();
			String realPath = servlet
					.getRealPath("/resources/images/logo/JG.png"); // image path
			Image img = Image.getInstance(realPath);

			// img.scaleToFit(10f, 200f);
			// img.scalePercent(200f);
			img.scaleAbsolute(30f, 34f);
			// img.setAbsolutePosition(145f, 780f);
			img.setAbsolutePosition(260f, 520f); // rotate

			document.add(img);
			
			CustomerDTO customer = cs.getCustomerInfo(customer_id);

			PdfPTable mTable = new PdfPTable(1);
			mTable.setWidthPercentage(100);
			pcell = new PdfPCell(new Paragraph(
					"JALALABAD GAS T & D SYSTEM LIMITED", ReportUtil.f11B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			

			pcell = new PdfPCell(new Paragraph("(A COMPANY OF PETROBANGLA)",
					ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);			

			Chunk chunk1 = new Chunk("Regional Distribution: ", font2);
			Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer
					.valueOf(getArea_id()) - 1]), ReportUtil.f8B);
			Paragraph p = new Paragraph();
			p.add(chunk1);
			p.add(chunk2);
			pcell = new PdfPCell(p);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			
			pcell = new PdfPCell(new Paragraph(
					"ledger for: "+customer.getConnectionInfo().getIsMetered_name()+" Customer", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			
			
			
			///////////customerTable//////////
			
				PdfPTable customerinfo = new PdfPTable(2);
				customerinfo.setWidths(new float[]{15,85});
				customerinfo.setWidthPercentage(97);
				
				DefaulterCCertificate dcc= new DefaulterCCertificate();
				customerInfo= dcc.getCustomerInfo(this.customer_id);
				
				pcell=new PdfPCell(new Paragraph("  ",font3));
				pcell.setPadding(5);
				pcell.setColspan(2);
				pcell.setBorder(0);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				customerinfo.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Customer Code: ",font3));
				pcell.setPadding(5);
				pcell.setBorder(0);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				customerinfo.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(this.customer_id,font2));
				pcell.setPadding(5);
				pcell.setBorder(0);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				customerinfo.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Customer Name: ",font3));
				pcell.setPadding(5);
				pcell.setBorder(0);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				customerinfo.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(customerInfo.getCustomer_name(),font3));
				pcell.setPadding(5);
				pcell.setBorder(0);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				customerinfo.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Customer Address: ",font3));
				pcell.setPadding(5);
				pcell.setBorder(0);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				customerinfo.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(customerInfo.getAddress(),font3));
				pcell.setPadding(5);
				pcell.setBorder(0);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				customerinfo.addCell(pcell);	
				
			//////////customerTable ends/////////
				int serial=1;
				int total=0;
				int total_cash_bank=0;
				int total_bg=0;
				
				PdfPTable ledgerTable = new PdfPTable(6);
				
				ledgerTable.setWidthPercentage(97);	
				ledgerTable.setWidths(new float[]{10,20,20,20,20,20});
				
				pcell= new PdfPCell(new Paragraph(" ", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setColspan(6);
				pcell.setBorder(0);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph("Sl", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph("Date", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);	
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph("Amount", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph("Deposit Type", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);	
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph("Bank Expire", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				pcell= new PdfPCell(new Paragraph("Deposit or Withdraw", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);					
				
				this.securityLedger= ls.getDepositLedger(this.customer_id);					
				
				for(DepositLedgerDTO x: securityLedger){
				 
					pcell= new PdfPCell(new Paragraph(String.valueOf(serial), font3));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
					pcell.setRowspan(1);
					pcell.setPadding(5);
					ledgerTable.addCell(pcell);
					
					pcell= new PdfPCell(new Paragraph(x.getDeposit_date(), font3));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
					pcell.setRowspan(1);
					pcell.setPadding(5);
					ledgerTable.addCell(pcell);
					
					pcell= new PdfPCell(new Paragraph(x.getDebit_amount(), font3));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
					pcell.setRowspan(1);
					pcell.setPadding(5);
					ledgerTable.addCell(pcell);
					
					pcell= new PdfPCell(new Paragraph(x.getDeposit_type(), font3));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
					pcell.setRowspan(1);
					pcell.setPadding(5);
					ledgerTable.addCell(pcell);
					
					pcell= new PdfPCell(new Paragraph(x.getExpire_date(), font3));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
					pcell.setRowspan(1);	
					pcell.setPadding(5);
					ledgerTable.addCell(pcell);
					
					pcell= new PdfPCell(new Paragraph(x.getDescription(), font3));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
					pcell.setRowspan(1);
					pcell.setPadding(5);
					ledgerTable.addCell(pcell);						
					
					serial++;
					total+=Integer.parseInt(x.getDebit_amount());
								
					
					if(x.getDeposit_type().equalsIgnoreCase("CASH BANK")){
						total_cash_bank+= Integer.parseInt(x.getDebit_amount());			
					}
					if(x.getDeposit_type().equalsIgnoreCase("BANK GUARANTEE")){
						total_bg+= Integer.parseInt(x.getDebit_amount());			
					}
					
					
				}
				
				pcell= new PdfPCell(new Paragraph("Total Cash Bank: "+String.valueOf(total_cash_bank)+" Tk" , font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setColspan(2);
				pcell.setBorder(0);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				
				
				pcell= new PdfPCell(new Paragraph("Total BG: "+String.valueOf(total_bg)+" Tk", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setBorder(0);
				pcell.setColspan(2);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
				
				
				pcell= new PdfPCell(new Paragraph("Total: "+String.valueOf(total)+" Tk", font2));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setRowspan(1);
				pcell.setBorder(0);
				pcell.setColspan(2);
				pcell.setPadding(5);
				ledgerTable.addCell(pcell);
	
			document.add(mTable);
			document.add(customerinfo);
			document.add(ledgerTable);
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(), fileName);
			document = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	
	public ServletContext getServlet() {
		return servlet;
	}

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}

	

}
